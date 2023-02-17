package com.backendless;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


abstract class PlainHeadersManager implements IHeadersManager
{
  protected final ConcurrentHashMap<String, String> headers = new ConcurrentHashMap<>();

  protected PlainHeadersManager()
  {
    cleanHeaders();
  }

  @Override
  public void cleanHeaders()
  {
    headers.clear();
  }

  @Override
  public void addHeader( IHeadersManager.HeadersEnum headersEnum, String value )
  {
    headers.put( headersEnum.getHeader(), value );
  }

  @Override
  public void addHeader( String name, String value )
  {
    headers.put( name, value );
  }

  @Override
  public void addHeaders( Map<String, String> headers )
  {
    this.headers.putAll( headers );
  }

  @Override
  public void removeHeader( IHeadersManager.HeadersEnum headersEnum )
  {
    headers.remove( headersEnum.getHeader() );
  }

  @Override
  public void removeHeader( String name )
  {
    headers.remove( name );
  }

  @Override
  public Map<String, String> getHeaders()
  {
    return Collections.unmodifiableMap( headers );
  }

  @Override
  public void setHeaders( Map<String, String> headers )
  {
    cleanHeaders();
    this.headers.putAll( headers );
  }

  @Override
  public String getHeader( IHeadersManager.HeadersEnum headersEnum )
  {
    return headers.get( headersEnum.getHeader() );
  }

  @Override
  public String getHeader( String name )
  {
    return headers.get( name );
  }
}
