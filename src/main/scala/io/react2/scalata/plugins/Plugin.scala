package io.react2.scalata.plugins

import io.react2.scalata.translation.DescriptionFormat.Reader
import io.react2.scalata.utils.ClassHelper
import org.tsers.zeison.Zeison.JValue
import scala.reflect.ClassTag

/**
 * @author dbalduini
 */
trait Plugin {
  def args: Plugin.Args
}

object Plugin {

  def of[T:ClassTag](classpath: String, params: Map[String, JValue]): T = {
    val clazz = ClassHelper.withName(classpath)
    ClassHelper.New[T](clazz, new Args(params))
  }

  class Args(_wrapped: Map[String, JValue]){
    def apply[T](key: String, default: T)(implicit r: Reader[T]): T =
      _wrapped.get(key).map(r.parse) getOrElse default
    override def toString: String = _wrapped mkString ", "
  }

}
