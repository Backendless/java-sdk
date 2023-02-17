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

import android.content.Context;
import android.content.SharedPreferences;


class AndroidUserIdStorage extends AbstractStorage<String>
{
  @Override
  public String get()
  {
    Context appContext = (Context) contextHandler.getAppContext();
    return appContext.getSharedPreferences( USER_ID_KEY, Context.MODE_PRIVATE ).getString( USER_ID_KEY, "" );
  }

  @Override
  public void set( String value )
  {
    Context appContext = (Context) contextHandler.getAppContext();
    SharedPreferences.Editor editor = appContext.getSharedPreferences( USER_ID_KEY, Context.MODE_PRIVATE ).edit();
    editor.putString( USER_ID_KEY, value );
    editor.commit();
  }
}
