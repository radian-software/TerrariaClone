package org.terraframe

import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO


import scala.util.control.NonFatal

object Images {
  def loadImage(path: String): BufferedImage = {
    val url: URL = getClass.getResource(path)
    var image: BufferedImage = null
    try {
      image = ImageIO.read(url)
    }
    catch {
      case NonFatal(_) => println("(ERROR) could not load image '" + path + "'.")
    }
    return image
  }
}
