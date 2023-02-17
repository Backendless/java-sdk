package com.backendless.rt;


public abstract class AbstractListenerFactory<T extends RTListener>
{
  abstract protected T create( final String name );
}
