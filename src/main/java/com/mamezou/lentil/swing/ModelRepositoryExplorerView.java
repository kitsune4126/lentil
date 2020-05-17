/* ModelRepositoryExplorerView.java */
package com.mamezou.lentil.swing ;

import javax.swing.JTree ;
import javax.swing.tree.DefaultTreeModel ;
import javax.swing.tree.TreePath ;

import org.springframework.context.ConfigurableApplicationContext ;

import com.mamezou.lentil.repository.ModelRepository ;
import com.mamezou.lentil.repository.ModelRepositoryContainer ;

/**
 * モデル・リポジトリの内容を階層的に表示/編集するツリー・ビュー。
 *
 * @author kitsune
 */
public class ModelRepositoryExplorerView extends JTree {

    // @Category instance variables

    /**
     * Spring Framework のアプリケーション・コンテキスト。
     */
    private ConfigurableApplicationContext context ;

    // @Category instance creation

    public ModelRepositoryExplorerView( final ConfigurableApplicationContext context ) {
        super() ;
        this.setRootVisible( false ) ;
        this.context = context ;
        final ElementTreeNode rootNode = new ElementTreeNode( this.context.getBean( ModelRepositoryContainer.class ) , null ) ;
        this.setModel( new DefaultTreeModel( rootNode ) ) ;
    }

    // @Category accessing

    /**
     * 指定されたリポジトリを追加する。
     * 指定されたリポジトリが既に追加済みであった場合は何もせずに {@code false} を返す。
     *
     * @param repository 指定されたリポジトリ。
     * @return 実際に追加されたか否か。
     * @throws IllegalArgumentException {@code repository} として {@code null} が指定された場合。
     */
    public boolean addRepository( final ModelRepository repository ) throws IllegalArgumentException {
        if ( this.context.getBean( ModelRepositoryContainer.class ).addRepository( repository ) ) {
            final ElementTreeNode rootNode = this.getRootNode() ;
            final ElementTreeNode newNode = new ElementTreeNode( repository , this.getTreeModel() ) ;
            this.getTreeModel().insertNodeInto( newNode , rootNode , rootNode.getChildCount() ) ;
            this.getSelectionModel().setSelectionPath( new TreePath( newNode.getPath() ) ) ;
            return true ;
        } else {
            return false ;
        }
    }

    /**
     * 最上位のノード(ルート・ノード)を返す。
     *
     * @return 最上位のノード。
     */
    private ElementTreeNode getRootNode() {
        return (ElementTreeNode)( this.getModel().getRoot() ) ;
    }

    private DefaultTreeModel getTreeModel() {
        return (DefaultTreeModel)( this.getModel() ) ;
    }

}
