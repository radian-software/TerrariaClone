package org.terraframe

import java.awt.image._
import java.io.Serializable

case class ItemCollection(`type`: String, ids: Array[Short], nums: Array[Short], durs: Array[Short]) extends Serializable {
    @transient var image: BufferedImage = _
    var FUELP: Double = 0
    var SMELTP: Double = 0
    var F_ON: Boolean = false
    var recipeNum: Short = _
}
