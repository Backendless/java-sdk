package com.backendless;


class ContextHandlerJavaImpl implements ContextHandler
{
  private Object appContext;

  ContextHandlerJavaImpl()
  {
  }

  @Override
  public Object getAppContext()
  {
    return this.appContext;
  }

  @Override
  public void setContext( Object anyContext )
  {
    this.appContext = anyContext;
  }
}
