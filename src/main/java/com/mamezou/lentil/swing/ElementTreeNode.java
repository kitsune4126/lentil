package com.mamezou.lentil.swing ;

import javax.swing.tree.DefaultMutableTreeNode ;
import javax.swing.tree.DefaultTreeModel ;

import com.mamezou.lentil.model.Element ;
import com.mamezou.lentil.model.ElementChangedEvent ;
import com.mamezou.lentil.model.ElementObserver ;

/**
 * ツリー・ビューの項目としてモデル要素を表示/編集するためのノード。
 *
 * @author kitsune
 */
public class ElementTreeNode extends DefaultMutableTreeNode implements ElementObserver {

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
    public ElementTreeNode( final Element element , final DefaultTreeModel treeModel ) throws IllegalArgumentException {
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
    public Element getElement() {
        return (Element)( this.getUserObject() ) ;
    }

    // @Category event handling

    @Override
    public void elementChanged( ElementChangedEvent event ) {
        if ( this.getElement().equals( event.getSender() ) && ( null != this.treeModel ) ) {
            // FIXME 未実装
        }
    }

    // @Category testing

    @Override
    public boolean isLeaf() {
        return false ;
    }

}
