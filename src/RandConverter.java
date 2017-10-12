import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class RandConverter {

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
        System.out.print("[D]uplicate, [R]andomize, or [O]utline? ");
       
        Scanner scanner = new Scanner(System.in); 
        char option = scanner.next().charAt(0);
        while (true) {
            System.out.print("Generate new textures for: ");
            String name = scanner.nextLine();
            if (name.equals("exit")) {
                break;
            }
            if (option == 'O') {
                for (int k=0; k<dirs.length; k++) {
                    for (int j=2; j<6; j++) {
                        BufferedImage texture = loadImage("outlines/" + name + "/" + dirs[k] + "1.png");
                        int i, x, y;
                        int[] xy;
                        int[][] coords = new int[IMAGESIZE*IMAGESIZE][2];
                        BufferedImage result;
                        for (i=0; i<7; i++) {
                            for (x=0; x<IMAGESIZE; x++) {
                                for (y=0; y<IMAGESIZE; y++) {
                                    coords[x*IMAGESIZE+y][0] = x;
                                    coords[x*IMAGESIZE+y][1] = y;
                                }
                            }
                            result = new BufferedImage(IMAGESIZE, IMAGESIZE, BufferedImage.TYPE_INT_ARGB);
                            for (x=0; x<IMAGESIZE; x++) {
                                for (y=0; y<IMAGESIZE; y++) {
                                    xy = coords[x*IMAGESIZE+y];
                                    result.setRGB(xy[0], xy[1], texture.getRGB(x, y));
                                }
                            }
                            try {
                                ImageIO.write(result, "png", new File("outlines/" + name + "/" + dirs[k] + j + ".png"));
                            }
                            catch (IOException e) {
                                System.out.println("Error in writing file.");
                            }
                        }
                    }
                }
            }
            else {
                BufferedImage texture = loadImage("blocks/" + name + "/texture1.png");
                int i, x, y;
                int[] xy;
                int[][] coords = new int[IMAGESIZE*IMAGESIZE][2];
                BufferedImage result;
                for (i=0; i<7; i++) {
                    for (x=0; x<IMAGESIZE; x++) {
                        for (y=0; y<IMAGESIZE; y++) {
                            coords[x*IMAGESIZE+y][0] = x;
                            coords[x*IMAGESIZE+y][1] = y;
                        }
                    }
                    if (option == 'R') {
                        Collections.shuffle(Arrays.asList(coords));
                    }
                    result = new BufferedImage(IMAGESIZE, IMAGESIZE, BufferedImage.TYPE_INT_ARGB);
                    for (x=0; x<IMAGESIZE; x++) {
                        for (y=0; y<IMAGESIZE; y++) {
                            xy = coords[x*IMAGESIZE+y];
                            result.setRGB(xy[0], xy[1], texture.getRGB(x, y));
                        }
                    }
                    try {
                        ImageIO.write(result, "png", new File("blocks/" + name + "/texture" + (i+2) + ".png"));
                    }
                    catch (IOException e) {
                        System.out.println("Error in writing file.");
                    }
                }
            }
        }

        scanner.close();
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
