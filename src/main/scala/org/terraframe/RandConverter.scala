package org.terraframe

import java.awt.image._
import java.io._
import java.util._
import javax.imageio.ImageIO
import Images.loadImage

object  RandConverter {

    val BLOCKSIZE: Int = 16
    val IMAGESIZE: Int = 8

    val dirs: Array[String] = Array("center", "tdown_both", "tdown_cw", "tdown_ccw",
        "tdown", "tup_both", "tup_cw", "tup_ccw",
        "tup", "leftright", "tright_both", "tright_cw",
        "tright_ccw", "tright", "upleftdiag", "upleft",
        "downleftdiag", "downleft", "left", "tleft_both",
        "tleft_cw", "tleft_ccw", "tleft", "uprightdiag",
        "upright", "downrightdiag", "downright", "right",
        "updown", "up", "down", "single")

    def main(args: Array[String]): Unit = {
        System.out.print("[D]uplicate, [R]andomize, or [O]utline? ")
        val option: Char = new Scanner(System.in).next().charAt(0)
        import scala.util.control.Breaks._
        breakable {
            while (true) {
                System.out.print("Generate new textures for: ")
                val name: String = (new Scanner(System.in)).nextLine()
                if (name.equals("exit")) {
                    break
                }
                if (option == 'O') {
                    (0 until dirs.length).foreach { k =>
                        (2 until 6).foreach { j =>
                            val texture: BufferedImage = loadImage("outlines/" + name + "/" + dirs(k) + "1.png")
                            val coords: Array2D[Int] = Array.ofDim(IMAGESIZE * IMAGESIZE, 2)
                            (0 until 7).foreach { i =>
                                (0 until IMAGESIZE).foreach { x =>
                                    (0 until IMAGESIZE).foreach { y =>
                                        coords(x * IMAGESIZE + y)(0) = x
                                        coords(x * IMAGESIZE + y)(1) = y
                                    }
                                }
                                val result = new BufferedImage(IMAGESIZE, IMAGESIZE, BufferedImage.TYPE_INT_ARGB)
                                (0 until IMAGESIZE).foreach { x =>
                                    (0 until IMAGESIZE).foreach { y =>
                                        val xy = coords(x * IMAGESIZE + y)
                                        result.setRGB(xy(0), xy(1), texture.getRGB(x, y))
                                    }
                                }
                                try {
                                    ImageIO.write(result, "png", new File("outlines/" + name + "/" + dirs(k) + j + ".png"))
                                }
                                catch {
                                    case _: IOException => println("Error in writing file.")
                                }
                            }
                        }
                    }
                }
                else {
                    val texture: BufferedImage = loadImage("blocks/" + name + "/texture1.png")
                    val coords: Array2D[Int] = Array.ofDim(IMAGESIZE * IMAGESIZE, 2)
                    (0 until 7).foreach { i =>
                        (0 until IMAGESIZE).foreach { x =>
                            (0 until IMAGESIZE).foreach { y =>
                                coords(x * IMAGESIZE + y)(0) = x
                                coords(x * IMAGESIZE + y)(1) = y
                            }
                        }
                        if (option == 'R') {
                            Collections.shuffle(Arrays.asList(coords))
                        }
                        val result = new BufferedImage(IMAGESIZE, IMAGESIZE, BufferedImage.TYPE_INT_ARGB)
                        (0 until IMAGESIZE).foreach { x =>
                            (0 until IMAGESIZE).foreach { y =>
                                val xy = coords(x * IMAGESIZE + y)
                                result.setRGB(xy(0), xy(1), texture.getRGB(x, y))
                            }
                        }
                        try {
                            ImageIO.write(result, "png", new File("blocks/" + name + "/texture" + (i + 2) + ".png"))
                        }
                        catch {
                            case _: IOException => println("Error in writing file.")
                        }
                    }
                }
            }
        }
    }
}
