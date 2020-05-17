package com.mamezou.lentil.repository ;

import java.util.UUID ;

import com.mamezou.lentil.model.ModelElement ;
import com.mamezou.lentil.model.ModelElementChangedEvent ;
import com.mamezou.lentil.model.ModelElementObserver ;

/**
 * 何もしないダミーのモデル・リポジトリ。
 *
 * @author kitsune
 */
public class DummyModelRepository implements ModelRepository {

    // @Category accessing

    @Override
    public void addObserver( ModelElementObserver observer ) throws IllegalArgumentException {}

    @Override
    public ModelElement getModelElement( UUID key ) throws ElementNotFoundException {
        throw new ElementNotFoundException() ;
    }

    @Override
    public String getName() {
        return "DUMMY" ; //$NON-NLS-1$
    }

    @Override
    public String getTypeName() {
        return "DummyModelRepository" ; //$NON-NLS-1$
    }

    @Override
    public UUID getUuid() {
        return null ;
    }

    @Override
    public void putModelElement( UUID key , ModelElement element ) throws ElementAlreadyExistsException {
        throw new ElementAlreadyExistsException() ;
    }

    @Override
    public void removeAllObservers() {}

    @Override
    public void removeModelElement( UUID key ) throws ElementNotFoundException {}

    @Override
    public void removeObserver( ModelElementObserver observer ) throws IllegalArgumentException {}

    @Override
    public void setName( String newName ) throws IllegalArgumentException {
        throw new IllegalArgumentException( "DummyModelRepository can not set new name. newName = " + newName ) ; //$NON-NLS-1$
    }

    // @Category converting

    @Override
    public String toString() {
        return this.getName() ;
    }

    // @Category event handling

    @Override
    public void modelElementChanged( ModelElementChangedEvent event ) {}

}
