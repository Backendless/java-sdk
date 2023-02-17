package com.backendless;

import com.backendless.commons.DeviceType;
import com.backendless.exceptions.BackendlessException;

import java.util.Map;


class BLHeadersManager implements IHeadersManager
{
  private static final BLHeadersManager instance = new BLHeadersManager();

  static BLHeadersManager getInstance()
  {
    return instance;
  }

  private final ThreadLocal<PlainHeadersManager> threadLocal;

  private BLHeadersManager()
  {
    threadLocal = new InheritableThreadLocal<PlainHeadersManager>()
    {
      @Override
      protected PlainHeadersManager initialValue()
      {
        return new BLHeadersManagerHolder();
      }
    };
  }

  public void cleanHeaders()
  {
    threadLocal.remove();
  }

  @Override
  public void addHeader( IHeadersManager.HeadersEnum headersEnum, String value )
  {
    threadLocal.get().addHeader( headersEnum, value );
  }

  @Override
  public void addHeader( String name, String value )
  {
    threadLocal.get().addHeader( name, value );
  }

  @Override
  public void addHeaders( Map<String, String> headers )
  {
    threadLocal.get().addHeaders( headers );
  }

  @Override
  public void removeHeader( IHeadersManager.HeadersEnum headersEnum )
  {
    threadLocal.get().removeHeader( headersEnum );
  }

  @Override
  public void removeHeader( String name )
  {
    threadLocal.get().removeHeader( name );
  }

  @Override
  public Map<String, String> getHeaders() throws BackendlessException
  {
    return threadLocal.get().getHeaders();
  }

  @Override
  public void setHeaders( Map<String, String> headers )
  {
    threadLocal.get().setHeaders( headers );
  }

  @Override
  public String getHeader( IHeadersManager.HeadersEnum headersEnum ) throws BackendlessException
  {
    return threadLocal.get().getHeader( headersEnum );
  }

  @Override
  public String getHeader( String name )
  {
    return threadLocal.get().getHeader( name );
  }

  private static class BLHeadersManagerHolder extends PlainHeadersManager
  {
    private BLHeadersManagerHolder()
    {
      super();
    }

    @Override
    public void cleanHeaders()
    {
      super.cleanHeaders();
      addHeader( IHeadersManager.HeadersEnum.APP_TYPE_NAME, DeviceType.BL.name() );
      addHeader( IHeadersManager.HeadersEnum.API_VERSION, "1.0" );
      addHeaders( BackendlessInjector.getInstance().getPrefs().getHeaders() ); // TODO: shouldn't it be also ThreadLocal?
    }
  }
}


