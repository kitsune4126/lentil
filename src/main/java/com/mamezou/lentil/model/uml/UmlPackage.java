package com.mamezou.lentil.model.uml ;

/**
 * UML の {@code Package} を表すモデル要素のためのインタフェース。
 *
 * @author kitsune
 */
public interface UmlPackage extends UmlPackageableElement , UmlTemplateableElement , UmlNamespace {
    // FIXME 未実装

    // @Category accessing

    /**
     * レシーバの URI 文字列を返す。
     *
     * @return レシーバの URI 文字列。未設定の場合は {@code null} が返される。
     */
    public String getUri() ;

    /**
     * レシーバの URI 文字列を設定する。
     *
     * @param newUri 新しい URI 文字列。未設定にする場合は {@code null} を渡す。
     */
    public void setUri( final String newUri ) ;

}
