package org.terraframe;

import java.awt._
import java.awt.image._
import java.io.Serializable
import java.net.URL
import java.util._
import javax.imageio.ImageIO

import scala.util.control.NonFatal;

object Inventory {
    def loadImage(path: String): BufferedImage = {
        val url: URL = getClass.getResource(path);
        var image: BufferedImage = null;
        try {
            image = ImageIO.read(url);
        }
        catch {
            case NonFatal(_) => System.out.println("(ERROR) could not load image '" + path + "'.");
        }
        return image;
    }

    def f(x: Int): Int = {
        if (x == 9) {
            return 0;
        }
        return x + 1;
    }

    def max(a: Int, b: Int, c: Int): Int = {
        return Math.max(Math.max(a, b), c);
    }

    def print(text: String): Unit = {
        System.out.println(text);
    }

    def print(text: Int): Unit = {
        System.out.println(text);
    }

    def print(text: Double): Unit = {
        System.out.println(text);
    }

    def print(text: Short): Unit = {
        System.out.println(text);
    }

    def print(text: Boolean): Unit = {
        System.out.println(text);
    }

    def print(text: AnyRef): Unit = {
        System.out.println(text);
    }
}

class Inventory extends Serializable {
    import Inventory._

    var i, j, k, x, y, n, px, py, selection, width, height: Int = _;
    var fpx, fpy: Double = _;
    var r: Short = _;

    var image, box, box_selected: BufferedImage = _; // was transient
    val font = new Font("Chalkboard", Font.PLAIN, 12);

    var g2: Graphics2D = _; // was transient

    var ids:Array[Short] = _;
    var nums:Array[Short] = _;
    var durs:Array[Short] = _;
    var list_thing:Array2D[Short] = _;
    var r1:Array2D[Short] = _;
    var r2:Array[Short] = _;

    var trolx: Int = 37;
    var troly: Int = 17;

    var CX, CY: Int = _;

    var valid: Boolean = false;

    var ic: ItemCollection = _;

    var RECIPES: Map[String,Array2D[Short]] = _;

    //Begin Constructor
    
        ids = Array.ofDim(40);
        nums = Array.ofDim(40);
        durs = Array.ofDim(40);
        (0 until 40).foreach { i =>
            ids(i) = 0;
            nums(i) = 0;
            durs(i) = 0;
        }
        selection = 0;
        image = new BufferedImage(466, 190, BufferedImage.TYPE_INT_ARGB);
        box = loadImage("interface/inventory.png");
        box_selected = loadImage("interface/inventory_selected.png");
        g2 = image.createGraphics();
        (0 until 10).foreach { x =>
            (0 until 4).foreach { y =>
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

        RECIPES = new HashMap[String,Array2D[Short]]();

        def list_thing1: Array2D[Short] = Array(
            Array[Short](15, 15, 15,
                0, 15, 0,
            0, 15, 0, 154, 1), // Wooden Pick
            Array[Short](2, 2, 2,
                0, 15, 0,
            0, 15, 0, 157, 1), // Stone Pick
            Array[Short](29, 29, 29,
                0, 15, 0,
            0, 15, 0, 7, 1), // Copper Pick
            Array[Short](30, 30, 30,
                0, 15, 0,
            0, 15, 0, 8, 1), // Iron Pick
            Array[Short](31, 31, 31,
                0, 15, 0,
            0, 15, 0, 9, 1), // Silver Pick
            Array[Short](32, 32, 32,
                0, 15, 0,
            0, 15, 0, 10, 1), // Gold Pick
            Array[Short](60, 60, 60,
                0, 15, 0,
            0, 15, 0, 51, 1), // Zinc Pick
            Array[Short](61, 61, 61,
                0, 15, 0,
            0, 15, 0, 54, 1), // Rhymestone Pick
            Array[Short](62, 62, 62,
                0, 15, 0,
            0, 15, 0, 57, 1), // Obdurite Pick
            Array[Short](73, 73, 73,
                0, 15, 0,
            0, 15, 0, 169, 1), // Magnetite Pick
            Array[Short](69, 69, 69,
                0, 15, 0,
            0, 15, 0, 172, 1), // Irradium Pick
            Array[Short](15, 15, 0,
                15, 15, 0,
            0, 15, 0, 155, 1), // Wooden Axe
            Array[Short](0, 15, 15,
                0, 15, 15,
            0, 15, 0, 155, 1),
            Array[Short](15, 15, 0,
                15, 15, 0,
            15, 0, 0, 155, 1),
            Array[Short](0, 15, 15,
                0, 15, 15,
            0, 0, 15, 155, 1),
            Array[Short](2, 2, 0,
                2, 15, 0,
            0, 15, 0, 158, 1), // Stone Axe
            Array[Short](0, 2, 2,
                0, 15, 2,
            0, 15, 0, 158, 1),
            Array[Short](2, 2, 0,
                15, 2, 0,
            15, 0, 0, 158, 1),
            Array[Short](0, 2, 2,
                0, 2, 15,
            0, 0, 15, 158, 1),
            Array[Short](29, 29, 0,
                29, 15, 0,
            0, 15, 0, 11, 1), // Copper Axe
            Array[Short](0, 29, 29,
                0, 15, 29,
            0, 15, 0, 11, 1),
            Array[Short](29, 29, 0,
                15, 29, 0,
            15, 0, 0, 11, 1),
            Array[Short](0, 29, 29,
                0, 29, 15,
            0, 0, 15, 11, 1),
            Array[Short](30, 30, 0,
                30, 15, 0,
            0, 15, 0, 11, 1), // Iron Axe
            Array[Short](0, 30, 30,
                0, 15, 30,
            0, 15, 0, 11, 1),
            Array[Short](30, 30, 0,
                15, 30, 0,
            15, 0, 0, 11, 1),
            Array[Short](0, 30, 30,
                0, 30, 15,
            0, 0, 15, 11, 1),
            Array[Short](31, 31, 0,
                31, 15, 0,
            0, 15, 0, 11, 1), // Silver Axe
            Array[Short](0, 31, 31,
                0, 15, 31,
            0, 15, 0, 11, 1),
            Array[Short](31, 31, 0,
                15, 31, 0,
            15, 0, 0, 11, 1),
            Array[Short](0, 31, 31,
                0, 31, 15,
            0, 0, 15, 11, 1),
            Array[Short](32, 32, 0,
                32, 15, 0,
            0, 15, 0, 11, 1), // Gold Axe
            Array[Short](0, 32, 32,
                0, 15, 32,
            0, 15, 0, 11, 1),
            Array[Short](32, 32, 0,
                15, 32, 0,
            15, 0, 0, 11, 1),
            Array[Short](0, 32, 32,
                0, 32, 15,
            0, 0, 15, 11, 1),
            Array[Short](60, 60, 0,
                60, 15, 0,
            0, 15, 0, 52, 1), // Zinc Axe
            Array[Short](0, 60, 60,
                0, 15, 60,
            0, 15, 0, 52, 1),
            Array[Short](60, 60, 0,
                15, 60, 0,
            15, 0, 0, 52, 1),
            Array[Short](0, 60, 60,
                0, 60, 15,
            0, 0, 15, 52, 1),
            Array[Short](61, 61, 0,
                61, 15, 0,
            0, 15, 0, 55, 1), // Rhymestone Axe
            Array[Short](0, 61, 61,
                0, 15, 61,
            0, 15, 0, 55, 1),
            Array[Short](61, 61, 0,
                15, 61, 0,
            15, 0, 0, 55, 1),
            Array[Short](0, 61, 61,
                0, 61, 15,
            0, 0, 15, 55, 1),
            Array[Short](62, 62, 0,
                62, 15, 0,
            0, 15, 0, 58, 1), // Obdurite Axe
            Array[Short](0, 62, 62,
                0, 15, 62,
            0, 15, 0, 58, 1),
            Array[Short](62, 62, 0,
                15, 62, 0,
            15, 0, 0, 58, 1),
            Array[Short](0, 62, 62,
                0, 62, 15,
            0, 0, 15, 58, 1),
            Array[Short](73, 73, 0,
                73, 15, 0,
            0, 15, 0, 170, 1), // Magnetite Axe
            Array[Short](0, 73, 73,
                0, 15, 73,
            0, 15, 0, 170, 1),
            Array[Short](73, 73, 0,
                15, 73, 0,
            15, 0, 0, 170, 1),
            Array[Short](0, 73, 73,
                0, 73, 15,
            0, 0, 15, 170, 1),
            Array[Short](69, 69, 0,
                69, 15, 0,
            0, 15, 0, 169, 1), // Irradium Axe
            Array[Short](0, 69, 69,
                0, 15, 69,
            0, 15, 0, 169, 1),
            Array[Short](69, 69, 0,
                15, 69, 0,
            15, 0, 0, 169, 1),
            Array[Short](0, 69, 69,
                0, 69, 15,
            0, 0, 15, 169, 1),
            Array[Short](15, 0, 0,
                15, 0, 0,
            15, 0, 0, 156, 1), // Wooden Sword
            Array[Short](0, 15, 0,
                0, 15, 0,
            0, 15, 0, 156, 1),
            Array[Short](0, 0, 15,
                0, 0, 15,
            0, 0, 15, 156, 1),
            Array[Short](2, 0, 0,
                2, 0, 0,
            15, 0, 0, 159, 1), // Stone Sword
            Array[Short](0, 2, 0,
                0, 2, 0,
            0, 15, 0, 159, 1),
            Array[Short](0, 0, 2,
                0, 0, 2,
            0, 0, 15, 159, 1),
            Array[Short](29, 0, 0,
                29, 0, 0,
            15, 0, 0, 16, 1), // Copper Sword
            Array[Short](0, 29, 0,
                0, 29, 0,
            0, 15, 0, 16, 1),
            Array[Short](0, 0, 29,
                0, 0, 29,
            0, 0, 15, 16, 1),
            Array[Short](30, 0, 0,
                30, 0, 0,
            15, 0, 0, 17, 1), // Iron Sword
            Array[Short](0, 30, 0,
                0, 30, 0,
            0, 15, 0, 17, 1),
            Array[Short](0, 0, 30,
                0, 0, 30,
            0, 0, 15, 17, 1),
            Array[Short](31, 0, 0,
                31, 0, 0,
            15, 0, 0, 18, 1), // Silver Sword
            Array[Short](0, 31, 0,
                0, 31, 0,
            0, 15, 0, 18, 1),
            Array[Short](0, 0, 31,
                0, 0, 31,
            0, 0, 15, 18, 1),
            Array[Short](32, 0, 0,
                32, 0, 0,
            15, 0, 0, 19, 1), // Gold Sword
            Array[Short](0, 32, 0,
                0, 32, 0,
            0, 15, 0, 19, 1),
            Array[Short](0, 0, 32,
                0, 0, 32,
            0, 0, 15, 19, 1),
            Array[Short](38, 0, 0,
                38, 0, 0,
            15, 0, 0, 19, 1), // Zinc Sword
            Array[Short](0, 38, 0,
                0, 38, 0,
            0, 15, 0, 19, 1),
            Array[Short](0, 0, 38,
                0, 0, 38,
            0, 0, 15, 19, 1),
            Array[Short](39, 0, 0,
                39, 0, 0,
            15, 0, 0, 19, 1), // Rhymestone Sword
            Array[Short](0, 39, 0,
                0, 39, 0,
            0, 15, 0, 19, 1),
            Array[Short](0, 0, 39,
                0, 0, 39,
            0, 0, 15, 19, 1),
            Array[Short](40, 0, 0,
                40, 0, 0,
            15, 0, 0, 19, 1), // Obdurite Sword
            Array[Short](0, 40, 0,
                0, 40, 0,
            0, 15, 0, 19, 1),
            Array[Short](0, 0, 40,
                0, 0, 40,
            0, 0, 15, 19, 1),
            Array[Short](73, 0, 0,
                73, 0, 0,
            15, 0, 0, 171, 1), // Magnetite Sword
            Array[Short](0, 73, 0,
                0, 73, 0,
            0, 15, 0, 171, 1),
            Array[Short](0, 0, 73,
                0, 0, 73,
            0, 0, 15, 171, 1),
            Array[Short](69, 0, 0,
                69, 0, 0,
            15, 0, 0, 174, 1), // Irradium Sword
            Array[Short](0, 69, 0,
                0, 69, 0,
            0, 15, 0, 174, 1),
            Array[Short](0, 0, 69,
                0, 0, 69,
            0, 0, 15, 174, 1),
            Array[Short](63, 0, 63,
                0, 63, 0,
            0, 63, 0, 190, 1), // Wrench
            Array[Short](15, 0, 0,
                2, 0, 0,
            175, 0, 0, 178, 1), // Lever
            Array[Short](0, 15, 0,
                0, 2, 0,
            0, 175, 0, 178, 1),
            Array[Short](0, 0, 15,
                0, 0, 2,
            0, 0, 175, 178, 1),
            Array[Short](29, 29, 29,
                29, 0, 29,
            0, 0, 0, 105, 1), // Copper Helmet
            Array[Short](0, 0, 0,
                29, 29, 29,
            29, 0, 29, 105, 1),
            Array[Short](29, 0, 29,
                29, 29, 29,
            29, 29, 29, 106, 1), // Copper Chestplate
            Array[Short](29, 29, 29,
                29, 0, 29,
            29, 0, 29, 107, 1), // Copper Leggings
            Array[Short](29, 0, 29,
                29, 0, 29,
            0, 0, 0, 108, 1), // Copper Greaves
            Array[Short](0, 0, 0,
                29, 0, 29,
            29, 0, 29, 108, 1),
            Array[Short](30, 30, 30,
                30, 0, 30,
            0, 0, 0, 109, 1), // Iron Helmet
            Array[Short](0, 0, 0,
                30, 30, 30,
            30, 0, 30, 109, 1),
            Array[Short](30, 0, 30,
                30, 30, 30,
            30, 30, 30, 110, 1), // Iron Chestplate
            Array[Short](30, 30, 30,
                30, 0, 30,
            30, 0, 30, 111, 1), // Iron Leggings
            Array[Short](30, 0, 30,
                30, 0, 30,
            0, 0, 0, 112, 1), // Iron Greaves
            Array[Short](0, 0, 0,
                30, 0, 30,
            30, 0, 30, 112, 1),
            Array[Short](31, 31, 31,
                31, 0, 31,
            0, 0, 0, 113, 1), // Silver Helmet
            Array[Short](0, 0, 0,
                31, 31, 31,
            31, 0, 31, 113, 1),
            Array[Short](31, 0, 31,
                31, 31, 31,
            31, 31, 31, 114, 1), // Silver Chestplate
            Array[Short](31, 31, 31,
                31, 0, 31,
            31, 0, 31, 115, 1), // Silver Leggings
            Array[Short](31, 0, 31,
                31, 0, 31,
            0, 0, 0, 116, 1), // Silver Greaves
            Array[Short](0, 0, 0,
                31, 0, 31,
            31, 0, 31, 116, 1),
            Array[Short](32, 32, 32,
                32, 0, 32,
            0, 0, 0, 117, 1), // Gold Helmet
            Array[Short](0, 0, 0,
                32, 32, 32,
            32, 0, 32, 117, 1),
            Array[Short](32, 0, 32,
                32, 32, 32,
            32, 32, 32, 118, 1), // Gold Chestplate
            Array[Short](32, 32, 32,
                32, 0, 32,
            32, 0, 32, 119, 1), // Gold Leggings
            Array[Short](32, 0, 32,
                32, 0, 32,
            0, 0, 0, 120, 1), // Gold Greaves
            Array[Short](0, 0, 0,
                32, 0, 32,
            32, 0, 32, 120, 1),
            Array[Short](60, 60, 60,
                60, 0, 60,
            0, 0, 0, 121, 1), // Zinc Helmet
            Array[Short](0, 0, 0,
                60, 60, 60,
            60, 0, 60, 121, 1),
            Array[Short](60, 0, 60,
                60, 60, 60,
            60, 60, 60, 122, 1), // Zinc Chestplate
            Array[Short](60, 60, 60,
                60, 0, 60,
            60, 0, 60, 123, 1), // Zinc Leggings
            Array[Short](60, 0, 60,
                60, 0, 60,
            0, 0, 0, 124, 1), // Zinc Greaves
            Array[Short](0, 0, 0,
                60, 0, 60,
            60, 0, 60, 124, 1),
            Array[Short](61, 61, 61,
                61, 0, 61,
            0, 0, 0, 125, 1), // Rhymestone Helmet
            Array[Short](0, 0, 0,
                61, 61, 61,
            61, 0, 61, 125, 1),
            Array[Short](61, 0, 61,
                61, 61, 61,
            61, 61, 61, 126, 1), // Rhymestone Chestplate
            Array[Short](61, 61, 61,
                61, 0, 61,
            61, 0, 61, 127, 1), // Rhymestone Leggings
            Array[Short](61, 0, 61,
                61, 0, 61,
            0, 0, 0, 128, 1), // Rhymestone Greaves
            Array[Short](0, 0, 0,
                61, 0, 61,
            61, 0, 61, 128, 1),
            Array[Short](62, 62, 62,
                62, 0, 62,
            0, 0, 0, 129, 1), // Obdurite Helmet
            Array[Short](0, 0, 0,
                62, 62, 62,
            62, 0, 62, 129, 1),
            Array[Short](62, 0, 62,
                62, 62, 62,
            62, 62, 62, 130, 1), // Obdurite Chestplate
            Array[Short](62, 62, 62,
                62, 0, 62,
            62, 0, 62, 131, 1), // Obdurite Leggings
            Array[Short](62, 0, 62,
                62, 0, 62,
            0, 0, 0, 132, 1), // Obdurite Greaves
            Array[Short](0, 0, 0,
                62, 0, 62,
            62, 0, 62, 132, 1),
            Array[Short](63, 63, 63,
                63, 0, 63,
            0, 0, 0, 133, 1), // Aluminum Helmet
            Array[Short](0, 0, 0,
                63, 63, 63,
            63, 0, 63, 133, 1),
            Array[Short](63, 0, 63,
                63, 63, 63,
            63, 63, 63, 134, 1), // Aluminum Chestplate
            Array[Short](63, 63, 63,
                63, 0, 63,
            63, 0, 63, 135, 1), // Aluminum Leggings
            Array[Short](63, 0, 63,
                63, 0, 63,
            0, 0, 0, 136, 1), // Aluminum Greaves
            Array[Short](0, 0, 0,
                63, 0, 63,
            63, 0, 63, 136, 1),
            Array[Short](64, 64, 64,
                64, 0, 64,
            0, 0, 0, 137, 1), // Lead Helmet
            Array[Short](0, 0, 0,
                64, 64, 64,
            64, 0, 64, 137, 1),
            Array[Short](64, 0, 64,
                64, 64, 64,
            64, 64, 64, 138, 1), // Lead Chestplate
            Array[Short](64, 64, 64,
                64, 0, 64,
            64, 0, 64, 139, 1), // Lead Leggings
            Array[Short](64, 0, 64,
                64, 0, 64,
            0, 0, 0, 140, 1), // Lead Greaves
            Array[Short](0, 0, 0,
                64, 0, 64,
            64, 0, 64, 140, 1),
            Array[Short](15, 15, 15,
                15, 0, 15,
            15, 15, 15, 21, 1), // Wooden Chest
            Array[Short](2, 2, 2,
                2, 21, 2,
            2, 2, 2, 22, 1), // Stone Chest
            Array[Short](29, 29, 29,
                29, 22, 29,
            29, 29, 29, 23, 1), // Copper Chest
            Array[Short](30, 30, 30,
                30, 22, 30,
            30, 30, 30, 24, 1), // Iron Chest
            Array[Short](31, 31, 31,
                31, 22, 31,
            31, 31, 31, 25, 1), // Silver Chest
            Array[Short](32, 32, 32,
                32, 22, 32,
            32, 32, 32, 26, 1), // Gold Chest
            Array[Short](60, 60, 60,
                60, 22, 60,
            60, 60, 60, 151, 1), // Zinc Chest
            Array[Short](61, 61, 61,
                61, 22, 61,
            61, 61, 61, 152, 1), // Rhymestone Chest
            Array[Short](62, 62, 62,
                62, 22, 62,
            62, 62, 62, 153, 1), // Obdurite Chest
            Array[Short](76, 76, 76,
                76, 34, 76,
            76, 175, 76, 177, 1), // Zythium Lamp
            Array[Short](76, 76, 76,
                175, 44, 175,
            76, 76, 76, 180, 1), // Zythium Amplifier
            Array[Short](76, 76, 76,
                44, 175, 44,
            76, 76, 76, 181, 1), // Zythium Inverter
            Array[Short](76, 175, 76,
                175, 175, 175,
            76, 175, 76, 186, 1), // Zythium Delayer
            Array[Short](15, 15, 0,
                15, 15, 0,
            0, 0, 0, 20, 1), // Workbench
            Array[Short](0, 15, 15,
                0, 15, 15,
            0, 0, 0, 20, 1),
            Array[Short](0, 0, 0,
                15, 15, 0,
            15, 15, 0, 20, 1),
            Array[Short](0, 0, 0,
                0, 15, 15,
            0, 15, 15, 20, 1),
            Array[Short](160, 160, 0,
                160, 160, 0,
            0, 0, 0, 15, 1), // Bark -> Wood
            Array[Short](0, 160, 160,
                0, 160, 160,
            0, 0, 0, 15, 1),
            Array[Short](0, 0, 0,
                160, 160, 0,
            160, 160, 0, 15, 1),
            Array[Short](0, 0, 0,
                0, 160, 160,
            0, 160, 160, 15, 1),
            Array[Short](2, 2, 0,
                2, 2, 0,
            0, 0, 0, 161, 4), // Cobblestone
            Array[Short](0, 2, 2,
                0, 2, 2,
            0, 0, 0, 161, 4),
            Array[Short](0, 0, 0,
                2, 2, 0,
            2, 2, 0, 161, 4),
            Array[Short](0, 0, 0,
                0, 2, 2,
            0, 2, 2, 161, 4),
            Array[Short](162, 162, 0,
                162, 162, 0,
            0, 0, 0, 163, 4), // Chiseled Cobblestone
            Array[Short](0, 162, 162,
                0, 162, 162,
            0, 0, 0, 163, 4),
            Array[Short](0, 0, 0,
                162, 162, 0,
            162, 162, 0, 163, 4),
            Array[Short](0, 0, 0,
                0, 162, 162,
            0, 162, 162, 163, 4),
            Array[Short](163, 163, 0,
                163, 163, 0,
            0, 0, 0, 164, 4), // Stone Bricks
            Array[Short](0, 163, 163,
                0, 163, 163,
            0, 0, 0, 164, 4),
            Array[Short](0, 0, 0,
                163, 163, 0,
            163, 163, 0, 164, 4),
            Array[Short](0, 0, 0,
                0, 163, 163,
            0, 163, 163, 164, 4),
            Array[Short](2, 2, 2,
                2, 0, 2,
            2, 2, 2, 27, 1), // Furnace
            Array[Short](67, 67, 67,
                0, 0, 0,
            0, 0, 0, 175, 10), // Zythium Wire
            Array[Short](0, 0, 0,
                67, 67, 67,
            0, 0, 0, 175, 20),
            Array[Short](0, 0, 0,
                0, 0, 0,
            67, 67, 67, 175, 20),
            Array[Short](2, 0, 0,
                0, 2, 0,
            0, 0, 0, 33, 1), // Stone Lighter
            Array[Short](0, 2, 0,
                0, 0, 2,
            0, 0, 0, 33, 1),
            Array[Short](0, 0, 0,
                2, 0, 0,
            0, 2, 0, 33, 1),
            Array[Short](0, 0, 0,
                0, 2, 0,
            0, 0, 2, 33, 1),
            Array[Short](0, 2, 0,
                2, 0, 0,
            0, 0, 0, 33, 1),
            Array[Short](0, 0, 2,
                0, 2, 0,
            0, 0, 0, 33, 1),
            Array[Short](0, 0, 0,
                0, 2, 0,
            2, 0, 0, 33, 1),
            Array[Short](0, 0, 0,
                0, 0, 2,
            0, 2, 0, 33, 1),
            Array[Short](15, 0, 0,
                15, 0, 0,
            0, 0, 0, 35, 4),  // Wooden Torch
            Array[Short](0, 15, 0,
                0, 15, 0,
            0, 0, 0, 35, 4),
            Array[Short](0, 0, 15,
                0, 0, 15,
            0, 0, 0, 35, 4),
            Array[Short](0, 0, 0,
                15, 0, 0,
            15, 0, 0, 35, 4),
            Array[Short](0, 0, 0,
                0, 15, 0,
            0, 15, 0, 35, 4),
            Array[Short](0, 0, 0,
                0, 0, 15,
            0, 0, 15, 35, 4),
            Array[Short](28, 0, 0,
                15, 0, 0,
            0, 0, 0, 36, 4), // Coal Torch
            Array[Short](0, 28, 0,
                0, 15, 0,
            0, 0, 0, 36, 4),
            Array[Short](0, 0, 28,
                0, 0, 15,
            0, 0, 0, 36, 4),
            Array[Short](0, 0, 0,
                28, 0, 0,
            15, 0, 0, 36, 4),
            Array[Short](0, 0, 0,
                0, 28, 0,
            0, 15, 0, 36, 4),
            Array[Short](0, 0, 0,
                0, 0, 28,
            0, 0, 15, 36, 4),
            Array[Short](34, 0, 0,
                15, 0, 0,
            0, 0, 0, 37, 4), // Lumenstone Torch
            Array[Short](0, 34, 0,
                0, 15, 0,
            0, 0, 0, 37, 4),
            Array[Short](0, 0, 34,
                0, 0, 15,
            0, 0, 0, 37, 4),
            Array[Short](0, 0, 0,
                34, 0, 0,
            15, 0, 0, 37, 4),
            Array[Short](0, 0, 0,
                0, 34, 0,
            0, 15, 0, 37, 4),
            Array[Short](0, 0, 0,
                0, 0, 34,
            0, 0, 15, 37, 4),
            Array[Short](44, 0, 0,
                15, 0, 0,
            0, 0, 0, 176, 4), // Zythium Torch
            Array[Short](0, 44, 0,
                0, 15, 0,
            0, 0, 0, 176, 4),
            Array[Short](0, 0, 44,
                0, 0, 15,
            0, 0, 0, 176, 4),
            Array[Short](0, 0, 0,
                44, 0, 0,
            15, 0, 0, 176, 4),
            Array[Short](0, 0, 0,
                0, 44, 0,
            0, 15, 0, 176, 4),
            Array[Short](0, 0, 0,
                0, 0, 44,
            0, 0, 15, 176, 4),
            Array[Short](15, 15, 0,
                0, 0, 0,
            0, 0, 0, 183, 1), // Wooden Pressure Plate
            Array[Short](0, 15, 15,
                0, 0, 0,
            0, 0, 0, 183, 1),
            Array[Short](0, 0, 0,
                15, 15, 0,
            0, 0, 0, 183, 1),
            Array[Short](0, 0, 0,
                0, 15, 15,
            0, 0, 0, 183, 1),
            Array[Short](0, 0, 0,
                0, 0, 0,
            15, 15, 0, 183, 1),
            Array[Short](0, 0, 0,
                0, 0, 0,
            0, 15, 15, 183, 1),
            Array[Short](2, 2, 0,
                0, 0, 0,
            0, 0, 0, 184, 1), // Stone Pressure Plate
            Array[Short](0, 2, 2,
                0, 0, 0,
            0, 0, 0, 184, 1),
            Array[Short](0, 0, 0,
                2, 2, 0,
            0, 0, 0, 184, 1),
            Array[Short](0, 0, 0,
                0, 2, 2,
            0, 0, 0, 184, 1),
            Array[Short](0, 0, 0,
                0, 0, 0,
            2, 2, 0, 184, 1),
            Array[Short](0, 0, 0,
                0, 0, 0,
            0, 2, 2, 184, 1),
            Array[Short](162, 44, 162,
                0, 175, 0,
            0, 0, 0, 185, 1), // Zythium Pressure Plate
            Array[Short](0, 0, 0,
                162, 44, 162,
            0, 175, 0, 185, 1)
        );

        RECIPES.put("workbench", list_thing1);

        def list_thing2: Array2D[Short] = Array(
            Array[Short](15, 15,
                15, 15, 20, 1), // Workbench
            Array[Short](160, 160,
                160, 160, 15, 1), // Bark -> Wood
            Array[Short](2, 2,
                2, 2, 161, 4), // Cobblestone
            Array[Short](162, 162,
                162, 162, 163, 4), // Chiseled Cobblestone
            Array[Short](163, 163,
                163, 163, 164, 4), // Stone Bricks
            Array[Short](2, 0,
                0, 2, 33, 1), // Stone Lighter
            Array[Short](0, 2,
                2, 0, 33, 1),
            Array[Short](15, 0,
                15, 0, 35, 4),  // Wooden Torch
            Array[Short](0, 15,
                0, 15, 35, 4),
            Array[Short](28, 0,
                15, 0, 36, 4), // Coal Torch
            Array[Short](0, 28,
                0, 15, 36, 4),
            Array[Short](34, 0,
                15, 0, 37, 4), // Lumenstone Torch
            Array[Short](0, 34,
                0, 15, 37, 4),
            Array[Short](44, 0,
                15, 0, 176, 4), // Zythium Torch
            Array[Short](0, 44,
                0, 15, 176, 4),
            Array[Short](15, 15,
                0, 0, 183, 1), // Wooden Pressure Plate
            Array[Short](0, 0,
                15, 15, 183, 1),
            Array[Short](2, 2,
                0, 0, 184, 1), // Stone Pressure Plate
            Array[Short](0, 0,
                2, 2, 184, 1)
        );

        RECIPES.put("cic", list_thing2);

        def list_thing3: Array2D[Short] = Array(
            Array[Short](15, 167, 0, 0, 0, 0, 0, 0, 0,
        168, 1),
            Array[Short](162, 0, 0, 0, 0, 0, 0, 0, 0,
        182, 1)
        );

        RECIPES.put("shapeless", list_thing3);

        def list_thing4: Array2D[Short] = Array(
            Array[Short](15, 167, 0, 0,
        168, 1),
            Array[Short](162, 0, 0, 0,
        182, 1)
        );

        RECIPES.put("shapeless_cic", list_thing4);
    // END CONSTRUCTOR

    def addItem(item: Short, quantity: Short): Int = {
        if (TerraFrame.getTOOLDURS().get(item) != null) {
            return addItem(item, quantity, TerraFrame.getTOOLDURS().get(item));
        }
        else {
            return addItem(item, quantity, 0.toShort);
        }
    }

    def addItem(item: Short, quantity: Short, durability: Short): Int = {
        var updatedQuantity: Short = quantity
        (0 until 40).foreach { i =>
            if (ids(i) == item && nums(i) < TerraFrame.getMAXSTACKS().get(ids(i))) {
                if (TerraFrame.getMAXSTACKS().get(ids(i)) - nums(i) >= updatedQuantity) {
                    nums(i) = (nums(i) + updatedQuantity).toShort;
                    update(i);
                    return 0;
                }
                else {
                    updatedQuantity = (updatedQuantity - TerraFrame.getMAXSTACKS().get(ids(i)) - nums(i)).toShort;
                    nums(i) = TerraFrame.getMAXSTACKS().get(ids(i));
                    update(i);
                }
            }
        }
        (0 until 40).foreach { i =>
            if (ids(i) == 0) {
                ids(i) = item;
                if (updatedQuantity <= TerraFrame.getMAXSTACKS().get(ids(i))) {
                    nums(i) = updatedQuantity;
                    durs(i) = durability;
                    update(i);
                    return 0;
                }
                else {
                    nums(i) = TerraFrame.getMAXSTACKS().get(ids(i));
                    durs(i) = durability;
                    updatedQuantity = (updatedQuantity - TerraFrame.getMAXSTACKS().get(ids(i))).toShort;
                }
            }
        }
        return updatedQuantity;
    }

    def removeItem(item: Short, quantity: Short): Int = {
        var updatedQuantity: Short = quantity
        (0 until 40).foreach { i =>
            if (ids(i) == item) {
                if (nums(i) <= updatedQuantity) {
                    nums(i) = (nums(i) - updatedQuantity).toShort;
                    if (nums(i) == 0) {
                        ids(i) = 0;
                    }
                    update(i);
                    return 0;
                }
                else {
                    updatedQuantity = (updatedQuantity - nums(i)).toShort;
                    nums(i) = 0;
                    ids(i) = 0;
                    update(i);
                }
            }
        }
        return updatedQuantity;
    }

    def addLocation(i: Int, item: Short, quantity: Short, durability: Short): Int = {
        var updatedQuantity: Short = quantity
        if (ids(i) == item) {
            if (TerraFrame.getMAXSTACKS().get(ids(i)) - nums(i) >= updatedQuantity) {
                nums(i) = (nums(i) + updatedQuantity).toShort;
                update(i);
                return 0;
            }
            else {
                updatedQuantity = (updatedQuantity - TerraFrame.getMAXSTACKS().get(ids(i)) - nums(i)).toShort;
                nums(i) = TerraFrame.getMAXSTACKS().get(ids(i));
                update(i);
            }
        }
        else {
            if (updatedQuantity <= TerraFrame.getMAXSTACKS().get(ids(i))) {
                ids(i) = item;
                nums(i) = updatedQuantity;
                durs(i) = durability;
                update(i);
                return 0;
            }
            else {
                updatedQuantity  = (updatedQuantity - TerraFrame.getMAXSTACKS().get(ids(i))).toShort;
                return updatedQuantity;
            }
        }
        return updatedQuantity;
    }

    def removeLocation(i: Int, quantity: Short): Int =  {
        var updatedQuantity: Short = quantity
        if (nums(i) >= updatedQuantity) {
            nums(i) = (nums(i) - updatedQuantity).toShort;
            if (nums(i) == 0) {
                ids(i) = 0;
            }
            update(i);
            return 0;
        }
        else {
            updatedQuantity = (updatedQuantity - nums(i)).toShort;
            nums(i) = 0;
            ids(i) = 0;
            update(i);
        }
        return updatedQuantity;
    }

    def reloadImage(): Unit = {
        image = new BufferedImage(466, 190, BufferedImage.TYPE_INT_ARGB);
        box = loadImage("interface/inventory.png");
        box_selected = loadImage("interface/inventory_selected.png");
        g2 = image.createGraphics();
        (0 until 10).foreach { x =>
            (0 until 4).foreach { y =>
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
        (0 until 40).foreach { i =>
            update(i);
        }
    }

    def update(i: Int): Unit = {
        py = (i/10).toInt;
        px = i-(py*10);
        (px*46+6 until px*46+46).foreach { x =>
            (py*46+6 until py*46+46).foreach { y =>
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
        if (ids(i) != 0) {
            width = TerraFrame.getItemImgs().get(ids(i)).getWidth();
            height = TerraFrame.getItemImgs().get(ids(i)).getHeight();
            g2.drawImage(TerraFrame.getItemImgs().get(ids(i)),
                px*46+14+((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*46+14+((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt, px*46+38-((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*46+38-((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt,
                0, 0, width, height,
                null);

            if (nums(i) > 1) {
                g2.setFont(font);
                g2.setColor(Color.WHITE);
                g2.drawString(nums(i) + " ", px*46+15, py*46+40);
            }
        }
    }

    def select(i: Int): Unit = {
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

    def select2(i: Int): Unit = {
        n = selection;
        selection = i;
        update(n);
        update(i);
    }

    def tool(): Short = {
        return ids(selection);
    }

    def renderCollection(ic: ItemCollection): Unit = {
        if (ic.`type`.equals("cic")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/cic.png");
                (0 until 4).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("armor")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/armor.png");
                CX = 1;
                CY = 4;
                (0 until 4).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("workbench")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/workbench.png");
                (0 until 9).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("wooden_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/wooden_chest.png");
                CX = 3;
                CY = 3;
                (0 until 9).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("stone_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/stone_chest.png");
                CX = 5;
                CY = 3;
                (0 until 15).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("copper_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/copper_chest.png");
                CX = 5;
                CY = 4;
                (0 until 20).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("iron_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/iron_chest.png");
                CX = 7;
                CY = 4;
                (0 until 28).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("silver_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/silver_chest.png");
                CX = 7;
                CY = 5;
                (0 until 35).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("gold_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/gold_chest.png");
                CX = 7;
                CY = 6;
                (0 until 42).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("zinc_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/zinc_chest.png");
                CX = 7;
                CY = 8;
                (0 until 56).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("rhymestone_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/rhymestone_chest.png");
                CX = 8;
                CY = 9;
                (0 until 72).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("obdurite_chest")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/obdurite_chest.png");
                CX = 10;
                CY = 10;
                (0 until 100).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
        if (ic.`type`.equals("furnace")) {
            if (ic.image == null) {
                ic.image = loadImage("interface/furnace.png");
                (-1 until 4).foreach { i =>
                    updateIC(ic, i);
                }
            }
        }
    }

    def addLocationIC(ic: ItemCollection, i: Int, item: Short, quantity: Short): Int = {
        return addLocationIC(ic, i, item, quantity, 0.toShort);
    }

    def addLocationIC(ic: ItemCollection, i: Int, item: Short, quantity: Short, durability: Short): Int = {
        var updatedQuantity: Short = quantity
        if (ic.ids(i) == item) {
            if (TerraFrame.getMAXSTACKS().get(ic.ids(i)) - ic.nums(i) >= updatedQuantity) {
                ic.nums(i) = (ic.nums(i) + updatedQuantity).toShort;
                if (ic.image != null) {
                    updateIC(ic, i);
                }
                return 0;
            }
            else {
                updatedQuantity = (updatedQuantity - TerraFrame.getMAXSTACKS().get(ic.ids(i)) - ic.nums(i)).toShort;
                ic.nums(i) = TerraFrame.getMAXSTACKS().get(ic.ids(i));
                if (ic.image != null) {
                    updateIC(ic, i);
                }
            }
        }
        else {
            if (updatedQuantity <= TerraFrame.getMAXSTACKS().get(ic.ids(i))) {
                ic.ids(i) = item;
                ic.nums(i) = updatedQuantity;
                ic.durs(i) = durability;
                if (ic.image != null) {
                    updateIC(ic, i);
                }
                return 0;
            }
            else {
                updatedQuantity = (updatedQuantity - TerraFrame.getMAXSTACKS().get(ic.ids(i))).toShort;
                return updatedQuantity;
            }
        }
        return updatedQuantity;
    }

    def removeLocationIC(ic: ItemCollection, i: Int, quantity: Short): Int = {
        var updatedQuantity: Short = quantity
        if (ic.nums(i) >= updatedQuantity) {
            ic.nums(i) = (ic.nums(i) - updatedQuantity).toShort;
            if (ic.nums(i) == 0) {
                ic.ids(i) = 0;
            }
            if (ic.image != null) {
                updateIC(ic, i);
            }
            return 0;
        }
        else {
            updatedQuantity = (updatedQuantity - ic.nums(i)).toShort;
            ic.nums(i) = 0;
            ic.ids(i) = 0;
            if (ic.image != null) {
                updateIC(ic, i);
            }
        }
        return updatedQuantity;
    }

    def updateIC(ic: ItemCollection, i: Int): Unit = {
        if (ic.`type`.equals("cic")) {
            py = (i/2).toInt;
            px = i-(py*2);
            (px*40 until px*40+40).foreach { x =>
                (py*40 until py*40+40).foreach { y =>
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                px*40, py*40, px*40+40, py*40+40,
                0, 0, 40, 40,
                null);
            if (ic.ids(i) != 0) {
                width = TerraFrame.getItemImgs().get(ic.ids(i)).getWidth();
                height = TerraFrame.getItemImgs().get(ic.ids(i)).getHeight();
                g2.drawImage(TerraFrame.getItemImgs().get(ic.ids(i)),
                    px*40+8+((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*40+8+((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt, px*40+32-((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*40+32-((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt,
                    0, 0, width, height,
                    null);
                if (ic.nums(i) > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums(i) + " ", px*40+9, py*40+34);
                }
            }
            ic.ids(4) = 0;
            ic.ids(4) = 0;
            import scala.util.control.Breaks._
            breakable {
                RECIPES.get("cic").foreach { r2 =>
                    valid = true;

                    breakable {
                        (0 until 4).foreach { i =>
                            if (ic.ids(i) != r2(i)) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (valid) {
                        ic.ids(4) = r2(4);
                        ic.nums(4) = r2(5);
                        if (TerraFrame.getTOOLDURS().get(r2(4)) != null)
                            ic.durs(4) = TerraFrame.getTOOLDURS().get(r2(4));
                        break;
                    }
                }
            }
            val r3: ArrayList[Short] = new ArrayList[Short](6);
            breakable {
                RECIPES.get("shapeless_cic").foreach { r2 =>
                    valid = true;
                    r3.clear();
                    (0 until r2.length - 2).foreach { j =>
                        r3.add(r2(j));
                    }
                    breakable {
                        (0 until 4).foreach { j =>
                            n = r3.indexOf(ic.ids(j));
                            if (n == -1) {
                                valid = false;
                                break;
                            }
                            else {
                                r3.remove(n);
                            }
                        }
                    }
                    if (valid) {
                        ic.ids(4) = r2(r2.length - 2);
                        ic.nums(4) = r2(r2.length - 1);
                        if (TerraFrame.getTOOLDURS().get(r2(r2.length - 2)) != null)
                            ic.durs(4) = TerraFrame.getTOOLDURS().get(r2(r2.length - 2));
                        break;
                    }
                }
            }
            (3*40 until 3*40+40).foreach { x =>
                (20 until 20+40).foreach { y =>
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                3*40, 20, 3*40+40, 20+40,
                0, 0, 40, 40,
                null);
            if (ic.ids(4) != 0) {
                width = TerraFrame.getItemImgs().get(ic.ids(4)).getWidth();
                height = TerraFrame.getItemImgs().get(ic.ids(4)).getHeight();
                g2.drawImage(TerraFrame.getItemImgs().get(ic.ids(4)),
                    3*40+8+((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, 20+8+((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt, 3*40+32-((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, 20+32-((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt,
                    0, 0, width, height,
                    null);

                if (ic.nums(4) > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums(4) + " ", 3*40+9, 20+34);
                }
            }
        }
        if (ic.`type`.equals("armor")) {
            py = (i/CX).toInt;
            px = i-(py*CX);
            (px*46 until px*46+40).foreach { x =>
                (py*46 until py*46+40).foreach { y =>
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                px*46, py*46, px*46+40, py*46+40,
                0, 0, 40, 40,
                null);
            if (ic.ids(i) != 0) {
                width = TerraFrame.getItemImgs().get(ic.ids(i)).getWidth();
                height = TerraFrame.getItemImgs().get(ic.ids(i)).getHeight();
                g2.drawImage(TerraFrame.getItemImgs().get(ic.ids(i)),
                    px*46+8+((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*46+8+((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt, px*46+32-((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*46+32-((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt,
                    0, 0, width, height,
                    null);

                if (ic.nums(i) > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums(i) + " ", px*46+9, py*46+34);
                }
            }
        }
        if (ic.`type`.equals("workbench")) {
            py = (i/3).toInt;
            px = i-(py*3);
            (px*40 until px*40+40).foreach { x =>
                (py*40 until py*40+40).foreach { y =>
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                px*40, py*40, px*40+40, py*40+40,
                0, 0, 40, 40,
                null);
            if (ic.ids(i) != 0) {
                width = TerraFrame.getItemImgs().get(ic.ids(i)).getWidth();
                height = TerraFrame.getItemImgs().get(ic.ids(i)).getHeight();
                g2.drawImage(TerraFrame.getItemImgs().get(ic.ids(i)),
                    px*40+8+((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*40+8+((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt, px*40+32-((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*40+32-((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt,
                    0, 0, width, height,
                    null);
                if (ic.nums(i) > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums(i) + " ", px*40+9, py*40+34);
                }
            }
            ic.ids(9) = 0;
            ic.ids(9) = 0;
            import scala.util.control.Breaks._
            breakable {
                RECIPES.get("workbench").foreach { r2 =>
                    valid = true;
                    breakable {
                        (0 until 9).foreach { i =>
                            if (ic.ids(i) != r2(i)) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (valid) {
                        ic.ids(9) = r2(9);
                        ic.nums(9) = r2(10);
                        if (TerraFrame.getTOOLDURS().get(r2(9)) != null)
                            ic.durs(9) = TerraFrame.getTOOLDURS().get(r2(9));
                        break;
                    }
                }
            }
            val r3: ArrayList[Short] = new ArrayList[Short](11);
            breakable {
                RECIPES.get("shapeless").foreach { r2 =>
                    valid = true;
                    r3.clear();
                    (0 until r2.length - 2).foreach { j =>
                        r3.add(r2(j));
                    }
                    breakable {
                        (0 until 9).foreach { j =>
                            n = r3.indexOf(ic.ids(j));
                            if (n == -1) {
                                valid = false;
                                break;
                            }
                            else {
                                r3.remove(n);
                            }
                        }
                    }
                    if (valid) {
                        ic.ids(9) = r2(r2.length - 2);
                        ic.nums(9) = r2(r2.length - 1);
                        if (TerraFrame.getTOOLDURS().get(r2(r2.length - 2)) != null)
                            ic.durs(9) = TerraFrame.getTOOLDURS().get(r2.length - 2);
                        break;
                    }
                }
            }
            (4*40 until 4*40+40).foreach { x =>
                (1*40 until 1*40+40).foreach { y =>
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                4*40, 1*40, 4*40+40, 1*40+40,
                0, 0, 40, 40,
                null);
            if (ic.ids(9) != 0) {
                width = TerraFrame.getItemImgs().get(ic.ids(9)).getWidth();
                height = TerraFrame.getItemImgs().get(ic.ids(9)).getHeight();
                    g2.drawImage(TerraFrame.getItemImgs().get(ic.ids(9)),
                        4*40+8+((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, 1*40+8+((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt, 4*40+32-((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, 1*40+32-((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt,
                        0, 0, width, height,
                        null);

                if (ic.nums(9) > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums(9) + " ", 4*40+9, 1*40+34);
                }
            }
        }
        if (ic.`type`.equals("wooden_chest") || ic.`type`.equals("stone_chest") ||
            ic.`type`.equals("copper_chest") || ic.`type`.equals("iron_chest") ||
            ic.`type`.equals("silver_chest") || ic.`type`.equals("gold_chest") ||
            ic.`type`.equals("zinc_chest") || ic.`type`.equals("rhymestone_chest") ||
            ic.`type`.equals("obdurite_chest")) {
            py = (i/CX).toInt;
            px = i-(py*CX);
            (px*46 until px*46+40).foreach { x =>
                (py*46 until py*46+40).foreach { y =>
                    ic.image.setRGB(x, y, 9539985);
                }
            }
            g2 = ic.image.createGraphics();
            g2.drawImage(box,
                px*46, py*46, px*46+40, py*46+40,
                0, 0, 40, 40,
                null);
            if (ic.ids(i) != 0) {
                width = TerraFrame.getItemImgs().get(ic.ids(i)).getWidth();
                height = TerraFrame.getItemImgs().get(ic.ids(i)).getHeight();
                g2.drawImage(TerraFrame.getItemImgs().get(ic.ids(i)),
                    px*46+8+((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*46+8+((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt, px*46+32-((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt, py*46+32-((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt,
                    0, 0, width, height,
                    null);

                if (ic.nums(i) > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.nums(i) + " ", px*46+9, py*46+34);
                }
            }
        }
        if (ic.`type`.equals("furnace")) {
            if (i == -1) {
                (0 until 5).foreach { y =>
                    (0 until (ic.FUELP*38).toInt).foreach { x =>
                        ic.image.setRGB(x+1, y+51, new Color(255, 0, 0).getRGB());
                    }
                    ((ic.FUELP*38).toInt until 38).foreach { x =>
                        ic.image.setRGB(x+1, y+51, new Color(145, 145, 145).getRGB());
                    }
                }
                (0 until 5).foreach { x =>
                    (0 until (ic.SMELTP*38).toInt).foreach { y =>
                        ic.image.setRGB(x+40, y+1, new Color(255, 0, 0).getRGB());
                    }
                    ((ic.SMELTP*38).toInt until 38).foreach { y =>
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
                ((fpx*40).toInt until (fpx*40+40).toInt).foreach { x =>
                    ((fpy*40).toInt until (fpy*40+40).toInt).foreach { y =>
                        ic.image.setRGB(x, y, 9539985);
                    }
                }
                g2 = ic.image.createGraphics();
                g2.drawImage(box,
                    (fpx*40).toInt, (fpy*40).toInt, (fpx*40+40).toInt, (fpy*40+40).toInt,
                    0, 0, 40, 40,
                    null);
                if (ic.ids(i) != 0) {
                    width = TerraFrame.getItemImgs().get(ic.ids(i)).getWidth();
                    height = TerraFrame.getItemImgs().get(ic.ids(i)).getHeight();
                    g2.drawImage(TerraFrame.getItemImgs().get(ic.ids(i)),
                        (fpx*40+8+((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt).toInt, (fpy*40+8+((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt).toInt, (fpx*40+32-((24-12.toDouble/max(width, height, 12)*width*2)/2).toInt).toInt, (fpy*40+32-((24-12.toDouble/max(width, height, 12)*height*2)/2).toInt).toInt,
                        0, 0, width, height,
                        null);

                    if (ic.nums(i) > 1) {
                        g2.setFont(font);
                        g2.setColor(Color.WHITE);
                        g2.drawString(ic.nums(i) + " ", (fpx*40+9).toInt, (fpy*40+34).toInt);
                    }
                }
            }
        }
    }

    def useRecipeWorkbench(ic: ItemCollection): Unit = {
        RECIPES.get("workbench").foreach { r2 =>
            valid = true;
            import scala.util.control.Breaks._
            breakable {
                (0 until 9).foreach { i =>
                    if (ic.ids(i) != r2(i)) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid) {
                (0 until 9).foreach { i =>
                    removeLocationIC(ic, i, 1.toShort);
                    updateIC(ic, i);
                }
            }
        }
        val r3: ArrayList[Short] = new ArrayList[Short](11);
        import scala.util.control.Breaks._
        breakable {
            RECIPES.get("shapeless").foreach { r2 =>
                valid = true;
                r3.clear();
                (0 until r2.length - 2).foreach { k =>
                    r3.add(r2(k));
                }
                breakable {
                    (0 until 9).foreach { k =>
                        n = r3.indexOf(ic.ids(k));
                        if (n == -1) {
                            valid = false;
                            break;
                        }
                        else {
                            r3.remove(n);
                        }
                    }
                }
                if (valid) {
                    r3.clear();
                    (0 until r2.length - 2).foreach { k =>
                        r3.add(r2(k));
                    }
                    (0 until 9).foreach { k =>
                        n = r3.indexOf(ic.ids(k));
                        r3.remove(n);
                        removeLocationIC(ic, k, 1.toShort);
                        updateIC(ic, k);
                    }
                    break;
                }
            }
        }
    }

    def useRecipeCIC(ic: ItemCollection): Unit = {
        RECIPES.get("cic").foreach { r2 =>
            valid = true;
            import scala.util.control.Breaks._
            breakable {
                (0 until 4).foreach { i =>
                    if (ic.ids(i) != r2(i)) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid) {
                (0 until 4).foreach { i =>
                    removeLocationIC(ic, i, 1.toShort);
                    updateIC(ic, i);
                }
            }
        }
        val r3: ArrayList[Short] = new ArrayList[Short](6);
        import scala.util.control.Breaks._
        breakable {
            RECIPES.get("shapeless_cic").foreach { r2 =>
                valid = true;
                r3.clear();
                (0 until r2.length - 2).foreach { k =>
                    r3.add(r2(k));
                }
                breakable {
                    (0 until 4).foreach { k =>
                        n = r3.indexOf(ic.ids(k));
                        if (n == -1) {
                            valid = false;
                            break;
                        }
                        else {
                            r3.remove(n);
                        }
                    }
                }
                if (valid) {
                    r3.clear();
                    (0 until r2.length - 2).foreach { k =>
                        r3.add(r2(k));
                    }
                    (0 until 4).foreach { k =>
                        n = r3.indexOf(ic.ids(k));
                        r3.remove(n);
                        removeLocationIC(ic, k, 1.toShort);
                        updateIC(ic, k);
                    }
                    break;
                }
            }
        }
    }


}
