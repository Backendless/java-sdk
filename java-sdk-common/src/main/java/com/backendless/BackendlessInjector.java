package com.backendless;

import com.backendless.core.IHandleCarrier;
import com.backendless.files.BackendlessFileCreator;
import com.backendless.persistence.local.IStorage;
import com.backendless.rt.RTClient;
import com.backendless.rt.RTService;
import com.backendless.rt.data.EventHandlerFactory;
import com.backendless.rt.messaging.ChannelFactory;
import com.backendless.rt.rso.SharedObjectFactory;


public class BackendlessInjector
{
  private static final BackendlessInjector instance = new BackendlessInjector();

  private boolean isAndroid;
  private boolean isCodeRunner;
  private ContextHandler contextHandler;
  private BackendlessPrefs prefs;
  private IStorage<String> userIdStorage;
  private IStorage<String> userTokenStorage;

  private FootprintsManager footprintsManager;
  private UserService userService;
  private Persistence persistence;
  private Messaging messaging;
  private Files files;
  private Commerce commerce;
  private Events events;
  private Cache cache;
  private Counters counters;
  private CustomService customService;
  private Logging logging;
  private RTService rtService;

  private EventHandlerFactory eventHandlerFactory;
  private ChannelFactory channelFactory;
  private SharedObjectFactory sharedObjectFactory;
  private IHandleCarrier handleCarrier;
  private BackendlessFileCreator backendlessFileCreator;
  private ThreadPoolService threadPoolService;
  private RTClient rtClient;
  private IHeadersManager headersManager;


  public static BackendlessInjector getInstance()
  {
    return instance;
  }

  private BackendlessInjector()
  {
  }

  public boolean isAndroid()
  {
    return isAndroid;
  }

  void setAndroid( boolean isAndroid )
  {
    this.isAndroid = isAndroid;
  }

  public boolean isCodeRunner()
  {
    return isCodeRunner;
  }

  void setCodeRunner( boolean isCodeRunner )
  {
    this.isCodeRunner = isCodeRunner;
  }

  public ContextHandler getContextHandler()
  {
    return contextHandler;
  }

  void setContextHandler( ContextHandler contextHandler )
  {
    this.contextHandler = contextHandler;
  }

  public BackendlessPrefs getPrefs()
  {
    return prefs;
  }

  void setPrefs( BackendlessPrefs prefs )
  {
    this.prefs = prefs;
  }

  public IStorage<String> getUserIdStorage()
  {
    return userIdStorage;
  }

  void setUserIdStorage( IStorage<String> userIdStorage )
  {
    this.userIdStorage = userIdStorage;
  }

  public IStorage<String> getUserTokenStorage()
  {
    return userTokenStorage;
  }

  void setUserTokenStorage( IStorage<String> userTokenStorage )
  {
    this.userTokenStorage = userTokenStorage;
  }

  public com.backendless.FootprintsManager getFootprintsManager()
  {
    return footprintsManager;
  }

  void setFootprintsManager( com.backendless.FootprintsManager footprintsManager )
  {
    this.footprintsManager = footprintsManager;
  }

  public com.backendless.UserService getUserService()
  {
    return userService;
  }

  void setUserService( com.backendless.UserService userService )
  {
    this.userService = userService;
  }

  public com.backendless.Persistence getPersistence()
  {
    return persistence;
  }

  void setPersistence( com.backendless.Persistence persistence )
  {
    this.persistence = persistence;
  }

  public Messaging getMessaging()
  {
    return messaging;
  }

  void setMessaging( Messaging messaging )
  {
    this.messaging = messaging;
  }

  public com.backendless.Files getFiles()
  {
    return files;
  }

  void setFiles( com.backendless.Files files )
  {
    this.files = files;
  }

  public com.backendless.Commerce getCommerce()
  {
    return commerce;
  }

  void setCommerce( com.backendless.Commerce commerce )
  {
    this.commerce = commerce;
  }

  public com.backendless.Events getEvents()
  {
    return events;
  }

  void setEvents( com.backendless.Events events )
  {
    this.events = events;
  }

  public com.backendless.Cache getCache()
  {
    return cache;
  }

  void setCache( com.backendless.Cache cache )
  {
    this.cache = cache;
  }

  public com.backendless.Counters getCounters()
  {
    return counters;
  }

  void setCounters( com.backendless.Counters counters )
  {
    this.counters = counters;
  }

  public com.backendless.CustomService getCustomService()
  {
    return customService;
  }

  void setCustomService( com.backendless.CustomService customService )
  {
    this.customService = customService;
  }

  public com.backendless.Logging getLogging()
  {
    return logging;
  }

  void setLogging( com.backendless.Logging logging )
  {
    this.logging = logging;
  }

  public RTService getRtService()
  {
    return rtService;
  }

  void setRtService( RTService rtService )
  {
    this.rtService = rtService;
  }

  public EventHandlerFactory getEventHandlerFactory()
  {
    return eventHandlerFactory;
  }

  void setEventHandlerFactory( EventHandlerFactory eventHandlerFactory )
  {
    this.eventHandlerFactory = eventHandlerFactory;
  }

  public ChannelFactory getChannelFactory()
  {
    return channelFactory;
  }

  void setChannelFactory( ChannelFactory channelFactory )
  {
    this.channelFactory = channelFactory;
  }

  public SharedObjectFactory getSharedObjectFactory()
  {
    return sharedObjectFactory;
  }

  void setSharedObjectFactory( SharedObjectFactory sharedObjectFactory )
  {
    this.sharedObjectFactory = sharedObjectFactory;
  }

  public IHandleCarrier getHandleCarrier()
  {
    return handleCarrier;
  }

  void setHandleCarrier( IHandleCarrier handleCarrier )
  {
    this.handleCarrier = handleCarrier;
  }

  public BackendlessFileCreator getBackendlessFileCreator()
  {
    return backendlessFileCreator;
  }

  void setBackendlessFileCreator( BackendlessFileCreator backendlessFileCreator )
  {
    this.backendlessFileCreator = backendlessFileCreator;
  }

  public ThreadPoolService getThreadPoolService()
  {
    return threadPoolService;
  }

  void setThreadPoolService( ThreadPoolService threadPoolService )
  {
    this.threadPoolService = threadPoolService;
  }

  public RTClient getRtClient()
  {
    return rtClient;
  }

  void setRtClient( RTClient rtClient )
  {
    this.rtClient = rtClient;
  }

  public IHeadersManager getHeadersManager()
  {
    return headersManager;
  }

  void setHeadersManager( IHeadersManager headersManager )
  {
    this.headersManager = headersManager;
  }
}
