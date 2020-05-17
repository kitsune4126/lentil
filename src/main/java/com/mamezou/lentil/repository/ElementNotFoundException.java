package com.mamezou.lentil.repository ;

/**
 * リポジトリに指定されたキーに対応するモデル要素が登録されていなかった(リトリーブできなかった)時に投げられる例外。
 *
 * @author kitsune
 */
public class ElementNotFoundException extends Exception {

    // @Category instance creation

    public ElementNotFoundException() {
        super() ;
    }

    public ElementNotFoundException( final String message ) {
        super( message ) ;
    }

    public ElementNotFoundException( final Throwable cause ) {
        super( cause ) ;
    }

    public ElementNotFoundException( final String message , final Throwable cause ) {
        super( message , cause ) ;
    }

}
