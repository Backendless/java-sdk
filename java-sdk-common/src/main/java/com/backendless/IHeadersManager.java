package com.backendless;

import lombok.Getter;

import java.util.Map;


public interface IHeadersManager
{
  void cleanHeaders();

  void addHeader( HeadersEnum headersEnum, String value );

  void addHeader( String name, String value );

  void addHeaders( Map<String, String> headers );

  void removeHeader( HeadersEnum headersEnum );

  void removeHeader( String name );

  Map<String, String> getHeaders();

  void setHeaders( Map<String, String> headers );

  String getHeader( HeadersEnum headersEnum );

  String getHeader( String name );

  enum HeadersEnum
  {
    USER_TOKEN_KEY( "user-token" ),
    LOGGED_IN_KEY( "logged-in" ),
    SESSION_TIME_OUT_KEY( "session-time-out" ),
    APP_TYPE_NAME( "application-type" ),
    API_VERSION( "api-version" ),
    UI_STATE( "uiState" );

    @Getter
    private final String header;

    HeadersEnum( String header )
    {
      this.header = header;
    }
  }
}
