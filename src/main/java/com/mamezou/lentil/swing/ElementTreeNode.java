package com.mamezou.lentil.swing ;

import javax.swing.tree.DefaultMutableTreeNode ;
import javax.swing.tree.DefaultTreeModel ;

import com.mamezou.lentil.model.ModelElementChangedEvent ;
import com.mamezou.lentil.model.ModelElementObserver ;

/**
 * ツリー・ビューの項目としてモデル要素を表示/編集するためのノード。
 *
 * @author kitsune
 */
public class ElementTreeNode extends DefaultMutableTreeNode implements ModelElementObserver {

    // @Category instance variables

    /**
     * このノードを含むツリー・モデル。
     */
    private final DefaultTreeModel treeModel ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param element 新しく作成されるノードが表わすモデル要素。
     * @throws IllegalArgumentException {@code element} として {@code null} が渡された場合。
     */
    public ElementTreeNode( final BrowsableElement element , final DefaultTreeModel treeModel ) throws IllegalArgumentException {
        super( element , true ) ;
        if ( null == element ) {
            throw new IllegalArgumentException( "element should not be null." ) ;
        }
        this.treeModel = treeModel ;
        element.addObserver( this ) ;
    }

    // @Category initialize-release

    /**
     * レシーバ破棄時の後始末。
     *
     * @throws Exception レシーバ破棄時の処理で何らかの例外が発生した場合。
     */
    public void destroy() throws Exception {
        this.getElement().removeObserver( this ) ;
    }

    // @Category accessing

    /**
     * レシーバが表わすモデル要素を返す。
     *
     * @return レシーバが表わすモデル要素。
     */
    public BrowsableElement getElement() {
        return (BrowsableElement)( this.getUserObject() ) ;
    }

    // @Category event handling

    @Override
    public void modelElementChanged( ModelElementChangedEvent event ) {
        if ( this.getElement().equals( event.getSender() ) && ( null != this.treeModel ) ) {
            if ( BrowsableElement.CHANGE_ASPECT_NAME.equals( event.getAspect() ) ) {
                this.treeModel.nodeChanged( this ) ;
            }
        }
    }

    // @Category testing

    @Override
    public boolean isLeaf() {
        return false ;
    }

}
