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

package com.backendless.cache;

import com.backendless.Cache;
import com.backendless.async.callback.AsyncCallback;

import java.util.Date;


/**
 * Use Hive.
 * @param <T>
 */
@Deprecated
public class CacheService<T> implements ICache<T>
{
  private final Class<? extends T> clazz;

  private final String key;
  private final Cache cache;

  public CacheService( Class<? extends T> clazz, String key, Cache cache )
  {
    this.clazz = clazz;
    this.key = key;
    this.cache = cache;
  }

  @Override
  public void put( T value, AsyncCallback<Object> callback )
  {
    cache.put( key, value, callback );
  }

  @Override
  public void put( T value, int timeToLive, AsyncCallback<Object> callback )
  {
    cache.put( key, value, timeToLive, callback );
  }

  @Override
  public void get( AsyncCallback<T> callback )
  {
    cache.get( key, callback );
  }

  @Override
  public void contains( AsyncCallback<Boolean> callback )
  {
    cache.contains( key, callback );
  }

  @Override
  public void expireIn( int seconds, AsyncCallback<Object> callback )
  {
    cache.expireIn( key, seconds, callback );
  }

  @Override
  public void expireIn( int seconds )
  {
    cache.expireIn( key, seconds );
  }

  @Override
  public void expireAt( Date date, AsyncCallback<Object> callback )
  {
    cache.expireAt( key, date, callback );
  }

  @Override
  public void expireAt( long timestamp, AsyncCallback<Object> callback )
  {
    cache.expireAt( key, timestamp, callback );
  }

  @Override
  public void expireAt( Date date )
  {
    cache.expireAt( key, date );
  }

  @Override
  public void expireAt( long timestamp )
  {
    cache.expireAt( key, timestamp );
  }

  @Override
  public void delete( AsyncCallback<Object> callback )
  {
    cache.delete( key, callback );
  }

  @Override
  public void delete()
  {
    cache.delete( key );
  }

  @Override
  public void put( T value )
  {
    cache.put( key, value );
  }

  @Override
  public void put( T value, int timeToLive )
  {
    cache.put( key, value, timeToLive );
  }

  @Override
  public T get()
  {
    return cache.get( key, clazz );
  }

  @Override
  public Boolean contains()
  {
    return cache.contains( key );
  }
}
