package com.backendless;

import com.backendless.commons.DeviceType;
import com.backendless.exceptions.BackendlessException;


class JavaHeadersManager extends PlainHeadersManager
{
  private static final JavaHeadersManager instance = new JavaHeadersManager();

  static JavaHeadersManager getInstance() throws BackendlessException
  {
    return instance;
  }

  private JavaHeadersManager()
  {
  }

  @Override
  public void cleanHeaders()
  {
    super.cleanHeaders();
    addHeader( IHeadersManager.HeadersEnum.APP_TYPE_NAME, DeviceType.CUSTOM.name() );
    addHeader( IHeadersManager.HeadersEnum.API_VERSION, "1.0" );
    addHeaders( BackendlessInjector.getInstance().getPrefs().getHeaders() );
  }
}
