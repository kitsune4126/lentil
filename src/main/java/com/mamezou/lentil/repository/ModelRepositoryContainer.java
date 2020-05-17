package com.mamezou.lentil.repository ;

import java.util.ArrayList ;
import java.util.List ;

import com.mamezou.lentil.model.AbstractModelElementBody ;
import com.mamezou.lentil.swing.BrowsableElement ;

/**
 * 現在開いている複数のモデル・リポジトリを保持/管理するオブジェクト。
 *
 * @author kitsune
 */
public class ModelRepositoryContainer extends AbstractModelElementBody implements BrowsableElement {

    // @Category instance variables

    /**
     * 現在開いているモデル・リポジトリのリスト。
     */
    private List< ModelRepository > repositories = new ArrayList< ModelRepository >() ;

    // @Category instance creation

    /**
     * デフォルト・コンストラクタ。
     */
    public ModelRepositoryContainer() {
        super( new DummyModelRepository() ) ;
    }

    // @Category accessing

    /**
     * レシーバのリポジトリ・リストの最後に指定されたリポジトリを追加する。
     * 指定されたリポジトリが既にレシーバのリポジトリ・リストに含まれていた場合は何もせずに {@code false} を返す。
     *
     * @param newRepository 指定されたリポジトリ。
     * @return 指定されたリポジトリが実際にレシーバのリポジトリ・リストに追加されたか否か。
     * @throws IllegalArgumentException {@code newRepository} として {@code null} が指定された場合。
     */
    public boolean addRepository( final ModelRepository newRepository ) throws IllegalArgumentException {
        if ( null == newRepository ) {
            throw new IllegalArgumentException( "newRepository is null." ) ; //$NON-NLS-1$
        }
        synchronized ( this.repositories ) {
            if ( this.repositories.contains( newRepository ) ) {
                return false ;
            } else {
                this.repositories.add( newRepository ) ;
                return true ;
            }
        }
    }

    /**
     * レシーバのリポジトリ・リストからすべてのリポジトリを削除する。
     */
    public void clearAllRepositories() {
        synchronized ( this.repositories ) {
            this.repositories.clear() ;
        }
    }

    @Override
    public String getName() {
        return "ROOT" ; //$NON-NLS-1$
    }

    @Override
    public String getTypeName() {
        return "ModelRepositoryContainer" ; //$NON-NLS-1$
    }

    /**
     * レシーバに登録されているすべてのリポジトリのリストを返す。
     *
     * @return レシーバに登録されているすべてのリポジトリのリスト。
     */
    public List< ModelRepository > getRepositories() {
        synchronized ( this.repositories ) {
            return new ArrayList< ModelRepository >( this.repositories ) ;
        }
    }

    /**
     * レシーバのリポジトリ・リストから指定されたリポジトリを削除する。
     * 指定されたリポジトリがレシーバのリポジトリ・リストに含まれていなかった場合は何もせずに {@code false} を返す。
     *
     * @param oldRepository 指定されたリポジトリ。
     * @return 指定されたリポジトリが実際にレシーバのリポジトリ・リストから削除されたか否か。
     * @throws IllegalArgumentException {@code newRepository} として {@code null} が指定された場合。
     */
    public boolean removeRepository( final ModelRepository oldRepository ) throws IllegalArgumentException {
        if ( null == oldRepository ) {
            throw new IllegalArgumentException( "oldRepository is null." ) ; //$NON-NLS-1$
        }
        synchronized ( this.repositories ) {
            return this.repositories.remove( oldRepository ) ;
        }
    }

    @Override
    public void setName( String newName ) throws IllegalArgumentException {}

    // @Category converting

    @Override
    public String toString() {
        return this.getName() ;
    }

}
