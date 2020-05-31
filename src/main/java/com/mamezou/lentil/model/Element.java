package com.mamezou.lentil.model ;

import java.util.UUID ;

/**
 * すべてのモデル構成要素のための抽象インタフェース。
 *
 * @author kitsune
 */
public interface Element extends ElementObserver {

    // @Category accessing

    /**
     * レシーバのオブザーバ・リストに指定されたオブザーバを追加する。
     * 指定されたオブザーバが既にレシーバのオブザーバ・リストに含まれていた場合は何もしない。
     *
     * @param observer 指定されたオブザーバ。
     * @throws IllegalArgumentException {@code observer} として {@code null} が渡された場合。
     */
    public void addObserver( final ElementObserver observer ) throws IllegalArgumentException ;

    /**
     * このモデル要素の型を表わすモデル要素を返す。
     *
     * @return このモデル要素の型を表わすモデル要素。
     */
    public Element getMetaClass() ;

    /**
     * レシーバが属するリポジトリを返す。
     *
     * @return レシーバが属するリポジトリ。
     */
    public ModelRepository getRepository() ;

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
    public void removeObserver( final ElementObserver observer ) throws IllegalArgumentException ;

    // @Category converting

    /**
     * レシーバに対応する本体要素を返す。
     * レシーバが本体要素 ( Body Element ) の場合は、レシーバ自身がそのまま返される。
     * レシーバが参照要素 ( Element Reference ) の場合は、参照先の本体要素が返される。
     *
     * @return レシーバに対応する本体要素。
     */
    public ElementBody toBody() ;

    /**
     * レシーバに対応する参照要素を返す。
     * レシーバが本体要素 ( Body Element ) の場合は、レシーバを参照する参照要素が新たに作られて返される。
     * レシーバが参照要素 ( Element Reference ) の場合は、レシーバ自身がそのまま返される。
     *
     * @return レシーバに対応する参照要素。
     */
    public ElementReference toReference() ;

}
