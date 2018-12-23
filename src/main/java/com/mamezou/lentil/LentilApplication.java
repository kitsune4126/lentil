package com.mamezou.lentil ;

import javax.swing.JFrame ;
import org.springframework.boot.autoconfigure.SpringBootApplication ;
import org.springframework.boot.builder.SpringApplicationBuilder ;
import org.springframework.context.ConfigurableApplicationContext ;

@SpringBootApplication
public class LentilApplication {

    public static void main( final String ... args ) {
        try ( final ConfigurableApplicationContext context = new SpringApplicationBuilder( LentilApplication.class ).headless( false ).run( args ) ) {
            LentilApplication application = context.getBean( LentilApplication.class ) ;
            application.run( args ) ;
        } catch ( Exception e ) {
            e.printStackTrace() ;
        }
    }

    public void run( String ... args ) throws Exception {
        final JFrame frame = new JFrame( "Lentil Main Window" ) ;
        frame.setSize( 640 , 480 ) ;
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
        frame.setVisible( true ) ;
    }

}
