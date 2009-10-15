package uk.co.jezuk.mango.iterators;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;

/**
 * @author Jez Higgins, jez@jezuk.co.uk
 */
public class ReverseIterator<T> implements Iterator<T>
{
  public ReverseIterator(List<? super T> list)
  {
    iter_ = ((List<T>)list).listIterator(list.size());
  } // ReverseIterator

  public boolean hasNext()
  {
    return iter_.hasPrevious();
  } // hasNext

  public T next()
  {
    return iter_.previous();
  } // next

  public void remove()
  {
    iter_.remove();
  } // remove

  private ListIterator<T> iter_;
} // class ReverseIterator