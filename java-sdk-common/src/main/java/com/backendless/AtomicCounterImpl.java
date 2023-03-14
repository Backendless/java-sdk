package com.backendless;

import com.backendless.async.callback.AsyncCallback;


/**
 * Use Hive.
 * @param <T>
 */
@Deprecated
class AtomicCounterImpl<T> implements IAtomic<T>
{
  private final com.backendless.Counters counters;
  private final String counterName;
  private final Class<? extends T> type;

  public AtomicCounterImpl( com.backendless.Counters counters, String counterName, Class<? extends T> type )
  {
    this.counters = counters;
    this.counterName = counterName;
    this.type = type;
  }

  @Override
  public void reset()
  {
    counters.reset( counterName );
  }

  @Override
  public void reset( AsyncCallback responder )
  {
    counters.reset( counterName, responder );
  }

  @Override
  public T get()
  {
    return (T) Counters.convertToType( counters.get( counterName ), type );
  }

  @Override
  public void get( AsyncCallback<T> responder )
  {
    counters.get( counterName, responder );
  }

  @Override
  public T getAndIncrement()
  {
    return (T) Counters.convertToType( counters.getAndIncrement( counterName ), type );
  }

  public void getAndIncrement( AsyncCallback<T> responder )
  {
    counters.getAndIncrement( counterName, responder );
  }

  @Override
  public T incrementAndGet()
  {
    return (T) Counters.convertToType( counters.incrementAndGet( counterName ), type );
  }

  public void incrementAndGet( AsyncCallback<T> responder )
  {
    counters.incrementAndGet( counterName, responder );
  }

  @Override
  public T getAndDecrement()
  {
    return (T) Counters.convertToType( counters.getAndDecrement( counterName ), type );
  }

  public void getAndDecrement( AsyncCallback<T> responder )
  {
    counters.getAndDecrement( counterName, responder );
  }

  @Override
  public T decrementAndGet()
  {
    return (T) Counters.convertToType( counters.decrementAndGet( counterName ), type );
  }

  public void decrementAndGet( AsyncCallback<T> responder )
  {
    counters.decrementAndGet( counterName, responder );
  }

  @Override
  public T addAndGet( Number value )
  {
    return (T) Counters.convertToType( counters.addAndGet( counterName, value ), type );
  }

  public void addAndGet( Number value, AsyncCallback<T> responder )
  {
    counters.addAndGet( counterName, value, responder );
  }

  @Override
  public T getAndAdd( Number value )
  {
    return (T) Counters.convertToType( counters.getAndAdd( counterName, value ), type );
  }

  public void getAndAdd( Number value, AsyncCallback<T> responder )
  {
    counters.getAndAdd( counterName, value, responder );
  }

  @Override
  public boolean compareAndSet( Number expected, Number updated )
  {
    return counters.compareAndSet( counterName, expected, updated );
  }

  public void compareAndSet( Number expected, Number updated, AsyncCallback<Boolean> responder )
  {
    counters.compareAndSet( counterName, expected, updated, responder );
  }
}
