package com.backendless.rt.rso;


import com.backendless.rt.MethodTypes;
import com.backendless.rt.RTCallback;
import com.backendless.rt.command.CommandRequest;

class SOCommandRequest extends CommandRequest
{
  SOCommandRequest( String name, RTCallback callback )
  {
    super( MethodTypes.RSO_COMMAND, callback );
    putOption( "name", name );
  }
}
