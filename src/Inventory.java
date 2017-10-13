import java.awt.*;
import java.awt.image.*;
import java.io.Serializable;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;

public class Inventory implements Serializable {

    int i, j, k, x, y, n, px, py, selection, width, height;
    double fpx, fpy;
    short r;

    transient BufferedImage image, box, box_selected;
    Font font = new Font("Chalkboard", Font.PLAIN, 12);

    transient Graphics2D g2;

    short[] ids;
    short[] nums;
    short[] durs;
    Short[][] list_thing;
    short[][] r1;
    short[] r2;

    int trolx = 37;
    int troly = 17;

    int CX, CY;

    boolean valid = false;

    ItemCollection ic;

    Map<String,Short[][]> RECIPES;

    public Inventory() {
        ids = new short[40];
        nums = new short[40];
        durs = new short[40];
        for (i=0; i<40; i++) {
            ids[i] = 0;
            nums[i] = 0;
            durs[i] = 0;
        }
        selection = 0;
        image = new BufferedImage(466, 190, BufferedImage.TYPE_INT_ARGB);
        box = loadImage("interface/inventory.png");
        box_selected = loadImage("interface/inventory_selected.png");
        g2 = image.createGraphics();
        for (x=0; x<10; x++) {
            for (y=0; y<4; y++) {
                if (x == 0 && y == 0) {
                    g2.drawImage(box_selected,
                        x*46+6, y*46+6, x*46+46, y*46+46,
                        0, 0, 40, 40,
                        null);
                    if (y == 0) {
                        g2.setFont(font);
                        g2.setColor(Color.BLACK);
                        g2.drawString(f(x) + " ", x*46+trolx, y*46+troly);
                    }
                }
                else {
                    g2.drawImage(box,
                        x*46+6, y*46+6, x*46+46, y*46+46,
                        0, 0, 40, 40,
                        null);
                    if (y == 0) {
                        g2.setFont(font);
                        g2.setColor(Color.BLACK);
                        g2.drawString(f(x) + " ", x*46+trolx, y*46+troly);
                    }
                }
            }
        }

        RECIPES = new HashMap<String,Short[][]>();

        Short[][] list_thing1 = {
            {15, 15, 15,
                0, 15, 0,
            0, 15, 0, 154, 1}, // Wooden Pick
            {2, 2, 2,
                0, 15, 0,
            0, 15, 0, 157, 1}, // Stone Pick
            {29, 29, 29,
                0, 15, 0,
            0, 15, 0, 7, 1}, // Copper Pick
            {30, 30, 30,
                0, 15, 0,
            0, 15, 0, 8, 1}, // Iron Pick
            {31, 31, 31,
                0, 15, 0,
            0, 15, 0, 9, 1}, // Silver Pick
            {32, 32, 32,
                0, 15, 0,
            0, 15, 0, 10, 1}, // Gold Pick
            {60, 60, 60,
                0, 15, 0,
            0, 15, 0, 51, 1}, // Zinc Pick
            {61, 61, 61,
                0, 15, 0,
            0, 15, 0, 54, 1}, // Rhymestone Pick
            {62, 62, 62,
                0, 15, 0,
            0, 15, 0, 57, 1}, // Obdurite Pick
            {73, 73, 73,
                0, 15, 0,
            0, 15, 0, 169, 1}, // Magnetite Pick
            {69, 69, 69,
                0, 15, 0,
            0, 15, 0, 172, 1}, // Irradium Pick
            {15, 15, 0,
                15, 15, 0,
            0, 15, 0, 155, 1}, // Wooden Axe
            {0, 15, 15,
                0, 15, 15,
            0, 15, 0, 155, 1},
            {15, 15, 0,
                15, 15, 0,
            15, 0, 0, 155, 1},
            {0, 15, 15,
                0, 15, 15,
            0, 0, 15, 155, 1},
            {2, 2, 0,
                2, 15, 0,
            0, 15, 0, 158, 1}, // Stone Axe
            {0, 2, 2,
                0, 15, 2,
            0, 15, 0, 158, 1},
            {2, 2, 0,
                15, 2, 0,
            15, 0, 0, 158, 1},
            {0, 2, 2,
                0, 2, 15,
            0, 0, 15, 158, 1},
            {29, 29, 0,
                29, 15, 0,
            0, 15, 0, 11, 1}, // Copper Axe
            {0, 29, 29,
                0, 15, 29,
            0, 15, 0, 11, 1},
            {29, 29, 0,
                15, 29, 0,
            15, 0, 0, 11, 1},
            {0, 29, 29,
                0, 29, 15,
            0, 0, 15, 11, 1},
            {30, 30, 0,
                30, 15, 0,
            0, 15, 0, 11, 1}, // Iron Axe
            {0, 30, 30,
                0, 15, 30,
            0, 15, 0, 11, 1},
            {30, 30, 0,
                15, 30, 0,
            15, 0, 0, 11, 1},
            {0, 30, 30,
                0, 30, 15,
            0, 0, 15, 11, 1},
            {31, 31, 0,
                31, 15, 0,
            0, 15, 0, 11, 1}, // Silver Axe
            {0, 31, 31,
                0, 15, 31,
            0, 15, 0, 11, 1},
            {31, 31, 0,
                15, 31, 0,
            15, 0, 0, 11, 1},
            {0, 31, 31,
                0, 31, 15,
            0, 0, 15, 11, 1},
            {32, 32, 0,
                32, 15, 0,
            0, 15, 0, 11, 1}, // Gold Axe
            {0, 32, 32,
                0, 15, 32,
            0, 15, 0, 11, 1},
            {32, 32, 0,
                15, 32, 0,
            15, 0, 0, 11, 1},
            {0, 32, 32,
                0, 32, 15,
            0, 0, 15, 11, 1},
            {60, 60, 0,
                60, 15, 0,
            0, 15, 0, 52, 1}, // Zinc Axe
            {0, 60, 60,
                0, 15, 60,
            0, 15, 0, 52, 1},
            {60, 60, 0,
                15, 60, 0,
            15, 0, 0, 52, 1},
            {0, 60, 60,
                0, 60, 15,
            0, 0, 15, 52, 1},
            {61, 61, 0,
                61, 15, 0,
            0, 15, 0, 55, 1}, // Rhymestone Axe
            {0, 61, 61,
                0, 15, 61,
            0, 15, 0, 55, 1},
            {61, 61, 0,
                15, 61, 0,
            15, 0, 0, 55, 1},
            {0, 61, 61,
                0, 61, 15,
            0, 0, 15, 55, 1},
            {62, 62, 0,
                62, 15, 0,
            0, 15, 0, 58, 1}, // Obdurite Axe
            {0, 62, 62,
                0, 15, 62,
            0, 15, 0, 58, 1},
            {62, 62, 0,
                15, 62, 0,
            15, 0, 0, 58, 1},
            {0, 62, 62,
                0, 62, 15,
            0, 0, 15, 58, 1},
            {73, 73, 0,
                73, 15, 0,
            0, 15, 0, 170, 1}, // Magnetite Axe
            {0, 73, 73,
                0, 15, 73,
            0, 15, 0, 170, 1},
            {73, 73, 0,
                15, 73, 0,
            15, 0, 0, 170, 1},
            {0, 73, 73,
                0, 73, 15,
            0, 0, 15, 170, 1},
            {69, 69, 0,
                69, 15, 0,
            0, 15, 0, 169, 1}, // Irradium Axe
            {0, 69, 69,
                0, 15, 69,
            0, 15, 0, 169, 1},
            {69, 69, 0,
                15, 69, 0,
            15, 0, 0, 169, 1},
            {0, 69, 69,
                0, 69, 15,
            0, 0, 15, 169, 1},
            {15, 0, 0,
                15, 0, 0,
            15, 0, 0, 156, 1}, // Wooden Sword
            {0, 15, 0,
                0, 15, 0,
            0, 15, 0, 156, 1},
            {0, 0, 15,
                0, 0, 15,
            0, 0, 15, 156, 1},
            {2, 0, 0,
                2, 0, 0,
            15, 0, 0, 159, 1}, // Stone Sword
            {0, 2, 0,
                0, 2, 0,
            0, 15, 0, 159, 1},
            {0, 0, 2,
                0, 0, 2,
            0, 0, 15, 159, 1},
            {29, 0, 0,
                29, 0, 0,
            15, 0, 0, 16, 1}, // Copper Sword
            {0, 29, 0,
                0, 29, 0,
            0, 15, 0, 16, 1},
            {0, 0, 29,
                0, 0, 29,
            0, 0, 15, 16, 1},
            {30, 0, 0,
                30, 0, 0,
            15, 0, 0, 17, 1}, // Iron Sword
            {0, 30, 0,
                0, 30, 0,
            0, 15, 0, 17, 1},
            {0, 0, 30,
                0, 0, 30,
            0, 0, 15, 17, 1},
            {31, 0, 0,
                31, 0, 0,
            15, 0, 0, 18, 1}, // Silver Sword
            {0, 31, 0,
                0, 31, 0,
            0, 15, 0, 18, 1},
            {0, 0, 31,
                0, 0, 31,
            0, 0, 15, 18, 1},
            {32, 0, 0,
                32, 0, 0,
            15, 0, 0, 19, 1}, // Gold Sword
            {0, 32, 0,
                0, 32, 0,
            0, 15, 0, 19, 1},
            {0, 0, 32,
                0, 0, 32,
            0, 0, 15, 19, 1},
            {38, 0, 0,
                38, 0, 0,
            15, 0, 0, 19, 1}, // Zinc Sword
            {0, 38, 0,
                0, 38, 0,
            0, 15, 0, 19, 1},
            {0, 0, 38,
                0, 0, 38,
            0, 0, 15, 19, 1},
            {39, 0, 0,
                39, 0, 0,
            15, 0, 0, 19, 1}, // Rhymestone Sword
            {0, 39, 0,
                0, 39, 0,
            0, 15, 0, 19, 1},
            {0, 0, 39,
                0, 0, 39,
            0, 0, 15, 19, 1},
            {40, 0, 0,
                40, 0, 0,
            15, 0, 0, 19, 1}, // Obdurite Sword
            {0, 40, 0,
                0, 40, 0,
            0, 15, 0, 19, 1},
            {0, 0, 40,
                0, 0, 40,
            0, 0, 15, 19, 1},
            {73, 0, 0,
                73, 0, 0,
            15, 0, 0, 171, 1}, // Magnetite Sword
            {0, 73, 0,
                0, 73, 0,
            0, 15, 0, 171, 1},
            {0, 0, 73,
                0, 0, 73,
            0, 0, 15, 171, 1},
            {69, 0, 0,
                69, 0, 0,
            15, 0, 0, 174, 1}, // Irradium Sword
            {0, 69, 0,
                0, 69, 0,
            0, 15, 0, 174, 1},
            {0, 0, 69,
                0, 0, 69,
            0, 0, 15, 174, 1},
            {63, 0, 63,
                0, 63, 0,
            0, 63, 0, 190, 1}, // Wrench
            {15, 0, 0,
                2, 0, 0,
            175, 0, 0, 178, 1}, // Lever
            {0, 15, 0,
                0, 2, 0,
            0, 175, 0, 178, 1},
            {0, 0, 15,
                0, 0, 2,
            0, 0, 175, 178, 1},
            {29, 29, 29,
                29, 0, 29,
            0, 0, 0, 105, 1}, // Copper Helmet
            {0, 0, 0,
                29, 29, 29,
            29, 0, 29, 105, 1},
            {29, 0, 29,
                29, 29, 29,
            29, 29, 29, 106, 1}, // Copper Chestplate
            {29, 29, 29,
                29, 0, 29,
            29, 0, 29, 107, 1}, // Copper Leggings
            {29, 0, 29,
                29, 0, 29,
            0, 0, 0, 108, 1}, // Copper Greaves
            {0, 0, 0,
                29, 0, 29,
            29, 0, 29, 108, 1},
            {30, 30, 30,
                30, 0, 30,
            0, 0, 0, 109, 1}, // Iron Helmet
            {0, 0, 0,
                30, 30, 30,
            30, 0, 30, 109, 1},
            {30, 0, 30,
                30, 30, 30,
            30, 30, 30, 110, 1}, // Iron Chestplate
            {30, 30, 30,
                30, 0, 30,
            30, 0, 30, 111, 1}, // Iron Leggings
            {30, 0, 30,
                30, 0, 30,
            0, 0, 0, 112, 1}, // Iron Greaves
            {0, 0, 0,
                30, 0, 30,
            30, 0, 30, 112, 1},
            {31, 31, 31,
                31, 0, 31,
            0, 0, 0, 113, 1}, // Silver Helmet
            {0, 0, 0,
                31, 31, 31,
            31, 0, 31, 113, 1},
            {31, 0, 31,
                31, 31, 31,
            31, 31, 31, 114, 1}, // Silver Chestplate
            {31, 31, 31,
                31, 0, 31,
            31, 0, 31, 115, 1}, // Silver Leggings
            {31, 0, 31,
                31, 0, 31,
            0, 0, 0, 116, 1}, // Silver Greaves
            {0, 0, 0,
                31, 0, 31,
            31, 0, 31, 116, 1},
            {32, 32, 32,
                32, 0, 32,
            0, 0, 0, 117, 1}, // Gold Helmet
            {0, 0, 0,
                32, 32, 32,
            32, 0, 32, 117, 1},
            {32, 0, 32,
                32, 32, 32,
            32, 32, 32, 118, 1}, // Gold Chestplate
            {32, 32, 32,
                32, 0, 32,
            32, 0, 32, 119, 1}, // Gold Leggings
            {32, 0, 32,
                32, 0, 32,
            0, 0, 0, 120, 1}, // Gold Greaves
            {0, 0, 0,
                32, 0, 32,
            32, 0, 32, 120, 1},
            {60, 60, 60,
                60, 0, 60,
            0, 0, 0, 121, 1}, // Zinc Helmet
            {0, 0, 0,
                60, 60, 60,
            60, 0, 60, 121, 1},
            {60, 0, 60,
                60, 60, 60,
            60, 60, 60, 122, 1}, // Zinc Chestplate
            {60, 60, 60,
                60, 0, 60,
            60, 0, 60, 123, 1}, // Zinc Leggings
            {60, 0, 60,
                60, 0, 60,
            0, 0, 0, 124, 1}, // Zinc Greaves
            {0, 0, 0,
                60, 0, 60,
            60, 0, 60, 124, 1},
            {61, 61, 61,
                61, 0, 61,
            0, 0, 0, 125, 1}, // Rhymestone Helmet
            {0, 0, 0,
                61, 61, 61,
            61, 0, 61, 125, 1},
            {61, 0, 61,
                61, 61, 61,
            61, 61, 61, 126, 1}, // Rhymestone Chestplate
            {61, 61, 61,
                61, 0, 61,
            61, 0, 61, 127, 1}, // Rhymestone Leggings
            {61, 0, 61,
                61, 0, 61,
            0, 0, 0, 128, 1}, // Rhymestone Greaves
            {0, 0, 0,
                61, 0, 61,
            61, 0, 61, 128, 1},
            {62, 62, 62,
                62, 0, 62,
            0, 0, 0, 129, 1}, // Obdurite Helmet
            {0, 0, 0,
                62, 62, 62,
            62, 0, 62, 129, 1},
            {62, 0, 62,
                62, 62, 62,
            62, 62, 62, 130, 1}, // Obdurite Chestplate
            {62, 62, 62,
                62, 0, 62,
            62, 0, 62, 131, 1}, // Obdurite Leggings
            {62, 0, 62,
                62, 0, 62,
            0, 0, 0, 132, 1}, // Obdurite Greaves
            {0, 0, 0,
                62, 0, 62,
            62, 0, 62, 132, 1},
            {63, 63, 63,
                63, 0, 63,
            0, 0, 0, 133, 1}, // Aluminum Helmet
            {0, 0, 0,
                63, 63, 63,
            63, 0, 63, 133, 1},
            {63, 0, 63,
                63, 63, 63,
            63, 63, 63, 134, 1}, // Aluminum Chestplate
            {63, 63, 63,
                63, 0, 63,
            63, 0, 63, 135, 1}, // Aluminum Leggings
            {63, 0, 63,
                63, 0, 63,
            0, 0, 0, 136, 1}, // Aluminum Greaves
            {0, 0, 0,
                63, 0, 63,
            63, 0, 63, 136, 1},
            {64, 64, 64,
                64, 0, 64,
            0, 0, 0, 137, 1}, // Lead Helmet
            {0, 0, 0,
                64, 64, 64,
            64, 0, 64, 137, 1},
            {64, 0, 64,
                64, 64, 64,
            64, 64, 64, 138, 1}, // Lead Chestplate
            {64, 64, 64,
                64, 0, 64,
            64, 0, 64, 139, 1}, // Lead Leggings
            {64, 0, 64,
                64, 0, 64,
            0, 0, 0, 140, 1}, // Lead Greaves
            {0, 0, 0,
                64, 0, 64,
            64, 0, 64, 140, 1},
            {15, 15, 15,
                15, 0, 15,
            15, 15, 15, 21, 1}, // Wooden Chest
            {2, 2, 2,
                2, 21, 2,
            2, 2, 2, 22, 1}, // Stone Chest
            {29, 29, 29,
                29, 22, 29,
            29, 29, 29, 23, 1}, // Copper Chest
            {30, 30, 30,
                30, 22, 30,
            30, 30, 30, 24, 1}, // Iron Chest
            {31, 31, 31,
                31, 22, 31,
            31, 31, 31, 25, 1}, // Silver Chest
            {32, 32, 32,
                32, 22, 32,
            32, 32, 32, 26, 1}, // Gold Chest
            {60, 60, 60,
                60, 22, 60,
            60, 60, 60, 151, 1}, // Zinc Chest
            {61, 61, 61,
                61, 22, 61,
            61, 61, 61, 152, 1}, // Rhymestone Chest
            {62, 62, 62,
                62, 22, 62,
            62, 62, 62, 153, 1}, // Obdurite Chest
            {76, 76, 76,
                76, 34, 76,
            76, 175, 76, 177, 1}, // Zythium Lamp
            {76, 76, 76,
                175, 44, 175,
            76, 76, 76, 180, 1}, // Zythium Amplifier
            {76, 76, 76,
                44, 175, 44,
            76, 76, 76, 181, 1}, // Zythium Inverter
            {76, 175, 76,
                175, 175, 175,
            76, 175, 76, 186, 1}, // Zythium Delayer
            {15, 15, 0,
                15, 15, 0,
            0, 0, 0, 20, 1}, // Workbench
            {0, 15, 15,
                0, 15, 15,
            0, 0, 0, 20, 1},
            {0, 0, 0,
                15, 15, 0,
            15, 15, 0, 20, 1},
            {0, 0, 0,
                0, 15, 15,
            0, 15, 15, 20, 1},
            {160, 160, 0,
                160, 160, 0,
            0, 0, 0, 15, 1}, // Bark -> Wood
            {0, 160, 160,
                0, 160, 160,
            0, 0, 0, 15, 1},
            {0, 0, 0,
                160, 160, 0,
            160, 160, 0, 15, 1},
            {0, 0, 0,
                0, 160, 160,
            0, 160, 160, 15, 1},
            {2, 2, 0,
                2, 2, 0,
            0, 0, 0, 161, 4}, // Cobblestone
            {0, 2, 2,
                0, 2, 2,
            0, 0, 0, 161, 4},
            {0, 0, 0,
                2, 2, 0,
            2, 2, 0, 161, 4},
            {0, 0, 0,
                0, 2, 2,
            0, 2, 2, 161, 4},
            {162, 162, 0,
                162, 162, 0,
            0, 0, 0, 163, 4}, // Chiseled Cobblestone
            {0, 162, 162,
                0, 162, 162,
            0, 0, 0, 163, 4},
            {0, 0, 0,
                162, 162, 0,
            162, 162, 0, 163, 4},
            {0, 0, 0,
                0, 162, 162,
            0, 162, 162, 163, 4},
            {163, 163, 0,
                163, 163, 0,
            0, 0, 0, 164, 4}, // Stone Bricks
            {0, 163, 163,
                0, 163, 163,
            0, 0, 0, 164, 4},
            {0, 0, 0,
                163, 163, 0,
            163, 163, 0, 164, 4},
            {0, 0, 0,
                0, 163, 163,
            0, 163, 163, 164, 4},
            {2, 2, 2,
                2, 0, 2,
            2, 2, 2, 27, 1}, // Furnace
            {67, 67, 67,
                0, 0, 0,
            0, 0, 0, 175, 10}, // Zythium Wire
            {0, 0, 0,
                67, 67, 67,
            0, 0, 0, 175, 20},
            {0, 0, 0,
                0, 0, 0,
            67, 67, 67, 175, 20},
            {2, 0, 0,
                0, 2, 0,
            0, 0, 0, 33, 1}, // Stone Lighter
            {0, 2, 0,
                0, 0, 2,
            0, 0, 0, 33, 1},
            {0, 0, 0,
                2, 0, 0,
            0, 2, 0, 33, 1},
            {0, 0, 0,
                0, 2, 0,
            0, 0, 2, 33, 1},
            {0, 2, 0,
                2, 0, 0,
            0, 0, 0, 33, 1},
            {0, 0, 2,
                0, 2, 0,
            0, 0, 0, 33, 1},
            {0, 0, 0,
                0, 2, 0,
            2, 0, 0, 33, 1},
            {0, 0, 0,
                0, 0, 2,
            0, 2, 0, 33, 1},
            {15, 0, 0,
                15, 0, 0,
            0, 0, 0, 35, 4},  // Wooden Torch
            {0, 15, 0,
                0, 15, 0,
            0, 0, 0, 35, 4},
            {0, 0, 15,
                0, 0, 15,
            0, 0, 0, 35, 4},
            {0, 0, 0,
                15, 0, 0,
            15, 0, 0, 35, 4},
            {0, 0, 0,
                0, 15, 0,
            0, 15, 0, 35, 4},
            {0, 0, 0,
                0, 0, 15,
            0, 0, 15, 35, 4},
            {28, 0, 0,
                15, 0, 0,
            0, 0, 0, 36, 4}, // Coal Torch
            {0, 28, 0,
                0, 15, 0,
            0, 0, 0, 36, 4},
            {0, 0, 28,
                0, 0, 15,
            0, 0, 0, 36, 4},
            {0, 0, 0,
                28, 0, 0,
            15, 0, 0, 36, 4},
            {0, 0, 0,
                0, 28, 0,
            0, 15, 0, 36, 4},
            {0, 0, 0,
                0, 0, 28,
            0, 0, 15, 36, 4},
            {34, 0, 0,
                15, 0, 0,
            0, 0, 0, 37, 4}, // Lumenstone Torch
            {0, 34, 0,
                0, 15, 0,
            0, 0, 0, 37, 4},
            {0, 0, 34,
                0, 0, 15,
            0, 0, 0, 37, 4},
            {0, 0, 0,
                34, 0, 0,
            15, 0, 0, 37, 4},
            {0, 0, 0,
                0, 34, 0,
            0, 15, 0, 37, 4},
            {0, 0, 0,
                0, 0, 34,
            0, 0, 15, 37, 4},
            {44, 0, 0,
                15, 0, 0,
            0, 0, 0, 176, 4}, // Zythium Torch
            {0, 44, 0,
                0, 15, 0,
            0, 0, 0, 176, 4},
            {0, 0, 44,
                0, 0, 15,
            0, 0, 0, 176, 4},
            {0, 0, 0,
                44, 0, 0,
            15, 0, 0, 176, 4},
            {0, 0, 0,
                0, 44, 0,
            0, 15, 0, 176, 4},
            {0, 0, 0,
                0, 0, 44,
            0, 0, 15, 176, 4},
            {15, 15, 0,
                0, 0, 0,
            0, 0, 0, 183, 1}, // Wooden Pressure Plate
            {0, 15, 15,
                0, 0, 0,
            0, 0, 0, 183, 1},
            {0, 0, 0,
                15, 15, 0,
            0, 0, 0, 183, 1},
            {0, 0, 0,
                0, 15, 15,
            0, 0, 0, 183, 1},
            {0, 0, 0,
                0, 0, 0,
            15, 15, 0, 183, 1},
            {0, 0, 0,
                0, 0, 0,
            0, 15, 15, 183, 1},
            {2, 2, 0,
                0, 0, 0,
            0, 0, 0, 184, 1}, // Stone Pressure Plate
            {0, 2, 2,
                0, 0, 0,
            0, 0, 0, 184, 1},
            {0, 0, 0,
                2, 2, 0,
            0, 0, 0, 184, 1},
            {0, 0, 0,
                0, 2, 2,
            0, 0, 0, 184, 1},
            {0, 0, 0,
                0, 0, 0,
            2, 2, 0, 184, 1},
            {0, 0, 0,
                0, 0, 0,
            0, 2, 2, 184, 1},
            {162, 44, 162,
                0, 175, 0,
            0, 0, 0, 185, 1}, // Zythium Pressure Plate
            {0, 0, 0,
                162, 44, 162,
            0, 175, 0, 185, 1}
        };

        RECIPES.put("workbench", list_thing1);

        Short[][] list_thing2 = {
            {15, 15,
                15, 15, 20, 1}, // Workbench
            {160, 160,
                160, 160, 15, 1}, // Bark -> Wood
            {2, 2,
                2, 2, 161, 4}, // Cobblestone
            {162, 162,
                162, 162, 163, 4}, // Chiseled Cobblestone
            {163, 163,
                163, 163, 164, 4}, // Stone Bricks
            {2, 0,
                0, 2, 33, 1}, // Stone Lighter
            {0, 2,
                2, 0, 33, 1},
            {15, 0,
                15, 0, 35, 4},  // Wooden Torch
            {0, 15,
                0, 15, 35, 4},
            {28, 0,
                15, 0, 36, 4}, // Coal Torch
            {0, 28,
                0, 15, 36, 4},
            {34, 0,
                15, 0, 37, 4}, // Lumenstone Torch
            {0, 34,
                0, 15, 37, 4},
            {44, 0,
                15, 0, 176, 4}, // Zythium Torch
            {0, 44,
                0, 15, 176, 4},
            {15, 15,
                0, 0, 183, 1}, // Wooden Pressure Plate
            {0, 0,
                15, 15, 183, 1},
            {2, 2,
                0, 0, 184, 1}, // Stone Pressure Plate
            {0, 0,
                2, 2, 184, 1}
        };

        RECIPES.put("cic", list_thing2);

        Short[][] list_thing3 = {
            {15, 167, 0, 0, 0, 0, 0, 0, 0,
        168, 1},
            {162, 0, 0, 0, 0, 0, 0, 0, 0,
        182, 1}
        };

        RECIPES.put("shapeless", list_thing3);

        Short[][] list_thing4 = {
            {15, 167, 0, 0,
        168, 1},
            {162, 0, 0, 0,
        182, 1}
        };

        RECIPES.put("shapeless_cic", list_thing4);
    }

    public int addItem(short item, short quantity) {
        if (TerrariaClone.getTOOLDURS().get(item) != null) {
            return addItem(item, quantity, TerrariaClone.getTOOLDURS().get(item));
        }
        else {
            return addItem(item, quantity, (short)0);
        }
    }

    public int addItem(short item, short quantity, short durability) {
        for (i=0; i<40; i++) {
            if (ids[i] == item && nums[i] < TerrariaClone.getMAXSTACKS().get(ids[i])) {
                if (TerrariaClone.getMAXSTACKS().get(ids[i]) - nums[i] >= quantity) {
                    nums[i] += quantity;
                    update(i);
                    return 0;
                }
                else {
                    quantity -= TerrariaClone.getMAXSTACKS().get(ids[i]) - nums[i];
                    nums[i] = TerrariaClone.getMAXSTACKS().get(ids[i]);
                    update(i);
                }
            }
        }
        for (i=0; i<40; i++) {
            if (ids[i] == 0) {
                ids[i] = item;
                if (quantity <= TerrariaClone.getMAXSTACKS().get(ids[i])) {
                    nums[i] = quantity;
                    durs[i] = durability;
                    update(i);
                    return 0;
                }
                else {
                    nums[i] = TerrariaClone.getMAXSTACKS().get(ids[i]);
                    durs[i] = durability;
                    quantity -= TerrariaClone.getMAXSTACKS().get(ids[i]);
                }
            }
        }
        return quantity;
    }

    public int removeItem(short item, short quantity) {
        for (i=0; i<40; i++) {
            if (ids[i] == item) {
                if (nums[i] <= quantity) {
                    nums[i] -= quantity;
                    if (nums[i] == 0) {
                        ids[i] = 0;
                    }
                    update(i);
                    return 0;
                }
                else {
                    quantity -= nums[i];
                    nums[i] = 0;
                    ids[i] = 0;
                    update(i);
                }
            }
        }
        return quantity;
    }

    public int addLocation(int i, short item, short quantity, short durability) {
        if (ids[i] == item) {
            if (TerrariaClone.getMAXSTACKS().get(ids[i]) - nums[i] >= quantity) {
                nums[i] += quantity;
                update(i);
                return 0;
            }
            else {
                quantity -= TerrariaClone.getMAXSTACKS().get(ids[i]) - nums[i];
                nums[i] = TerrariaClone.getMAXSTACKS().get(ids[i]);
                update(i);
            }
        }
        else {
            if (quantity <= TerrariaClone.getMAXSTACKS().get(ids[i])) {
                ids[i] = item;
                nums[i] = quantity;
                durs[i] = durability;
                update(i);
                return 0;
            }
            else {
                quantity -= TerrariaClone.getMAXSTACKS().get(ids[i]);
                return quantity;
            }
        }
        return quantity;
    }

    public int removeLocation(int i, short quantity) {
        if (nums[i] >= quantity) {
            nums[i] -= quantity;
            if (nums[i] == 0) {
                ids[i] = 0;
            }
            update(i);
            return 0;
        }
        else {
            quantity -= nums[i];
            nums[i] = 0;
            ids[i] = 0;
            update(i);
        }
        return quantity;
    }

    public void reloadImage() {
        image = new BufferedImage(466, 190, BufferedImage.TYPE_INT_ARGB);
        box = loadImage("interface/inventory.png");
        box_selected = loadImage("interface/inventory_selected.png");
        g2 = image.createGraphics();
        for (x=0; x<10; x++) {
            for (y=0; y<4; y++) {
                if (x == 0 && y == 0) {
                    g2.drawImage(box_selected,
                        x*46+6, y*46+6, x*46+46, y*46+46,
                        0, 0, 40, 40,
                        null);
                    if (y == 0) {
                        g2.setFont(font);
                        g2.setColor(Color.BLACK);
                        g2.drawString(f(x) + " ", x*46+trolx, y*46+troly);
                    }
                }
                else {
                    g2.drawImage(box,
                        x*46+6, y*46+6, x*46+46, y*46+46,
                        0, 0, 40, 40,
                        null);
                    if (y == 0) {
                        g2.setFont(font);
                        g2.setColor(Color.BLACK);
                        g2.drawString(f(x) + " ", x*46+trolx, y*46+troly);
                    }
                }
            }
        }
        for (i=0; i<40; i++) {
            update(i);
        }
    }

    public void update(int i) {
        py = (int)(i/10);
        px = i-(py*10);
        for (x=px*46+6; x<px*46+46; x++) {
            for (y=py*46+6; y<py*46+46; y++) {
                image.setRGB(x, y, 9539985);
            }
        }
        g2 = image.createGraphics();
        if (i == selection) {
            g2.drawImage(box_selected,
                px*46+6, py*46+6, px*46+46, py*46+46,
                0, 0, 40, 40,
                null);
            if (py == 0) {
                g2.setFont(font);
                g2.setColor(Color.BLACK);
                g2.drawString(f(px) + " ", px*46+trolx, py*46+troly);
            }
        }
        else {
            g2.drawImage(box,
                px*46+6, py*46+6, px*46+46, py*46+46,
                0, 0, 40, 40,
                null);
            if (py == 0) {
                g2.setFont(font);
                g2.setColor(Color.BLACK);
                g2.drawString(f(px) + " ", px*46+trolx, py*46+troly);
            }
        }
        if (ids[i] != 0) {
            width = TerrariaClone.getItemImgs().get(ids[i]).getWidth();
            height = TerrariaClone.getItemImgs().get(ids[i]).getHeight();
            g2.drawImage(TerrariaClone.getItemImgs().get(ids[i]),
                px*46+14+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+14+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*46+38-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+38-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                0, 0, width, height,
                null);

            if (nums[i] > 1) {
                g2.setFont(font);
                g2.setColor(Color.WHITE);
                g2.drawString(nums[i] + " ", px*46+15, py*46+40);
            }
        }
    }

    public void select(int i) {
        if (i == 0) {
            n = selection;
            selection = 9;
            update(n);
            update(9);
        }
        else {
            n = selection;
            selection = i - 1;
            update(n);
            update(i - 1);
        }
    }

    public void select2(int i) {
        n = selection;
        selection = i;
        update(n);
        update(i);
    }

    public short tool() {
        return ids[selection];
    }

    public void renderCollection(ItemCollection ic) {
        if (ic.type.equals("cic")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/cic.png");
                for (i=0; i<4; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("armor")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/armor.png");
                CX = 1;
                CY = 4;
                for (i=0; i<4; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("workbench")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/workbench.png");
                for (i=0; i<9; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("wooden_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/wooden_chest.png");
                CX = 3;
                CY = 3;
                for (i=0; i<9; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("stone_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/stone_chest.png");
                CX = 5;
                CY = 3;
                for (i=0; i<15; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("copper_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/copper_chest.png");
                CX = 5;
                CY = 4;
                for (i=0; i<20; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("iron_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/iron_chest.png");
                CX = 7;
                CY = 4;
                for (i=0; i<28; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("silver_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/silver_chest.png");
                CX = 7;
                CY = 5;
                for (i=0; i<35; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("gold_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/gold_chest.png");
                CX = 7;
                CY = 6;
                for (i=0; i<42; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("zinc_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/zinc_chest.png");
                CX = 7;
                CY = 8;
                for (i=0; i<56; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("rhymestone_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/rhymestone_chest.png");
                CX = 8;
                CY = 9;
                for (i=0; i<72; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("obdurite_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/obdurite_chest.png");
                CX = 10;
                CY = 10;
                for (i=0; i<100; i++) {
                    updateIC(ic, i);
                }
            }
        }
        if (ic.type.equals("furnace")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/furnace.png");
                for (i=-1; i<4; i++) {
                    updateIC(ic, i);
                }
            }
        }
    }

    public int addLocationIC(ItemCollection ic, int i, short item, short quantity) {
        return addLocationIC(ic, i, item, quantity, (short)0);
    }

    public int addLocationIC(ItemCollection ic, int i, short item, short quantity, short durability) {
        if (ic.ids[i] == item) {
            if (TerrariaClone.getMAXSTACKS().get(ic.ids[i]) - ic.nums[i] >= quantity) {
                ic.nums[i] += quantity;
                if (ic.image != null) {
                    updateIC(ic, i);
                }
                return 0;
            }
            else {
                quantity -= TerrariaClone.getMAXSTACKS().get(ic.ids[i]) - ic.nums[i];
                ic.nums[i] = TerrariaClone.getMAXSTACKS().get(ic.ids[i]);
                if (ic.image != null) {
                    updateIC(ic, i);
                }
            }
        }
        else {
            if (quantity <= TerrariaClone.getMAXSTACKS().get(ic.ids[i])) {
                ic.ids[i] = item;
                ic.nums[i] = quantity;
                ic.durs[i] = durability;
                if (ic.image != null) {
                    updateIC(ic, i);
                }
                return 0;
            }
            else {
                quantity -= TerrariaClone.getMAXSTACKS().get(ic.ids[i]);
                return quantity;
            }
        }
        return quantity;
    }

    public int removeLocationIC(ItemCollection ic, int i, short quantity) {
        if (ic.nums[i] >= quantity) {
            ic.nums[i] -= quantity;
            if (ic.nums[i] == 0) {
                ic.ids[i] = 0;
            }
            if (ic.image != null) {
                updateIC(ic, i);
            }
            return 0;
        }
        else {
            quantity -= ic.nums[i];
            ic.nums[i] = 0;
            ic.ids[i] = 0;
            if (ic.image != null) {
                updateIC(ic, i);
            }
        }
        return quantity;
    }

    public void updateIC(ItemCollection ic, int i) {
        if (ic.type.equals("cic")) {
            py = (int)(i/2);
            px = i-(py*2);
            for (x=px*40; x<px*40+40; x++) {
                for (y=py*40; y<py*40+40; y++) {
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                px*40, py*40, px*40+40, py*40+40,
                0, 0, 40, 40,
                null);
            if (ic.ids[i] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.ids[i]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.ids[i]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.ids[i]),
                    px*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*40+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*40+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);
                if (ic.nums[i] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums[i] + " ", px*40+9, py*40+34);
                }
            }
            ic.ids[4] = 0;
            ic.ids[4] = 0;
            for (Short[] r2 : RECIPES.get("cic")) {
                valid = true;
                for (i=0; i<4; i++) {
                    if (ic.ids[i] != r2[i]) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    ic.ids[4] = r2[4];
                    ic.nums[4] = r2[5];
                    if (TerrariaClone.getTOOLDURS().get(r2[4]) != null)
                        ic.durs[4] = TerrariaClone.getTOOLDURS().get(r2[4]);
                    break;
                }
            }
            ArrayList<Short> r3 = new ArrayList<Short>(6);
            for (Short[] r2 : RECIPES.get("shapeless_cic")) {
                valid = true;
                r3.clear();
                for (j=0; j<r2.length-2; j++) {
                    r3.add(r2[j]);
                }
                for (j=0; j<4; j++) {
                    n = r3.indexOf(ic.ids[j]);
                    if (n == -1) {
                        valid = false;
                        break;
                    }
                    else {
                        r3.remove(n);
                    }
                }
                if (valid) {
                    ic.ids[4] = r2[r2.length-2];
                    ic.nums[4] = r2[r2.length-1];
                    if (TerrariaClone.getTOOLDURS().get(r2[r2.length-2]) != null)
                        ic.durs[4] = TerrariaClone.getTOOLDURS().get(r2[r2.length-2]);
                    break;
                }
            }
            for (x=3*40; x<3*40+40; x++) {
                for (y=20; y<20+40; y++) {
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                3*40, 20, 3*40+40, 20+40,
                0, 0, 40, 40,
                null);
            if (ic.ids[4] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.ids[4]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.ids[4]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.ids[4]),
                    3*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), 20+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), 3*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), 20+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);

                if (ic.nums[4] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums[4] + " ", 3*40+9, 20+34);
                }
            }
        }
        if (ic.type.equals("armor")) {
            py = (int)(i/CX);
            px = i-(py*CX);
            for (x=px*46; x<px*46+40; x++) {
                for (y=py*46; y<py*46+40; y++) {
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                px*46, py*46, px*46+40, py*46+40,
                0, 0, 40, 40,
                null);
            if (ic.ids[i] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.ids[i]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.ids[i]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.ids[i]),
                    px*46+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*46+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);

                if (ic.nums[i] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums[i] + " ", px*46+9, py*46+34);
                }
            }
        }
        if (ic.type.equals("workbench")) {
            py = (int)(i/3);
            px = i-(py*3);
            for (x=px*40; x<px*40+40; x++) {
                for (y=py*40; y<py*40+40; y++) {
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                px*40, py*40, px*40+40, py*40+40,
                0, 0, 40, 40,
                null);
            if (ic.ids[i] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.ids[i]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.ids[i]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.ids[i]),
                    px*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*40+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*40+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);
                if (ic.nums[i] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums[i] + " ", px*40+9, py*40+34);
                }
            }
            ic.ids[9] = 0;
            ic.ids[9] = 0;
            for (Short[] r2 : RECIPES.get("workbench")) {
                valid = true;
                for (i=0; i<9; i++) {
                    if (ic.ids[i] != r2[i]) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    ic.ids[9] = r2[9];
                    ic.nums[9] = r2[10];
                    if (TerrariaClone.getTOOLDURS().get(r2[9]) != null)
                        ic.durs[9] = TerrariaClone.getTOOLDURS().get(r2[9]);
                    break;
                }
            }
            ArrayList<Short> r3 = new ArrayList<Short>(11);
            for (Short[] r2 : RECIPES.get("shapeless")) {
                valid = true;
                r3.clear();
                for (j=0; j<r2.length-2; j++) {
                    r3.add(r2[j]);
                }
                for (j=0; j<9; j++) {
                    n = r3.indexOf(ic.ids[j]);
                    if (n == -1) {
                        valid = false;
                        break;
                    }
                    else {
                        r3.remove(n);
                    }
                }
                if (valid) {
                    ic.ids[9] = r2[r2.length-2];
                    ic.nums[9] = r2[r2.length-1];
                    if (TerrariaClone.getTOOLDURS().get(r2[r2.length-2]) != null)
                        ic.durs[9] = TerrariaClone.getTOOLDURS().get(r2.length-2);
                    break;
                }
            }
            for (x=4*40; x<4*40+40; x++) {
                for (y=1*40; y<1*40+40; y++) {
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                4*40, 1*40, 4*40+40, 1*40+40,
                0, 0, 40, 40,
                null);
            if (ic.ids[9] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.ids[9]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.ids[9]).getHeight();
                    g2.drawImage(TerrariaClone.getItemImgs().get(ic.ids[9]),
                        4*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), 1*40+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), 4*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), 1*40+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                        0, 0, width, height,
                        null);

                if (ic.nums[9] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums[9] + " ", 4*40+9, 1*40+34);
                }
            }
        }
        if (ic.type.equals("wooden_chest") || ic.type.equals("stone_chest") ||
            ic.type.equals("copper_chest") || ic.type.equals("iron_chest") ||
            ic.type.equals("silver_chest") || ic.type.equals("gold_chest") ||
            ic.type.equals("zinc_chest") || ic.type.equals("rhymestone_chest") ||
            ic.type.equals("obdurite_chest")) {
            py = (int)(i/CX);
            px = i-(py*CX);
            for (x=px*46; x<px*46+40; x++) {
                for (y=py*46; y<py*46+40; y++) {
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                px*46, py*46, px*46+40, py*46+40,
                0, 0, 40, 40,
                null);
            if (ic.ids[i] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.ids[i]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.ids[i]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.ids[i]),
                    px*46+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*46+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);

                if (ic.nums[i] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums[i] + " ", px*46+9, py*46+34);
                }
            }
        }
        if (ic.type.equals("furnace")) {
            if (i == -1) {
                for (y=0; y<5; y++) {
                    for (x=0; x<ic.FUELP*38; x++) {
                        ic.image.setRGB(x+1, y+51, new Color(255, 0, 0).getRGB());
                    }
                    for (x=(int)(ic.FUELP*38); x<38; x++) {
                        ic.image.setRGB(x+1, y+51, new Color(145, 145, 145).getRGB());
                    }
                }
                for (x=0; x<5; x++) {
                    for (y=0; y<ic.SMELTP*38; y++) {
                        ic.image.setRGB(x+40, y+1, new Color(255, 0, 0).getRGB());
                    }
                    for (y=(int)(ic.SMELTP*38); y<38; y++) {
                        ic.image.setRGB(x+40, y+1, new Color(145, 145, 145).getRGB());
                    }
                }
            }
            else {
                if (i == 0) {
                    fpx = 0;
                    fpy = 0;
                }
                if (i == 1) {
                    fpx = 0;
                    fpy = 1.4;
                }
                if (i == 2) {
                    fpx = 0;
                    fpy = 2.4;
                }
                if (i == 3) {
                    fpx = 1.4;
                    fpy = 0;
                }
                for (x=(int)(fpx*40); x<fpx*40+40; x++) {
                    for (y=(int)(fpy*40); y<fpy*40+40; y++) {
                        ic.image.setRGB(x, y, 9539985);
                    }
                }
                g2 = ic.image.createGraphics();
                g2.drawImage(box,
                    (int)(fpx*40), (int)(fpy*40), (int)(fpx*40+40), (int)(fpy*40+40),
                    0, 0, 40, 40,
                    null);
                if (ic.ids[i] != 0) {
                    width = TerrariaClone.getItemImgs().get(ic.ids[i]).getWidth();
                    height = TerrariaClone.getItemImgs().get(ic.ids[i]).getHeight();
                    g2.drawImage(TerrariaClone.getItemImgs().get(ic.ids[i]),
                        (int)(fpx*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2)), (int)(fpy*40+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2)), (int)(fpx*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2)), (int)(fpy*40+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2)),
                        0, 0, width, height,
                        null);

                    if (ic.nums[i] > 1) {
                        g2.setFont(font);
                        g2.setColor(Color.WHITE);
                        g2.drawString(ic.nums[i] + " ", (int)(fpx*40+9), (int)(fpy*40+34));
                    }
                }
            }
        }
    }

    public void useRecipeWorkbench(ItemCollection ic) {
        for (Short[] r2 : RECIPES.get("workbench")) {
            valid = true;
            for (i=0; i<9; i++) {
                if (ic.ids[i] != r2[i]) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                for (i=0; i<9; i++) {
                    removeLocationIC(ic, i, new Short((short)1));
                    updateIC(ic, i);
                }
            }
        }
        ArrayList<Short> r3 = new ArrayList<Short>(11);
        for (Short[] r2 : RECIPES.get("shapeless")) {
            valid = true;
            r3.clear();
            for (k=0; k<r2.length-2; k++) {
                r3.add(r2[k]);
            }
            for (k=0; k<9; k++) {
                n = r3.indexOf(ic.ids[k]);
                if (n == -1) {
                    valid = false;
                    break;
                }
                else {
                    r3.remove(n);
                }
            }
            if (valid) {
                r3.clear();
                for (k=0; k<r2.length-2; k++) {
                    r3.add(r2[k]);
                }
                for (k=0; k<9; k++) {
                    n = r3.indexOf(ic.ids[k]);
                    r3.remove(n);
                    removeLocationIC(ic, k, new Short((short)1));
                    updateIC(ic, k);
                }
                break;
            }
        }
    }

    public void useRecipeCIC(ItemCollection ic) {
        for (Short[] r2 : RECIPES.get("cic")) {
            valid = true;
            for (i=0; i<4; i++) {
                if (ic.ids[i] != r2[i]) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                for (i=0; i<4; i++) {
                    removeLocationIC(ic, i, new Short((short)1));
                    updateIC(ic, i);
                }
            }
        }
        ArrayList<Short> r3 = new ArrayList<Short>(6);
        for (Short[] r2 : RECIPES.get("shapeless_cic")) {
            valid = true;
            r3.clear();
            for (k=0; k<r2.length-2; k++) {
                r3.add(r2[k]);
            }
            for (k=0; k<4; k++) {
                n = r3.indexOf(ic.ids[k]);
                if (n == -1) {
                    valid = false;
                    break;
                }
                else {
                    r3.remove(n);
                }
            }
            if (valid) {
                r3.clear();
                for (k=0; k<r2.length-2; k++) {
                    r3.add(r2[k]);
                }
                for (k=0; k<4; k++) {
                    n = r3.indexOf(ic.ids[k]);
                    r3.remove(n);
                    removeLocationIC(ic, k, new Short((short)1));
                    updateIC(ic, k);
                }
                break;
            }
        }
    }

    private static BufferedImage loadImage(String path) {
        path = "textures/" + path;
        URL url = TerrariaClone.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            System.out.println("[ERROR] could not load image '" + path + "'.");
            e.printStackTrace();
        }
        return image;
    }

    private static int f(int x) {
        if (x == 9) {
            return 0;
        }
        return x + 1;
    }

    public static int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
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
