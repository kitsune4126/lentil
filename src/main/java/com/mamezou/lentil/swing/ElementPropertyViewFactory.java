package com.mamezou.lentil.swing ;

import org.springframework.context.ConfigurableApplicationContext ;

/**
 * 指定されたタイプのモデル要素のプロパティ表示/編集用ビューを生成するファクトリのためのインタフェース。
 *
 * @author kitsune
 */
public interface ElementPropertyViewFactory {

    // @Category factory methods

    /**
     * 指定されたタイプのモデル要素のプロパティ表示/編集用の主ビューを生成して返す。
     *
     * @param elementTypeName モデル要素のタイプを示す文字列。
     * @param context Spring Framework のアプリケーション・コンテキスト。
     * @return 生成されたビュー。
     * @throws IllegalArgumentException {@code elementTypeName} または {@code context} として {@code null} が渡された場合。
     */
    public ElementPropertyView createPrimaryPropertyView( final String elementTypeName , final ConfigurableApplicationContext context ) throws IllegalArgumentException ;

    /**
     * 指定されたタイプのモデル要素のプロパティ表示/編集用の副ビューを生成して返す。
     *
     * @param elementTypeName モデル要素のタイプを示す文字列。
     * @param context Spring Framework のアプリケーション・コンテキスト。
     * @return 生成されたビュー。副ビューが不要なモデル要素の場合は {@code null} が返される。
     * @throws IllegalArgumentException {@code elementTypeName} または {@code context} として {@code null} が渡された場合。
     */
    public ElementPropertyView createSecondaryPropertyView( final String elementTypeName , final ConfigurableApplicationContext context ) throws IllegalArgumentException ;

}
