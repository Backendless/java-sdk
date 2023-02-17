package com.backendless.push;

import com.backendless.BackendlessInjector;
import com.backendless.BackendlessPrefs;
import com.backendless.DeviceRegistration;
import com.backendless.Invoker;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.exceptions.ExceptionMessage;

import java.util.Date;
import java.util.List;


public class DeviceRegistrationUtil
{
  final static String DEVICE_REGISTRATION_MANAGER_SERVER_ALIAS = "com.backendless.services.messaging.DeviceRegistrationService";

  private final static DeviceRegistrationUtil instance = new DeviceRegistrationUtil();
  private final BackendlessPrefs prefs = BackendlessInjector.getInstance().getPrefs();

  private DeviceRegistrationUtil()
  {
  }

  public static DeviceRegistrationUtil getInstance()
  {
    return instance;
  }

  public static String getDeviceRegistrationManagerServerAlias()
  {
    return DEVICE_REGISTRATION_MANAGER_SERVER_ALIAS;
  }

  public String registerDeviceOnServer( String deviceToken, final List<String> channels, final long expiration )
  {
    if( deviceToken == null )
      throw new IllegalArgumentException( ExceptionMessage.NULL_DEVICE_TOKEN );

    DeviceRegistration deviceRegistration = new DeviceRegistration();
    deviceRegistration.setDeviceId( prefs.getDeviceId() );
    deviceRegistration.setOs( prefs.getOs() );
    deviceRegistration.setOsVersion( prefs.getOsVersion() );
    deviceRegistration.setDeviceToken( deviceToken );
    deviceRegistration.setChannels( channels );
    if( expiration != 0 )
      deviceRegistration.setExpiration( new Date( expiration ) );

    return Invoker.invokeSync( DEVICE_REGISTRATION_MANAGER_SERVER_ALIAS, "registerDevice", new Object[] { deviceRegistration } );
  }

  public void registerDeviceOnServer( String deviceToken, final List<String> channels, final long expiration, final AsyncCallback<String> responder )
  {
    try
    {
      if( deviceToken == null )
        throw new IllegalArgumentException( ExceptionMessage.NULL_DEVICE_TOKEN );

      DeviceRegistration deviceRegistration = new DeviceRegistration();
      deviceRegistration.setDeviceId( prefs.getDeviceId() );
      deviceRegistration.setOs( prefs.getOs() );
      deviceRegistration.setOsVersion( prefs.getOsVersion() );
      deviceRegistration.setDeviceToken( deviceToken );
      deviceRegistration.setChannels( channels );
      if( expiration != 0 )
        deviceRegistration.setExpiration( new Date( expiration ) );

      Invoker.invokeAsync( DEVICE_REGISTRATION_MANAGER_SERVER_ALIAS, "registerDevice", new Object[] { deviceRegistration }, new AsyncCallback<String>()
      {
        @Override
        public void handleResponse( String response )
        {
          if( responder != null )
            responder.handleResponse( response );
        }

        @Override
        public void handleFault( BackendlessFault fault )
        {
          if( responder != null )
            responder.handleFault( fault );
        }
      } );
    }
    catch( Throwable e )
    {
      if( responder != null )
        responder.handleFault( new BackendlessFault( e ) );
    }
  }

  public boolean unregisterDeviceOnServer()
  {
    return Invoker.invokeSync( DEVICE_REGISTRATION_MANAGER_SERVER_ALIAS, "unregisterDevice", new Object[] { prefs.getDeviceId() } );
  }

  public void unregisterDeviceOnServer( final AsyncCallback<Boolean> responder )
  {
    Invoker.invokeAsync( DEVICE_REGISTRATION_MANAGER_SERVER_ALIAS, "unregisterDevice", new Object[] { prefs.getDeviceId() }, responder );
  }

  public int unregisterDeviceOnServer( List<String> channels )
  {
    return Invoker.invokeSync( DEVICE_REGISTRATION_MANAGER_SERVER_ALIAS, "unregisterDevice", new Object[] { prefs.getDeviceId(), channels } );
  }

  public void unregisterDeviceOnServer( List<String> channels, final AsyncCallback<Integer> responder )
  {
    Invoker.invokeAsync( DEVICE_REGISTRATION_MANAGER_SERVER_ALIAS, "unregisterDevice", new Object[] { prefs.getDeviceId(), channels }, responder );
  }
}
