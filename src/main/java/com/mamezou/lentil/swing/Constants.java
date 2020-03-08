/* Constants.java */
package com.mamezou.lentil.swing ;

import java.util.Locale ;

import org.springframework.context.support.ReloadableResourceBundleMessageSource ;

/**
 * 各種定数定義のアクセスを行う utility クラス。
 *
 * @author kitsune
 */
public class Constants {

    private static ReloadableResourceBundleMessageSource CachedMessageSource = null ;

    private static ReloadableResourceBundleMessageSource getMessageSource() {
        if ( null == CachedMessageSource ) {
            CachedMessageSource = new ReloadableResourceBundleMessageSource() ;
            CachedMessageSource.setDefaultEncoding( "UTF-8" ) ;
            CachedMessageSource.setBasename( "classpath:messages" ) ;
            CachedMessageSource.setUseCodeAsDefaultMessage( true ) ;
        }
        return CachedMessageSource ;
    }

    public static String getMessage( final String code ) {
        return getMessage( code , null , Locale.getDefault() ) ;
    }

    public static String getMessage( final String code , final Object[] args ) {
        return getMessage( code , args , Locale.getDefault() ) ;
    }

    public static String getMessage( final String code , final Object[] args , final Locale locale ) {
        return getMessageSource().getMessage( code , args , locale ) ;
    }

}
