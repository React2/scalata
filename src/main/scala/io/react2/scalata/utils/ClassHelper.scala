package io.react2.scalata.utils

import scala.reflect.ClassTag

/**
 * @author dbalduini
 */
object ClassHelper {

  def withName(classpath: String): Class[_] = Class.forName(classpath)

  def New[T: ClassTag](clazz: Class[_], args: AnyRef*): T = {
    val types = args.map(_.getClass)
    val c = clazz.getDeclaredConstructor(types:_*)
    val o = c.newInstance(args:_*)
    if (clazz.isInstance(o)) o.asInstanceOf[T]
    else throw new ClassCastException(c.getName + " is not an instance of " + clazz)
  }

  def simpleName(clazz: Class[_]): String = {
    val name = clazz.getName
    name.substring(name.lastIndexOf('.') + 1)
  }

}
