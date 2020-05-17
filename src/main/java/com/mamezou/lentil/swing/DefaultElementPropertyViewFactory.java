package com.mamezou.lentil.swing ;

import org.springframework.context.ConfigurableApplicationContext ;

import com.mamezou.lentil.repository.FileBasedModelRepository ;

/**
 * デフォルトの {@link ElementPropertyViewFactory} 。
 *
 * @author kitsune
 */
public class DefaultElementPropertyViewFactory implements ElementPropertyViewFactory {

    // @Category factory methods

    @Override
    public ElementPropertyView createPrimaryPropertyView( String elementTypeName , ConfigurableApplicationContext context ) throws IllegalArgumentException {
        switch ( elementTypeName ) {
        case FileBasedModelRepository.ELEMENT_TYPE_NAME :
            return new FileBasedModelRepositoryViewPrimary( context ) ;
        default :
            throw new IllegalArgumentException( "Unknown type. elementTypeName = " + elementTypeName ) ;
        }
    }

    @Override
    public ElementPropertyView createSecondaryPropertyView( String elementTypeName , ConfigurableApplicationContext context ) throws IllegalArgumentException {
        return null ;
    }

}
