package uk.co.jezuk.mango;

import uk.co.jezuk.mango.unarypredicates.*;

/**
 * The Mango Library Unary and Binary Predicates
 *
 * @author Jez Higgins, jez@jezuk.co.uk
 */
public class Predicates
{
  ///////////////////////////////////////////////////////
  // Unary Predicates
  /**
   * A <code>Predicate</code> which always returns <code>true</code>
   */
  static public <T> Predicate<T> True() { return new True<T>(); }

  /**
   * A <code>Predicate</code> which always returns <code>false</code>
   */
  static public <T> Predicate<T> False() { return new False<T>(); }

  /**
   * A <code>Predicate</code> which is the logical negation of some other <code>Predicate</code>.  If <code>n</code>
   * is a <code>Not</code> object, and <code>pred</code> is the <code>Predicate</code> it was constructed with,
   * then <code>n.test(x)</code> returns <code>!pred.test(x)</code>.
   */
  static public <T> Predicate<T> Not(Predicate<T> pred) { return new Not<T>(pred); }

  /**
   * A <code>Predicate</code> which returns the logical AND of two other <code>Predicate</code>.  If <code>a</code>
   * is an <code>And</code> object, constructed with <code>pred1</code> and <code>pred2</code>, then 
   * <code>a.test(x)</code> returns <code>pred1.test(x) && pred2.test(x)</code>
   */
  static public <T> Predicate<T> And(Predicate<T> pred1, Predicate<T> pred2) { return new And(pred1, pred2); }

  /**
   * A <code>Predicate<T></code> which returns the logical OR of two other <code>Predicate<T></code>.  If <code>a</code>
   * is an <code>Or</code> object, constructed with <code>pred1</code> and <code>pred2</code>, then 
   * <code>a.test(x)</code> returns <code>pred1.test(x) || pred2.test(x)</code>
   */
  static public <T> Predicate<T> Or(Predicate<T> pred1, Predicate<T> pred2) { return new Or(pred1, pred2); }

  /////////////////////////////////////////////////
  // Binary Predicates
  /**
   * <code>BinaryPredicate</code> testing for equality.
   * <code>true</code> if <code>x.equals(y)</code> or <code>(x == null && y == null)</code>
   */
  static public <T1, T2> BinaryPredicate<T1, T2> EqualTo() 
  { 
    return new uk.co.jezuk.mango.binarypredicates.EqualTo<T1, T2>(); 
  } // EqualTo

  /**
   * <code>BinaryPredicate</code> that returns true if <code>x</code> is greater than <code>y</code>.
   * <code>x</code> and <code>y</code> must implement the <code>java.lang.Comparable<code> interface.
   */
  static public <T1, T2> BinaryPredicate<T1, T2> GreaterThan() 
  { 
    return new uk.co.jezuk.mango.binarypredicates.GreaterThan<T1, T2>(); 
  } // GreaterThan

  /**
   * <code>BinaryPredicate</code> that returns true if <code>x</code> is greater than or equal to <code>y</code>.
   * <code>x</code> and <code>y</code> must implement the <code>java.lang.Comparable<code> interface.
   */
  static public <T1, T2> BinaryPredicate<T1, T2> GreaterThanEquals() 
  { 
    return new uk.co.jezuk.mango.binarypredicates.GreaterThanEquals<T1, T2>(); 
  } // GreaterThanEquals

  /**
   * <code>BinaryPredicate</code> that returns true if <code>x</code> is less than <code>y</code>.
   * <code>x</code> and <code>y</code> must implement the <code>java.lang.Comparable<code> interface.
   */
  static public <T1, T2> BinaryPredicate<T1, T2> LessThan() 
  { 
    return new uk.co.jezuk.mango.binarypredicates.LessThan(); 
  } // LessThan

  /**
   * <code>BinaryPredicate</code> that returns true if <code>x</code> is less than or equal to <code>y</code>.
   * <code>x</code> and <code>y</code> must implement the <code>java.lang.Comparable<code> interface.
   */
  static public <T1, T2> BinaryPredicate<T1, T2> LessThanEquals() 
  { 
    return new uk.co.jezuk.mango.binarypredicates.LessThanEquals<T1, T2>(); 
  } // LessThanEquals

  /**
   * <code>true</code> if <code>not(x.equals(y))</code>, <code>(x == null) && not(y == null)</code> or <code>not(x == null) && (y == null)</code>
   */
  static public <T1, T2> BinaryPredicate<T1, T2> NotEqualTo() 
  { 
    return new uk.co.jezuk.mango.binarypredicates.NotEqualTo(); 
  } // NotEqualTo

  /**
   * A <code>BinaryPredicate</code> which is the logical negation of some other <code>BinaryPredicate</code>.  If <code>n</code>
   * is a <code>Not</code> object, and <code>pred</code> is the <code>Predicate</code> it was constructed with,
   * then <code>n.test(x,y)</code> returns <code>!pred.test(x,y)</code>.
   */
  static public <T1, T2> BinaryPredicate<T1, T2> Not(BinaryPredicate<T1, T2> pred) 
  { 
    return new uk.co.jezuk.mango.binarypredicates.Not(pred); 
  } // Not

  /**
   * A <code>BinaryPredicate</code> which returns the logical AND of two other <code>BinaryPredicate</code>.  If <code>a</code>
   * is an <code>And</code> object, constructed with <code>pred1</code> and <code>pred2</code>, then 
   * <code>a.test(x,y)</code> returns <code>pred1.test(x,y) && pred2.test(x,y)</code>
   */
  static public <T1, T2> BinaryPredicate<T1, T2> And(BinaryPredicate<T1, T2> pred1, 
                                                     BinaryPredicate<T1, T2> pred2) 
  { 
    return new uk.co.jezuk.mango.binarypredicates.And(pred1, pred2); 
  } // And

  /**
   * A <code>BinaryPredicate</code> which returns the logical OR of two other <code>BinaryPredicate</code>.  If <code>a</code>
   * is an <code>Or</code> object, constructed with <code>pred1</code> and <code>pred2</code>, then 
   * <code>a.test(x,y)</code> returns <code>pred1.test(x,y) || pred2.test(x,y)</code>
   */
  static public <T1, T2> BinaryPredicate<T1, T2> Or(BinaryPredicate<T1, T2> pred1, 
                                                    BinaryPredicate<T1, T2> pred2) 
  { 
    return new uk.co.jezuk.mango.binarypredicates.Or(pred1, pred2); 
  } // Or

  //////////////////////////////////
  private Predicates() { }
} // Predicates


