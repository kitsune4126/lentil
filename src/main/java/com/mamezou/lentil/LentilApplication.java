package com.mamezou.lentil ;

import javax.swing.JFrame ;
import org.springframework.boot.autoconfigure.SpringBootApplication ;
import org.springframework.boot.builder.SpringApplicationBuilder ;
import org.springframework.context.ConfigurableApplicationContext ;

/**
 * Lentil ( スタンドアロン、GUI 版 ) のメイン・アプリケーション・クラス。
 *
 * @author kitsune@mamezou.com
 */
@SpringBootApplication
public class LentilApplication {

    /**
     * Lentil ( スタンドアロン、GUI 版 ) のメイン・メソッド。
     *
     * @param args コマンド・ラインで指定されたパラメータ文字列の配列。
     */
    public static void main( final String ... args ) {
        try ( final ConfigurableApplicationContext context = new SpringApplicationBuilder( LentilApplication.class ).headless( false ).run( args ) ) {
            LentilApplication application = context.getBean( LentilApplication.class ) ;
            application.run( args ) ;
        } catch ( Exception e ) {
            e.printStackTrace() ;
        }
    }

    /**
     * Lentil ( スタンドアロン、GUI 版 ) の実質的なメイン・メソッド。
     *
     * @param args コマンド・ラインで指定されたパラメータ文字列の配列。
     * @throws Exception アプリケーションの初期化やウインドウ・オープン時に何らかの例外が発生した場合。
     */
    public void run( String ... args ) throws Exception {
        final JFrame frame = new JFrame( "Lentil Main Window" ) ;
        frame.setSize( 640 , 480 ) ;
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
        frame.setVisible( true ) ;
    }

}
