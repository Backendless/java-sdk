package com.backendless;

import com.backendless.files.BackendlessFile;
import com.backendless.files.BackendlessFileCreator;


class BackendlessFileBasicCreator implements BackendlessFileCreator
{
  @Override
  public BackendlessFile create( String fileURL )
  {
    return new BackendlessFile( fileURL );
  }
}
