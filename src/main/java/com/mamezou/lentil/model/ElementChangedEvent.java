package com.mamezou.lentil.model ;

/**
 * モデル要素の変更通知を表わすオブジェクト。
 *
 * @author kitsune
 */
public class ElementChangedEvent {

    // @Category instance variables

    /**
     * この変更通知の種類を示す文字列。
     */
    private final String aspect ;

    /**
     * この変更通知を発行する原因となった他の変更通知。
     * この変更通知が他の変更通知によらず発行されたものであれば {@code null} が設定される。
     */
    private final ElementChangedEvent cause ;

    /**
     * この変更通知に付加されたパラメータの配列。
     * パラメータとしてどのようなデータが格納されるかは変更通知の種類によって異なる。
     */
    private final Object[] parameters ;

    /**
     * この変更通知を直接発行したモデル要素。
     */
    private final Element sender ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param sender 変更通知を直接発行したモデル要素。
     * @param aspect 変更通知の種類を示す文字列。
     * @throws IllegalArgumentException {@code sender} か {@code aspect} として {@code null} が渡された場合。
     */
    public ElementChangedEvent( final Element sender , final String aspect ) throws IllegalArgumentException {
        this( sender , aspect , null , null ) ;
    }

    /**
     * コンストラクタ。
     *
     * @param sender 変更通知を直接発行したモデル要素。
     * @param aspect 変更通知の種類を示す文字列。
     * @param cause 変更通知を発行する原因となった他の変更通知。他の変更通知によらない場合は {@code null} を指定する。
     * @throws IllegalArgumentException {@code sender} か {@code aspect} として {@code null} が渡された場合。
     */
    public ElementChangedEvent( final Element sender , final String aspect , final ElementChangedEvent cause ) throws IllegalArgumentException {
        this( sender , aspect , cause , null ) ;
    }

    /**
     * コンストラクタ。
     *
     * @param sender 変更通知を直接発行したモデル要素。
     * @param aspect 変更通知の種類を示す文字列。
     * @param parameters 変更通知に付加されたパラメータの配列。
     * @throws IllegalArgumentException {@code sender} か {@code aspect} として {@code null} が渡された場合。
     */
    public ElementChangedEvent( final Element sender , final String aspect , final Object[] parameters ) throws IllegalArgumentException {
        this( sender , aspect , null , parameters ) ;
    }

    /**
     * コンストラクタ。
     *
     * @param sender 変更通知を直接発行したモデル要素。
     * @param aspect 変更通知の種類を示す文字列。
     * @param cause 変更通知を発行する原因となった他の変更通知。他の変更通知によらない場合は {@code null} を指定する。
     * @param parameters 変更通知に付加されたパラメータの配列。
     * @throws IllegalArgumentException {@code sender} か {@code aspect} として {@code null} が渡された場合。
     */
    public ElementChangedEvent( final Element sender , final String aspect , final ElementChangedEvent cause , final Object[] parameters ) throws IllegalArgumentException {
        if ( ( null == sender ) || ( null == aspect ) ) {
            throw new IllegalArgumentException( "sender and aspect should not be null." ) ;
        }
        this.sender = sender ;
        this.aspect = aspect ;
        this.cause = cause ;
        this.parameters = parameters ;
    }

    // @Category accessing

    /**
     * この変更通知の種類を示す文字列を返す。
     *
     * @return aspect この変更通知の種類を示す文字列。
     */
    public String getAspect() {
        return this.aspect ;
    }

    /**
     * この変更通知を発行する原因となった他の変更通知を返す。
     *
     * @return cause この変更通知を発行する原因となった他の変更通知。この変更通知が他の変更通知によらず発行されたものであれば {@code null} が返される。
     */
    public ElementChangedEvent getCause() {
        return this.cause ;
    }

    /**
     * この変更通知に付加されたパラメータの配列を返す。
     * パラメータとしてどのようなデータが格納されるかは変更通知の種類によって異なる。
     *
     * @return parameters この変更通知に付加されたパラメータの配列。{@code null} が返される場合もある。
     */
    public Object[] getParameters() {
        return this.parameters ;
    }

    /**
     * この変更通知を直接発行したモデル要素を返す。
     *
     * @return sender この変更通知を直接発行したモデル要素。
     */
    public Element getSender() {
        return this.sender ;
    }

    // @Category testing

    /**
     * 指定されたモデル要素がこの変更通知とその元になった変更通知の発行元モデル要素群の中に含まれているか否かを返す。
     *
     * @param element 指定されたモデル要素。
     * @return レシーバのセンダ・リストに含まれているか否か。含まれている場合は {@code true}、そうでなければ {@code false}。
     * @throws IllegalArgumentException {@code element} として {@code null} が渡された場合。
     */
    public boolean sendersIncludes( final Element element ) throws IllegalArgumentException {
        if ( null == element ) {
            throw new IllegalArgumentException( "element should not be null." ) ;
        }
        if ( this.getSender().equals( element ) ) {
            return true ;
        } else if ( null == this.getCause() ) {
            return false ;
        } else {
            return this.getCause().sendersIncludes( element ) ;
        }
    }

}
