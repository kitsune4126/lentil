package com.mamezou.lentil.swing ;

import com.mamezou.lentil.model.ModelElement ;

/**
 * GUI によって表示/編集対象となるモデル要素のためのインタフェース。
 *
 * @author kitsune
 */
public interface BrowsableElement extends ModelElement {

    // @Category constant definitions

    /**
     * レシーバの名前が変更されたことを示す変更種別。
     * 変更通知のパラメータには 変更前の名前 と 変更後の名前 が渡される。
     */
    public static final String CHANGE_ASPECT_NAME = "Name" ;

    // @Category accessing

    /**
     * このモデル要素の名前を返す。
     *
     * @return このモデル要素の名前。
     */
    public String getName() ;

    /**
     * このモデル要素の型名を表わす文字列を返す。
     *
     * @return このモデル要素の型名。
     */
    public String getTypeName() ;

    /**
     * このモデル要素の名前を変更する。
     * 現在の名前と指定された名前が同じだった場合は何もしない。
     * 実際に名前が変更された場合は {@link #CHANGE_ASPECT_NAME} の変更通知を発する。
     *
     * @param newName 新たに設定される名前。
     * @throws IllegalArgumentException {@code newName} が {@code null} または空文字列だった場合。
     */
    public void setName( final String newName ) throws IllegalArgumentException ;

}
