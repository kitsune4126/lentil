package com.mamezou.lentil.model ;

/**
 * モデル要素からの変更通知を受けるオブジェクトのためのインタフェース。
 *
 * @author kitsune
 */
public interface ElementObserver {

    // @Category event handling

    /**
     * 観察しているモデル要素のいずれかが変更された。
     *
     * @param event 変更の内容を示す変更通知。
     */
    public void elementChanged( final ElementChangedEvent event ) ;

}
