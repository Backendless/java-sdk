package com.backendless.core;


public class JavaCarrierFactory
{
  private static final JavaCarrierFactory instance = new JavaCarrierFactory();

  public static JavaCarrierFactory getInstance()
  {
    return instance;
  }

  private JavaCarrierFactory()
  {
  }

  public IHandleCarrier getHandlerCarrier()
  {
    return JavaCarrier.getInstance();
  }
}
