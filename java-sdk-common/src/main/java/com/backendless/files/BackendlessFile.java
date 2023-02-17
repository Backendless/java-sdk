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

package com.backendless.files;

import com.backendless.BackendlessInjector;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import lombok.Getter;


public class BackendlessFile
{
  private final BackendlessInjector injector = BackendlessInjector.getInstance();
  @Getter
  private String fileURL;

  public BackendlessFile( String fileURL )
  {
    this.fileURL = fileURL;
  }

  public int remove() throws BackendlessException
  {
    return injector.getFiles().remove( fileURL );
  }

  public void remove( AsyncCallback<Integer> responder )
  {
    injector.getFiles().remove( fileURL, responder );
  }
}
