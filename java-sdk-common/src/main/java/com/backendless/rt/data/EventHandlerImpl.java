package com.backendless.rt.data;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessSerializer;
import com.backendless.rt.RTCallback;
import com.backendless.rt.RTEvent;
import com.backendless.rt.RTListenerImpl;
import com.backendless.rt.RTSubscription;
import com.backendless.rt.SubscriptionNames;
import weborb.exceptions.AdaptingException;
import weborb.types.IAdaptingType;

import java.util.HashMap;
import java.util.List;

public class EventHandlerImpl<T> extends RTListenerImpl implements EventHandler<T>
{
  private final Class clazz;
  private final String tableName;

  EventHandlerImpl( Class<T> clazz )
  {
    this.clazz = clazz;
    this.tableName = BackendlessSerializer.getSimpleName( clazz );
  }

  EventHandlerImpl( String tableName )
  {
    this.clazz = HashMap.class;
    this.tableName = tableName;
  }

  //--------create-------

  @Override
  public void addCreateListener( AsyncCallback<T> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.created, tableName, createCallback( callback ) );
    addEventListener( subscription );
  }

  @Override
  public void addCreateListener( String whereClause, AsyncCallback<T> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.created, tableName, createCallback( callback ) )
            .withWhere( whereClause );

    addEventListener( subscription );
  }

  @Override
  public void removeCreateListeners()
  {
     removeListeners( ObjectEvents.created );
  }

  @Override
  public void removeCreateListener( final String whereClause, final AsyncCallback<T> callback )
  {
    removeListeners( ObjectEvents.created, whereClause, callback );
  }

  @Override
  public void removeCreateListener( final AsyncCallback<T> callback )
  {
    removeListeners( ObjectEvents.created, callback );
  }

  @Override
  public void removeCreateListeners( final String whereClause )
  {
    removeListeners( ObjectEvents.created, whereClause );
  }

  //--------update-------

  @Override
  public void addUpdateListener( AsyncCallback<T> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.updated, tableName, createCallback( callback ) );
    addEventListener( subscription );
  }

  @Override
  public void addUpdateListener( String whereClause, AsyncCallback<T> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.updated, tableName, createCallback( callback ) )
            .withWhere( whereClause );

    addEventListener( subscription );
  }

  @Override
  public void removeUpdateListeners()
  {
    removeListeners( ObjectEvents.updated );
  }

  @Override
  public void removeUpdateListener( final String whereClause, final AsyncCallback<T> callback )
  {
    removeListeners( ObjectEvents.updated, whereClause, callback );
  }

  @Override
  public void removeUpdateListener( final AsyncCallback<T> callback )
  {
    removeListeners( ObjectEvents.updated, callback );
  }

  @Override
  public void removeUpdateListeners( final String whereClause )
  {
    removeListeners( ObjectEvents.updated, whereClause );
  }

  //--------upsert-------

  @Override
  public void addUpsertListener( AsyncCallback<T> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.upserted, tableName, createCallback( callback ) );
    addEventListener( subscription );
  }

  @Override
  public void addUpsertListener( String whereClause, AsyncCallback<T> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.upserted, tableName, createCallback( callback ) )
        .withWhere( whereClause );

    addEventListener( subscription );
  }

  @Override
  public void removeUpsertListeners()
  {
    removeListeners( ObjectEvents.upserted );
  }


  @Override
  public void removeUpsertListener( AsyncCallback<T> callback )
  {
    removeListeners( ObjectEvents.upserted, callback );
  }

  @Override
  public void removeUpsertListeners( String whereClause )
  {
    removeListeners( ObjectEvents.upserted, whereClause );
  }

  @Override
  public void removeUpsertListener( String whereClause, AsyncCallback<T> callback )
  {
    removeListeners( ObjectEvents.upserted, whereClause, callback );
  }

  //--------remove-------

  @Override
  public void addDeleteListener( AsyncCallback<T> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.deleted, tableName, createCallback( callback ) );
    addEventListener( subscription );
  }

  @Override
  public void addDeleteListener( String whereClause, AsyncCallback<T> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.deleted, tableName, createCallback( callback ) )
            .withWhere( whereClause );

    addEventListener( subscription );
  }

  @Override
  public void removeDeleteListeners()
  {
    removeListeners( ObjectEvents.deleted );
  }

  @Override
  public void removeDeleteListener( final String whereClause, final AsyncCallback<T> callback )
  {
    removeListeners( ObjectEvents.deleted, whereClause, callback );
  }

  @Override
  public void removeDeleteListener( final AsyncCallback<T> callback )
  {
    removeListeners( ObjectEvents.deleted, callback );
  }

  @Override
  public void removeDeleteListeners( final String whereClause )
  {
    removeListeners( ObjectEvents.deleted, whereClause );
  }

  //--------bulk-create-------

  @Override
  public void addBulkCreateListener( AsyncCallback<List> callback )
  {
    DataSubscription subscription =
        new DataSubscription( ObjectEvents.bulk_created, tableName, createCallback( callback, List.class ) );
    addEventListener( subscription );
  }

  @Override
  public void removeBulkCreateListener( AsyncCallback<List<String>> callback )
  {
    removeListeners( ObjectEvents.bulk_created, callback );
  }

  @Override
  public void removeBulkCreateListeners()
  {
    removeListeners( ObjectEvents.bulk_created );
  }

  //--------bulk-update-------

  @Override
  public void addBulkUpdateListener( AsyncCallback<BulkEvent> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.bulk_updated, tableName, createCallback( callback, BulkEvent.class ) );
    addEventListener( subscription );
  }

  @Override
  public void addBulkUpdateListener( String whereClause, AsyncCallback<BulkEvent> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.bulk_updated, tableName, createCallback( callback, BulkEvent.class ) )
            .withWhere( whereClause );

    addEventListener( subscription );
  }

  @Override
  public void removeBulkUpdateListeners()
  {
    removeListeners( ObjectEvents.bulk_updated );
  }

  @Override
  public void removeBulkUpdateListener( final String whereClause, final AsyncCallback<BulkEvent> callback )
  {
    removeListeners( ObjectEvents.bulk_updated, whereClause, callback );
  }

  @Override
  public void removeBulkUpdateListener( final AsyncCallback<BulkEvent> callback )
  {
    removeListeners( ObjectEvents.bulk_updated, callback );
  }

  @Override
  public void removeBulkUpdateListeners( final String whereClause )
  {
    removeListeners( ObjectEvents.bulk_updated, whereClause );
  }

  //--------bulk-upsert-------

  @Override
  public void addBulkUpsertListener( AsyncCallback<List> callback )
  {
    DataSubscription subscription =
        new DataSubscription( ObjectEvents.bulk_upserted, tableName, createCallback( callback, List.class ) );
    addEventListener( subscription );
  }

  @Override
  public void removeBulkUpsertListeners()
  {
    removeListeners( ObjectEvents.bulk_upserted );
  }

  @Override
  public void removeBulkUpsertListener( AsyncCallback<List<String>> callback )
  {
    removeListeners( ObjectEvents.bulk_upserted, callback );
  }

  //--------bulk-delete-------

  @Override
  public void addBulkDeleteListener( AsyncCallback<BulkEvent> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.bulk_deleted, tableName, createCallback( callback, BulkEvent.class ) );
    addEventListener( subscription );
  }

  @Override
  public void addBulkDeleteListener( String whereClause, AsyncCallback<BulkEvent> callback )
  {
    DataSubscription subscription = new DataSubscription( ObjectEvents.bulk_deleted, tableName, createCallback( callback, BulkEvent.class ) )
            .withWhere( whereClause );

    addEventListener( subscription );
  }

  @Override
  public void removeBulkDeleteListeners()
  {
    removeListeners( ObjectEvents.bulk_deleted );
  }

  @Override
  public void removeBulkDeleteListener( final String whereClause, final AsyncCallback<BulkEvent> callback )
  {
    removeListeners( ObjectEvents.bulk_deleted, whereClause, callback );
  }

  @Override
  public void removeBulkDeleteListener( final AsyncCallback<BulkEvent> callback )
  {
    removeListeners( ObjectEvents.bulk_deleted, callback );
  }

  @Override
  public void removeBulkDeleteListeners( final String whereClause )
  {
    removeListeners( ObjectEvents.bulk_deleted, whereClause );
  }

  @Override
  public void addSetRelationListener( String relationColumnName,
                                      AsyncCallback<RelationStatus> callback )
  {
    DataSubscription subscription = new DataSubscription( RelationEvents.relations_set, tableName, relationColumnName,
                                                          createCallback( callback, RelationStatus.class ) );
    addEventListener( subscription );
  }

  @Override
  public void addSetRelationListener( String relationColumnName, List<String> parentObjects,
                                      AsyncCallback<RelationStatus> callback )
  {
    DataSubscription subscription = new DataSubscription( RelationEvents.relations_set, tableName, relationColumnName,
                                                          createCallback( callback, RelationStatus.class ) ).withParentObjects( parentObjects );
    addEventListener( subscription );
  }

  @Override
  public void removeSetRelationListeners()
  {
    removeListeners( RelationEvents.relations_set );
  }

  @Override
  public void addAddRelationListener( String relationColumnName,
                                      AsyncCallback<RelationStatus> callback )
  {
    DataSubscription subscription = new DataSubscription( RelationEvents.relations_added, tableName, relationColumnName,
                                                          createCallback( callback, RelationStatus.class ) );
    addEventListener( subscription );
  }

  @Override
  public void addAddRelationListener( String relationColumnName, List<String> parentObjects,
                                      AsyncCallback<RelationStatus> callback )
  {
    DataSubscription subscription = new DataSubscription( RelationEvents.relations_added, tableName, relationColumnName,
                                                          createCallback( callback, RelationStatus.class ) ).withParentObjects( parentObjects );
    addEventListener( subscription );
  }

  @Override
  public void removeAddRelationListeners()
  {
    removeListeners( RelationEvents.relations_added );
  }

  @Override
  public void addDeleteRelationListener( String relationColumnName,
                                         AsyncCallback<RelationStatus> callback )
  {
    DataSubscription subscription = new DataSubscription( RelationEvents.relations_removed, tableName, relationColumnName,
                                                          createCallback( callback, RelationStatus.class ) );
    addEventListener( subscription );
  }

  @Override
  public void addDeleteRelationListener( String relationColumnName, List<String> parentObjects,
                                         AsyncCallback<RelationStatus> callback )
  {
    DataSubscription subscription = new DataSubscription( RelationEvents.relations_removed, tableName, relationColumnName,
                                                          createCallback( callback, RelationStatus.class ) ).withParentObjects( parentObjects );
    addEventListener( subscription );
  }

  @Override
  public void removeDeleteRelationListeners()
  {
    removeListeners( RelationEvents.relations_removed );
  }

  //------end-------

  private void removeListeners( final RTEvent event )
  {
    removeEventListener( new Predicate()
    {
      @Override
      public boolean test( RTSubscription subscription )
      {
        return isEventSubscription( subscription, event );
      }
    } );
  }

  private void removeListeners(final RTEvent event, final AsyncCallback callback )
  {
    checkCallback( callback );
    removeEventListener( new Predicate()
    {
      @Override
      public boolean test( RTSubscription subscription )
      {
        return isEventSubscription( subscription, event )
                && subscription.getCallback().usersCallback().equals( callback );
      }
    } );
  }

  private void removeListeners(final RTEvent event, final String whereClause, final AsyncCallback callback )
  {
    checkCallback( callback );
    checkWhereClause( whereClause );
    removeEventListener( new Predicate()
    {
      @Override
      public boolean test( RTSubscription subscription )
      {
        return isEventSubscription( subscription, event )
                && subscription.getCallback().usersCallback().equals( callback )
                && whereClause.equals(((DataSubscription)subscription).getWhereClause());
      }
    } );
  }

  private void removeListeners(final RTEvent event, final String whereClause )
  {
    checkWhereClause( whereClause );
    removeEventListener( new Predicate()
    {
      @Override
      public boolean test( RTSubscription subscription )
      {
        return isEventSubscription( subscription, event )
                && whereClause.equals(((DataSubscription)subscription).getWhereClause());
      }
    } );
  }

  private boolean isEventSubscription( RTSubscription subscription, RTEvent event )
  {
    if( !(subscription instanceof DataSubscription) )
      return false;

    DataSubscription dataSubscription = (DataSubscription) subscription;

    if( event instanceof ObjectEvents )
      return dataSubscription.getSubscriptionName() == SubscriptionNames.OBJECTS_CHANGES
              && dataSubscription.getObjectEvent() == event;
    if( event instanceof RelationEvents )
      return dataSubscription.getSubscriptionName() == SubscriptionNames.RELATIONS_CHANGES
              && dataSubscription.getRelationEvent() == event;
    return false;
  }

  private RTCallback createCallback( final AsyncCallback<T> callback )
  {
    return createCallback( callback, clazz );
  }

  private <Type> RTCallback createCallback( final AsyncCallback<Type> callback, final Class<Type> classType )
  {
    checkCallback( callback );

    return new RTCallback()
    {
      @Override
      public AsyncCallback<Type> usersCallback()
      {
        return callback;
      }

      @Override
      public void handleResponse( IAdaptingType response )
      {
        try
        {
          final Type adaptedResponse = (Type) response.adapt( classType );
          callback.handleResponse( adaptedResponse );
        }
        catch( AdaptingException e )
        {
          callback.handleFault( new BackendlessFault( e.getMessage() ) );
        }
      }

      @Override
      public void handleFault( BackendlessFault fault )
      {
        callback.handleFault( fault );
      }
    };
  }

  private void checkCallback( AsyncCallback callback )
  {
    if( callback == null )
      throw new IllegalArgumentException( "Callback can not be null" );
  }

  private void checkWhereClause( String whereClause )
  {
    if( whereClause == null )
      throw new IllegalArgumentException( "whereClause can not be null" );
  }
}
