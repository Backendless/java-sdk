package com.backendless;

import com.backendless.exceptions.ExceptionMessage;
import com.backendless.messaging.AndroidPushTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


public abstract class BackendlessPrefs
{
  static final String PREFS_NAME = "BackendlessPrefs";

  protected AuthKeys authKeys;
  protected final HashMap<String, String> headers = new HashMap<>();
  protected String url;
  protected String customDomain;
  protected String deviceId;
  protected String os;
  protected String osVersion;
  protected String pushTemplatesAsJson;
  protected final HashMap<String, AndroidPushTemplate> pushNotificationTemplates = new HashMap<>();
  protected final AtomicReference<Integer> notificationIdGeneratorState = new AtomicReference<>();

  BackendlessPrefs()
  {
  }

  abstract protected void retrieveDeviceId();

  abstract protected void retrieveOS();

  abstract protected void retrieveOSVersion();

  abstract protected boolean restoreAuthKeysFromPreferences();

  protected synchronized AuthKeys getAuthKeys()
  {
    if( authKeys == null )
      restoreAuthKeysFromPreferences();

    if( authKeys == null && getCustomDomain() == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    return authKeys;
  }

  public void init()
  {
    this.retrieveDeviceId();
    this.retrieveOS();
    this.retrieveOSVersion();
    this.restorePushTemplates();
  }

  public void initPreferences( String applicationId, String apiKey )
  {
    this.authKeys = new AuthKeys( applicationId, apiKey );
  }

  public synchronized void setHeaders( Map<String, String> headers )
  {
    this.headers.clear();
    this.headers.putAll( headers );
  }

  public synchronized void cleanHeaders()
  {
    headers.clear();
  }

  public String getApplicationId()
  {
    final AuthKeys authKeys = getAuthKeys();
    return authKeys == null ? null : authKeys.getApplicationId();
  }

  public String getApiKey()
  {
    final AuthKeys authKeys = getAuthKeys();
    return authKeys == null ? null : authKeys.getApiKey();
  }

  public synchronized HashMap<String, String> getHeaders()
  {
    return headers;
  }

  public void setUrl( String url )
  {
    this.url = url;
  }

  public String getUrl()
  {
    return this.url;
  }

  public String getCustomDomain()
  {
    return customDomain;
  }

  public void setCustomDomain( String customDomain )
  {
    this.customDomain = customDomain;
  }

  public String getApplicationIdOrDomain()
  {
    final String appId = this.getApplicationId();
    if( appId != null )
      return appId;

    final String customDomain = this.getCustomDomain();
    if( customDomain != null )
      return customDomain;

    throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );
  }

  public Object getContext()
  {
    return BackendlessInjector.getInstance().getContextHandler().getAppContext();
  }

  public String getDeviceId()
  {
    return deviceId;
  }

  public String getOs()
  {
    return os;
  }

  public String getOsVersion()
  {
    return osVersion;
  }

  public String getPushTemplateAsJson()
  {
    return pushTemplatesAsJson;
  }

  public void savePushTemplateAsJson( String pushTemplatesAsJson )
  {
    this.pushTemplatesAsJson = pushTemplatesAsJson;
  }

  public int getNotificationIdGeneratorInitValue()
  {
    return notificationIdGeneratorState.get();
  }

  public void saveNotificationIdGeneratorState( int value )
  {
    this.notificationIdGeneratorState.set( value );
  }

  public Map<String, AndroidPushTemplate> getPushNotificationTemplates()
  {
    return Collections.unmodifiableMap( pushNotificationTemplates );
  }

  public AndroidPushTemplate getPushNotificationTemplate( String templateName )
  {
    return this.pushNotificationTemplates.get( templateName );
  }

  protected void restorePushTemplates()
  {
    if( getPushTemplateAsJson() == null )
      return;

    Map<String, AndroidPushTemplate> templates;
    try
    {
      templates = (Map<String, AndroidPushTemplate>) weborb.util.io.Serializer.fromBytes( getPushTemplateAsJson().getBytes(), weborb.util.io.Serializer.JSON, false );
      pushNotificationTemplates.clear();
      pushNotificationTemplates.putAll( templates );
    }
    catch( IOException e )
    {
      throw new RuntimeException( "Cannot deserialize AndroidPushTemplate to JSONObject.", e );
    }
  }

  public void setPushNotificationTemplates( Map<String, AndroidPushTemplate> pushNotificationTemplates )
  {
    this.pushNotificationTemplates.clear();
    this.pushNotificationTemplates.putAll( pushNotificationTemplates );

    try
    {
      byte[] rawTemplates = weborb.util.io.Serializer.toBytes( pushNotificationTemplates, weborb.util.io.Serializer.JSON );
      savePushTemplateAsJson( new String( rawTemplates ) );
    }
    catch( Exception e )
    {
      throw new RuntimeException( "Cannot serialize AndroidPushTemplate to JSONObject.", e );
    }
  }

  enum Type
  {
    APPLICATION_ID_KEY, API_KEY, URL_KEY, CUSTOM_DOMAIN_KEY, HEADERS, PUSH_TEMPLATES, NOTIFICATION_ID_GENERATOR;

    String name64()
    {
      return UUID.nameUUIDFromBytes( this.name().getBytes() ).toString();
    }
  }
}
