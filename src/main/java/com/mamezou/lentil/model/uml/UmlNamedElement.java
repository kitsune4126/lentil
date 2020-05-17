package com.mamezou.lentil.model.uml ;

/**
 * UML の {@code NamedElement} を表すモデル要素のためのインタフェース。
 *
 * @author kitsune
 */
public interface UmlNamedElement extends UmlElement {
    // FIXME 未実装

    // @Category accessing

    /**
     * レシーバの名前を返す。
     *
     * @return レシーバの名前。未設定の場合は {@code null} が返される。
     */
    public String getName() ;

    /**
     * レシーバの可視性を返す。
     *
     * @return レシーバの可視性。未設定の場合は {@code null} が返される。
     */
    public UmlVisibilityKind getVisibility() ;

    /**
     * レシーバの名前を設定する。
     *
     * @param newName 新しい名前。未設定にする場合は {@code null} を渡す。
     */
    public void setName( final String newName ) ;

    /**
     * レシーバの可視性を設定する。
     *
     * @param newVisibility 新しい可視性。未設定にする場合は {@code null} を渡す。
     */
    public void setVisibility( final UmlVisibilityKind newVisibility ) ;

}
