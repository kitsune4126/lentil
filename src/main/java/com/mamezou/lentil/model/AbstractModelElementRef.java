package com.mamezou.lentil.model ;

import java.util.UUID ;

import com.fasterxml.jackson.annotation.JsonIgnore ;
import com.fasterxml.jackson.annotation.JsonProperty ;
import com.mamezou.lentil.repository.ModelRepository ;

/**
 * モデル要素への参照を表わすオブジェクトのための抽象クラス。
 *
 * @author kitsune
 */
public abstract class AbstractModelElementRef< E extends ModelElement > implements ModelElement {

    // @Category instance variables

    /**
     * このオブジェクトによって参照されているモデル要素。
     */
    @JsonIgnore
    private E body = null ;

    /**
     * このモデル要素が属するリポジトリ。
     */
    @JsonIgnore
    private final ModelRepository repository ;

    /**
     * このモデル要素に対してユニークに付けられた識別子。
     */
    @JsonProperty( "uuid" )
    private final UUID uuid ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param repository モデル要素が属するリポジトリ。
     * @param uuid モデル要素に対してユニークに付けられたID。
     * @throws IllegalArgumentException {@code repository} または {@code uuid} として {@code null} が指定された場合。
     */
    protected AbstractModelElementRef( final ModelRepository repository , final UUID uuid ) throws IllegalArgumentException {
        super() ;
        if ( null == uuid ) {
            throw new IllegalArgumentException( "repository and uuid should not be null." ) ;
        }
        this.repository = repository ;
        this.uuid = uuid ;
    }

    // @Category accessing

    @Override
    public void addObserver( final ModelElementObserver observer ) throws IllegalArgumentException {
        this.getBody().addObserver( observer ) ;
    }

    /**
     * レシーバの参照先のモデル要素本体を返す。
     *
     * @return 参照先のモデル要素本体。
     */
    protected E getBody() {
        if ( null == this.body ) {
            this.body = this.loadBody() ;
        }
        return this.body ;
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
    public void removeObserver( final ModelElementObserver observer ) throws IllegalArgumentException {
        this.getBody().removeObserver( observer ) ;
    }

    // @Category comparing

    @Override
    public boolean equals( Object obj ) {
        return ( obj instanceof ModelElement ) ? this.getUuid().equals( ( (ModelElement)( obj ) ).getUuid() ) : false ;
    }

    @Override
    public int hashCode() {
        return this.getUuid().hashCode() ;
    }

    // @Category event handling

    @Override
    public void modelElementChanged( final ModelElementChangedEvent event ) {
        // do nothing at top abstract level.
    }

    // @Category repository accessing

    /**
     * レシーバの参照先本体のモデル要素をリポジトリから取り出して返す。
     *
     * @return 参照先本体のモデル要素。
     */
    protected abstract E loadBody() ;

}
