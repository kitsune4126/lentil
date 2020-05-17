package com.mamezou.lentil.swing ;

import java.awt.Color ;

import javax.swing.JTabbedPane ;
import javax.swing.border.TitledBorder ;

import org.springframework.context.ConfigurableApplicationContext ;

import com.mamezou.lentil.model.ModelElement ;
import com.mamezou.lentil.model.ModelElementChangedEvent ;
import com.mamezou.lentil.model.ModelElementObserver ;

/**
 * モデル要素のプロパティ表示/編集用ビューのための抽象クラス。
 *
 * @author kitsune
 */
public abstract class ElementPropertyView extends JTabbedPane implements ModelElementObserver {

    // @Category constant definitions

    /**
     * プロパティ名のカラム・インデックス。
     */
    protected static final int COLUMN_INDEX_NAME = 0 ;

    /**
     * プロパティ値のカラム・インデックス。
     */
    protected static final int COLUMN_INDEX_VALUE = 1 ;

    /**
     * プロパティ名カラムのデフォルト幅。単位はピクセル。
     */
    protected static final int DEFAULT_PROPERTY_COLUMN_WIDTH = 64 ;

    /**
     * プロパティ値カラムのデフォルト幅。単位はピクセル。
     */
    protected static final int DEFAULT_VALUE_COLUMN_WIDTH = 256 ;

    /**
     * プロパティ名セルの背景色。
     */
    protected static final Color PROPERTY_NAME_CELL_BACKGROUND_COLOR = new Color( 240 , 240 , 240 ) ;

    /**
     * UUID フィールドの行インデックス。
     */
    protected static final int ROW_INDEX_UUID = 0 ;

    // @Category instance variables

    /**
     * Spring Framework のアプリケーション・コンテキスト。
     */
    protected ConfigurableApplicationContext context ;

    /**
     * 表示/編集の対象となっているモデル要素。
     */
    protected ModelElement modelElement ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param context Spring Framework のアプリケーション・コンテキスト。
     * @throws IllegalArgumentException {@code context} として {@code null} が渡された場合。
     */
    protected ElementPropertyView( final ConfigurableApplicationContext context ) throws IllegalArgumentException {
        super() ;
        if ( null == context ) {
            throw new IllegalArgumentException( "context should not be null." ) ; //$NON-NLS-1$
        }
        this.context = context ;
        this.initializeComponents() ;
        this.updateAll() ;
    }

    // @Category initialize-release

    /**
     * レシーバの子 GUI 部品群を生成/初期化する。
     */
    protected void initializeComponents() {
        this.setBorder( new TitledBorder( this.getBorderTitle() ) ) ;
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
    public void setModelElement( final ModelElement newModelElement ) throws IllegalArgumentException {
        if ( this.modelElement != newModelElement ) {
            if ( null != this.modelElement ) {
                this.modelElement.removeObserver( this ) ;
            }
            this.modelElement = newModelElement ;
            if ( null != this.modelElement ) {
                this.modelElement.addObserver( this ) ;
            }
            this.updateAll() ;
        }
    }

    // @Category event handling

    @Override
    public void modelElementChanged( ModelElementChangedEvent event ) {}

    // @Category updating

    /**
     * 現在の {@link #modelElement} の内容に合致するようにレシーバが保持しているすべてのフィールドの表示を更新する。
     */
    protected void updateAll() {}

}
