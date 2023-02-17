package com.backendless.rt;


@Deprecated
public class RTClientFactory
{
  private static final RTClientFactory instance = new RTClientFactory();

  public static RTClientFactory getInstance()
  {
    return instance;
  }

  private final RTClient rtClient;

  private RTClientFactory()
  {
    boolean socketIoPresent;
    try
    {
      RTClientFactory.class.getClassLoader().loadClass( "io.socket.emitter.Emitter" );
      socketIoPresent = true;
    }
    catch( ClassNotFoundException e )
    {
      socketIoPresent = false;
    }

    if( socketIoPresent )
      rtClient = new AsynRTClient();
    else
      rtClient = new RTClientWithoutSocketIO();
  }

  public RTClient get()
  {
    return rtClient;
  }
}
