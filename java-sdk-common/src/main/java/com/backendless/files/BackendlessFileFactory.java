package com.backendless.files;

import com.backendless.BackendlessInjector;
import weborb.reader.NamedObject;
import weborb.reader.ReferenceCache;
import weborb.reader.StringType;
import weborb.types.IAdaptingType;
import weborb.util.IArgumentObjectFactory;

import java.lang.reflect.Type;


/**********************************************************************************************************************
 * BACKENDLESS.COM CONFIDENTIAL
 * <p>
 * *********************************************************************************************************************
 * <p>
 * Copyright 2012 BACKENDLESS.COM. All Rights Reserved.
 * <p>
 * NOTICE:  All information contained herein is, and remains the property of Backendless.com and its suppliers,
 * if any.  The intellectual and technical concepts contained herein are proprietary to Backendless.com and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret
 * or copyright law. Dissemination of this information or reproduction of this material is strictly forbidden
 * unless prior written permission is obtained from Backendless.com.
 * <p>
 * CREATED ON: 6/7/16
 * AT: 4:23 PM
 **********************************************************************************************************************/
public class BackendlessFileFactory implements IArgumentObjectFactory
{
  private final BackendlessInjector injector = BackendlessInjector.getInstance();

  public Object createObject( IAdaptingType adaptingType )
  {
    if( adaptingType instanceof NamedObject )
      adaptingType = ((NamedObject) adaptingType).getTypedObject();

    if( adaptingType.getClass().getName().equals( "weborb.reader.NullType" ) )
      return null;

    ReferenceCache refCache = ReferenceCache.getInstance();

    if( refCache.hasObject( adaptingType, BackendlessFile.class ) )
    {
      return refCache.getObject( adaptingType, BackendlessFile.class );
    }
    else if( adaptingType instanceof StringType )
    {
      StringType stringType = (StringType) adaptingType;
      BackendlessFile file;

      file = injector.getBackendlessFileCreator().create( stringType.getValue() );

      refCache.addObject( adaptingType, BackendlessFile.class, file );
      return file;
    }
    else
    {
      throw new RuntimeException( "Can not create BackendlessFile from type " + adaptingType.getClass().getName() );
    }
  }

  public boolean canAdapt( IAdaptingType type, Type target )
  {
    return false;
  }
}
