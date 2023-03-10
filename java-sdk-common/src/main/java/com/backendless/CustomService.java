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

import com.backendless.async.callback.AsyncCallback;
import com.backendless.core.responder.AdaptingResponder;
import com.backendless.core.responder.policy.UniversalAdaptingPolicy;
import com.backendless.servercode.ExecutionType;

public class CustomService
{
  private static final String CUSTOM_SERVICE_ALIAS = "com.backendless.services.servercode.CustomServiceHandler";
  private static final String METHOD_NAME_ALIAS = "dispatchService";

  private static final CustomService instance = new CustomService();

  private CustomService()
  {

  }

  static CustomService getInstance()
  {
    return instance;
  }

  public <T> T invoke( String serviceName, String method, Object[] arguments )
  {
    Object[] args =  new Object[] { serviceName, method, arguments };
    return Invoker.invokeSync( CUSTOM_SERVICE_ALIAS, METHOD_NAME_ALIAS, args );
  }

  public <T> T invoke( String serviceName, String method, Object[] arguments, ExecutionType executionType )
  {
    Object[] args =  new Object[] { serviceName, method, arguments, executionType };
    return Invoker.invokeSync( CUSTOM_SERVICE_ALIAS, METHOD_NAME_ALIAS, args );
  }

   public <T> T invoke( String serviceName, String method, Object[] arguments, Class<?> clazz )
  {
    Object[] args = new Object[] { serviceName, method, arguments };
    return Invoker.invokeSync( CUSTOM_SERVICE_ALIAS, METHOD_NAME_ALIAS, args, new AdaptingResponder( clazz, new UniversalAdaptingPolicy() ) );
  }

  public <T> T invoke( String serviceName, String method, Object[] arguments, ExecutionType executionType, Class<?> clazz )
  {
    Object[] args = new Object[] { serviceName, method, arguments, executionType };
    return Invoker.invokeSync( CUSTOM_SERVICE_ALIAS, METHOD_NAME_ALIAS, args, new AdaptingResponder( clazz, new UniversalAdaptingPolicy() ) );
  }

  public <E> void invoke( String serviceName, String method, Object[] arguments, AsyncCallback<E> callback )
  {
    Object[] args = new Object[] { serviceName, method, arguments };
    Invoker.invokeAsync( CUSTOM_SERVICE_ALIAS, METHOD_NAME_ALIAS, args, callback );
  }

  public <E> void invoke( String serviceName, String method, Object[] arguments, ExecutionType executionType, AsyncCallback<E> callback )
  {
    Object[] args = new Object[] { serviceName, method, arguments, executionType };
    Invoker.invokeAsync( CUSTOM_SERVICE_ALIAS, METHOD_NAME_ALIAS, args, callback );
  }

  public <E> void invoke( String serviceName, String method, Object[] arguments, Class<?> clazz, AsyncCallback<E> callback )
  {
    Object[] args = new Object[] { serviceName, method, arguments };
    Invoker.invokeAsync( CUSTOM_SERVICE_ALIAS, METHOD_NAME_ALIAS, args, callback, new AdaptingResponder( clazz, new UniversalAdaptingPolicy() ) );
  }

  public <E> void invoke( String serviceName, String method, Object[] arguments, ExecutionType executionType, Class<?> clazz, AsyncCallback<E> callback )
  {
    Object[] args = new Object[] { serviceName, method, arguments, executionType };
    Invoker.invokeAsync( CUSTOM_SERVICE_ALIAS, METHOD_NAME_ALIAS, args, callback, new AdaptingResponder( clazz, new UniversalAdaptingPolicy() ) );
  }
}
