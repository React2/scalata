package io.react2.scalata.utils

/**
 * @author dbalduini
 */
object Args {

  type OptionMap = Map[Symbol, Any]

  val usage =
    """
      Usage: scalata [--config str] [--parser str] [--exporter str]
    """

  def options(args: Array[String]): OptionMap = {
    if (args.length == 0) println(usage)
    val arglist = args.toList
    def nextOption(map : OptionMap, list: List[String]) : OptionMap = {
      list match {
        case Nil => map
        case "--config" :: value :: tail =>
          nextOption(map ++ Map('config -> value), tail)
        case "--parser" :: value :: tail =>
          nextOption(map ++ Map('parser -> value), tail)
        case "--exporter" :: value :: tail =>
          nextOption(map ++ Map('exporter -> value), tail)
        case option :: tail => nextOption(map, tail)
      }
    }
    nextOption(Map(),arglist)
  }


}
