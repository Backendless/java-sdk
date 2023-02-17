package com.backendless.rt.rso;

import com.backendless.rt.AbstractListenerFactory;


public class SharedObjectFactory extends AbstractListenerFactory<SharedObject>
{
  private static final SharedObjectFactory instance = new SharedObjectFactory();

  public static SharedObjectFactory getInstance()
  {
    return instance;
  }

  public SharedObject get( final String name )
  {
    return create( name );
  }

  @Override
  protected SharedObject create( String name )
  {
    return new SharedObjectImpl( name );
  }
}
