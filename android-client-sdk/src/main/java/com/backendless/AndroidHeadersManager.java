package com.backendless;

import com.backendless.commons.DeviceType;
import com.backendless.exceptions.BackendlessException;


class AndroidHeadersManager extends PlainHeadersManager
{
  private static final AndroidHeadersManager instance = new AndroidHeadersManager();

  static AndroidHeadersManager getInstance() throws BackendlessException
  {
    return instance;
  }

  private AndroidHeadersManager()
  {
  }

  @Override
  public void cleanHeaders()
  {
    super.cleanHeaders();
    addHeader( IHeadersManager.HeadersEnum.APP_TYPE_NAME, DeviceType.ANDROID.name() );
    addHeader( IHeadersManager.HeadersEnum.API_VERSION, "1.0" );
    addHeaders( BackendlessInjector.getInstance().getPrefs().getHeaders() );
  }
}
