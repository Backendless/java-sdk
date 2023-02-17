package com.backendless.core;


public class AndroidCarrierFactory
{
  private static final AndroidCarrierFactory instance = new AndroidCarrierFactory();

  public static AndroidCarrierFactory getInstance()
  {
    return instance;
  }

  private AndroidCarrierFactory()
  {
  }

  public IHandleCarrier getHandlerCarrier()
  {
    return AndroidCarrier.getInstance();
  }
}
