package com.mamezou.lentil.model.uml ;

/**
 * UML の {@code PackageableElement} を表すモデル要素のためのインタフェース。
 *
 * @author kitsune
 */
public interface UmlPackageableElement extends UmlParameterableElement , UmlNamedElement {
    // FIXME 未実装

    // @Category accessing

    /**
     * レシーバの可視性を返す。
     *
     * @return レシーバの可視性。未設定の場合は {@code null} が返される。
     */
    public UmlVisibilityKind getVisibility() ;

    /**
     * レシーバの可視性を設定する。
     *
     * @param newVisibility 新しい可視性。未設定にする場合は {@code null} を渡す。
     */
    public void setVisibility( final UmlVisibilityKind newVisibility ) ;

}
