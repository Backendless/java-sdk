package com.backendless;

import com.backendless.core.JavaCarrierFactory;
import com.backendless.hive.Hive;
import com.backendless.hive.HiveManagement;
import com.backendless.rt.RTService;

import java.util.Map;


public final class Backendless
{
  private Backendless()
  {
  }

  // it is only necessary for static initialization in interface
  private static final boolean isEnvInitialized = initEnvironment();

  private static boolean initEnvironment()
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
    injector.setMessaging( GeneralMessaging.getInstance() );

    return true;
  }

  public static final FootprintsManager FootprintsManager = BackendlessInjector.getInstance().getFootprintsManager();
  public static final UserService UserService = BackendlessInjector.getInstance().getUserService();
  public static final Persistence Data = BackendlessInjector.getInstance().getPersistence();
  public static final Messaging Messaging = BackendlessInjector.getInstance().getMessaging();
  public static final Files Files = BackendlessInjector.getInstance().getFiles();
  public static final Commerce Commerce = BackendlessInjector.getInstance().getCommerce();
  public static final Events Events = BackendlessInjector.getInstance().getEvents();
  @Deprecated
  public static final Cache Cache = BackendlessInjector.getInstance().getCache();
  @Deprecated
  public static final Counters Counters = BackendlessInjector.getInstance().getCounters();
  public static final CustomService CustomService = BackendlessInjector.getInstance().getCustomService();
  public static final Logging Logging = BackendlessInjector.getInstance().getLogging();
  public static final RTService RT = BackendlessInjector.getInstance().getRtService();

  public static HiveManagement Hive()
  {
    return BackendlessInternal.Hive();
  }

  public static Hive Hive( String name )
  {
    return BackendlessInternal.Hive( name );
  }

  public static boolean isAndroid()
  {
    return BackendlessInjector.getInstance().isAndroid();
  }

  public static boolean isCodeRunner()
  {
    return BackendlessInjector.getInstance().isCodeRunner();
  }

  public static boolean isInitialized()
  {
    return BackendlessInternal.isInitialized();
  }

  public static BackendlessPrefs getPrefs()
  {
    return BackendlessInternal.getPrefs();
  }

  public static void initApp( String customDomain )
  {
    BackendlessInternal.initApp( customDomain );
  }

  public static void initApp( Object context, final String customDomain )
  {
    BackendlessInternal.initApp( context, customDomain );
  }

  public static void initApp( String applicationId, String apiKey )
  {
    BackendlessInternal.initApp( applicationId, apiKey );
  }

  public static void initApp( Object context, final String applicationId, final String apiKey )
  {
    BackendlessInternal.initApp( context, applicationId, apiKey );
  }

  public static void initApplicationFromProperties()
  {
    BackendlessInternal.initApplicationFromProperties();
  }

  public static void initApplicationFromProperties( Object context )
  {
    BackendlessInternal.initApplicationFromProperties( context );
  }

  public static void setUrl( String url )
  {
    BackendlessInternal.setUrl( url );
  }

  public static void setUIState( String state )
  {
    BackendlessInternal.setUIState( state );
  }

  public static String getApplicationIdOrDomain()
  {
    return BackendlessInternal.getApplicationIdOrDomain();
  }

  public static String getApiKey()
  {
    return BackendlessInternal.getApiKey();
  }

  public static String getUrl()
  {
    return BackendlessInternal.getUrl();
  }

  public static Map<String, String> getHeaders()
  {
    return BackendlessInternal.getHeaders();
  }

  public static String getApplicationUrl()
  {
    return BackendlessInternal.getApplicationUrl();
  }
}
