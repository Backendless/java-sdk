package com.backendless.rt.messaging;

import com.backendless.rt.AbstractListenerFactory;


public class ChannelFactory extends AbstractListenerFactory<Channel>
{
  private static final ChannelFactory instance = new ChannelFactory();

  public static ChannelFactory getInstance()
  {
    return instance;
  }

  @Override
  public Channel create( final String channel )
  {
    return new ChannelImpl( channel );
  }
}
