package com.backendless;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import com.backendless.exceptions.ExceptionMessage;
import com.backendless.utils.AndroidIO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AndroidBackendlessPrefs extends BackendlessPrefs
{
  private SharedPreferences sharedPreferences;
  private boolean initialized = false;

  AndroidBackendlessPrefs()
  {
    super();
  }

  @Override
  protected void retrieveDeviceId()
  {
    this.deviceId = Settings.Secure.getString( ((Context) getContext()).getContentResolver(), Settings.Secure.ANDROID_ID );
  }

  @Override
  protected void retrieveOS()
  {
    this.osVersion = String.valueOf( Build.VERSION.SDK_INT );
  }

  @Override
  protected void retrieveOSVersion()
  {
    this.os = "ANDROID";
  }

  @Override
  public void init()
  {
    this.sharedPreferences = ((Context) getContext()).getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE );
    this.restoreAuthKeysFromPreferences();
    super.init();
  }

  @Override
  public void initPreferences( String applicationId, String apiKey )
  {
    super.initPreferences( applicationId, apiKey );
    saveAuthKeysToPreferences( authKeys );
  }

  @Override
  public void setHeaders( Map<String, String> headers )
  {
    super.setHeaders( headers );
    saveHeadersToPreferences( headers );
  }

  @Override
  public void cleanHeaders()
  {
    super.cleanHeaders();
    cleanHeadersFromPreferences();
  }

  @Override
  public void setUrl( String url )
  {
    if( sharedPreferences == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString( Type.URL_KEY.name64(), url );
    editor.commit();

    this.url = url;
  }

  @Override
  public String getUrl()
  {
    if( sharedPreferences == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    if( this.url == null )
      this.url = sharedPreferences.getString( Type.URL_KEY.name64(), Backendless.getUrl() );

    return this.url;
  }

  @Override
  public void setCustomDomain( String customDomain )
  {
    if( sharedPreferences == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString( Type.CUSTOM_DOMAIN_KEY.name64(), customDomain );
    editor.commit();

    this.customDomain = customDomain;
  }

  @Override
  public String getCustomDomain()
  {
    if( sharedPreferences == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    if( this.customDomain == null )
      this.customDomain = sharedPreferences.getString( Type.CUSTOM_DOMAIN_KEY.name64(), Backendless.getUrl() );

    return this.customDomain;
  }

  @Override
  public synchronized HashMap<String, String> getHeaders()
  {
    if( !initialized )
    {
      restoreHeadersFromPreferences();
      initialized = true;
    }

    return headers;
  }

  private boolean restoreHeadersFromPreferences()
  {
    if( sharedPreferences == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    String rawHeaders = sharedPreferences.getString( Type.HEADERS.name64(), null );

    if( rawHeaders != null )
    {
      try
      {
        Map restoredHeaders = AndroidIO.mapFromString( rawHeaders );
        this.headers.clear();
        this.headers.putAll( restoredHeaders );
      }
      catch( Exception e )
      {
        return false;
      }

      return true;
    }

    return false;
  }

  protected boolean restoreAuthKeysFromPreferences()
  {
    if( sharedPreferences == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    String applicationId = sharedPreferences.getString( Type.APPLICATION_ID_KEY.name64(), null );
    String apiKey = sharedPreferences.getString( Type.API_KEY.name64(), null );

    if( applicationId != null && apiKey != null )
    {
      authKeys = new AuthKeys( applicationId, apiKey );
      return true;
    }

    return false;
  }

  private void saveHeadersToPreferences( Map<String, String> headers )
  {
    try
    {
      SharedPreferences.Editor editor = sharedPreferences.edit();
      String rawHeaders = AndroidIO.mapToString( headers );
      editor.putString( Type.APPLICATION_ID_KEY.name64(), rawHeaders );
      editor.commit();
    }
    catch( IOException e )
    {
      //ignored
    }
  }

  private void saveAuthKeysToPreferences( AuthKeys authKeys )
  {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString( Type.APPLICATION_ID_KEY.name64(), authKeys.getApplicationId() );
    editor.putString( Type.API_KEY.name64(), authKeys.getApiKey() );
    editor.commit();
  }

  private void cleanHeadersFromPreferences()
  {
    if( sharedPreferences == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    if( sharedPreferences.contains( Type.HEADERS.name64() ) )
    {
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.remove( Type.HEADERS.name64() );
      editor.commit();
    }
  }

  public String getPushTemplateAsJson()
  {
    if( pushTemplatesAsJson == null )
      this.pushTemplatesAsJson = sharedPreferences.getString( Type.PUSH_TEMPLATES.name64(), null );

    return pushTemplatesAsJson;
  }

  public void savePushTemplateAsJson( String pushTemplatesAsJson )
  {
    this.pushTemplatesAsJson = pushTemplatesAsJson;
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString( Type.PUSH_TEMPLATES.name64(), pushTemplatesAsJson );
    editor.commit();
  }

  public int getNotificationIdGeneratorInitValue()
  {
    if( notificationIdGeneratorState.get() == null )
      this.notificationIdGeneratorState.set( sharedPreferences.getInt( Type.NOTIFICATION_ID_GENERATOR.name64(), 0 ) );

    return notificationIdGeneratorState.get();
  }

  public void saveNotificationIdGeneratorState( int value )
  {
    this.notificationIdGeneratorState.set( value );
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt( Type.NOTIFICATION_ID_GENERATOR.name64(), value );
    editor.commit();
  }
}
