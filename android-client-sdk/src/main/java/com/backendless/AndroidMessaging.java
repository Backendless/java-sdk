package com.backendless;

import android.content.Context;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.ExceptionMessage;
import com.backendless.push.DeviceRegistrationResult;
import com.backendless.push.FCMRegistration;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


class AndroidMessaging extends GeneralMessaging
{
  static final AndroidMessaging instance = new AndroidMessaging();

  static AndroidMessaging getInstance()
  {
    return instance;
  }

  private AndroidMessaging()
  {
    super();
  }

  public void registerDevice( List<String> channels, Date expiration, AsyncCallback<DeviceRegistrationResult> callback )
  {
    if( channels == null || channels.isEmpty() || (channels.size() == 1 && (channels.get( 0 ) == null || channels.get( 0 ).isEmpty())) )
    {
      channels = Collections.singletonList( DEFAULT_CHANNEL_NAME );
    }

    for( String channel : channels )
      checkChannelName( channel );

    long expirationMs = 0;
    if( expiration != null )
    {
      if( expiration.before( Calendar.getInstance().getTime() ) )
        throw new IllegalArgumentException( ExceptionMessage.WRONG_EXPIRATION_DATE );
      else
        expirationMs = expiration.getTime();
    }
    FCMRegistration.registerDevice( (Context) contextHandler.getAppContext(), channels, expirationMs, callback );
  }

  public void unregisterDevice( final List<String> channels, final AsyncCallback<Integer> callback )
  {
    FCMRegistration.unregisterDevice( (Context) contextHandler.getAppContext(), channels, callback );
  }
}
