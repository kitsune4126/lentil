package com.mamezou.lentil.model ;

import java.util.UUID ;

import com.fasterxml.jackson.annotation.JsonIgnore ;
import com.fasterxml.jackson.annotation.JsonProperty ;

/**
 * モデル要素への参照を表わすオブジェクト。
 *
 * @author kitsune
 */
public class ElementReference implements Element {

    // @Category instance variables

    /**
     * このモデル参照の本体に当たるモデル要素。
     */
    private ElementBody body = null ;

    /**
     * このモデル要素が属するリポジトリ。
     */
    @JsonIgnore
    private final ModelRepository repository ;

    /**
     * このモデル要素にユニークに割り当てられたID。
     */
    @JsonProperty
    private final UUID uuid ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param repository 参照先のモデル要素本体が属するリポジトリ。
     * @param uuid 参照先のモデル要素本体の UUID 。
     * @throws IllegalArgumentException {@code repository} か {@code uuid} として {@code null} が渡された場合。
     */
    public ElementReference( final ModelRepository repository , final UUID uuid ) throws IllegalArgumentException {
        super() ;
        if ( null == repository ) {
            throw new IllegalArgumentException( "repository should not be null." ) ;
        }
        if ( null == uuid ) {
            throw new IllegalArgumentException( "uuid should not be null." ) ;
        }
        this.repository = repository ;
        this.uuid = uuid ;
    }

    // @Category accessing

    @Override
    public void addObserver( final ElementObserver observer ) throws IllegalArgumentException {
        this.getBody().addObserver( observer ) ;
    }

    /**
     * このモデル参照の本体に当たるモデル要素を返す。
     *
     * @return このモデル参照の本体に当たるモデル要素。
     */
    private ElementBody getBody() {
        if ( null == this.body ) {
            this.body = this.repository.getElement( this.uuid ) ;
        }
        return this.body ;
    }

    @Override
    public Element getMetaClass() {
        return this.getBody().getMetaClass() ;
    }

    @Override
    public ModelRepository getRepository() {
        return this.repository ;
    }

    @Override
    public UUID getUuid() {
        return this.uuid ;
    }

    @Override
    public void removeAllObservers() {
        this.getBody().removeAllObservers() ;
    }

    @Override
    public void removeObserver( ElementObserver observer ) throws IllegalArgumentException {
        this.getBody().removeObserver( observer ) ;
    }

    // @Category comparing

    @Override
    public boolean equals( Object other ) {
        return ( other instanceof Element ) && ( this.getUuid().equals( ( (Element)( other ) ).getUuid() ) ) ;
    }

    @Override
    public int hashCode() {
        return this.getUuid().hashCode() ;
    }

    // @Category converting

    @Override
    public ElementBody toBody() {
        return this.getBody() ;
    }

    @Override
    public ElementReference toReference() {
        return this ;
    }

    // @Category event handling

    @Override
    public void elementChanged( final ElementChangedEvent event ) {}

}
