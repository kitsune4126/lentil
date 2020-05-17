package com.mamezou.lentil.repository ;

import java.nio.file.Path ;
import java.nio.file.Paths ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.UUID ;

import com.fasterxml.jackson.annotation.JsonIgnore ;
import com.fasterxml.jackson.annotation.JsonProperty ;
import com.mamezou.lentil.model.AbstractModelElementBody ;
import com.mamezou.lentil.model.ModelElement ;
import com.mamezou.lentil.swing.BrowsableElement ;

/**
 * ファイル・ベースなモデル・リポジトリの具象クラス。
 *
 * @author kitsune@mamezou.com
 */
public class FileBasedModelRepository extends AbstractModelElementBody implements ModelRepository {

    // @Category constant definitions

    /**
     * モデル要素の内容が書き込まれるモデル・ファイルの拡張子。
     */
    private final static String MODEL_FILE_EXTENSION = "json" ;

    /**
     * モデル要素の型名を示す文字列。
     */
    public final static String ELEMENT_TYPE_NAME = "FileBasedModelRepository" ;

    // @Category instance fields

    /**
     * リポジトリのトップ・フォルダのパス。
     */
    @JsonIgnore
    private final Path basePath ;

    /**
     * オン・メモリでキャッシュしているモデル要素のマップ。
     */
    @JsonIgnore
    private final Map< UUID , ModelElement > cachedElementMap = new HashMap< UUID , ModelElement >() ;

    /**
     * リポジトリ名。
     */
    @JsonProperty
    private String name = "" ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param basePath リポジトリのトップ・フォルダのパス。
     * @throws IllegalArgumentException {@code basePath} として {@code null} が渡された場合。
     */
    public FileBasedModelRepository( final Path basePath ) throws IllegalArgumentException {
        super( new DummyModelRepository() ) ;
        if ( null == basePath ) {
            throw new IllegalArgumentException( "basePath should not be null." ) ;
        }
        this.basePath = basePath.toAbsolutePath() ;
        this.name = this.basePath.getFileName().toString() ;
    }

    // @Category accessing

    /**
     * リポジトリのトップ・フォルダのパスを返す。
     *
     * @return リポジトリのトップ・フォルダのパス。
     */
    public Path getBasePath() {
        return this.basePath ;
    }

    @Override
    public ModelElement getModelElement( UUID key ) throws ElementNotFoundException {
        // TODO 自動生成されたメソッド・スタブ
        return null ;
    }

    @Override
    public String getName() {
        return this.name ;
    }

    @Override
    public String getTypeName() {
        return ELEMENT_TYPE_NAME ;
    }

    @Override
    public void putModelElement( UUID key , ModelElement element ) throws ElementAlreadyExistsException {
        if ( this.cachedElementMap.containsKey( key ) ) {
            throw new ElementAlreadyExistsException( "UUID = " + key ) ;
        }
        this.cachedElementMap.put( key , element ) ;
    }

    @Override
    public void removeModelElement( UUID key ) throws ElementNotFoundException {
        // TODO 自動生成されたメソッド・スタブ
    }

    @Override
    public void setName( final String newName ) throws IllegalArgumentException {
        if ( ( null == newName ) || ( newName.isEmpty() ) ) {
            throw new IllegalAccessError( "newName should not be null or empty string." ) ;
        }
        if ( !this.name.equals( newName ) ) {
            final String oldName = this.name ;
            this.name = newName ;
            this.notifyObservers( BrowsableElement.CHANGE_ASPECT_NAME , null , new String[] { oldName , newName } ) ;
        }
    }

    // @Category comparing

    @Override
    public boolean equals( Object obj ) {
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

    // @Category utilities

    /**
     * 指定されたキーに対応するモデル・ファイルのファイル名を返す。
     *
     * @param key 指定されたキー。
     * @return モデル・ファイルのファイル名。
     * @see #MODEL_FILE_EXTENSION
     */
    private static String modelFileName( final UUID key ) {
        return key.toString() + "." + MODEL_FILE_EXTENSION ;
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

    // @Category testing

    /**
     * 一時的なテスト用のメイン・メソッド。
     *
     * @param args コマンド・ラインで指定されたパラメータ文字列の配列。
     */
    public static void main( String[] args ) {
        final UUID uuid = UUID.randomUUID() ;
        System.out.println( "UUID = " + uuid.toString() ) ;
        final Path relativeDirectoryPath = relativeDirectoryPath( uuid ) ;
        System.out.println( "Directory Path = " + relativeDirectoryPath ) ;
        System.out.println( "Model File Name = " + modelFileName( uuid ) ) ;
    }

}
