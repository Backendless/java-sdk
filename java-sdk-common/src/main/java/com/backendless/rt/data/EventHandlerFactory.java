package com.backendless.rt.data;

import com.backendless.rt.AbstractListenerFactory;

import java.util.Map;


public class EventHandlerFactory extends AbstractListenerFactory<EventHandler<?>>
{
  private static final EventHandlerFactory instance = new EventHandlerFactory();

  public static EventHandlerFactory getInstance()
  {
    return instance;
  }

  private EventHandlerFactory()
  {
  }

  @SuppressWarnings( "unchecked" )
  public <T> EventHandler<T> of( final Class<T> entity )
  {
    return new EventHandlerImpl<>( entity );
  }

  @SuppressWarnings( "unchecked" )
  public EventHandler<Map> of( final String tableName )
  {
    return new EventHandlerImpl<>( tableName );
  }

  @Override
  protected EventHandler<?> create( String name )
  {
    throw new IllegalStateException( "Use direct implementation with 'of()' methods." );
  }
}
