package com.backendless;

import com.backendless.core.AndroidCarrierFactory;
import com.backendless.hive.Hive;
import com.backendless.hive.HiveManagement;
import com.backendless.rt.RTService;

import java.util.Map;


public interface Backendless
{
  // ------------------------------ Initialization block ------------------------------

  // it is only necessary for static initialization in interface
  boolean isEnvInitialized = initEnvironment();

  static boolean initEnvironment()
  {
    final BackendlessInjector injector = BackendlessInjector.getInstance();

    injector.setAndroid( Platform.isAndroid() );
    injector.setCodeRunner( Platform.isCodeRunner() );

    final ContextHandlerAndroidImpl contextHandler = new ContextHandlerAndroidImpl();
    contextHandler.getAppContext();
    injector.setContextHandler( contextHandler );

    final AndroidBackendlessPrefs prefs = new AndroidBackendlessPrefs();
    prefs.init();
    injector.setPrefs( prefs );

    injector.setUserIdStorage( new AndroidUserIdStorage() );
    injector.setUserTokenStorage( new AndroidUserTokenStorage() );

    injector.setHandleCarrier( AndroidCarrierFactory.getInstance().getHandlerCarrier() );
    injector.setBackendlessFileCreator( new BackendlessFileAndroidCreator() );
    injector.setHeadersManager( AndroidHeadersManager.getInstance() );
    BackendlessInternal.init();
    injector.setMessaging( com.backendless.AndroidMessaging.getInstance() );

    Invoker.getWebOrbClient().setHostnameVerifier( new org.apache.http.conn.ssl.StrictHostnameVerifier() );

    return true;
  }


  // ------------------------------ Static fields for services ------------------------------

  FootprintsManager FootprintsManager = BackendlessInjector.getInstance().getFootprintsManager();
  UserService UserService = BackendlessInjector.getInstance().getUserService();
  Persistence Persistence = BackendlessInjector.getInstance().getPersistence();
  Persistence Data = BackendlessInjector.getInstance().getPersistence();
  Messaging Messaging = BackendlessInjector.getInstance().getMessaging();
  Files Files = BackendlessInjector.getInstance().getFiles();
  Commerce Commerce = BackendlessInjector.getInstance().getCommerce();
  Events Events = BackendlessInjector.getInstance().getEvents();
  Cache Cache = BackendlessInjector.getInstance().getCache();
  Counters Counters = BackendlessInjector.getInstance().getCounters();
  CustomService CustomService = BackendlessInjector.getInstance().getCustomService();
  Logging Logging = BackendlessInjector.getInstance().getLogging();
  RTService RT = BackendlessInjector.getInstance().getRtService();

  static HiveManagement Hive()
  {
    return BackendlessInternal.Hive();
  }

  static Hive Hive( String name )
  {
    return BackendlessInternal.Hive( name );
  }

  // ------------------------------ common client init methods ------------------------------

  static boolean isAndroid()
  {
    return BackendlessInjector.getInstance().isAndroid();
  }

  static boolean isCodeRunner()
  {
    return BackendlessInjector.getInstance().isCodeRunner();
  }

  static boolean isInitialized()
  {
    return BackendlessInternal.isInitialized();
  }

  static BackendlessPrefs getPrefs()
  {
    return BackendlessInternal.getPrefs();
  }

  static void initApp( String customDomain )
  {
    BackendlessInternal.initApp( customDomain );
  }

  static void initApp( Object context, final String customDomain )
  {
    BackendlessInternal.initApp( context, customDomain );
  }

  static void initApp( String applicationId, String apiKey )
  {
    BackendlessInternal.initApp( applicationId, apiKey );
  }

  static void initApp( Object context, final String applicationId, final String apiKey )
  {
    BackendlessInternal.initApp( context, applicationId, apiKey );
  }

  static void initApplicationFromProperties()
  {
    BackendlessInternal.initApplicationFromProperties();
  }

  static void initApplicationFromProperties( Object context )
  {
    BackendlessInternal.initApplicationFromProperties( context );
  }

  static void setUrl( String url )
  {
    BackendlessInternal.setUrl( url );
  }

  static void setUIState( String state )
  {
    BackendlessInternal.setUIState( state );
  }

  static String getApplicationIdOrDomain()
  {
    return BackendlessInternal.getApplicationIdOrDomain();
  }

  static String getApiKey()
  {
    return BackendlessInternal.getApiKey();
  }

  static String getUrl()
  {
    return BackendlessInternal.getUrl();
  }

  static Map<String, String> getHeaders()
  {
    return BackendlessInternal.getHeaders();
  }

  static String getApplicationUrl()
  {
    return BackendlessInternal.getApplicationUrl();
  }


  // ------------------------------ Android specific methods ------------------------------

  static void savePushTemplates( String pushTemplatesAsJson )
  {
    BackendlessInjector.getInstance().getPrefs().savePushTemplateAsJson( pushTemplatesAsJson );
  }

  static String getPushTemplatesAsJson()
  {
    return BackendlessInjector.getInstance().getPrefs().getPushTemplateAsJson();
  }

  static int getNotificationIdGeneratorInitValue()
  {
    return BackendlessInjector.getInstance().getPrefs().getNotificationIdGeneratorInitValue();
  }

  static void saveNotificationIdGeneratorState( int value )
  {
    BackendlessInjector.getInstance().getPrefs().saveNotificationIdGeneratorState( value );
  }
}
