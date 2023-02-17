package com.backendless;

import com.backendless.core.JavaCarrierFactory;
import com.backendless.hive.Hive;
import com.backendless.hive.HiveManagement;
import com.backendless.rt.RTService;

import java.util.Map;


public interface Backendless
{
  // it is only necessary for static initialization in interface
  boolean isEnvInitialized = initEnvironment();

  static boolean initEnvironment()
  {
    final BackendlessInjector injector = BackendlessInjector.getInstance();

    injector.setAndroid( Platform.isAndroid() );
    injector.setCodeRunner( Platform.isCodeRunner() );

    injector.setContextHandler( new ContextHandlerJavaImpl() );
    final JavaBackendlessPrefs prefs = new JavaBackendlessPrefs();
    prefs.init();
    injector.setPrefs( prefs );

    if( injector.isCodeRunner() )
    {
      injector.setUserIdStorage( new CodeRunnerUserIdStorage() );
      injector.setUserTokenStorage( new CodeRunnerUserTokenStorage() );
      injector.setHeadersManager( BLHeadersManager.getInstance() );
    }
    else
    {
      injector.setUserIdStorage( new JavaUserIdStorage() );
      injector.setUserTokenStorage( new JavaUserTokenStorage() );
      injector.setHeadersManager( JavaHeadersManager.getInstance() );
    }

    injector.setHandleCarrier( JavaCarrierFactory.getInstance().getHandlerCarrier() );
    injector.setBackendlessFileCreator( new BackendlessFileBasicCreator() );
    BackendlessInternal.init();
    injector.setMessaging( com.backendless.GeneralMessaging.getInstance() );

    return true;
  }

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
}
