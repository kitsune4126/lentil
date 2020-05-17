package com.mamezou.lentil.model ;

import java.util.UUID ;

/**
 * すべてのモデル構成要素のための抽象インタフェース。
 *
 * @author kitsune
 */
public interface ModelElement extends ModelElementObserver {

    // @Category accessing

    /**
     * レシーバのオブザーバ・リストに指定されたオブザーバを追加する。
     * 指定されたオブザーバが既にレシーバのオブザーバ・リストに含まれていた場合は何もしない。
     *
     * @param observer 指定されたオブザーバ。
     * @throws IllegalArgumentException {@code observer} として {@code null} が渡された場合。
     */
    public void addObserver( final ModelElementObserver observer ) throws IllegalArgumentException ;

    /**
     * レシーバに対してユニークに割り当てられた識別子を返す。
     *
     * @return レシーバに対してユニークに割り当てられた識別子。
     */
    public UUID getUuid() ;

    /**
     * レシーバのオブザーバ・リストを空にする。
     */
    public void removeAllObservers() ;

    /**
     * レシーバのオブザーバ・リストから指定されたオブザーバを削除する。
     * 指定されたオブザーバがレシーバのオブザーバ・リストに含まれていなかった場合は何もしない。
     *
     * @param observer 指定されたオブザーバ。
     * @throws IllegalArgumentException {@code observer} として {@code null} が渡された場合。
     */
    public void removeObserver( final ModelElementObserver observer ) throws IllegalArgumentException ;

}
