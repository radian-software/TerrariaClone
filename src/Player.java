import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.lang.Math;
import java.net.URL;
import javax.imageio.ImageIO;

public class Player implements Serializable {
    transient BufferedImage image;
    int ix, iy, ivx, ivy, width, height, bx1, by1, bx2, by2, thp, hp;
    double x, y, vx, vy, pvy, oldx, oldy;
    boolean onGround, onGroundDelay, grounded;
    boolean ghost = true;
    Rectangle rect;
    short[] blocklist;

    int i, j, n;

    int imgDelay;
    String imgState;

    int BLOCKSIZE = TerrariaClone.getBLOCKSIZE();

    public Player(double x, double y) {
        oldx = this.x = x; oldy = this.y = y;

        vx = 0;
        vy = 0;
        pvy = 0;

        onGround = false;

        image = loadImage("sprites/player/right_still.png");

        width = TerrariaClone.getPLAYERSIZEX();
        height = TerrariaClone.getPLAYERSIZEY();

        ix = (int)x;
        iy = (int)y;
        ivx = (int)ivx;
        ivy = (int)ivy;

        rect = new Rectangle(ix, iy, width, height);

        imgDelay = 0;
        imgState = "still right";

        thp = 50;

        hp = thp;
    }

    public void update(Integer[][] blocks, boolean[] queue, int u, int v) {
        grounded = (onGround || onGroundDelay);
        if (queue[0] == true) {
            if (vx > -4 || TerrariaClone.DEBUG_SPEED) {
                vx = vx - 0.5;
            }
            if (imgState.equals("still left") || imgState.equals("still right") ||
                imgState.equals("walk right 1") || imgState.equals("walk right 2")) {
                imgDelay = 5;
                imgState = "walk left 2";
                image = loadImage("sprites/player/left_walk.png");
            }
            if (imgDelay <= 0) {
                if (imgState.equals("walk left 1")) {
                    imgDelay = 5;
                    imgState = "walk left 2";
                    image = loadImage("sprites/player/left_walk.png");
                }
                else {
                    if (imgState.equals("walk left 2")) {
                        imgDelay = 5;
                        imgState = "walk left 1";
                        image = loadImage("sprites/player/left_still.png");
                    }
                }
            }
            else {
                imgDelay = imgDelay - 1;
            }
        }
        if (queue[1] == true) {
            if (vx < 4 || TerrariaClone.DEBUG_SPEED) {
                vx = vx + 0.5;
            }
            if (imgState.equals("still left") || imgState.equals("still right") ||
                imgState.equals("walk left 1") || imgState.equals("walk left 2")) {
                imgDelay = 5;
                imgState = "walk right 2";
                image = loadImage("sprites/player/right_walk.png");
            }
            if (imgDelay <= 0) {
                if (imgState.equals("walk right 1")) {
                    imgDelay = 5;
                    imgState = "walk right 2";
                    image = loadImage("sprites/player/right_walk.png");
                }
                else {
                    if (imgState.equals("walk right 2")) {
                        imgDelay = 5;
                        imgState = "walk right 1";
                        image = loadImage("sprites/player/right_still.png");
                    }
                }
            }
            else {
                imgDelay = imgDelay - 1;
            }
        }
        if (queue[2] == true) {
            if (TerrariaClone.DEBUG_FLIGHT) {
                vy -= 1;
                pvy -= 1;
            }
            else {
                if (onGround == true) {
                    vy = -7;
                    pvy = -7;
                }
            }
        }
        if (queue[6] == true) {
            if (TerrariaClone.DEBUG_FLIGHT) {
                vy += 1;
                pvy += 1;
            }
        }
        if (!onGround) {
            vy = vy + 0.3;
            pvy = pvy + 0.3;
            if (vy > 7 && !TerrariaClone.DEBUG_FLIGHT) {
                vy = 7;
            }
        }
        if (!queue[0] && !queue[1]) {
            if (Math.abs(vx) < 0.3) {
                vx = 0;
            }
            if (vx >= 0.3) {
                vx = vx - 0.3;
            }
            if (vx <= -0.3) {
                vx = vx + 0.3;
            }
            if (grounded) {
                if (imgState.equals("still left") || imgState.equals("walk left 1") ||
                    imgState.equals("walk left 2")) {
                    imgState = "still left";
                    image = loadImage("sprites/player/left_still.png");
                }
                if (imgState.equals("still right") || imgState.equals("walk right 1") ||
                    imgState.equals("walk right 2")) {
                    imgState = "still right";
                    image = loadImage("sprites/player/right_still.png");
                }
            }
        }

        if (!grounded) {
            if (imgState.equals("still left") || imgState.equals("walk left 1") ||
                imgState.equals("walk left 2")) {
                image = loadImage("sprites/player/left_jump.png");
            }
            if (imgState.equals("still right") || imgState.equals("walk right 1") ||
                imgState.equals("walk right 2")) {
                image = loadImage("sprites/player/right_jump.png");
            }
        }

        onGroundDelay = onGround;

        oldx = x; oldy = y;

        x = x + vx;

        if (!TerrariaClone.DEBUG_NOCLIP) {
            for (i=0; i<2; i++) {
                ix = (int)x;
                iy = (int)y;
                ivx = (int)vx;
                ivy = (int)vy;

                rect = new Rectangle(ix-1, iy, width+2, height);

                bx1 = (int)x/BLOCKSIZE; by1 = (int)y/BLOCKSIZE;
                bx2 = (int)(x+width)/BLOCKSIZE; by2 = (int)(y+height)/BLOCKSIZE;

                for (i=bx1; i<=bx2; i++) {
                    for (j=by1; j<=by2; j++) {
                        if (blocks[j+v][i+u] != 0 && TerrariaClone.getBLOCKCD().get(blocks[j+v][i+u])) {
                            if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                if (oldx <= i*16 - width && vx > 0) {
                                    x = i*16 - width;
                                    vx = 0; // right
                                }
                                if (oldx >= i*16 + BLOCKSIZE && vx < 0) {
                                    x = i*16 + BLOCKSIZE;
                                    vx = 0; // left
                                }
                            }
                        }
                    }
                }
            }
        }

        y = y + vy;
        onGround = false;
        if (!TerrariaClone.DEBUG_NOCLIP) {
            for (i=0; i<2; i++) {
                ix = (int)x;
                iy = (int)y;
                ivx = (int)vx;
                ivy = (int)vy;

                rect = new Rectangle(ix, iy-1, width, height+2);

                bx1 = (int)x/BLOCKSIZE; by1 = (int)y/BLOCKSIZE;
                bx2 = (int)(x+width)/BLOCKSIZE; by2 = (int)(y+height)/BLOCKSIZE;

                by1 = Math.max(0, by1);
                by2 = Math.min(blocks.length - 1, by2);

                for (i=bx1; i<=bx2; i++) {
                    for (j=by1; j<=by2; j++) {
                        if (blocks[j+v][i+u] != 0 && TerrariaClone.getBLOCKCD().get(blocks[j+v][i+u])) {
                            if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                if (oldy <= j*16 - height && vy > 0) {
                                    y = j*16 - height;
                                    if (pvy >= 10 && !TerrariaClone.DEBUG_INVINCIBLE) {
                                        hp -= (int)((pvy - 12.5))*2;
                                    }
                                    onGround = true;
                                    vy = 0; // down
                                    pvy = 0;
                                }
                                if (oldy >= j*16 + BLOCKSIZE && vy < 0) {
                                    y = j*16 + BLOCKSIZE;
                                    vy = 0; // up
                                }
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
    }

    public void reloadImage() {
        if (grounded) {
            if (imgState.equals("still left") || imgState.equals("walk left 1")) {
                image = loadImage("sprites/player/left_still.png");
            }
            if (imgState.equals("walk left 2")) {
                image = loadImage("sprites/player/left_walk.png");
            }
            if (imgState.equals("still right") || imgState.equals("walk right 1")) {
                image = loadImage("sprites/player/right_still.png");
            }
            if (imgState.equals("walk right 2")) {
                image = loadImage("sprites/player/right_walk.png");
            }
        }
        else {
            if (imgState.equals("still left") || imgState.equals("walk left 1") ||
                imgState.equals("walk left 2")) {
                image = loadImage("sprites/player/left_jump.png");
            }
            if (imgState.equals("still right") || imgState.equals("walk right 1") ||
                imgState.equals("walk right 2")) {
                image = loadImage("sprites/player/right_jump.png");
            }
        }
    }

    public void damage(int damage, boolean useArmor, Inventory inventory) {
        int fd = damage;
        if (useArmor) {
            fd -= sumArmor();
            for (i=0; i<4; i++) {
                TerrariaClone.armor.durs[i] -= 1;
                if (TerrariaClone.armor.durs[i] <= 0) {
                    inventory.removeLocationIC(TerrariaClone.armor, i, TerrariaClone.armor.nums[i]);
                }
            }
        }
        if (fd < 1) {
            fd = 1;
        }
        hp -= fd;
    }

    public int sumArmor() {
        return (TerrariaClone.getARMOR().get(TerrariaClone.armor.ids[0]) +
                TerrariaClone.getARMOR().get(TerrariaClone.armor.ids[1]) +
                TerrariaClone.getARMOR().get(TerrariaClone.armor.ids[2]) +
                TerrariaClone.getARMOR().get(TerrariaClone.armor.ids[3]));
    }

    private static BufferedImage loadImage(String path) {
        URL url = TerrariaClone.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            System.out.println("[ERROR] could not load image '" + path + "'.");
        }
        return image;
    }

    public static int mod(int a, int q) {
        return TerrariaClone.mod(a, q);
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
