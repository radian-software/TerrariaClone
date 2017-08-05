package org.terraframe

import java.awt._
import java.awt.image._
import java.io._
import java.net.URL
import javax.imageio.ImageIO

import scala.util.control.NonFatal

object Player {
    val BLOCKSIZE: Int = TerraFrame.getBLOCKSIZE()

    val width = TerraFrame.getPLAYERSIZEX()
    val height = TerraFrame.getPLAYERSIZEY()

    def loadImage(path: String): BufferedImage = {
        val url: URL = getClass.getResource(path)
        var image: BufferedImage = null
        try {
            image = ImageIO.read(url)
        }
        catch {
            case NonFatal(e) => println("(ERROR) could not load image '" + path + "'.")
        }
        return image
    }

    def mod(a: Int, q: Int): Int = {
        return TerraFrame.mod(a, q)
    }
}

case class Player(var x: Double, var y: Double) extends Serializable {
    import Player._
    var ix, iy, ivx, ivy, width, height, bx1, by1, bx2, by2, thp, hp: Int = _
    var onGround, onGroundDelay, grounded: Boolean = _
    var ghost: Boolean = true
    var rect: Rectangle = _
    var blocklist:Array[Short] = _

    var i, j, n: Int = _

    var imgDelay: Int = _
    var imgState: String = _


    //begin constructor
    var oldx: Double = x
    var oldy: Double = y

    var vx: Double = 0
    var vy: Double = 0
    var pvy: Double = 0

    onGround = false

    @transient var image: BufferedImage = loadImage("sprites/player/right_still.png")

    ix = x.toInt
    iy = y.toInt
    ivx = ivx
    ivy = ivy

    rect = new Rectangle(ix, iy, width, height)

    imgDelay = 0
    imgState = "still right"

    thp = 50

    hp = thp

    //end constructor

    def update(blocks: Array2D[Int], queue: Array[Boolean], u: Int, v: Int): Unit = {
        grounded = (onGround || onGroundDelay)
        if (queue(0) == true) {
            if (vx > -4 || TerraFrame.DEBUG_SPEED) {
                vx = vx - 0.5
            }
            if (imgState.equals("still left") || imgState.equals("still right") ||
                imgState.equals("walk right 1") || imgState.equals("walk right 2")) {
                imgDelay = 5
                imgState = "walk left 2"
                image = loadImage("sprites/player/left_walk.png")
            }
            if (imgDelay <= 0) {
                if (imgState.equals("walk left 1")) {
                    imgDelay = 5
                    imgState = "walk left 2"
                    image = loadImage("sprites/player/left_walk.png")
                }
                else {
                    if (imgState.equals("walk left 2")) {
                        imgDelay = 5
                        imgState = "walk left 1"
                        image = loadImage("sprites/player/left_still.png")
                    }
                }
            }
            else {
                imgDelay = imgDelay - 1
            }
        }
        if (queue(1) == true) {
            if (vx < 4 || TerraFrame.DEBUG_SPEED) {
                vx = vx + 0.5
            }
            if (imgState.equals("still left") || imgState.equals("still right") ||
                imgState.equals("walk left 1") || imgState.equals("walk left 2")) {
                imgDelay = 5
                imgState = "walk right 2"
                image = loadImage("sprites/player/right_walk.png")
            }
            if (imgDelay <= 0) {
                if (imgState.equals("walk right 1")) {
                    imgDelay = 5
                    imgState = "walk right 2"
                    image = loadImage("sprites/player/right_walk.png")
                }
                else {
                    if (imgState.equals("walk right 2")) {
                        imgDelay = 5
                        imgState = "walk right 1"
                        image = loadImage("sprites/player/right_still.png")
                    }
                }
            }
            else {
                imgDelay = imgDelay - 1
            }
        }
        if (queue(2) == true) {
            if (TerraFrame.DEBUG_FLIGHT) {
                vy -= 1
                pvy -= 1
            }
            else {
                if (onGround == true) {
                    vy = -7
                    pvy = -7
                }
            }
        }
        if (queue(6) == true) {
            if (TerraFrame.DEBUG_FLIGHT) {
                vy += 1
                pvy += 1
            }
        }
        if (!onGround) {
            vy = vy + 0.3
            pvy = pvy + 0.3
            if (vy > 7 && !TerraFrame.DEBUG_FLIGHT) {
                vy = 7
            }
        }
        if (!queue(0) && !queue(1)) {
            if (Math.abs(vx) < 0.3) {
                vx = 0
            }
            if (vx >= 0.3) {
                vx = vx - 0.3
            }
            if (vx <= -0.3) {
                vx = vx + 0.3
            }
            if (grounded) {
                if (imgState.equals("still left") || imgState.equals("walk left 1") ||
                    imgState.equals("walk left 2")) {
                    imgState = "still left"
                    image = loadImage("sprites/player/left_still.png")
                }
                if (imgState.equals("still right") || imgState.equals("walk right 1") ||
                    imgState.equals("walk right 2")) {
                    imgState = "still right"
                    image = loadImage("sprites/player/right_still.png")
                }
            }
        }

        if (!grounded) {
            if (imgState.equals("still left") || imgState.equals("walk left 1") ||
                imgState.equals("walk left 2")) {
                image = loadImage("sprites/player/left_jump.png")
            }
            if (imgState.equals("still right") || imgState.equals("walk right 1") ||
                imgState.equals("walk right 2")) {
                image = loadImage("sprites/player/right_jump.png")
            }
        }

        onGroundDelay = onGround

        oldx = x
        oldy = y

        x = x + vx

        if (!TerraFrame.DEBUG_NOCLIP) {
            (0 until 2).foreach { i =>
                ix = x.toInt
                iy = y.toInt
                ivx = vx.toInt
                ivy = vy.toInt

                rect = new Rectangle(ix-1, iy, width+2, height)

                bx1 = (x/BLOCKSIZE).toInt
                by1 = (y/BLOCKSIZE).toInt
                bx2 = ((x+width)/BLOCKSIZE).toInt
                by2 = ((y+height)/BLOCKSIZE).toInt

                (bx1 to bx2).foreach { i =>
                    (by1 to by2).foreach { j =>
                        if (blocks(j+v)(i+u) != 0 && TerraFrame.getBLOCKCD().get(blocks(j+v)(i+u)).exists(identity)) {
                            if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                if (oldx <= i*16 - width && vx > 0) {
                                    x = i*16 - width
                                    vx = 0 // right
                                }
                                if (oldx >= i*16 + BLOCKSIZE && vx < 0) {
                                    x = i*16 + BLOCKSIZE
                                    vx = 0 // left
                                }
                            }
                        }
                    }
                }
            }
        }

        y = y + vy
        onGround = false
        if (!TerraFrame.DEBUG_NOCLIP) {
            (0 until 2).foreach { i =>
                ix = x.toInt
                iy = y.toInt
                ivx = vx.toInt
                ivy = vy.toInt

                rect = new Rectangle(ix, iy-1, width, height+2)

                bx1 = (x/BLOCKSIZE).toInt
                by1 = (y/BLOCKSIZE).toInt
                bx2 = ((x+width)/BLOCKSIZE).toInt
                by2 = ((y+height)/BLOCKSIZE).toInt

                by1 = Math.max(0, by1)
                by2 = Math.min(blocks.length - 1, by2)

                (bx1 to bx2).foreach { i =>
                    (by1 to by2).foreach { j =>
                        if (blocks(j+v)(i+u) != 0 && TerraFrame.getBLOCKCD().get(blocks(j+v)(i+u)).exists(identity)) {
                            if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                if (oldy <= j*16 - height && vy > 0) {
                                    y = j*16 - height
                                    if (pvy >= 10 && !TerraFrame.DEBUG_INVINCIBLE) {
                                        hp -= (((pvy - 12.5))*2).toInt
                                    }
                                    onGround = true
                                    vy = 0 // down
                                    pvy = 0
                                }
                                if (oldy >= j*16 + BLOCKSIZE && vy < 0) {
                                    y = j*16 + BLOCKSIZE
                                    vy = 0 // up
                                }
                            }
                        }
                    }
                }
            }
        }

        ix = x.toInt
        iy = y.toInt
        ivx = vx.toInt
        ivy = vy.toInt

        rect = new Rectangle(ix-1, iy-1, width+2, height+2)
    }

    def reloadImage(): Unit = {
        if (grounded) {
            if (imgState.equals("still left") || imgState.equals("walk left 1")) {
                image = loadImage("sprites/player/left_still.png")
            }
            if (imgState.equals("walk left 2")) {
                image = loadImage("sprites/player/left_walk.png")
            }
            if (imgState.equals("still right") || imgState.equals("walk right 1")) {
                image = loadImage("sprites/player/right_still.png")
            }
            if (imgState.equals("walk right 2")) {
                image = loadImage("sprites/player/right_walk.png")
            }
        }
        else {
            if (imgState.equals("still left") || imgState.equals("walk left 1") ||
                imgState.equals("walk left 2")) {
                image = loadImage("sprites/player/left_jump.png")
            }
            if (imgState.equals("still right") || imgState.equals("walk right 1") ||
                imgState.equals("walk right 2")) {
                image = loadImage("sprites/player/right_jump.png")
            }
        }
    }

    def damage(damage: Int, useArmor: Boolean, inventory: Inventory): Unit =  {
        var fd: Int = damage
        if (useArmor) {
            fd -= sumArmor()
            (0 until 4).foreach { i =>
                TerraFrame.armor.durs(i) = (TerraFrame.armor.durs(i) - 1).toShort
                if (TerraFrame.armor.durs(i) <= 0) {
                    inventory.removeLocationIC(TerraFrame.armor, i, TerraFrame.armor.nums(i))
                }
            }
        }
        if (fd < 1) {
            fd = 1
        }
        hp -= fd
    }

    def sumArmor(): Int = {
        val s = for {
            armor0 <- TerraFrame.getARMOR().get(TerraFrame.armor.ids(0))
            armor1 <- TerraFrame.getARMOR().get(TerraFrame.armor.ids(1))
            armor2 <- TerraFrame.getARMOR().get(TerraFrame.armor.ids(2))
            armor3 <- TerraFrame.getARMOR().get(TerraFrame.armor.ids(3))
        } yield armor0 + armor1 + armor2 + armor3
        s.getOrElse(0)
    }
   
}
