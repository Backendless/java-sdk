package com.backendless;

import com.backendless.persistence.local.IStorage;


abstract class AbstractStorage<T> implements IStorage<T>
{
  protected final ContextHandler contextHandler;

  AbstractStorage()
  {
    this.contextHandler = BackendlessInjector.getInstance().getContextHandler();
  }
}
