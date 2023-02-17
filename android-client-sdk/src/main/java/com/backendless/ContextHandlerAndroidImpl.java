package com.backendless;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


class ContextHandlerAndroidImpl implements ContextHandler
{
  private Context appContext;

  ContextHandlerAndroidImpl()
  {
  }

  @Override
  public synchronized Context getAppContext()
  {
    if( appContext == null )
      appContext = recoverAppContext();
    return appContext;
  }

  @Override
  public synchronized void setContext( Object anyContext )
  {
    appContext = ((Context) anyContext).getApplicationContext();
  }

  /**
   * Magic methods
   */
  private synchronized Context recoverAppContext()
  {
    try
    {
      final Class<?> activityThreadClass = Class.forName( "android.app.ActivityThread" );
      final Method method = activityThreadClass.getMethod( "currentApplication" );

      Application app = (Application) method.invoke( null, (Object[]) null );
      return app.getApplicationContext();
    }
    catch( NoSuchMethodException e )
    {
      return recoverAppContextOldAndroid();
    }
    catch( Throwable e )
    {
      e.printStackTrace();
    }
    return null;
  }

  private synchronized Context recoverAppContextOldAndroid()
  {
    try
    {
      final Class<?> activityThreadClass = Class.forName( "android.app.ActivityThread" );
      final Method method = activityThreadClass.getMethod( "currentActivityThread" );
      Object activityThread = method.invoke( null, (Object[]) null );
      final Field field = activityThreadClass.getDeclaredField( "mInitialApplication" );
      field.setAccessible( true );
      Application app = (Application) field.get( activityThread );
      return app.getApplicationContext();
    }
    catch( Throwable e )
    {
      e.printStackTrace();
    }
    return null;
  }
}
