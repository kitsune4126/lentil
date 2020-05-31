package com.mamezou.lentil.swing ;

import org.springframework.context.ConfigurableApplicationContext ;

import com.mamezou.lentil.model.Element ;
import com.mamezou.lentil.model.ElementChangedEvent ;
import com.mamezou.lentil.model.ElementObserver ;

/**
 * モデル要素のプロパティ表示/編集用ビューのための抽象クラス。
 *
 * @author kitsune
 */
public abstract class ElementPropertyView extends PropertyView< Element > implements ElementObserver {

    // @Category constant definitions

    /**
     * UUID フィールドの行インデックス。
     */
    protected static final int ROW_INDEX_UUID = 0 ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param context Spring Framework のアプリケーション・コンテキスト。
     * @throws IllegalArgumentException {@code context} として {@code null} が渡された場合。
     */
    protected ElementPropertyView( final ConfigurableApplicationContext context ) throws IllegalArgumentException {
        super( context ) ;
    }

    // @Category accessing

    /**
     * プロパティ・ビューのボーダー部分に表示されるタイトル文字列を返す。
     *
     * @return プロパティ・ビューのボーダー部分に表示されるタイトル文字列。
     */
    public abstract String getBorderTitle() ;

    /**
     * 表示/編集の対象となるモデル要素を設定する。
     *
     * @param newModelElement 新たに表示/編集の対象となるモデル要素。何も表示/編集しない場合は {@code null} を渡す。
     * @throws IllegalArgumentException {@code newModelElement} として表示/編集できないタイプのモデル要素が渡された場合。
     */
    @Override
    public void setTargetModel( final Element newModel ) throws IllegalArgumentException {
        if ( this.targetModel != newModel ) {
            if ( null != this.targetModel ) {
                this.targetModel.removeObserver( this ) ;
            }
            this.targetModel = newModel ;
            if ( null != this.targetModel ) {
                this.targetModel.addObserver( this ) ;
            }
            this.updateAll() ;
        }
    }

    // @Category event handling

    @Override
    public void elementChanged( final ElementChangedEvent event ) {}

    // @Category updating

    /**
     * 現在の {@link #modelElement} の内容に合致するようにレシーバが保持しているすべてのフィールドの表示を更新する。
     */
    protected void updateAll() {}

}
