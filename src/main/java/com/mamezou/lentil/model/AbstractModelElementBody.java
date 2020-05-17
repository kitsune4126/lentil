package com.mamezou.lentil.model ;

import java.util.HashSet ;
import java.util.Set ;
import java.util.UUID ;

import com.fasterxml.jackson.annotation.JsonIgnore ;
import com.fasterxml.jackson.annotation.JsonProperty ;
import com.mamezou.lentil.repository.ModelRepository ;

/**
 * モデル要素本体を表わすオブジェクトのための抽象クラス。
 *
 * @author kitsune
 */
public abstract class AbstractModelElementBody implements ModelElement {

    // @Category instance variables

    /**
     * このモデル要素を直接監視しているオブザーバの集合。
     */
    @JsonIgnore
    private final Set< ModelElementObserver > observers = new HashSet< ModelElementObserver >() ;

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
     * {@link #uuid} としてランダムな {@link UUID} が設定されるコンストラクタ。
     *
     * @param repository モデル要素が属するリポジトリ。
     * @throws IllegalArgumentException {@code repository} として {@code null} が指定された場合。
     */
    protected AbstractModelElementBody( final ModelRepository repository ) throws IllegalArgumentException {
        this( repository , UUID.randomUUID() ) ;
    }

    /**
     * コンストラクタ。
     *
     * @param repository モデル要素が属するリポジトリ。
     * @param uuid モデル要素に対してユニークに付けられたID。
     * @throws IllegalArgumentException {@code repository} または {@code uuid} として {@code null} が指定された場合。
     */
    protected AbstractModelElementBody( final ModelRepository repository , final UUID uuid ) throws IllegalArgumentException {
        super() ;
        if ( ( null == repository ) || ( null == uuid ) ) {
            throw new IllegalArgumentException( "repository and uuid should not be null." ) ;
        }
        this.repository = repository ;
        this.uuid = uuid ;
    }

    // @Category accessing

    @Override
    public void addObserver( final ModelElementObserver observer ) throws IllegalArgumentException {
        if ( null == observer ) {
            throw new IllegalArgumentException( "observer should not be null." ) ;
        }
        synchronized ( this.observers ) {
            this.observers.add( observer ) ;
        }
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
    public void removeObserver( final ModelElementObserver observer ) throws IllegalArgumentException {
        if ( null == observer ) {
            throw new IllegalArgumentException( "observer should not be null." ) ;
        }
        synchronized ( this.observers ) {
            this.observers.remove( observer ) ;
        }
    }

    // @Category change notifications

    /**
     * レシーバのオブザーバ群を返す。
     *
     * @return レシーバのオブザーバ群。
     */
    protected Set< ModelElementObserver > collectObservers() {
        final Set< ModelElementObserver > collectedObservers = new HashSet< ModelElementObserver >() ;
        synchronized ( this.observers ) {
            collectedObservers.addAll( this.observers ) ;
        }
        collectedObservers.add( this.repository ) ;
        return collectedObservers ;
    }

    /**
     * レシーバのすべてのオブザーバに対して変更通知を発行する。
     *
     * @param aspect 変更通知の種類を示す文字列。
     * @param cause 変更通知を発行する原因となった他の変更通知。変更通知が他の変更通知によらず発行されるものであれば {@code null} を指定する。
     * @param parameters 変更通知に付加されるパラメータの配列。パラメータ不要な変更通知の場合は {@code null} を指定しても良い。
     */
    protected void notifyObservers( final String aspect , final ModelElementChangedEvent cause , final Object[] parameters ) {
        assert ( null != aspect ) ;
        final ModelElementChangedEvent changedEvent = new ModelElementChangedEvent( this , aspect , cause , parameters ) ;
        for ( final ModelElementObserver observer : this.collectObservers() ) {
            observer.modelElementChanged( changedEvent ) ;
        }
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

}
