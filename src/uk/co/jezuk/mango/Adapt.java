package uk.co.jezuk.mango;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Arrays;

/**
 * Object method adaptors.
 * @see Bind
 * @author Jez Higgins, jez@jezuk.co.uk
 */
public class Adapt
{
  /**
   * Adapts member functions as <code>Function</code> objects, allowing them
   * to be passed to algorithms.
   * <br>
   * e.g. to print all the elements in a list<br>
   * <code>Mango.forEach(list, Adapt.Method(System.out, "println"));</code><br>
   * is equivalent to <br>
   * <code>for(int i = 0; i < list.size(); ++i)</code><br>
   * <code>  System.out.println(list.get(i));</code>
   * <p>
   * If the named method is not found, or its signature is incorrect throws a
   * RuntimeException.  If multiple methods have the correct name, and take a single
   * parameter one of them will be called, but you can't determine which.
   */
  static public Function Method(final Object obj, final String methodName)
  {
    return wrapMethod(obj.getClass(), obj, methodName, null);
  } // Method 
  static public <T, Void> Function<T, Void> Method(final Object obj, 
                                                   final String methodName,
                                                   final Class<T> argType)
  {
    return (Function<T, Void>)wrapMethod(obj.getClass(), obj, methodName, argType);
  } // Method
  static public <T, R> Function<T, R> Method(final Object obj, 
                                             final String methodName,
                                             final Class<T> argType, 
                                             final Class<R> returnType)
  {
    return (Function<T, R>)wrapMethod(obj.getClass(), obj, methodName, argType);
  } // Method


  /**
   * Adapts static member functions as <code>Function</code> objects, allowing them
   * to be passed to algorithms.
   * <p>
   * If the named method is not found, or its signature is incorrect throws a
   * RuntimeException.  If multiple methods have the correct name, and take a single
   * parameter one of them will be called, but you can't determine which.
   */
  static public Function Method(final Class klass, final String methodName)
  {
    return wrapMethod(klass, null, methodName, Object.class);
  } // Method
  static public <T, Void> Function<T, Void> Method(final Class klass, 
						   final String methodName,
						   final Class<T> argType)
  {
    return wrapMethod(klass, null, methodName, argType);
  } // Method
  static public <T, R> Function<T, R> Method(final Class klass, 
					     final String methodName,
					     final Class<T> argType,
					     final Class<R> returnType)
  {
    return wrapMethod(klass, null, methodName, argType);
  } // Method

  ////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////
  static private Function wrapMethod(final Class<?> klass, 
                                     final Object obj, 
                                     final String methodName, 
                                     final Class<?> argClass)
  {
    final List<Method> methods = Arrays.asList(klass.getMethods());
    
    Predicate<Method> methodTest = new UnaryMethodNamed(methodName, argClass != null ? argClass : Object.class);

    Method m = Algorithms.findIf(methods, methodTest);
    if((m == null) && (argClass == null))
      m = Algorithms.findIf(methods, new AnyUnaryMethodNamed(methodName));
    if(m == null)
      throw new RuntimeException(new NoSuchMethodException(methodName + "(" + argClass + ")"));
    final Method method = m;

    return new Function() {
      private final Object obj_;
      private final Method method_;
      { obj_ = obj; method_ = method; }
      public Object fn(Object arg) 
      { 
        Object[] args = new Object[]{ arg };
        try {
          return method_.invoke(obj_, args);
        } // try
        catch(IllegalArgumentException e) {
          throw new RuntimeException("Passed " + arg.getClass() + " to " + method_.getName() + "(" + method_.getParameterTypes()[0] + ")", e);
        } // catch 
        catch(Exception e) {
          throw new RuntimeException(e);
        } // catch
      } // fn
    }; // Function
  } // wrapMethod

  static private class NullaryMethodNamed implements Predicate<Method>
  {
    NullaryMethodNamed(final String name)
    {
      name_ = name;
    } // NullaryMethodNamed
    
    public boolean test(final Method m)
    {
      return (m.getName().equals(name_) &&
              (m.getParameterTypes().length == 0));
    } // test
    
    private final String name_;
  } // class NullaryMethodNamed

  static private class UnaryMethodNamed implements Predicate<Method>
  {
    UnaryMethodNamed(final String name, final Class<?> argClass) 
    { 
      name_ = name; 
      argClass_ = argClass;
    } // UnaryMethodNamed

    public boolean test(final Method m)
    {
      if(!m.getName().equals(name_))
        return false;
      if(m.getParameterTypes().length != 1)
        return false;
      if(!m.getParameterTypes()[0].equals(argClass_))
        return false;
      return true;
    } // test

    private final String name_;
    private final Class<?> argClass_;
  } // UnaryMethodNamed

  static private class AnyUnaryMethodNamed implements Predicate<Method>
  {
    AnyUnaryMethodNamed(final String name)
    { 
      name_ = name; 
    } // UnaryMethodNamed

    public boolean test(final Method m)
    {
      if(!m.getName().equals(name_))
        return false;
      if(m.getParameterTypes().length != 1)
        return false;
      return true;
    } // test

    private final String name_;
  } // AnyUnaryMethodNamed    

  /**
   * Creates a <code>Function</code> which will call a method on the
   * object passed as the argument to <code>Function.fn</code> method.
   * <br>
   * e.g. to print all the elements in a list<br>
   * <code>interface Something { void persist(); }<br>
   * // fill list with Somethings
   * Mango.forEach(list, Bind.ArgumentMethod("persist"));</code><br>
   * is equivalent to <br>
   * <code>for(int i = 0; i < list.size(); ++i)<br>
   * {<br>
   * &nbsp;&nbsp;Something s = (Something)list.get(i);<br>
   * &nbsp;&nbsp;s.persist();<br>
   * }</code>
   * <p>
   * If the named method is not found, or its signature is incorrect throws a
   * RuntimeException.  
   * @see Function
   */
  static public Function ArgumentMethod(final String methodName)
  {
    return new Function() {
      private String methodName_;
      private Class lastClass_;
      private Method method_;
      { methodName_ = methodName; }
      public Object fn(Object arg) 
      { 
        if(!arg.getClass().equals(lastClass_))
        {
          lastClass_ = arg.getClass();
          List<Method> methods = Arrays.asList(lastClass_.getMethods());
          method_ = (Method)Algorithms.findIf(methods, new NullaryMethodNamed(methodName));
          if(method_ == null)
            throw new RuntimeException(new NoSuchMethodException(methodName + "()"));
        } // if ...
	  
        try {
          return method_.invoke(arg, (Object[])(null));
        } // try
        catch(Exception e) {
          throw new RuntimeException(e);
        } // catch
      } // fn
    }; // Function
  } // ArgumentMethod

  static public <T, Void> Function<T, Void> ArgumentMethod(final String methodName,
                                                           final Class<T> argType)
  {
    return (Function<T, Void>)wrapArgumentMethod(methodName, argType);
  } // ArgumentMethod
  static public <T, R> Function<T, R> ArgumentMethod(final String methodName,
                                                     final Class<T> argType,
                                                     final Class<R> returnType)
  {
    return (Function<T, R>)wrapArgumentMethod(methodName, argType);
  } // ArgumentMethod

  static private Function wrapArgumentMethod(final String methodName,
                                             final Class<?> argType)
  {
    final List<Method> methods = Arrays.asList(argType.getMethods());
    final Method method = Algorithms.findIf(methods, new NullaryMethodNamed(methodName));
    if(method == null)
      throw new RuntimeException(new NoSuchMethodException(methodName + "()"));
                  
    return new Function() {
      private final Method method_;
      { method_ = method; }
      public Object fn(Object arg)
      {
        try {
          return method_.invoke(arg, (Object[])(null));
        } // try
        catch(Exception e) {
          throw new RuntimeException(e);
        } // catch
      } // fn
    }; // new Function
  } // wrapArgumentMethod        

  //////////////////////////////////////////
  private Adapt() { }
} // Adapt
