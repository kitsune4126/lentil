package com.mamezou.lentil.model ;

import java.io.IOException ;
import java.nio.file.Path ;
import java.nio.file.Paths ;
import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.HashSet ;
import java.util.Map ;
import java.util.Set ;
import java.util.UUID ;

import com.fasterxml.jackson.annotation.JsonIgnore ;
import com.fasterxml.jackson.annotation.JsonProperty ;
import com.fasterxml.jackson.databind.ObjectMapper ;

/**
 * ファイル・ベースなモデル・リポジトリの具象クラス。
 *
 * @author kitsune@mamezou.com
 */
public class FileBasedModelRepository implements ModelRepository {

    // @Category constant definitions

    /**
     * 各モデル要素が保存されるファイルの拡張子。
     */
    private static final String MODEL_ELEMENT_FILE_EXTENSION = ".json" ;

    /**
     * リポジトリ自身の情報を保存しているファイルの名前。
     */
    private static final String REPOSITORY_FILE_NAME = "Repository.json" ;

    // @Category instance variables

    /**
     * リポジトリのトップ・フォルダのパス。
     */
    @JsonIgnore
    private Path basePath = null ;

    /**
     * このリポジトリの説明文。
     */
    @JsonProperty
    private String description = "" ;

    /**
     * メモリ上のモデル要素の内容とモデル・ファイルの内容とにズレがある(モデル・ファイルの上書き、または、新規生成が必要な)モデル要素の集合。
     */
    @JsonIgnore
    private Set< ElementBody > dirtyElements = new HashSet< ElementBody >() ;

    /**
     * オン・メモリでキャッシュしているモデル要素本体のマップ。
     */
    @JsonIgnore
    private final Map< UUID , ElementBody > elementMap = new HashMap< UUID , ElementBody >() ;

    /**
     * このリポジトリ自身が持つ属性のいずれかが、ファイルからロードされた時点と変更されているか否か(つまり、セーブが必要か否か)。
     */
    @JsonIgnore
    private boolean isDirty = false ;

    /**
     * メタ・リポジトリ(このリポジトリに含まれるモデル要素群の一段メタな型定義をしているモデル要素を含むリポジトリ)。
     */
    private final ModelRepository metaRepository ;

    /**
     * リポジトリ名。
     */
    @JsonProperty
    private String name = "" ;

    /**
     * メモリ上のリポジトリからは既に削除されているものの、まだモデル・ファイルの物理的な削除が行われていないモデル要素の集合。
     */
    @JsonIgnore
    private Set< ElementBody > removedElements = new HashSet< ElementBody >() ;

    /**
     * このリポジトリに含まれる最上位要素のリスト。
     */
    private ArrayList< ElementReference > topElements = new ArrayList< ElementReference >() ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param basePath リポジトリのトップ・フォルダのパス。
     * @param metaRepository 生成されるリポジトリの一段メタなリポジトリ。{@code null} を渡すとメタ・リポジトリとして自身を参照する自己完結型のリポジトリになる。
     * @throws IllegalArgumentException {@code basePath} として {@code null} が渡された場合。
     */
    public FileBasedModelRepository( final Path basePath , final ModelRepository metaRepository ) throws IllegalArgumentException {
        if ( null == basePath ) {
            throw new IllegalArgumentException( "basePath should not be null." ) ;
        }
        this.setBasePath( basePath ) ;
        this.name = this.basePath.getFileName().toString() ;
        this.metaRepository = ( null == metaRepository ) ? this : metaRepository ;
        this.isDirty = true ;
    }

    // @Category accessing

    @Override
    public void addElement( final ElementBody element , final boolean asTopElement ) throws ElementAlreadyExistsException , IllegalArgumentException {
        if ( null == element ) {
            throw new IllegalArgumentException( "element should not be null." ) ;
        }
        synchronized ( this.elementMap ) {
            final UUID key = element.getUuid() ;
            if ( this.elementMap.containsKey( key ) ) {
                throw new ElementAlreadyExistsException( "UUID = " + key ) ;
            } else {
                this.elementMap.put( key , element ) ;
                this.dirtyElements.add( element ) ;
                if ( asTopElement ) {
                    this.topElements.add( element.toReference() ) ;
                    this.isDirty = true ;
                }
            }
        }
    }

    /**
     * リポジトリのトップ・フォルダのパスを返す。
     *
     * @return リポジトリのトップ・フォルダのパス。
     */
    public Path getBasePath() {
        return this.basePath ;
    }

    public String getDescription() {
        return this.description ;
    }

    @Override
    public ElementBody getElement( final UUID key ) throws ElementNotFoundException , IllegalArgumentException {
        if ( null == key ) {
            throw new IllegalArgumentException( "key should not be null." ) ;
        }
        synchronized ( this.elementMap ) {
            ElementBody element = this.elementMap.get( key ) ;
            if ( null != element ) {
                return element ;
            }
            if ( this.removedElements.stream().anyMatch( e -> key.equals( e.getUuid() ) ) ) {
                throw new ElementNotFoundException( "UUID = " + key ) ;
            }
            final Path filePath = this.getBasePath().resolve( relativeModelFilePath( key ) ) ;
            final ObjectMapper mapper = new ObjectMapper() ;
            try {
                element = mapper.readValue( filePath.toFile() , ElementBody.class ) ;
                this.elementMap.put( key , element ) ;
            } catch ( Exception e ) {
                e.printStackTrace() ;
            }
            return element ;
        }
    }

    @Override
    public ModelRepository getMetaRepository() {
        return this.metaRepository ;
    }

    /**
     * リポジトリ名を返す。
     *
     * @return リポジトリ名。
     */
    public String getName() {
        return this.name ;
    }

    @Override
    public void removeElement( final UUID key ) throws ElementNotFoundException , IllegalArgumentException {
        if ( null == key ) {
            throw new IllegalArgumentException( "key should not be null." ) ;
        }
        synchronized ( this.elementMap ) {
            final ElementBody removedElement = this.elementMap.remove( key ) ;
            if ( null == removedElement ) {
                throw new ElementNotFoundException( "UUID = " + key ) ;
            } else {
                this.removedElements.add( removedElement ) ;
                this.dirtyElements.remove( removedElement ) ;
                if ( this.topElements.remove( removedElement.toReference() ) ) {
                    this.isDirty = true ;
                }
            }
        }
    }

    /**
     * レシーバのベース・パスを設定する。
     *
     * @param basePath 指定されたベース・パス。
     */
    private void setBasePath( final Path basePath ) {
        assert ( null != basePath ) ;
        this.basePath = basePath.toAbsolutePath() ;
    }

    public void setDescription( final String newDescription ) throws IllegalArgumentException {
        if ( null == newDescription ) {
            throw new IllegalAccessError( "newDescription should not be null." ) ;
        }
        if ( !this.description.equals( newDescription ) ) {
            this.description = newDescription ;
            this.isDirty = true ;
        }
    }

    /**
     * レシーバの名前を変更する。
     * 現在の名前と指定された名前が同じだった場合は何もしない。
     *
     * @param newName 新たに設定される名前。
     * @throws IllegalArgumentException {@code newName} として {@code null} か空文字列が渡された場合。
     */
    public void setName( final String newName ) throws IllegalArgumentException {
        if ( ( null == newName ) || ( newName.isEmpty() ) ) {
            throw new IllegalAccessError( "newName should not be null or empty string." ) ;
        }
        if ( !this.name.equals( newName ) ) {
            this.name = newName ;
            this.isDirty = true ;
        }
    }

    // @Category comparing

    @Override
    public boolean equals( final Object obj ) {
        return ( obj instanceof FileBasedModelRepository ) && ( this.getBasePath().equals( ( (FileBasedModelRepository)( obj ) ).getBasePath() ) ) ;
    }

    @Override
    public int hashCode() {
        return this.getBasePath().hashCode() ;
    }

    // @Category converting

    @Override
    public String toString() {
        return this.getName() ;
    }

    // @Category persistency handling

    @Override
    public void flush() throws IOException {
        synchronized ( this.elementMap ) {
            // FIXME 未実装
        }
    }

    @Override
    public void reflesh() {
        synchronized ( this.elementMap ) {
            this.removedElements.clear() ;
            this.dirtyElements.clear() ;
            this.elementMap.clear() ;
        }
    }

    @Override
    public void save() throws IOException {
        this.flush() ;
        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue( this.basePath.resolve( REPOSITORY_FILE_NAME ).toFile() , this ) ;
        this.isDirty = false ;
    }

    /**
     * ベース・パスで指定されたディレクトリ以下に保存されている既存のリポジトリをロードして返す。
     *
     * @param basePath 指定されたベース・パス。
     * @return ロードされたリポジトリ。
     * @throws IllegalArgumentException {@code basePath} として {@code null} が渡された場合。
     * @throws IOException 入出力エラーが発生した場合。
     */
    public static FileBasedModelRepository load( final Path basePath ) throws IllegalArgumentException , IOException {
        if ( null == basePath ) {
            throw new IllegalArgumentException( "basePath should not be null." ) ;
        }
        final ObjectMapper mapper = new ObjectMapper() ;
        final FileBasedModelRepository repository = mapper.readValue( basePath.resolve( REPOSITORY_FILE_NAME ).toFile() , FileBasedModelRepository.class ) ;
        repository.setBasePath( basePath ) ;
        return repository ;
    }

    // @Category utilities

    /**
     * 指定されたキーに対応するモデル・ファイルのファイル名を返す。
     *
     * @param key 指定されたキー。
     * @return モデル・ファイルのファイル名。
     * @see #MODEL_ELEMENT_FILE_EXTENSION
     */
    private static String modelFileName( final UUID key ) {
        return key.toString() + MODEL_ELEMENT_FILE_EXTENSION ;
    }

    /**
     * 指定されたキーに対応するモデル・ファイルが置かれるフォルダのリポジトリ・トップ・フォルダからの相対パスを返す。
     *
     * @param key 指定されたキー。
     * @return モデル・ファイルが置かれるフォルダのリポジトリ・トップ・フォルダからの相対パス。
     */
    private static Path relativeDirectoryPath( final UUID key ) {
        final String uuidString = key.toString() ;
        return Paths.get( uuidString.substring( 0 , 2 ) , uuidString.substring( 2 , 4 ) , uuidString.substring( 4 , 6 ) , uuidString.substring( 6 , 8 ) ) ;
    }

    /**
     * 指定されたキーに対応するモデル・ファイルのリポジトリ・トップ・フォルダからの相対パスを返す。
     *
     * @param key 指定されたキー。
     * @return モデル・ファイルのリポジトリ・トップ・フォルダからの相対パス。
     */
    private static Path relativeModelFilePath( final UUID key ) {
        return relativeDirectoryPath( key ).resolve( modelFileName( key ) ) ;
    }

}
