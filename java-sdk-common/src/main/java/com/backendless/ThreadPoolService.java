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

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPoolService
{
  private static final ThreadPoolService instance = new ThreadPoolService();

  static ThreadPoolService getInstance()
  {
    return instance;
  }

  private final SimpleThreadFactory THREAD_FACTORY;
  private final ThreadPoolExecutor THREAD_POOL_EXECUTOR;
  private final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR;

  private ThreadPoolService()
  {
    if( BackendlessInjector.getInstance().isCodeRunner() )
    {
      THREAD_FACTORY = new SimpleThreadFactory( "BackendlessSDK_CodeRunner" );
      THREAD_POOL_EXECUTOR = new ThreadPoolExecutor( 1, 5, 15, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), THREAD_FACTORY );

      SCHEDULED_THREAD_POOL_EXECUTOR = null;
    }
    else
    {
      THREAD_FACTORY = new SimpleThreadFactory( "BackendlessSDK" );
      THREAD_POOL_EXECUTOR = new ThreadPoolExecutor( 2, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), THREAD_FACTORY );

      SCHEDULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor( 2, THREAD_FACTORY );
    }
  }

  public ThreadPoolExecutor getThreadPoolExecutor()
  {
    return THREAD_POOL_EXECUTOR;
  }

  public ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor()
  {
    return SCHEDULED_THREAD_POOL_EXECUTOR;
  }

  private static class SimpleThreadFactory implements ThreadFactory
  {
    private final static ThreadGroup THREAD_GROUP = new ThreadGroup( "BackendlessSDK_ThreadGroup" );

    private final String threadNamePrefix;
    private final boolean isDaemon = true;
    private final AtomicInteger threadNumber = new AtomicInteger();

    public SimpleThreadFactory( String poolName )
    {
      this.threadNamePrefix = "pool-" + poolName + "-thread-";
    }

    @Override
    public Thread newThread( Runnable runnable )
    {
      Thread t = new Thread( THREAD_GROUP, runnable, threadNamePrefix + threadNumber.getAndIncrement() );
      t.setDaemon( isDaemon );
      return t;
    }
  }
}
