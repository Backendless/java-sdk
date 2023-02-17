package com.backendless;

import java.util.UUID;


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
}
