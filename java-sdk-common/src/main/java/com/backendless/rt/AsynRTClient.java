package com.backendless.rt;

import com.backendless.BackendlessInjector;
import com.backendless.ThreadPoolService;
import com.backendless.async.callback.Fault;
import com.backendless.async.callback.Result;


class AsynRTClient implements RTClient
{
  private static final RTClient rtClient = new RTClientSocketIO();
  private final ThreadPoolService threadPoolService = BackendlessInjector.getInstance().getThreadPoolService();

  AsynRTClient()
  {
  }

  @Override
  public void subscribe( final RTSubscription subscription )
  {
    threadPoolService.getThreadPoolExecutor().execute( new Runnable()
    {
      @Override
      public void run()
      {
        rtClient.subscribe( subscription );
      }
    } );
  }

  @Override
  public void unsubscribe( final String subscriptionId )
  {
    threadPoolService.getThreadPoolExecutor().execute( new Runnable()
    {
      @Override
      public void run()
      {
        rtClient.unsubscribe( subscriptionId );
      }
    } );
  }

  @Override
  public void userLoggedIn( final String userToken )
  {
    threadPoolService.getThreadPoolExecutor().execute( new Runnable()
    {
      @Override
      public void run()
      {
        rtClient.userLoggedIn( userToken );
      }
    } );
  }

  @Override
  public void userLoggedOut()
  {
    threadPoolService.getThreadPoolExecutor().execute( new Runnable()
    {
      @Override
      public void run()
      {
        rtClient.userLoggedOut();
      }
    } );
  }

  @Override
  public void invoke( final RTMethodRequest methodRequest )
  {
    threadPoolService.getThreadPoolExecutor().execute( new Runnable()
    {
      @Override
      public void run()
      {
        rtClient.invoke( methodRequest );
      }
    } );
  }

  @Override
  public void setConnectEventListener( Result<Void> callback )
  {
    rtClient.setConnectEventListener( callback );
  }

  @Override
  public void setReconnectAttemptEventListener( Result<ReconnectAttempt> callback )
  {
    rtClient.setReconnectAttemptEventListener( callback );
  }

  @Override
  public void setConnectErrorEventListener( Fault fault )
  {
    rtClient.setConnectErrorEventListener( fault );
  }

  @Override
  public void setDisconnectEventListener( Result<String> callback )
  {
    rtClient.setDisconnectEventListener( callback );
  }

  @Override
  public boolean isConnected()
  {
    return rtClient.isConnected();
  }

  @Override
  public void connect()
  {
    threadPoolService.getThreadPoolExecutor().execute( new Runnable()
    {
      @Override
      public void run()
      {
        rtClient.connect();
      }
    } );
  }

  @Override
  public void disconnect()
  {
    rtClient.disconnect();
  }

  @Override
  public boolean isAvailable()
  {
    return rtClient.isAvailable();
  }
}
