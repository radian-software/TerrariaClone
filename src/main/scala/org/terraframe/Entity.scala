package org.terraframe;

import java.awt._
import java.awt.image._
import java.io.Serializable
import java.util._
import Images.loadImage


case class Entity(var x: Double, var y: Double, var vx: Double, var vy: Double, id: Short, num: Short, dur: Short, var mdelay: Int, name: String) extends Serializable {
    import TerraFrame.BLOCKSIZE

    //Begin constructor
    var oldx: Double = x
    var oldy: Double = y
    
    var nohit: Boolean = false

    var thp, ap, atk: Int = _
    var AI, imgState: String = _
    var onGround, immune, grounded, onGroundDelay: Boolean = _

    var dframes: Int = _
    var n: Double = _
    var bx1, bx2, by1, by2: Int = _
    var i, j, k: Int = _

    var newMob: Entity = _


    @transient var image: BufferedImage = _
    
    if(name != null) {
        if (name.equals("blue_bubble")) {
            thp = 18; ap = 0; atk = 2; AI = "bubble";
        }
        if (name.equals("green_bubble")) {
            thp = 25; ap = 0; atk = 4; AI = "bubble";
        }
        if (name.equals("red_bubble")) {
            thp = 40; ap = 0; atk = 6; AI = "bubble";
        }
        if (name.equals("yellow_bubble")) {
            thp = 65; ap = 1; atk = 9; AI = "bubble";
        }
        if (name.equals("black_bubble")) {
            thp = 100; ap = 3; atk = 14; AI = "bubble";
        }
        if (name.equals("white_bubble")) {
            thp = 70; ap = 2; atk = 11; AI = "fast_bubble";
        }
        if (name.equals("zombie")) {
            thp = 35; ap = 0; atk = 5; AI = "zombie";
        }
        if (name.equals("armored_zombie")) {
            thp = 45; ap = 2; atk = 7; AI = "zombie";
        }
        if (name.equals("shooting_star")) {
            thp = 25; ap = 0; atk = 5; AI = "shooting_star";
        }
        if (name.equals("sandbot")) {
            thp = 50; ap = 2; atk = 3; AI = "sandbot";
        }
        if (name.equals("sandbot_bullet")) {
            thp = 1; ap = 0; atk = 7; AI = "bullet"; nohit = false;
        }
        if (name.equals("snowman")) {
            thp = 40; ap = 0; atk = 6; AI = "zombie";
        }
        if (name.equals("bat")) {
            thp = 15; ap = 0; atk = 5; AI = "bat";
        };
        if (name.equals("bee")) {
            thp = 1; ap = 0; atk = 5; AI = "bee";
        };
        if (name.equals("skeleton")) {
            thp = 50; ap = 1; atk = 7; AI = "zombie";
        };

        if (AI == "bubble" || AI == "fast_bubble" || AI == "shooting_star" || AI == "sandbot" || AI == "bullet" || AI == "bee") {
            image = loadImage("sprites/monsters/" + name + "/normal.png")
        }
        if (AI == "zombie") {
            image = loadImage("sprites/monsters/" + name + "/right_still.png")
        }
        if (AI == "bat") {
            image = loadImage("sprites/monsters/" + name + "/normal_right.png")
        }

        if (AI == "bat") {
            imgState = "normal right"
            vx = 3
        }
        else {
            imgState = "still right"
        }
    } else {
        dframes = 0
        TerraFrame.itemImgs.get(id).foreach { i =>
            image = i
        }
    }

    val width: Int = image.getWidth()*2
    val height: Int = image.getHeight()*2

    var ix = x.toInt
    var iy = y.toInt
    var ivx = vx.toInt
    var ivy = vy.toInt
    var rect: Rectangle = new Rectangle(ix - 1, iy, width + 2, height)

    var imgDelay: Int = 0
    var bcount: Int = 0

    var hp: Int = thp
    
   
    def this(x: Double, y: Double, vx: Double, vy: Double, name: String) {
        this(x, y, vx, vy, 0, 0, 0, 0, name)
    }

    def this(x: Double, y: Double, vx: Double, vy: Double, id: Short, num: Short) {
        this(x, y, vx, vy, id, num, 0, 0, null)
    }

    def this(x: Double, y: Double, vx: Double, vy: Double, id: Short, num: Short, mdelay: Int) {
        this(x, y, vx, vy, id, num, 0, mdelay, null)
    }

    def this(x: Double, y: Double, vx: Double, vy: Double, id: Short, num: Short, dur: Short) {
        this(x, y, vx, vy, id, num, dur, 0, null)
    }

    def this(x: Double, y: Double, vx: Double, vy: Double, id: Short, num: Short, dur: Short, mdelay: Int) {
        this(x, y, vx, vy, id, num, dur, mdelay, null)
    }

    def update(blocks: Array2D[Int], player: Player, u: Int, v: Int): Boolean = {
        newMob = null
        if (name == null) {
            if (!onGround) {
                vy = vy + 0.3
                if (vy > 7) {
                    vy = 7
                }
            }
            if (vx < -0.15) {
                vx = vx + 0.15
            }
            else if (vx > 0.15) {
                vx = vx - 0.15
            }
            else {
                vx = 0
            }
            collide(blocks, player, u, v)
            mdelay -= 1
        }
        if (AI == "bullet") {
            if (collide(blocks, player, u, v)) {
                return true
            }
        }
        if (AI == "zombie") {
            if (!onGround) {
                vy = vy + 0.3
                if (vy > 7) {
                    vy = 7
                }
            }
            if (x > player.x) {
                vx = Math.max(vx - 0.1, -1.2)
                if (imgState == "still left" || imgState == "still right" ||
                    imgState == "walk right 1" || imgState == "walk right 2") {
                    imgDelay = 10
                    imgState = "walk left 2"
                    image = loadImage("sprites/monsters/" + name + "/left_walk.png")
                }
                if (imgDelay <= 0) {
                    if (imgState == "walk left 1") {
                        imgDelay = 10
                        imgState = "walk left 2"
                        image = loadImage("sprites/monsters/" + name + "/left_walk.png")
                    }
                    else {
                        if (imgState == "walk left 2") {
                            imgDelay = 10
                            imgState = "walk left 1"
                            image = loadImage("sprites/monsters/" + name + "/left_still.png")
                        }
                    }
                }
                else {
                    imgDelay = imgDelay - 1
                }
            }
            else {
                vx = Math.min(vx + 0.1, 1.2)
                if (imgState == "still left" || imgState == "still right" ||
                    imgState == "walk left 1" || imgState == "walk left 2") {
                    imgDelay = 10
                    imgState = "walk right 2"
                    image = loadImage("sprites/monsters/" + name + "/right_walk.png")
                }
                if (imgDelay <= 0) {
                    if (imgState == "walk right 1") {
                        imgDelay = 10
                        imgState = "walk right 2"
                        image = loadImage("sprites/monsters/" + name + "/right_walk.png")
                    }
                    else {
                        if (imgState == "walk right 2") {
                            imgDelay = 10
                            imgState = "walk right 1"
                            image = loadImage("sprites/monsters/" + name + "/right_still.png")
                        }
                    }
                }
                else {
                    imgDelay = imgDelay - 1
                }
            }
            if (!grounded) {
                if (imgState == "still left" || imgState == "walk left 1" ||
                    imgState == "walk left 2") {
                    image = loadImage("sprites/monsters/" + name + "/left_jump.png")
                }
                if (imgState == "still right" || imgState == "walk right 1" ||
                    imgState == "walk right 2") {
                    image = loadImage("sprites/monsters/" + name + "/right_jump.png")
                }
            }
            collide(blocks, player, u, v)
        }
        if (AI == "bubble") {
            if (x > player.x) {
                vx = Math.max(vx - 0.1, -1.2)
            }
            else {
                vx = Math.min(vx + 0.1, 1.2)
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.1, -1.2)
            }
            else {
                vy = Math.min(vy + 0.1, 1.2)
            }
            collide(blocks, player, u, v)
        }
        if (AI == "fast_bubble") {
            if (x > player.x) {
                vx = Math.max(vx - 0.2, -2.4)
            }
            else {
                vx = Math.min(vx + 0.2, 2.4)
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.2, -2.4)
            }
            else {
                vy = Math.min(vy + 0.2, 2.4)
            }
            collide(blocks, player, u, v)
        }
        if (AI == "shooting_star") {
            n = Math.atan2(player.y - y, player.x - x)
            vx += Math.cos(n)/10
            vy += Math.sin(n)/10
            if (vx < -5) vx = -5
            if (vx > 5) vx = 5
            if (vy < -5) vy = -5
            if (vy > 5) vy = 5
            collide(blocks, player, u, v)
        }
        if (AI == "sandbot") {
            if (Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2)) > 160) {
                if (x > player.x) {
                    vx = Math.max(vx - 0.1, -1.2)
                }
                else {
                    vx = Math.min(vx + 0.1, 1.2)
                }
                if (y > player.y) {
                    vy = Math.max(vy - 0.1, -1.2)
                }
                else {
                    vy = Math.min(vy + 0.1, 1.2)
                }
            }
            else {
                if (x < player.x) {
                    vx = Math.max(vx - 0.1, -1.2)
                }
                else {
                    vx = Math.min(vx + 0.1, 1.2)
                }
                if (y < player.y) {
                    vy = Math.max(vy - 0.1, -1.2)
                }
                else {
                    vy = Math.min(vy + 0.1, 1.2)
                }
            }
            bcount += 1
            if (bcount == 110) {
                image = loadImage("sprites/monsters/" + name + "/ready1.png")
            }
            if (bcount == 130) {
                image = loadImage("sprites/monsters/" + name + "/ready2.png")
            }
            if (bcount == 150) {
                val theta: Double = Math.atan2(player.y - y, player.x - x)
                newMob = new Entity(x, y, Math.cos(theta)*3.5, Math.sin(theta)*3.5, name + "_bullet")
            }
            if (bcount == 170) {
                image = loadImage("sprites/monsters/" + name + "/ready1.png")
            }
            if (bcount == 190) {
                image = loadImage("sprites/monsters/" + name + "/normal.png")
                bcount = 0
            }
            collide(blocks, player, u, v)
        }
        if (AI == "bat") {
            if (vx > 3) {
                vx = 3
            }
            if (vx < 3) {
                vx = -3
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.05, -2.0)
            }
            else {
                vy = Math.min(vy + 0.05, 2.0)
            }
            imgDelay -= 1
            if (vx > 0 && imgState != "normal right") {
                imgState = "normal right"
                image = loadImage("sprites/monsters/" + name + "/normal_right.png")
                imgDelay = 10
            }
            if (vx < 0 && imgState != "normal left") {
                imgState = "normal left"
                image = loadImage("sprites/monsters/" + name + "/normal_left.png")
                imgDelay = 10
            }
            if (imgState == "normal left" && imgDelay <= 0) {
                imgState = "flap left"
                image = loadImage("sprites/monsters/" + name + "/flap_left.png")
                imgDelay = 10
            }
            if (imgState == "normal right" && imgDelay <= 0) {
                imgState = "flap right"
                image = loadImage("sprites/monsters/" + name + "/flap_right.png")
                imgDelay = 10
            }
            if (imgState == "flap left" && imgDelay <= 0) {
                imgState = "normal left"
                image = loadImage("sprites/monsters/" + name + "/normal_left.png")
                imgDelay = 10
            }
            if (imgState == "flap right" && imgDelay <= 0) {
                imgState = "normal right"
                image = loadImage("sprites/monsters/" + name + "/normal_right.png")
                imgDelay = 10
            }
            collide(blocks, player, u, v)
        }
        if (AI == "bee") {
            val theta: Double = Math.atan2(player.y - y, player.x - x)
            vx = Math.cos(theta)*2.5
            vy = Math.sin(theta)*2.5
            collide(blocks, player, u, v)
        }
        return false
    }

    def collide(blocks: Array2D[Int], player: Player, u: Int, v: Int): Boolean = {
        var rv: Boolean = false

        grounded = (onGround || onGroundDelay)

        onGroundDelay = onGround

        oldx = x
        oldy = y

        x = x + vx

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

            bx1 = Math.max(0, bx1)
            by1 = Math.max(0, by1)
            bx2 = Math.min(blocks(0).length - 1, bx2)
            by2 = Math.min(blocks.length - 1, by2)

            (bx1 to bx2).foreach { i =>
                (by1 to by2).foreach { j =>
                    if (blocks(j)(i) != 0 && TerraFrame.BLOCKCD.get(blocks(j+v)(i+u)).exists(identity)) {
                        if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                            if (oldx <= i*16 - width && (vx > 0 || AI == "shooting_star")) {
                                x = i*16 - width
                                if (AI == "bubble") {
                                    vx = -vx
                                }
                                else if (AI == "zombie") {
                                    vx = 0
                                    if (onGround && player.x > x) {
                                        vy = -7
                                    }
                                }
                                else if (AI == "bat") {
                                    vx = -vx
                                }
                                else {
                                    vx = 0 // right
                                }
                                rv = true
                            }
                            if (oldx >= i*16 + BLOCKSIZE && (vx < 0 || AI == "shooting_star")) {
                                x = i*16 + BLOCKSIZE
                                if (AI == "bubble") {
                                    vx = -vx
                                }
                                else if (AI == "zombie") {
                                    vx = 0
                                    if (onGround && player.x < x) {
                                        vy = -7
                                    }
                                }
                                else if (AI == "bat") {
                                    vx = -vx
                                }
                                else {
                                    vx = 0 // left
                                }
                                rv = true
                            }
                        }
                    }
                }
            }
        }

        y = y + vy
        onGround = false

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

            bx1 = Math.max(0, bx1)
            by1 = Math.max(0, by1)
            bx2 = Math.min(blocks(0).length - 1, bx2)
            by2 = Math.min(blocks.length - 1, by2)

            (bx1 to bx2).foreach { i =>
                (by1 to by2).foreach { j =>
                    if (blocks(j)(i) != 0 && TerraFrame.BLOCKCD.get(blocks(j+v)(i+u)).exists(identity)) {
                        if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                            if (oldy <= j*16 - height && (vy > 0 || AI == "shooting_star")) {
                                y = j*16 - height
                                onGround = true
                                if (AI == "bubble") {
                                    vy = -vy
                                }
                                else {
                                    vy = 0 // down
                                }
                                rv = true
                            }
                            if (oldy >= j*16 + BLOCKSIZE && (vy < 0 || AI == "shooting_star")) {
                                y = j*16 + BLOCKSIZE
                                if (AI == "bubble") {
                                    vy = -vy
                                }
                                else {
                                    vy = 0 // up
                                }
                                rv = true
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

        return rv
    }

    def hit(damage: Int, player: Player): Boolean = {
        if (!immune && !nohit) {
            hp -= Math.max(1, damage - ap)
            immune = true
            if (AI == "shooting_star") {
                if (player.x + player.width/2 < x + width/2) {
                    vx = 4
                }
                else {
                    vx = -4
                }
            }
            else {
                if (player.x + player.width/2 < x + width/2) {
                    vx += 4
                }
                else {
                    vx -= 4
                }
                vy -= 1.2
            }
        }
        return hp <= 0
    }

    def drops(): ArrayList[Short] = {
        val dropList = new ArrayList[Short]()
        val random: Random = TerraFrame.random
        if (name == "blue_bubble") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(97.toShort)
            }
        }
        if (name == "green_bubble") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(98.toShort)
            }
        }
        if (name == "red_bubble") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(99.toShort)
            }
        }
        if (name == "yellow_bubble") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(100.toShort)
            }
        }
        if (name == "black_bubble") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(101.toShort)
            }
        }
        if (name == "white_bubble") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(102.toShort)
            }
        }
        if (name == "shooting_star") {
            (0 until random.nextInt(2)).foreach { i =>
                dropList.add(103.toShort)
            }
        }
        if (name == "zombie") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(104.toShort)
            }
        }
        if (name == "armored_zombie") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(104.toShort)
            }
            if (random.nextInt(15) == 0) {
                dropList.add(109.toShort)
            }
            if (random.nextInt(15) == 0) {
                dropList.add(110.toShort)
            }
            if (random.nextInt(15) == 0) {
                dropList.add(111.toShort)
            }
            if (random.nextInt(15) == 0) {
                dropList.add(112.toShort)
            }
        }
        if (name == "sandbot") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(74.toShort)
            }
            if (random.nextInt(2) == 0) {
                dropList.add(44.toShort)
            }
            if (random.nextInt(6) == 0) {
                dropList.add(45.toShort)
            }
        }
        if (name == "snowman") {
            (0 until random.nextInt(3)).foreach { i =>
                dropList.add(75.toShort)
            }
        }
        return dropList
    }

    def reloadImage(): Unit = {
        if (AI.equals("bubble") || AI.equals("shooting_star")) {
            image = loadImage("sprites/monsters/" + name + "/normal.png")
        }
        if (AI.equals("zombie")) {
            image = loadImage("sprites/monsters/" + name + "/right_still.png")
        }
    }

}
