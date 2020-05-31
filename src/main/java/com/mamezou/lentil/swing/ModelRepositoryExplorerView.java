/* ModelRepositoryExplorerView.java */
package com.mamezou.lentil.swing ;

import java.util.ArrayList ;
import java.util.Enumeration ;
import java.util.List ;

import javax.swing.JTree ;
import javax.swing.tree.DefaultMutableTreeNode ;
import javax.swing.tree.DefaultTreeModel ;
import javax.swing.tree.TreeNode ;
import javax.swing.tree.TreePath ;

import org.springframework.context.ConfigurableApplicationContext ;

import com.mamezou.lentil.model.ModelRepository ;

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

    /**
     * コンストラクタ。
     *
     * @param context Spring Framework のアプリケーション・コンテキスト。
     */
    public ModelRepositoryExplorerView( final ConfigurableApplicationContext context ) {
        super() ;
        this.setRootVisible( false ) ;
        this.context = context ;
        this.setModel( new DefaultTreeModel( new DefaultMutableTreeNode( "ROOT" ) ) ) ;
    }

    // @Category accessing

    /**
     * リポジトリを追加する。
     * 指定されたリポジトリが既に追加済みであった場合は何もせずに {@code false} を返す。
     *
     * @param repository 指定されたリポジトリ。
     * @return 実際に追加されたか否か。
     * @throws IllegalArgumentException {@code repository} として {@code null} が指定された場合。
     */
    public boolean addRepository( final ModelRepository repository ) throws IllegalArgumentException {
        if ( this.getRepositories().contains( repository ) ) {
            return false ;
        } else {
            final DefaultMutableTreeNode rootNode = this.getRootNode() ;
            final DefaultMutableTreeNode newNode = new DefaultMutableTreeNode( repository ) ;
            this.getTreeModel().insertNodeInto( newNode , rootNode , rootNode.getChildCount() ) ;
            this.getSelectionModel().setSelectionPath( new TreePath( newNode.getPath() ) ) ;
            return true ;
        }
    }

    /**
     * このビューに登録されているモデル・リポジトリのリストを返す。
     *
     * @return モデル・リポジトリのリスト。
     */
    private List< ModelRepository > getRepositories() {
        final DefaultMutableTreeNode rootNode = this.getRootNode() ;
        @SuppressWarnings( "unchecked" )
        Enumeration< TreeNode > children = (Enumeration< TreeNode >)( rootNode.children() ) ;
        final ArrayList< ModelRepository > repositories = new ArrayList< ModelRepository >( rootNode.getChildCount() ) ;
        while ( children.hasMoreElements() ) {
            repositories.add( (ModelRepository)( ( (DefaultMutableTreeNode)( children.nextElement() ) ).getUserObject() ) ) ;
        }
        return repositories ;
    }

    /**
     * 最上位のノードを返す。
     *
     * @return 最上位のノード。
     */
    private DefaultMutableTreeNode getRootNode() {
        return (DefaultMutableTreeNode)( this.getModel().getRoot() ) ;
    }

    /**
     * レシーバのツリー・モデルを返す。
     *
     * @return ツリー・モデル。
     */
    private DefaultTreeModel getTreeModel() {
        return (DefaultTreeModel)( this.getModel() ) ;
    }

}
