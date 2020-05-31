package com.mamezou.lentil.model ;

import java.util.HashSet ;
import java.util.Set ;
import java.util.UUID ;

import com.fasterxml.jackson.annotation.JsonIgnore ;
import com.fasterxml.jackson.annotation.JsonProperty ;

/**
 * モデル要素本体を表わすオブジェクト。
 *
 * @author kitsune
 */
public class ElementBody implements Element {

    // @Category instance variables

    /**
     * このモデル要素の型を表わすモデル要素への参照。
     */
    @JsonProperty
    private ElementReference metaClass = null ;

    /**
     * このモデル要素を監視しているオブザーバの集合。
     */
    @JsonIgnore
    private final Set< ElementObserver > observers = new HashSet< ElementObserver >() ;

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
     * @param repository 新たに生成されるモデル要素が属するリポジトリ。
     * @throws IllegalArgumentException {@code repository} として {@code null} が渡された場合。
     */
    public ElementBody( final ModelRepository repository ) throws IllegalArgumentException {
        this( repository , null ) ;
    }

    /**
     * コンストラクタ。
     *
     * @param repository 新たに生成されるモデル要素が属するリポジトリ。
     * @param metaClass 新たに生成されるモデル要素の型を表わすモデル要素。
     * @throws IllegalArgumentException {@code repository} として {@code null} が渡された場合。
     */
    public ElementBody( final ModelRepository repository , final Element metaClass ) throws IllegalArgumentException {
        this( repository , metaClass , null ) ;
    }

    /**
     * コンストラクタ。
     *
     * @param repository 新たに生成されるモデル要素が属するリポジトリ。
     * @param metaClass 新たに生成されるモデル要素の型を表わすモデル要素。
     * @param uuid 新たに生成されるモデル要素に割り当てられるユニークID。{@code null} が渡された場合、ランダムなIDを生成して割り当てる。
     * @throws IllegalArgumentException {@code repository} として {@code null} が渡された場合。
     */
    public ElementBody( final ModelRepository repository , final Element metaClass , final UUID uuid ) throws IllegalArgumentException {
        super() ;
        if ( null == repository ) {
            throw new IllegalArgumentException( "repository should not be null." ) ;
        }
        this.repository = repository ;
        this.metaClass = ( null == metaClass ) ? null : metaClass.toReference() ;
        this.uuid = ( null == uuid ) ? UUID.randomUUID() : uuid ;
    }

    // @Category accessing

    @Override
    public void addObserver( final ElementObserver observer ) throws IllegalArgumentException {
        if ( null == observer ) {
            throw new IllegalArgumentException( "observer should not be null." ) ;
        }
        synchronized ( this.observers ) {
            this.observers.add( observer ) ;
        }
    }

    @Override
    public Element getMetaClass() {
        return this.metaClass ;
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
        synchronized ( this.observers ) {
            this.observers.clear() ;
        }
    }

    @Override
    public void removeObserver( final ElementObserver observer ) throws IllegalArgumentException {
        if ( null == observer ) {
            throw new IllegalArgumentException( "observer should not be null." ) ;
        }
        synchronized ( this.observers ) {
            this.observers.remove( observer ) ;
        }
    }

    // @Category comparing

    @Override
    public boolean equals( final Object other ) {
        return ( other instanceof Element ) && ( this.getUuid().equals( ( (Element)( other ) ).getUuid() ) ) ;
    }

    @Override
    public int hashCode() {
        return this.getUuid().hashCode() ;
    }

    // @Category converting

    @Override
    public ElementBody toBody() {
        return this ;
    }

    @Override
    public ElementReference toReference() {
        return new ElementReference( this.repository , this.uuid ) ;
    }

    // @Category event handling

    @Override
    public void elementChanged( final ElementChangedEvent event ) {}

}
