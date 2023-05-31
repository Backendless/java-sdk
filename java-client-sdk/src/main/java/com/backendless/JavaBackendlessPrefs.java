package com.backendless;

import com.backendless.exceptions.ExceptionMessage;

import java.util.UUID;
import java.util.prefs.Preferences;


class JavaBackendlessPrefs extends BackendlessPrefs
{
  JavaBackendlessPrefs()
  {
    super();
  }

  @Override
  protected void retrieveDeviceId()
  {
    StringBuilder builder = new StringBuilder();
    builder.append( System.getProperty( "os.name" ) ).append( '_' );
    builder.append( System.getProperty( "os.arch" ) ).append( '_' );
    builder.append( System.getProperty( "os.version" ) ).append( '_' );
    builder.append( System.getProperty( "user.name" ) ).append( '_' );
    builder.append( System.getProperty( "java.home" ) );
    this.deviceId = UUID.nameUUIDFromBytes( builder.toString().getBytes() ).toString();
  }

  @Override
  protected void retrieveOS()
  {
    this.os = System.getProperty( "os.name" );
  }

  @Override
  protected void retrieveOSVersion()
  {
    this.osVersion = System.getProperty( "os.version" );
  }

  @Override
  public void init()
  {
    super.init();
  }

  protected boolean restoreAuthKeysFromPreferences()
  {
    // At the moment, restoring the auth information in the Desktop/CodeRunner environment is not provided.
    // I think the code should be like this:

/*
    private final Preferences prefs = Preferences.userRoot().node( this.getClass().getName() );

    if( prefs == null )
      throw new IllegalStateException( ExceptionMessage.NOT_INITIALIZED );

    String applicationId = prefs.get( Type.APPLICATION_ID_KEY.name64(), null );
    String apiKey = prefs.get( Type.API_KEY.name64(), null );

    if( applicationId != null && apiKey != null )
    {
      authKeys = new AuthKeys( applicationId, apiKey );
      return true;
    }

*/
    return false;
  }
}
