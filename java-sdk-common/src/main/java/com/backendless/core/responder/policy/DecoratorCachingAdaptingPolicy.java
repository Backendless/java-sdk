package com.backendless.core.responder.policy;

import com.backendless.BackendlessInjector;
import weborb.exceptions.AdaptingException;
import weborb.types.IAdaptingType;


public class DecoratorCachingAdaptingPolicy<E> extends AbstractAdaptingPolicyDecorator<E>
{
  public DecoratorCachingAdaptingPolicy( IAdaptingPolicy<E> adaptingPolicy )
  {
    super( adaptingPolicy );
  }

  @Override
  public void notifyInstanceCreated( Object instance, IAdaptingType entity ) throws AdaptingException
  {
    BackendlessInjector.getInstance().getFootprintsManager().Inner.putEntityFootprintToCache( instance, entity );
  }
}
