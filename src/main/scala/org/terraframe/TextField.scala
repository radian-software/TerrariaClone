package org.terraframe

import java.awt._
import java.awt.image._

case class TextField(width: Int, var text: String) {
    val height: Int = 30
    var font = new Font("Chalkboard", Font.BOLD, 16)
    val image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

    // begin constructor

    renderImage()
    //end constructor

    def typeKey(c: Char): Unit = {
        text += c
        renderImage()
    }

    def deleteKey(): Unit = {
        if (text.length() > 0) {
            text = text.substring(0, text.length()-1)
            renderImage()
        }
    }

    def renderImage(): Unit = {
        val g2: Graphics2D = image.createGraphics()

        g2.setColor(Color.WHITE)
        g2.setFont(font)
        g2.drawString(text, 6, height-10)

        g2.setColor(Color.BLACK)
        g2.fillRect(0, 0, width, 3)
        g2.fillRect(0, 0, 3, height)
        g2.fillRect(0, height-3, width, 3)
        g2.fillRect(width-3, 0, 3, height)
    }
}
