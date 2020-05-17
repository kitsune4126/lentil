package com.mamezou.lentil.repository ;

/**
 * リポジトリにすでに登録されているキーの値を変更しようとした場合に投げられる例外。
 *
 * @author kitsune
 */
public class ElementAlreadyExistsException extends Exception {

    // @Category instance creation

    public ElementAlreadyExistsException() {
        super() ;
    }

    public ElementAlreadyExistsException( final String message ) {
        super( message ) ;
    }

    public ElementAlreadyExistsException( final Throwable cause ) {
        super( cause ) ;
    }

    public ElementAlreadyExistsException( final String message , final Throwable cause ) {
        super( message , cause ) ;
    }

}
