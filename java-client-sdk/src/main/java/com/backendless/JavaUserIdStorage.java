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

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


class JavaUserIdStorage extends AbstractStorage<String>
{
  private final Preferences prefs = Preferences.userRoot().node( this.getClass().getName() );

  @Override
  public String get()
  {
    return prefs.get( USER_ID_KEY, "" );
  }

  @Override
  public void set( String value )
  {
    prefs.put( USER_ID_KEY, value );
    try
    {
      prefs.flush();
    }
    catch( BackingStoreException e )
    {
      throw new RuntimeException( e );
    }
  }
}
