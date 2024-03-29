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

package com.backendless.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


public class BackendlessException extends RuntimeException
{
  private static final long serialVersionUID = -7537447408166433783L;
  private BackendlessFault backendlessFault;
  @Getter @Setter
  private int httpStatusCode = 400;

  public BackendlessException()
  {
  }

  public BackendlessException( String message )
  {
    super( message );
    backendlessFault = new BackendlessFault( message );
  }

  public BackendlessException( String message, int httpStatusCode )
  {
    this( message );
    this.httpStatusCode = httpStatusCode;
  }

  public BackendlessException( String message, Throwable throwable )
  {
    super( message, throwable );
    this.backendlessFault = new BackendlessFault( message );
  }

  public BackendlessException( String message, Throwable throwable, int httpStatusCode )
  {
    this( message, throwable );
    this.httpStatusCode = httpStatusCode;
  }

  public BackendlessException( Throwable throwable )
  {
    super( throwable );
    this.backendlessFault = new BackendlessFault( throwable );
  }

  public BackendlessException( String code, String message )
  {
    super( message );
    this.backendlessFault = new BackendlessFault( code, message );
  }

  public BackendlessException( String code, String message, int httpStatusCode )
  {
    this( code, message );
    this.httpStatusCode = httpStatusCode;
  }

  public BackendlessException( BackendlessFault fault )
  {
    super( fault.getMessage() == null ? fault.getDetail() : fault.getMessage() );
    backendlessFault = fault;
  }

  public String getCode()
  {
    return backendlessFault.getCode();
  }

  @Override
  public String getMessage()
  {
    final String message = backendlessFault.getMessage();
    return message == null ? backendlessFault.getDetail() : message;
  }

  public String getDetail()
  {
    return backendlessFault.getDetail();
  }

  public Map<String, Object> getExtendedData()
  {
    return backendlessFault.getExtendedData();
  }

  @Override
  public String toString()
  {
    return getClass().getSimpleName() +
            "{ code: '" + getCode() + '\'' +
            ", message: '" + getMessage() + '\'' +
            ", detail: '" + getDetail() + '\'' +
            ", extendedData: '" + getExtendedData() + '\'' +
            ", httpStatusCode: '" + getHttpStatusCode() + '\'' +
            " }";
  }
}
