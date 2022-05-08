import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class LightConverter {

    static int BLOCKSIZE = 16;
    static int IMAGESIZE = 8;

    static String[] dirs = {"center", "tdown_both", "tdown_cw", "tdown_ccw",
        "tdown", "tup_both", "tup_cw", "tup_ccw",
        "tup", "leftright", "tright_both", "tright_cw",
        "tright_ccw", "tright", "upleftdiag", "upleft",
        "downleftdiag", "downleft", "left", "tleft_both",
        "tleft_cw", "tleft_ccw", "tleft", "uprightdiag",
        "upright", "downrightdiag", "downright", "right",
        "updown", "up", "down", "single"};

    public static void main(String[] args) {
        for (int i=0; i<17; i++) {
            System.out.print("Generate new textures [" + i + "] for: ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            scanner.close();
            BufferedImage light = loadImage("light/" + i + ".png");
            for (int j=1; j<9; j++) {
                BufferedImage texture = loadImage("blocks/" + name + "/texture" + j + ".png");
                texture.createGraphics().drawImage(light,
                    0, 0, IMAGESIZE, IMAGESIZE,
                    0, 0, IMAGESIZE, IMAGESIZE,
                    null);
                try {
                    ImageIO.write(texture, "png", new File("blocks/" + name + "/texture" + j + ".png"));
                }
                catch (IOException e) {
                    System.out.println("Error in writing file.");
                }
            }
        }
    }

    private static BufferedImage loadImage(String path) {
        URL url = RandConverter.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            System.out.println("Error: could not load image '" + path + "'.");
        }
        return image;
    }
}
