/*
 * ********************************************************************************************************************
 *  <p/>
 *  BACKENDLESS.COM CONFIDENTIAL
 *  <p/>
 *  ********************************************************************************************************************
 *  <p/>
 *  Copyright 2012 BACKENDLESS.COM. All Rights Reserved.
 *  <p/>
 *  NOTICE: All information contained herein is, and remains the property of Backendless.com and its suppliers,
 *  if any. The intellectual and technical concepts contained herein are proprietary to Backendless.com and its
 *  suppliers and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret
 *  or copyright law. Dissemination of this information or reproduction of this material is strictly forbidden
 *  unless prior written permission is obtained from Backendless.com.
 *  <p/>
 *  ********************************************************************************************************************
 */

package com.backendless;

import com.backendless.exceptions.ExceptionMessage;
import com.backendless.files.BackendlessFile;
import com.backendless.files.BackendlessFileFactory;
import com.backendless.hive.Hive;
import com.backendless.hive.HiveManagement;
import com.backendless.hive.ScanResult;
import com.backendless.hive.ScanResultFactory;
import com.backendless.io.BackendlessUserFactory;
import com.backendless.io.BackendlessUserWriter;
import com.backendless.io.DoubleWriter;
import com.backendless.persistence.BackendlessGeometryFactory;
import com.backendless.persistence.BackendlessGeometryWriter;
import com.backendless.persistence.BackendlessSerializer;
import com.backendless.persistence.Geometry;
import com.backendless.persistence.GeometryDTO;
import com.backendless.persistence.JsonDTO;
import com.backendless.persistence.JsonDTOAdaptingType;
import com.backendless.persistence.LineString;
import com.backendless.persistence.Point;
import com.backendless.persistence.Polygon;
import com.backendless.persistence.QueryOptions;
import com.backendless.persistence.RealmSerializer;
import com.backendless.rt.RTClientFactory;
import com.backendless.rt.data.EventHandlerFactory;
import com.backendless.rt.messaging.ChannelFactory;
import com.backendless.rt.rso.SharedObjectFactory;
import com.backendless.util.JSONUtil;
import com.backendless.utils.JSONConverterWeborbImpl;
import weborb.ORBConstants;
import weborb.config.ORBConfig;
import weborb.types.Types;
import weborb.util.ObjectFactories;
import weborb.util.log.ILoggingConstants;
import weborb.util.log.Log;
import weborb.writer.IProtocolFormatter;
import weborb.writer.ITypeWriter;
import weborb.writer.MessageWriter;
import weborb.writer.amf.AmfV3Formatter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


final class BackendlessInternal
{
  private static String url = "https://api.backendless.com";
  private static BackendlessPrefs prefs;
  private static boolean initialized;

  static void init()
  {
    final BackendlessInjector injector = BackendlessInjector.getInstance();
    prefs = injector.getPrefs();

    if( prefs.getUrl() == null )
      prefs.setUrl( BackendlessInternal.url );

    ORBConfig.getORBConfig();
    Log.removeLogger( ILoggingConstants.DEFAULT_LOGGER );
    JSONUtil.setJsonConverter( new JSONConverterWeborbImpl() );

    AmfV3Formatter.AddTypeWriter( QueryOptions.class, new ITypeWriter()
    {
      @Override
      public void write( Object o, IProtocolFormatter iProtocolFormatter ) throws IOException
      {
        QueryOptions queryOptions = (QueryOptions) o;
        Map<String, Object> queryOptionsMap = new HashMap<>();
        queryOptionsMap.put( ORBConstants.WEBORB_TYPE_NAME.toString(), QueryOptions.class.getSimpleName() );
        queryOptionsMap.put( "sortBy", queryOptions.getSortBy() );
        queryOptionsMap.put( "related", queryOptions.getRelated() );
        queryOptionsMap.put( "fileReferencePrefix", queryOptions.getFileReferencePrefix() );
        if( queryOptions.getRelationsDepth() != null )
          queryOptionsMap.put( "relationsDepth", queryOptions.getRelationsDepth() );
        if( queryOptions.getRelationsPageSize() != null )
          queryOptionsMap.put( "relationsPageSize", queryOptions.getRelationsPageSize() );

        MessageWriter.writeObject( queryOptionsMap, iProtocolFormatter );
      }

      @Override
      public boolean isReferenceableType()
      {
        return false;
      }
    } );

    injector.setEventHandlerFactory( EventHandlerFactory.getInstance() );
    injector.setChannelFactory( ChannelFactory.getInstance() );
    injector.setSharedObjectFactory( SharedObjectFactory.getInstance() );
    injector.setThreadPoolService( ThreadPoolService.getInstance() );
    injector.setRtClient( RTClientFactory.getInstance().get() );

    injector.setFootprintsManager( com.backendless.FootprintsManager.getInstance() );
    injector.setUserService( com.backendless.UserService.getInstance() );
    injector.setPersistence( com.backendless.Persistence.getInstance() );
    injector.setFiles( com.backendless.Files.getInstance() );
    injector.setCommerce( com.backendless.Commerce.getInstance() );
    injector.setEvents( com.backendless.Events.getInstance() );
    injector.setCache( com.backendless.Cache.getInstance() );
    injector.setCounters( com.backendless.Counters.getInstance() );
    injector.setCustomService( com.backendless.CustomService.getInstance() );
    injector.setLogging( com.backendless.Logging.getInstance() );
    injector.setRtService( com.backendless.rt.RTServiceImpl.getInstance() );

    try
    {
      if( !initialized && prefs.getApplicationId() != null && prefs.getApiKey() != null )
        BackendlessInternal.initApplicationFromProperties();
    }
    catch( IllegalStateException e )
    {
      if( !ExceptionMessage.NOT_INITIALIZED.equals( e.getMessage() ) )
        throw e;
    }
  }

  private BackendlessInternal()
  {
  }

  /**
   * Initializes the Backendless API and all Backendless dependencies. This is the first step in using the client API.
   * <p>
   * There is a low probability for internal API data to be cleared by the garbage collector.
   * In this case, an exception or fault, thrown by any of Backendless API methods, will contain 904 error code.
   *
   * @param applicationId a Backendless application ID, which could be retrieved at the Backendless console
   * @param apiKey        a Backendless application api key, which could be retrieved at the Backendless console
   */
  static void initApp( String applicationId, String apiKey )
  {
    initApp( BackendlessInjector.getInstance().getContextHandler().getAppContext(), applicationId, apiKey );
  }

  /**
   * Initializes the Backendless API and all Backendless dependencies. This is the first step in using the client API.
   * <p>
   * There is a low probability for internal API data to be cleared by the garbage collector.
   * In this case, an exception or fault, thrown by any of Backendless API methods, will contain 904 error code.
   *
   * @param customDomain custom domain which you setup in Backendless console https://backendless.com/docs/android/mgmt_custom_domain.html
   */
  static void initApp( String customDomain )
  {
    initApp( BackendlessInjector.getInstance().getContextHandler().getAppContext(), customDomain );
  }

  static boolean isInitialized()
  {
    return initialized;
  }

  static void initApp( Object context, final String customDomain )
  {
    if( customDomain == null || customDomain.trim().isEmpty() )
      throw new IllegalArgumentException( "Custom domain cant be null or empty" );

    if( customDomain.startsWith( "http" ) )
      setUrl( customDomain );
    else
      setUrl( "http://" + customDomain );

    URI uri;
    try
    {
      uri = new URI( getUrl() );
    }
    catch( URISyntaxException e )
    {
      throw new RuntimeException( e );
    }

    prefs.setCustomDomain( uri.getHost() );
    BackendlessInjector.getInstance().getContextHandler().setContext( context );
    initApp();
  }

  static void initApp( Object context, final String applicationId, final String apiKey )
  {
    if( applicationId == null || applicationId.equals( "" ) )
      throw new IllegalArgumentException( ExceptionMessage.NULL_APPLICATION_ID );

    if( apiKey == null || apiKey.equals( "" ) )
      throw new IllegalArgumentException( ExceptionMessage.NULL_API_KEY );

    prefs.initPreferences( applicationId, apiKey );
    BackendlessInjector.getInstance().getContextHandler().setContext( context );
    initApp();
  }

  private static void initApp()
  {
    if( BackendlessInjector.getInstance().isAndroid() && BackendlessInjector.getInstance().getContextHandler().getAppContext() == null )
      throw new IllegalArgumentException( ExceptionMessage.NULL_CONTEXT );

    MessageWriter.addTypeWriter( BackendlessUser.class, new BackendlessUserWriter() );
    MessageWriter.addTypeWriter( Double.class, new DoubleWriter() );
    MessageWriter.addTypeWriter( Geometry.class, new BackendlessGeometryWriter() );
    MessageWriter.addTypeWriter( Point.class, new BackendlessGeometryWriter() );
    MessageWriter.addTypeWriter( LineString.class, new BackendlessGeometryWriter() );
    MessageWriter.addTypeWriter( Polygon.class, new BackendlessGeometryWriter() );
    ObjectFactories.addArgumentObjectFactory( BackendlessUser.class.getName(), new BackendlessUserFactory() );
    ObjectFactories.addArgumentObjectFactory( BackendlessFile.class.getName(), new BackendlessFileFactory() );
    ObjectFactories.addArgumentObjectFactory( GeometryDTO.class.getName(), new BackendlessGeometryFactory() );
    ObjectFactories.addArgumentObjectFactory( Geometry.class.getName(), new BackendlessGeometryFactory() );
    ObjectFactories.addArgumentObjectFactory( Point.class.getName(), new BackendlessGeometryFactory() );
    ObjectFactories.addArgumentObjectFactory( LineString.class.getName(), new BackendlessGeometryFactory() );
    ObjectFactories.addArgumentObjectFactory( Polygon.class.getName(), new BackendlessGeometryFactory() );
    ObjectFactories.addArgumentObjectFactory( ScanResult.class.getName(), new ScanResultFactory() );
    Types.addClientClassMapping( JsonDTO.class.getName(), JsonDTOAdaptingType.class );
//    ObjectFactories.addArgumentObjectFactory( JsonDTO.class.getName(), new BackendlessJsonFactory() );

    BackendlessInjector.getInstance().getHeadersManager().cleanHeaders();
    Invoker.reinitialize();

    if( BackendlessInjector.getInstance().isCodeRunner() )
    {
      initialized = true;
      return;
    }

    final String userToken = BackendlessInjector.getInstance().getUserTokenStorage().get();
    if( userToken != null && !userToken.equals( "" ) )
      BackendlessInjector.getInstance().getHeadersManager().addHeader( IHeadersManager.HeadersEnum.USER_TOKEN_KEY, userToken );

    // check if Realm is present in classpath
    try
    {
      Class realmObjectClass = Class.forName( "io.realm.RealmObject" );
      BackendlessSerializer.addSerializer( realmObjectClass, new RealmSerializer() );
    }
    catch( Throwable ignore )
    {
    }
    initialized = true;
  }

  static BackendlessPrefs getPrefs()
  {
    return prefs;
  }

  static void setUIState( String state )
  {
    if( prefs == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    if( state == null || state.equals( "" ) )
      prefs.cleanHeaders();
    else
    {
      Map<String, String> headers = prefs.getHeaders();
      if( headers == null )
        headers = new HashMap<>();
      headers.put( IHeadersManager.HeadersEnum.UI_STATE.getHeader(), state );
      prefs.setHeaders( headers );
    }
  }

  static String getApplicationIdOrDomain()
  {
    if( prefs == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    return prefs.getApplicationIdOrDomain();
  }

  static String getApiKey()
  {
    if( prefs == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    return prefs.getApiKey();
  }

  protected static Map<String, String> getHeaders()
  {
    if( prefs == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    return prefs.getHeaders();
  }

  static String getUrl()
  {
    return url;
  }

  static void setUrl( String url )
  {
    if( url == null )
      throw new IllegalArgumentException( ExceptionMessage.NULL_URL );

    BackendlessInternal.url = url;
    prefs.setUrl( url );
    Invoker.reinitialize();
  }

  /**
   * @return url to call to backendless
   * If application initialized with appId and api key returns url with the following pattern
   * "http(s)://<backendless-host>/<appId>/<apiKey>/..."
   * Else if application initialized with domain returns url with the following pattern
   * "http(s)://<backendless-host>/..."
   */
  static String getApplicationUrl()
  {
    final String applicationId = prefs.getApplicationId();
    return applicationId == null ? getUrl() + "/api" : getUrl() + '/' + applicationId + '/' + getApiKey();
  }

  static void initApplicationFromProperties( Object context )
  {
    BackendlessInternal.initApp( context, prefs.getApplicationId(), prefs.getApiKey() );
    BackendlessInternal.setUrl( prefs.getUrl() );
  }

  static void initApplicationFromProperties()
  {
    BackendlessInternal.initApp( prefs.getApplicationId(), prefs.getApiKey() );
    BackendlessInternal.setUrl( prefs.getUrl() );
  }

  static HiveManagement Hive()
  {
    return HiveManagement.getInstance();
  }

  static Hive Hive( String name )
  {
    return Hive.getOrCreate( name );
  }
}
