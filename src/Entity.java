import java.awt.*;
import java.awt.image.*;
import java.io.Serializable;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;

public class Entity implements Serializable {

    double x, y, vx, vy, oldx, oldy, n;
    int ix, iy, ivx, ivy, width, height, bx1, bx2, by1, by2, bcount;
    int i, j, k;
    int BLOCKSIZE = TerrariaClone.getBLOCKSIZE();
    double mdelay = 0;

    int thp, hp, ap, atk;

    short id, num, dur;

    boolean onGround, immune, grounded, onGroundDelay, nohit;

    int dframes, imgDelay;

    String name, AI, imgState;

    Rectangle rect;

    Entity newMob;

    transient BufferedImage image;

    public Entity(double x, double y, double vx, double vy, String name) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.name = name;
        oldx = x;
        oldy = y;
        ix = (int)x;
        iy = (int)y;
        nohit = false;

        if (name.equals("blue_bubble")) { thp = 18; ap = 0; atk = 2; AI = "bubble"; }
        if (name.equals("green_bubble")) { thp = 25; ap = 0; atk = 4; AI = "bubble"; }
        if (name.equals("red_bubble")) { thp = 40; ap = 0; atk = 6; AI = "bubble"; }
        if (name.equals("yellow_bubble")) { thp = 65; ap = 1; atk = 9; AI = "bubble"; }
        if (name.equals("black_bubble")) { thp = 100; ap = 3; atk = 14; AI = "bubble"; }
        if (name.equals("white_bubble")) { thp = 70; ap = 2; atk = 11; AI = "fast_bubble"; }
        if (name.equals("zombie")) { thp = 35; ap = 0; atk = 5; AI = "zombie"; }
        if (name.equals("armored_zombie")) { thp = 45; ap = 2; atk = 7; AI = "zombie"; }
        if (name.equals("shooting_star")) { thp = 25; ap = 0; atk = 5; AI = "shooting_star"; }
        if (name.equals("sandbot")) { thp = 50; ap = 2; atk = 3; AI = "sandbot"; }
        if (name.equals("sandbot_bullet")) { thp = 1; ap = 0; atk = 7; AI = "bullet"; nohit = false; }
        if (name.equals("snowman")) { thp = 40; ap = 0; atk = 6; AI = "zombie"; }
        if (name.equals("bat")) { thp = 15; ap = 0; atk = 5; AI = "bat"; };
        if (name.equals("bee")) { thp = 1; ap = 0; atk = 5; AI = "bee"; };
        if (name.equals("skeleton")) { thp = 50; ap = 1; atk = 7; AI = "zombie"; };

        if (AI == "bubble" || AI == "fast_bubble" || AI == "shooting_star" || AI == "sandbot" || AI == "bullet" || AI == "bee") {
            image = loadImage("sprites/monsters/" + name + "/normal.png");
        }
        if (AI == "zombie") {
            image = loadImage("sprites/monsters/" + name + "/right_still.png");
        }
        if (AI == "bat") {
            image = loadImage("sprites/monsters/" + name + "/normal_right.png");
        }

        width = image.getWidth()*2; height = image.getHeight()*2;

        ix = (int)x;
        iy = (int)y;
        ivx = (int)vx;
        ivy = (int)vy;

        rect = new Rectangle(ix-1, iy, width+2, height);

        imgDelay = 0;
        bcount = 0;
        if (AI == "bat") {
            imgState = "normal right";
            this.vx = 3;
        }
        else {
            imgState = "still right";
        }

        hp = thp;
    }

    public Entity(double x, double y, double vx, double vy, short id, short num) {
        this(x, y, vx, vy, id, num, (short)0, 0);
    }

    public Entity(double x, double y, double vx, double vy, short id, short num, int mdelay) {
        this(x, y, vx, vy, id, num, (short)0, mdelay);
    }

    public Entity(double x, double y, double vx, double vy, short id, short num, short dur) {
        this(x, y, vx, vy, id, num, dur, 0);
    }

    public Entity(double x, double y, double vx, double vy, short id, short num, short dur, int mdelay) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.id = id;
        this.num = num;
        this.dur = dur;
        this.mdelay = mdelay;
        oldx = x;
        oldy = y;
        ix = (int)x;
        iy = (int)y;

        dframes = 0;

        image = TerrariaClone.getItemImgs().get(id);

        width = image.getWidth()*2; height = image.getHeight()*2;
    }

    public boolean update(Integer[][] blocks, Player player, int u, int v) {
        newMob = null;
        if (name == null) {
            if (!onGround) {
                vy = vy + 0.3;
                if (vy > 7) {
                    vy = 7;
                }
            }
            if (vx < -0.15) {
                vx = vx + 0.15;
            }
            else if (vx > 0.15) {
                vx = vx - 0.15;
            }
            else {
                vx = 0;
            }
            collide(blocks, player, u, v);
            mdelay -= 1;
        }
        if (AI == "bullet") {
            if (collide(blocks, player, u, v)) {
                return true;
            }
        }
        if (AI == "zombie") {
            if (!onGround) {
                vy = vy + 0.3;
                if (vy > 7) {
                    vy = 7;
                }
            }
            if (x > player.x) {
                vx = Math.max(vx - 0.1, -1.2);
                if (imgState == "still left" || imgState == "still right" ||
                    imgState == "walk right 1" || imgState == "walk right 2") {
                    imgDelay = 10;
                    imgState = "walk left 2";
                    image = loadImage("sprites/monsters/" + name + "/left_walk.png");
                }
                if (imgDelay <= 0) {
                    if (imgState == "walk left 1") {
                        imgDelay = 10;
                        imgState = "walk left 2";
                        image = loadImage("sprites/monsters/" + name + "/left_walk.png");
                    }
                    else {
                        if (imgState == "walk left 2") {
                            imgDelay = 10;
                            imgState = "walk left 1";
                            image = loadImage("sprites/monsters/" + name + "/left_still.png");
                        }
                    }
                }
                else {
                    imgDelay = imgDelay - 1;
                }
            }
            else {
                vx = Math.min(vx + 0.1, 1.2);
                if (imgState == "still left" || imgState == "still right" ||
                    imgState == "walk left 1" || imgState == "walk left 2") {
                    imgDelay = 10;
                    imgState = "walk right 2";
                    image = loadImage("sprites/monsters/" + name + "/right_walk.png");
                }
                if (imgDelay <= 0) {
                    if (imgState == "walk right 1") {
                        imgDelay = 10;
                        imgState = "walk right 2";
                        image = loadImage("sprites/monsters/" + name + "/right_walk.png");
                    }
                    else {
                        if (imgState == "walk right 2") {
                            imgDelay = 10;
                            imgState = "walk right 1";
                            image = loadImage("sprites/monsters/" + name + "/right_still.png");
                        }
                    }
                }
                else {
                    imgDelay = imgDelay - 1;
                }
            }
            if (!grounded) {
                if (imgState == "still left" || imgState == "walk left 1" ||
                    imgState == "walk left 2") {
                    image = loadImage("sprites/monsters/" + name + "/left_jump.png");
                }
                if (imgState == "still right" || imgState == "walk right 1" ||
                    imgState == "walk right 2") {
                    image = loadImage("sprites/monsters/" + name + "/right_jump.png");
                }
            }
            collide(blocks, player, u, v);
        }
        if (AI == "bubble") {
            if (x > player.x) {
                vx = Math.max(vx - 0.1, -1.2);
            }
            else {
                vx = Math.min(vx + 0.1, 1.2);
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.1, -1.2);
            }
            else {
                vy = Math.min(vy + 0.1, 1.2);
            }
            collide(blocks, player, u, v);
        }
        if (AI == "fast_bubble") {
            if (x > player.x) {
                vx = Math.max(vx - 0.2, -2.4);
            }
            else {
                vx = Math.min(vx + 0.2, 2.4);
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.2, -2.4);
            }
            else {
                vy = Math.min(vy + 0.2, 2.4);
            }
            collide(blocks, player, u, v);
        }
        if (AI == "shooting_star") {
            n = Math.atan2(player.y - y, player.x - x);
            vx += Math.cos(n)/10;
            vy += Math.sin(n)/10;
            if (vx < -5) vx = -5;
            if (vx > 5) vx = 5;
            if (vy < -5) vy = -5;
            if (vy > 5) vy = 5;
            collide(blocks, player, u, v);
        }
        if (AI == "sandbot") {
            if (Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2)) > 160) {
                if (x > player.x) {
                    vx = Math.max(vx - 0.1, -1.2);
                }
                else {
                    vx = Math.min(vx + 0.1, 1.2);
                }
                if (y > player.y) {
                    vy = Math.max(vy - 0.1, -1.2);
                }
                else {
                    vy = Math.min(vy + 0.1, 1.2);
                }
            }
            else {
                if (x < player.x) {
                    vx = Math.max(vx - 0.1, -1.2);
                }
                else {
                    vx = Math.min(vx + 0.1, 1.2);
                }
                if (y < player.y) {
                    vy = Math.max(vy - 0.1, -1.2);
                }
                else {
                    vy = Math.min(vy + 0.1, 1.2);
                }
            }
            bcount += 1;
            if (bcount == 110) {
                image = loadImage("sprites/monsters/" + name + "/ready1.png");
            }
            if (bcount == 130) {
                image = loadImage("sprites/monsters/" + name + "/ready2.png");
            }
            if (bcount == 150) {
                double theta = Math.atan2(player.y - y, player.x - x);
                newMob = new Entity(x, y, Math.cos(theta)*3.5, Math.sin(theta)*3.5, name + "_bullet");
            }
            if (bcount == 170) {
                image = loadImage("sprites/monsters/" + name + "/ready1.png");
            }
            if (bcount == 190) {
                image = loadImage("sprites/monsters/" + name + "/normal.png");
                bcount = 0;
            }
            collide(blocks, player, u, v);
        }
        if (AI == "bat") {
            if (vx > 3) {
                vx = 3;
            }
            if (vx < 3) {
                vx = -3;
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.05, -2.0);
            }
            else {
                vy = Math.min(vy + 0.05, 2.0);
            }
            imgDelay -= 1;
            if (vx > 0 && imgState != "normal right") {
                imgState = "normal right";
                image = loadImage("sprites/monsters/" + name + "/normal_right.png");
                imgDelay = 10;
            }
            if (vx < 0 && imgState != "normal left") {
                imgState = "normal left";
                image = loadImage("sprites/monsters/" + name + "/normal_left.png");
                imgDelay = 10;
            }
            if (imgState == "normal left" && imgDelay <= 0) {
                imgState = "flap left";
                image = loadImage("sprites/monsters/" + name + "/flap_left.png");
                imgDelay = 10;
            }
            if (imgState == "normal right" && imgDelay <= 0) {
                imgState = "flap right";
                image = loadImage("sprites/monsters/" + name + "/flap_right.png");
                imgDelay = 10;
            }
            if (imgState == "flap left" && imgDelay <= 0) {
                imgState = "normal left";
                image = loadImage("sprites/monsters/" + name + "/normal_left.png");
                imgDelay = 10;
            }
            if (imgState == "flap right" && imgDelay <= 0) {
                imgState = "normal right";
                image = loadImage("sprites/monsters/" + name + "/normal_right.png");
                imgDelay = 10;
            }
            collide(blocks, player, u, v);
        }
        if (AI == "bee") {
            double theta = Math.atan2(player.y - y, player.x - x);
            vx = Math.cos(theta)*2.5;
            vy = Math.sin(theta)*2.5;
            collide(blocks, player, u, v);
        }
        return false;
    }

    public boolean collide(Integer[][] blocks, Player player, int u, int v) {
        boolean rv = false;

        grounded = (onGround || onGroundDelay);

        onGroundDelay = onGround;

        oldx = x; oldy = y;

        x = x + vx;

        for (i=0; i<2; i++) {
            ix = (int)x;
            iy = (int)y;
            ivx = (int)vx;
            ivy = (int)vy;

            rect = new Rectangle(ix-1, iy, width+2, height);

            bx1 = (int)x/BLOCKSIZE; by1 = (int)y/BLOCKSIZE;
            bx2 = (int)(x+width)/BLOCKSIZE; by2 = (int)(y+height)/BLOCKSIZE;

            bx1 = Math.max(0, bx1); by1 = Math.max(0, by1);
            bx2 = Math.min(blocks[0].length - 1, bx2); by2 = Math.min(blocks.length - 1, by2);

            for (i=bx1; i<=bx2; i++) {
                for (j=by1; j<=by2; j++) {
                    if (blocks[j][i] != 0 && TerrariaClone.getBLOCKCD().get(blocks[j+v][i+u])) {
                        if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                            if (oldx <= i*16 - width && (vx > 0 || AI == "shooting_star")) {
                                x = i*16 - width;
                                if (AI == "bubble") {
                                    vx = -vx;
                                }
                                else if (AI == "zombie") {
                                    vx = 0;
                                    if (onGround && player.x > x) {
                                        vy = -7;
                                    }
                                }
                                else if (AI == "bat") {
                                    vx = -vx;
                                }
                                else {
                                    vx = 0; // right
                                }
                                rv = true;
                            }
                            if (oldx >= i*16 + BLOCKSIZE && (vx < 0 || AI == "shooting_star")) {
                                x = i*16 + BLOCKSIZE;
                                if (AI == "bubble") {
                                    vx = -vx;
                                }
                                else if (AI == "zombie") {
                                    vx = 0;
                                    if (onGround && player.x < x) {
                                        vy = -7;
                                    }
                                }
                                else if (AI == "bat") {
                                    vx = -vx;
                                }
                                else {
                                    vx = 0; // left
                                }
                                rv = true;
                            }
                        }
                    }
                }
            }
        }

        y = y + vy;
        onGround = false;

        for (i=0; i<2; i++) {
            ix = (int)x;
            iy = (int)y;
            ivx = (int)vx;
            ivy = (int)vy;

            rect = new Rectangle(ix, iy-1, width, height+2);

            bx1 = (int)x/BLOCKSIZE; by1 = (int)y/BLOCKSIZE;
            bx2 = (int)(x+width)/BLOCKSIZE; by2 = (int)(y+height)/BLOCKSIZE;

            bx1 = Math.max(0, bx1); by1 = Math.max(0, by1);
            bx2 = Math.min(blocks[0].length - 1, bx2); by2 = Math.min(blocks.length - 1, by2);

            for (i=bx1; i<=bx2; i++) {
                for (j=by1; j<=by2; j++) {
                    if (blocks[j][i] != 0 && TerrariaClone.getBLOCKCD().get(blocks[j+v][i+u])) {
                        if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                            if (oldy <= j*16 - height && (vy > 0 || AI == "shooting_star")) {
                                y = j*16 - height;
                                onGround = true;
                                if (AI == "bubble") {
                                    vy = -vy;
                                }
                                else {
                                    vy = 0; // down
                                }
                                rv = true;
                            }
                            if (oldy >= j*16 + BLOCKSIZE && (vy < 0 || AI == "shooting_star")) {
                                y = j*16 + BLOCKSIZE;
                                if (AI == "bubble") {
                                    vy = -vy;
                                }
                                else {
                                    vy = 0; // up
                                }
                                rv = true;
                            }
                        }
                    }
                }
            }
        }

        ix = (int)x;
        iy = (int)y;
        ivx = (int)vx;
        ivy = (int)vy;

        rect = new Rectangle(ix-1, iy-1, width+2, height+2);

        return rv;
    }

    public boolean hit(int damage, Player player) {
        if (!immune && !nohit) {
            hp -= Math.max(1, damage - ap);
            immune = true;
            if (AI == "shooting_star") {
                if (player.x + player.width/2 < x + width/2) {
                    vx = 4;
                }
                else {
                    vx = -4;
                }
            }
            else {
                if (player.x + player.width/2 < x + width/2) {
                    vx += 4;
                }
                else {
                    vx -= 4;
                }
                vy -= 1.2;
            }
        }
        return hp <= 0;
    }

    public ArrayList<Short> drops() {
        ArrayList<Short> dropList = new ArrayList<Short>();
        Random random = TerrariaClone.getRandom();
        if (name == "blue_bubble") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)97));
            }
        }
        if (name == "green_bubble") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)98));
            }
        }
        if (name == "red_bubble") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)99));
            }
        }
        if (name == "yellow_bubble") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)100));
            }
        }
        if (name == "black_bubble") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)101));
            }
        }
        if (name == "white_bubble") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)102));
            }
        }
        if (name == "shooting_star") {
            for (i=0; i<random.nextInt(2); i++) {
                dropList.add(new Short((short)103));
            }
        }
        if (name == "zombie") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)104));
            }
        }
        if (name == "armored_zombie") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)104));
            }
            if (random.nextInt(15) == 0) {
                dropList.add(new Short((short)109));
            }
            if (random.nextInt(15) == 0) {
                dropList.add(new Short((short)110));
            }
            if (random.nextInt(15) == 0) {
                dropList.add(new Short((short)111));
            }
            if (random.nextInt(15) == 0) {
                dropList.add(new Short((short)112));
            }
        }
        if (name == "sandbot") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)74));
            }
            if (random.nextInt(2) == 0) {
                dropList.add(new Short((short)44));
            }
            if (random.nextInt(6) == 0) {
                dropList.add(new Short((short)45));
            }
        }
        if (name == "snowman") {
            for (i=0; i<random.nextInt(3); i++) {
                dropList.add(new Short((short)75));
            }
        }
        return dropList;
    }

    public void reloadImage() {
        if (AI.equals("bubble") || AI.equals("shooting_star")) {
            image = loadImage("sprites/monsters/" + name + "/normal.png");
        }
        if (AI.equals("zombie")) {
            image = loadImage("sprites/monsters/" + name + "/right_still.png");
        }
    }

    public static BufferedImage loadImage(String path) {
        URL url = TerrariaClone.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
//            System.out.println("[ERROR] could not load image '" + path + "'.");
        }
        return image;
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void print(int text) {
        System.out.println(text);
    }

    public static void print(double text) {
        System.out.println(text);
    }

    public static void print(short text) {
        System.out.println(text);
    }

    public static void print(boolean text) {
        System.out.println(text);
    }

    public static void print(Object text) {
        System.out.println(text);
    }
}
