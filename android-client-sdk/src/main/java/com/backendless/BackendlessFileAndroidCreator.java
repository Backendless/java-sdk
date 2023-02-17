package com.backendless;

import com.backendless.files.BackendlessFile;
import com.backendless.files.BackendlessFileAndroid;
import com.backendless.files.BackendlessFileCreator;


class BackendlessFileAndroidCreator implements BackendlessFileCreator
{
  @Override
  public BackendlessFile create( String fileURL )
  {
    return new BackendlessFileAndroid( fileURL );
  }
}
