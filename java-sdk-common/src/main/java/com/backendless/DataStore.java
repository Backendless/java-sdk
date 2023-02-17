package com.backendless;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.commons.persistence.GroupResult;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessSerializer;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.GroupDataQueryBuilder;
import com.backendless.persistence.LoadRelationsQueryBuilder;
import com.backendless.rt.data.EventHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


class DataStore<T> implements IDataStore<T>
{
  private final Class<T> entityClass;
  private final Persistence persistence;
  private final EventHandler<T> eventHandler;

  DataStore( Class<T> entityClass, Persistence persistence, EventHandler<T> eventHandler )
  {
    this.entityClass = entityClass;
    this.persistence = persistence;
    this.eventHandler = eventHandler;
  }

  @Override
  public List<String> create( List<T> objects ) throws BackendlessException
  {
    return persistence.create( objects );
  }

  @Override
  public void create( List<T> objects, AsyncCallback<List<String>> responder ) throws BackendlessException
  {
    persistence.create( objects, responder );
  }

  @Override
  public T save( final T entity ) throws BackendlessException
  {
    return persistence.save( entity );
  }

  @Override
  public T save( final T entity, boolean isUpsert ) throws BackendlessException
  {
    return persistence.save( entity );
  }

  @Override
  public void save( final T entity, final AsyncCallback<T> responder )
  {
    persistence.save( entity, responder );
  }

  @Override
  public void save( final T entity, boolean isUpsert, final AsyncCallback<T> responder )
  {
    persistence.save( entity, responder );
  }

  @Override
  public T deepSave( final T entity ) throws BackendlessException
  {
    return persistence.deepSave( entity );
  }

  @Override
  public void deepSave( final T entity, final AsyncCallback<T> responder )
  {
    persistence.deepSave( entity, responder );
  }

  @Override
  public Long remove( final T entity ) throws BackendlessException
  {
    return persistence.remove( entity );
  }

  @Override
  public void remove( final T entity, final AsyncCallback<Long> responder )
  {
    persistence.remove( entity, responder );
  }

  @Override
  public int remove( final String whereClause ) throws BackendlessException
  {
    return persistence.remove( BackendlessSerializer.getSimpleName( entityClass ), whereClause );
  }

  @Override
  public void remove( final String whereClause, AsyncCallback<Integer> responder ) throws BackendlessException
  {
    persistence.remove( BackendlessSerializer.getSimpleName( entityClass ), whereClause, responder );
  }

  @Override
  public int update( final String whereClause, Map<String, Object> changes ) throws BackendlessException
  {
    return persistence.update( BackendlessSerializer.getSimpleName( entityClass ), whereClause, changes );
  }

  @Override
  public void update( final String whereClause, Map<String, Object> changes, AsyncCallback<Integer> responder ) throws BackendlessException
  {
    persistence.update( BackendlessSerializer.getSimpleName( entityClass ), whereClause, changes, responder );
  }

  @Override
  public T findFirst() throws BackendlessException
  {
    return persistence.first( entityClass );
  }

  @Override
  public T findFirst( Integer relationsDepth ) throws BackendlessException
  {
    return findFirst( Collections.emptyList(), relationsDepth, null );
  }

  @Override
  public T findFirst( List<String> relations ) throws BackendlessException
  {
    return findFirst( relations, null, null );
  }

  @Override
  public T findFirst( List<String> relations, Integer relationsDepth, Integer relationsPageSize ) throws BackendlessException
  {
    return persistence.first( entityClass, relations, relationsDepth, relationsPageSize );
  }

  @Override
  public int getObjectCount()
  {
    return persistence.getObjectCount( entityClass );
  }

  @Override
  public int getObjectCount( DataQueryBuilder dataQueryBuilder )
  {
    return persistence.getObjectCount( entityClass, dataQueryBuilder );
  }

  @Override
  public int getObjectCountInGroup( GroupDataQueryBuilder dataQueryBuilder )
  {
    return persistence.getObjectCountInGroup( entityClass, dataQueryBuilder );
  }

  public void findFirst( final AsyncCallback<T> responder )
  {
    persistence.first( entityClass, responder );
  }

  @Override
  public void findFirst( Integer relationsDepth, final AsyncCallback<T> responder )
  {

    findFirst( Collections.emptyList(), relationsDepth, null, responder );
  }

  @Override
  public void findFirst( List<String> relations, AsyncCallback<T> responder )
  {
    findFirst( relations, null, null, responder );
  }

  @Override
  public void findFirst( List<String> relations, Integer relationsDepth, Integer relationsPageSize, final AsyncCallback<T> responder )
  {
    persistence.first( entityClass, relations, relationsDepth, relationsPageSize, responder );
  }

  @Override
  public T findLast() throws BackendlessException
  {
    return persistence.last( entityClass );
  }

  @Override
  public T findLast( Integer relationsDepth ) throws BackendlessException
  {
    return findLast( Collections.emptyList(), relationsDepth, null );
  }

  @Override
  public T findLast( List<String> relations ) throws BackendlessException
  {
    return findLast( relations, null, null );
  }

  @Override
  public T findLast( List<String> relations, Integer relationsDepth, Integer relationsPageSize ) throws BackendlessException
  {
    return persistence.last( entityClass, relations, relationsDepth, relationsPageSize );
  }

  @Override
  public void findLast( final AsyncCallback<T> responder )
  {
    persistence.last( entityClass, responder );
  }

  @Override
  public void findLast( Integer relationsDepth, final AsyncCallback<T> responder )
  {
    findLast( Collections.emptyList(), relationsDepth, null, responder );
  }

  @Override
  public void findLast( List<String> relations, AsyncCallback<T> responder )
  {
    findLast( relations, null, null, responder );
  }

  @Override
  public void findLast( List<String> relations, Integer relationsDepth, Integer relationsPageSize, final AsyncCallback<T> responder )
  {
    persistence.last( entityClass, relations, relationsDepth, relationsPageSize, responder );
  }

  @Override
  public List<T> find() throws BackendlessException
  {
    return persistence.find( entityClass, DataQueryBuilder.create() );
  }

  @Override
  public List<T> find( DataQueryBuilder dataQueryBuilder ) throws BackendlessException
  {
    return persistence.find( entityClass, dataQueryBuilder );
  }

  @Override
  public void find( AsyncCallback<List<T>> responder )
  {
    persistence.find( entityClass, DataQueryBuilder.create(), responder );
  }

  @Override
  public void find( DataQueryBuilder dataQueryBuilder, AsyncCallback<List<T>> responder )
  {
    persistence.find( entityClass, dataQueryBuilder, responder );
  }

  @Override
  public GroupResult<?, T> group( GroupDataQueryBuilder dataQueryBuilder ) throws BackendlessException
  {
    return persistence.group( entityClass, dataQueryBuilder );
  }

  @Override
  public void group( GroupDataQueryBuilder dataQueryBuilder, AsyncCallback<GroupResult<?, T>> responder )
  {
    persistence.group( entityClass, dataQueryBuilder, responder );
  }

  @Override
  public T findById( String objectId ) throws BackendlessException
  {
    return findById( objectId, Collections.emptyList() );
  }

  @Override
  public T findById( String objectId, List<String> relations ) throws BackendlessException
  {
    return persistence.findById( entityClass, objectId, relations );
  }

  @Override
  public T findById( String objectId, Integer relationsDepth ) throws BackendlessException
  {
    return persistence.findById( entityClass, objectId, Collections.emptyList(), relationsDepth );
  }

  @Override
  public T findById( String objectId, List<String> relations, Integer relationsDepth ) throws BackendlessException
  {
    return persistence.findById( entityClass, objectId, relations, relationsDepth );
  }

  @Override
  public T findById( T entity )
  {
    return findById( entity, Collections.emptyList() );
  }

  @Override
  public T findById( T entity, List<String> relations )
  {
    return findById( entity, relations, (Integer) null );
  }

  @Override
  public T findById( T entity, Integer relationsDepth )
  {
    return findById( entity, Collections.emptyList(), relationsDepth );
  }

  @Override
  public T findById( T entity, List<String> relations, Integer relationsDepth )
  {
    return persistence.findById( entity, relations, relationsDepth );
  }

  @Override
  public void findById( String objectId, AsyncCallback<T> responder )
  {
    findById( objectId, Collections.emptyList(), responder );
  }

  @Override
  public void findById( String objectId, List<String> relations, AsyncCallback<T> responder )
  {
    persistence.findById( entityClass, objectId, relations, responder );
  }

  @Override
  public void findById( String objectId, Integer relationsDepth, AsyncCallback<T> responder )
  {
    findById( objectId, Collections.emptyList(), relationsDepth, responder );
  }

  @Override
  public void findById( String objectId, List<String> relations, Integer relationsDepth, AsyncCallback<T> responder )
  {
    persistence.findById( entityClass, objectId, relations, relationsDepth, responder );
  }

  @Override
  public void findById( T entity, AsyncCallback<T> responder )
  {
    findById( entity, Collections.emptyList(), responder );
  }

  @Override
  public void findById( T entity, List<String> relations, AsyncCallback<T> responder )
  {
    findById( entity, relations, null, responder );
  }

  @Override
  public void findById( T entity, Integer relationsDepth, AsyncCallback<T> responder )
  {
    findById( entity, Collections.emptyList(), relationsDepth, responder );
  }

  @Override
  public void findById( T entity, List<String> relations, Integer relationsDepth, AsyncCallback<T> responder )
  {
    persistence.findById( entity, relations, relationsDepth, responder );
  }

  @Override
  public T findById( String id, DataQueryBuilder queryBuilder ) throws BackendlessException
  {
    return persistence.findById( entityClass, id, queryBuilder );
  }

  @Override
  public T findById( T entity, DataQueryBuilder queryBuilder ) throws BackendlessException
  {
    return persistence.findById( entity, queryBuilder );
  }

  @Override
  public void findById( String id, DataQueryBuilder queryBuilder, AsyncCallback<T> responder )
  {
    persistence.findById( entityClass, id, queryBuilder, responder );
  }

  @Override
  public void findById( T entity, DataQueryBuilder queryBuilder, AsyncCallback<T> responder )
  {
    persistence.findById( entity, queryBuilder, responder );
  }

  @Override
  public <R> List<R> loadRelations( String objectId, LoadRelationsQueryBuilder<R> queryBuilder )
  {
    String typeName = BackendlessSerializer.getSimpleName( entityClass );
    return persistence.loadRelations( typeName, objectId, queryBuilder, queryBuilder.getRelationType() );
  }

  @Override
  public <R> void loadRelations( String objectId, LoadRelationsQueryBuilder<R> queryBuilder, AsyncCallback<List<R>> responder )
  {
    String typeName = BackendlessSerializer.getSimpleName( entityClass );
    persistence.loadRelations( typeName, objectId, queryBuilder, queryBuilder.getRelationType(), responder );
  }

  @Override
  public void getObjectCount( AsyncCallback<Integer> responder )
  {
    persistence.getObjectCount( entityClass, responder );
  }

  @Override
  public void getObjectCount( DataQueryBuilder dataQueryBuilder, AsyncCallback<Integer> responder )
  {
    persistence.getObjectCount( entityClass, dataQueryBuilder, responder );
  }

  @Override
  public void getObjectCountInGroup( GroupDataQueryBuilder dataQueryBuilder, AsyncCallback<Integer> responder )
  {
    persistence.getObjectCountInGroup( entityClass, dataQueryBuilder, responder );
  }

  @Override
  public <R> int addRelation( T parent, String relationColumnName, Collection<R> children )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    Collection<String> childrenObjectIds = new ArrayList<>();
    for( R child : children )
    {
      String childObjectId = Persistence.getEntityId( child );
      childrenObjectIds.add( childObjectId );
    }

    return addRelation( parentObjectId, relationColumnName, childrenObjectIds );
  }

  @Override
  public <R> void addRelation( T parent, String relationColumnName, Collection<R> children, AsyncCallback<Integer> callback )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    Collection<String> childrenObjectIds = new ArrayList<>();
    for( R child : children )
    {
      String childObjectId = Persistence.getEntityId( child );
      childrenObjectIds.add( childObjectId );
    }

    addRelation( parentObjectId, relationColumnName, childrenObjectIds, callback );
  }

  @Override
  public int addRelation( T parent, String relationColumnName, String whereClause )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    return addRelation( parentObjectId, relationColumnName, whereClause );
  }

  @Override
  public void addRelation( T parent, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    addRelation( parentObjectId, relationColumnName, whereClause, callback );
  }

  @Override
  public <R> int setRelation( T parent, String relationColumnName, Collection<R> children )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    Collection<String> childrenObjectIds = new ArrayList<>();
    for( R child : children )
    {
      String childObjectId = Persistence.getEntityId( child );
      childrenObjectIds.add( childObjectId );
    }

    return setRelation( parentObjectId, relationColumnName, childrenObjectIds );
  }

  @Override
  public <R> void setRelation( T parent, String relationColumnName, Collection<R> children, AsyncCallback<Integer> callback )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    Collection<String> childrenObjectIds = new ArrayList<>();
    for( R child : children )
    {
      String childObjectId = Persistence.getEntityId( child );
      childrenObjectIds.add( childObjectId );
    }

    setRelation( parentObjectId, relationColumnName, childrenObjectIds, callback );
  }

  @Override
  public int setRelation( T parent, String relationColumnName, String whereClause )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    return setRelation( parentObjectId, relationColumnName, whereClause );
  }

  @Override
  public void setRelation( T parent, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    setRelation( parentObjectId, relationColumnName, whereClause, callback );
  }

  @Override
  public <R> int deleteRelation( T parent, String relationColumnName, Collection<R> children )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    Collection<String> childrenObjectIds = new ArrayList<>();
    for( R child : children )
    {
      String childObjectId = Persistence.getEntityId( child );
      childrenObjectIds.add( childObjectId );
    }

    return deleteRelation( parentObjectId, relationColumnName, childrenObjectIds );
  }

  @Override
  public <R> void deleteRelation( T parent, String relationColumnName, Collection<R> children, AsyncCallback<Integer> callback )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    Collection<String> childrenObjectIds = new ArrayList<>();
    for( R child : children )
    {
      String childObjectId = Persistence.getEntityId( child );
      childrenObjectIds.add( childObjectId );
    }

    deleteRelation( parentObjectId, relationColumnName, childrenObjectIds, callback );
  }

  @Override
  public int deleteRelation( T parent, String relationColumnName, String whereClause )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    return deleteRelation( parentObjectId, relationColumnName, whereClause );
  }

  @Override
  public void deleteRelation( T parent, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
  {
    String parentObjectId = Persistence.getEntityId( parent );

    deleteRelation( parentObjectId, relationColumnName, whereClause, callback );
  }

  @Override
  public <R> int addRelation( String parentObjectId, String relationColumnName, Collection<String> childrenObjectIds )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
    return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "addRelation", args );
  }

  @Override
  public <R> void addRelation( String parentObjectId, String relationColumnName, Collection<String> childrenObjectIds,
                               AsyncCallback<Integer> callback )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
    Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "addRelation", args, callback );
  }

  @Override
  public int addRelation( String parentObjectId, String relationColumnName, String whereClause )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
    return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "addRelation", args );
  }

  @Override
  public void addRelation( String parentObjectId, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
    Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "addRelation", args, callback );
  }

  @Override
  public <R> int setRelation( String parentObjectId, String relationColumnName, Collection<String> childrenObjectIds )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
    return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "setRelation", args );
  }

  @Override
  public <R> void setRelation( String parentObjectId, String relationColumnName, Collection<String> childrenObjectIds,
                               AsyncCallback<Integer> callback )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
    Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "setRelation", args, callback );
  }

  @Override
  public int setRelation( String parentObjectId, String relationColumnName, String whereClause )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
    return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "setRelation", args );
  }

  @Override
  public void setRelation( String parentObjectId, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
    Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "setRelation", args, callback );
  }

  @Override
  public <R> int deleteRelation( String parentObjectId, String relationColumnName, Collection<String> childrenObjectIds )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
    return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "deleteRelation", args );
  }

  @Override
  public <R> void deleteRelation( String parentObjectId, String relationColumnName, Collection<String> childrenObjectIds,
                                  AsyncCallback<Integer> callback )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
    Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "deleteRelation", args, callback );
  }

  @Override
  public int deleteRelation( String parentObjectId, String relationColumnName, String whereClause )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
    return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "deleteRelation", args );
  }

  @Override
  public void deleteRelation( String parentObjectId, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
  {
    String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

    Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
    Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "deleteRelation", args, callback );
  }

  @Override
  public EventHandler<T> rt()
  {
    return eventHandler;
  }
}
