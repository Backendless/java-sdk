package com.backendless;


public interface ContextHandler
{
  Object getAppContext();

  void setContext( Object context );
}
