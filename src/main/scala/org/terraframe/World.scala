package org.terraframe

import java.util.Random

object World {
  var x, y: Int = 0
  var x2, y2, i, j, n, xpos, ypos, xpos2, ypos2: Int = _
  var f: Double = _
  var `type`: Short = _

  var random: Random = _

  var coordlist:Array2D[Boolean] = _
  var coordlist2:Array2D[Boolean] = _

  def generateChunk(cx: Int, cy: Int, random: Random): Array[AnyRef] = {
    val size: Int = TerraFrame.CHUNKBLOCKS
    val blocks: Array3D[Int] = Array.ofDim(3,size,size)
    val blockds: Array3D[Byte] = Array.ofDim(3,size,size)
    val blockdns: Array2D[Byte] = Array.ofDim(size,size)
    val blockbgs: Array2D[Byte] = Array.ofDim(size,size)
    val blockts: Array2D[Byte] = Array.ofDim(size,size)
    val lights: Array2D[Float] = Array.ofDim(size,size)
    val power: Array3D[Float] = Array.ofDim(3,size,size)
    val lsources: Array2D[Boolean] = Array.ofDim(size,size)
    val zqn: Array2D[Byte] = Array.ofDim(size,size)
    val pzqn: Array3D[Byte] = Array.ofDim(3,size,size)
    val arbprd: Array3D[Boolean] = Array.ofDim(3,size,size)
    val wcnct: Array2D[Boolean] = Array.ofDim(size,size)
    val drawn: Array2D[Boolean] = Array.ofDim(size,size)
    val rdrawn: Array2D[Boolean] = Array.ofDim(size,size)
    val ldrawn: Array2D[Boolean] = Array.ofDim(size,size)
    (0 until size).foreach { y =>
      (0 until size).foreach { x =>
        (0 until 3).foreach { l =>
        if (l == 1 && cy*size+y >= PerlinNoise.perlinNoise((cx*size+x) / 10.0, 0.5, 0) * 30 + 50) {
          blocks(l)(y)(x) = 1 // dirt
        }
        else {
          blocks(l)(y)(x) = 0
        }
        arbprd(l)(y)(x) = false
        power(l)(y)(x) = 0.toFloat
      }
        blockdns(y)(x) = random.nextInt(5).toByte
        blockbgs(y)(x) = 0
        blockts(y)(x) = random.nextInt(8).toByte
        lights(y)(x) = 19.toFloat
        lsources(y)(x) = false
        wcnct(y)(x) = false
        drawn(y)(x) = false
        rdrawn(y)(x) = false
        ldrawn(y)(x) = false
        blockds(0)(y)(x) = 0.toByte
        blockds(2)(y)(x) = 0.toByte
      }
    }
    blockds(1) = generateOutlines(blocks(1))
    val rv: Array[AnyRef] = Array(blocks, blockds, blockdns, blockbgs, blockts, lights, power, lsources, zqn, pzqn, arbprd, wcnct, drawn, rdrawn, ldrawn)
    return rv
  }

  def generateOutlines(blocks: Array2D[Int]): Array2D[Byte] = {
    return generate2(blocks, false)
  }


  /*    public static generate: Array[AnyRef](width: Int, height: Int, sealevel: Int, stonelevel: Int, TerraFrame inst) {
        pmsg("Generating new world...")
        random = TerraFrame.getRandom()
        pmsg("-> Creating blocks...")
        blocks: Array2D[Int] = new Int(height)(width)
        (0 until height).foreach { y =>
            (0 until width).foreach { x =>
                blocks(y)(x) = 0
            }
        }
        blocksB: Array2D[Int] = new Int(height)(width)
        (0 until height).foreach { y =>
            (0 until width).foreach { x =>
                blocksB(y)(x) = 0
            }
        }
        blocksF: Array2D[Int] = new Int(height)(width)
        (0 until height).foreach { y =>
            (0 until width).foreach { x =>
                blocksF(y)(x) = 0
            }
        }
        biome: String = "plains"
        bCount: Int
        rnum: Int
        biomes: Array[String] = new String(width)
        verify: Array[Boolean] = {false, false, false, false, false}
        bvar: Boolean = false
        while (true) {
            bCount = 0
            (0 until 5).foreach { i =>
                verify(i) = false
            }
            bvar = true
            (0 until width).foreach { x =>
                if (bCount == 0) {
                    rnum = random.nextInt(100)
                    if (rnum >= 0 && rnum < 50) {
                        biome = "plains" // 50%
                        if (x < width - 300) verify(0) = true
                    }
                    if (rnum >= 50 && rnum < 65) {
                        biome = "desert" // 15%
                        if (x < width - 300) verify(1) = true
                    }
                    if (rnum >= 65 && rnum < 80) {
                        biome = "jungle" // 15%
                        if (x < width - 300) verify(2) = true
                    }
                    if (rnum >= 80 && rnum < 90) {
                        biome = "swamp" // 10%
                        if (x < width - 300) verify(3) = true
                    }
                    if (rnum >= 90 && rnum < 100) {
                        biome = "frost" // 10%
                        if (x < width - 300) verify(4) = true
                    }
                    bCount = random.nextInt(200) + 100
                }
                biomes(x) = biome
                bCount -= 1
            }
            (0 until 5).foreach { i =>
                if (!verify(i)) {
                    bvar = false
                }
            }
            if (bvar || width < 1500) {
                break
            }
        }

        terrain: Array[Double] = new Double(width)
        (0 until width).foreach { x =>
            terrain(x) = PerlinNoise.perlinNoise(x / 10.0, 0.5, 0) * 30 + sealevel
        }
        stonelayer: Array[Double] = new Double(width)
        (0 until width).foreach { x =>
            stonelayer(x) = PerlinNoise.perlinNoise(x / 10.0, 0.5, 0) * 30 + stonelevel
        }
        (0 until width).foreach { x =>
            (0 until height).foreach { y =>
                if (y >= (int)terrain(x)) {
                    blocks(y)(x) = 1 // dirt
                    if (biomes(x) == "desert") {
                        if (y <= terrain(x) + random.nextInt(5) + 30) {
                            blocks(y)(x) = 45 // sand
                        }
                    }
                    if (biomes(x) == "swamp") {
                        if (y <= terrain(x) + random.nextInt(5) + 35) {
                            blocks(y)(x) = 75 // mud
                        }
                    }
                    if (biomes(x) == "frost") {
                        if (y <= terrain(x) + random.nextInt(5) + 35) {
                            blocks(y)(x) = 46 // snow
                        }
                    }
                }
            }
        }
        (1 until width-1).foreach { x =>
            (1 until height-1).foreach { y =>
                if (biomes(x) == "plains") {
                    if (blocks(y)(x) == 1) { // dirt
                        if (blocks(y-1)(x) == 0 || blocks(y+1)(x) == 0 ||
                            blocks(y)(x-1) == 0 || blocks(y)(x+1) == 0 ||
                            blocks(y-1)(x-1) == 0 || blocks(y+1)(x+1) == 0 ||
                            blocks(y-1)(x+1) == 0 || blocks(y+1)(x-1) == 0) {
                            blocks(y)(x) = 72 // grass
                        }
                    }
                }
                if (biomes(x) == "jungle") {
                    if (blocks(y)(x) == 1) { // dirt
                        if (blocks(y-1)(x) == 0 || blocks(y+1)(x) == 0 ||
                            blocks(y)(x-1) == 0 || blocks(y)(x+1) == 0 ||
                            blocks(y-1)(x-1) == 0 || blocks(y+1)(x+1) == 0 ||
                            blocks(y-1)(x+1) == 0 || blocks(y+1)(x-1) == 0) {
                            blocks(y)(x) = 73 // jungle_grass
                        }
                    }
                }
                if (biomes(x) == "swamp") {
                    if (blocks(y)(x) == 75) { // mud
                        if (blocks(y-1)(x) == 0 || blocks(y+1)(x) == 0 ||
                            blocks(y)(x-1) == 0 || blocks(y)(x+1) == 0 ||
                            blocks(y-1)(x-1) == 0 || blocks(y+1)(x+1) == 0 ||
                            blocks(y-1)(x+1) == 0 || blocks(y+1)(x-1) == 0) {
                            blocks(y)(x) = 74 // swamp_grass
                        }
                    }
                }
            }
        }
        (0 until width).foreach { x =>
            (0 until height).foreach { y =>
                if (y >= stonelayer(x)) {
                    blocks(y)(x) = 2 // stone
                }
            }
        }
        pmsg("-> Adding underground deposits, caverns, and floating islands...")
        v: Int = (int)(height*0.04)
        (0 until width*height/2000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(stonelevel - sealevel) + sealevel
            coordlist = blob(random.nextInt(7) + 5, random.nextInt(7) + 5, 1)
            (0 until coordlist(0).length).foreach { y =>
                (0 until coordlist.length).foreach { x =>
                    if (coordlist(x)(y)) {
                        if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                            blocks(ypos+y)(xpos+x) = 2 // stone
                        }
                    }
                }
            }
        }
        (0 until width*height/2000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height - stonelevel - v) + stonelevel
            coordlist = blob(random.nextInt(7) + 5, random.nextInt(7) + 5, 1)
            (0 until coordlist(0).length).foreach { y =>
                (0 until coordlist.length).foreach { x =>
                    if (coordlist(x)(y)) {
                        if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                            blocks(ypos+y)(xpos+x) = 1 // dirt
                        }
                    }
                }
            }
        }
        (0 until width*height/2000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(stonelevel - sealevel) + sealevel
            if (biomes(xpos).equals("swamp")) {
                coordlist = blob(random.nextInt(7) + 5, random.nextInt(7) + 5, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 88 // clay
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/400).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (true) {
                coordlist = blob(random.nextInt(2) + 2, random.nextInt(2) + 2, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                        if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 18 // coal
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/1500).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.45) {
                coordlist = blob(random.nextInt(2) + 2, random.nextInt(2) + 2, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 19 // lumenstone
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/3500).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (true) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 3 // copper_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/4000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (true) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 4 // iron_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/5500).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.25) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 5 // silver_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/7000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.4) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 6 // gold_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/8500).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.55) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 31 // zinc_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/10500).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.7) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 32 // rhymestone_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/12500).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.85) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 33 // obdurite_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/6000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.2) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 34 // aluminum_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/8000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.45) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 35 // lead_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/15000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.65) {
                coordlist = blob(random.nextInt(3) + 2, random.nextInt(3) + 2, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 36 // uranium_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/11000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.4) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 37 // zythium_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/13000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (ypos >= height*0.6) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks(ypos+y)(xpos+x) != 0) {
                                blocks(ypos+y)(xpos+x) = 39 // silicon_ore
                            }
                        }
                    }
                }
            }
        }
        (0 until width*height/20000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            if (blocks(ypos)(xpos) != 0) {
                blocks(ypos)(xpos) = 40 // unobtainium_ore
            }
        }
        (0 until width*height/1500).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(height-sealevel-v)+sealevel
            coordlist = blob(random.nextInt(15) + 7, random.nextInt(13) + 7, 0.5)
            (0 until coordlist(0).length).foreach { y =>
                (0 until coordlist.length).foreach { x =>
                    if (coordlist(x)(y)) {
                        if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height) {
                            blocks(ypos+y)(xpos+x) = 0 // air
                        }
                    }
                }
            }
        }
        (0 until width*height/672000).foreach { i =>
            xpos = random.nextInt(width)
            ypos = random.nextInt(sealevel-(int)(height*0.03))
            coordlist = blob(random.nextInt(25) + 10, random.nextInt(20) + 10, 0.5)
            (0 until coordlist(0).length).foreach { y =>
                (0 until coordlist.length).foreach { x =>
                    if (coordlist(x)(y)) {
                        if (ypos+y > 0 && ypos+y < height) {
                            blocks(ypos+y)[mod(xpos+x,width)] = 91 // dirt_trans
                        }
                    }
                }
            }
            (0 until 30).foreach { j =>
                xpos2 = xpos+random.nextInt(70)
                ypos2 = ypos+random.nextInt(60)
                coordlist = blob(random.nextInt(4) + 3, random.nextInt(4) + 3, 1)
                (0 until coordlist(0).length).foreach { y =>
                    (0 until coordlist.length).foreach { x =>
                        if (coordlist(x)(y)) {
                            if (ypos2+y > 0 && ypos2+y < height && blocks(ypos2+y)[mod(xpos2+x,width)] == 91) { // dirt_trans
                                blocks(ypos2+y)[mod(xpos2+x,width)] = 92 // magnetite_ore_trans
                            }
                        }
                    }
                }
            }
        }
        (0 until height).foreach { y =>
            (0 until width).foreach { x =>
                if (blocks(y)(x) == 91 && TerraFrame.hasOpenSpace(x, y, blocks)) {
                    blocks(y)(x) = 93 // grass_trans
                }
            }
        }
        pmsg("-> Adding vegetation...")
        (0 until width).foreach { x =>
            for (y=(int)(terrain(x)+35) y>5 y--) {
                if (blocks(y)(x) == 45 && blocks(y-1)(x) == 45 && blocks(y-2)(x) == 45 && blocks(y-3)(x) == 45 && blocks(y+1)(x) != 45) { // sand
                    (0 until random.nextInt(5)+5).foreach { i =>
                        blocks(y+i)(x) = 76 // sandstone
                    }
                    break
                }
            }
        }
        (0 until width*height*24).foreach { i =>
            x = random.nextInt(width)
            y = random.nextInt(height)
            if (x >= 1 && x < width-1 && y >= 1 && y < height-1) {
                if (blocks(y)(x) == 1 && TerraFrame.hasOpenSpace(x, y, blocks) && blocks[y+random.nextInt(3)-1][x+random.nextInt(3)-1] == 72) {
                    blocks(y)(x) = 72
                }
                if (blocks(y)(x) == 1 && TerraFrame.hasOpenSpace(x, y, blocks) && blocks[y+random.nextInt(3)-1][x+random.nextInt(3)-1] == 73) {
                    blocks(y)(x) = 73
                }
                if (blocks(y)(x) == 75 && TerraFrame.hasOpenSpace(x, y, blocks) && blocks[y+random.nextInt(3)-1][x+random.nextInt(3)-1] == 74) {
                    blocks(y)(x) = 74
                }
            }
        }

        (0 until width).foreach { x =>
            if (biomes(x) == "plains") {
                (2 until terrain(x)+10).foreach { y =>
                    if (blocks(y)(x) == 72 && blocks(y-1)(x) == 0 && blocks(y-2)(x) == 0 && random.nextInt(10) == 0) { // grass
                        i = 1
                        blocksB(y)(x) = 30
                        while (true) {
                            blocks(y-i)(x) = 15 // tree
                            i += 1
                            if (i >= 10 && random.nextInt(3) == 0 || y-i-1 < 0 || y - i == 0 || blocks(y-i-1)(x) != 0) {
                                n = random.nextInt(2) + 3
                                coordlist = blob(n, n, 0.5)
                                (0 until coordlist(0).length).foreach { y2 =>
                                    (0 until coordlist.length).foreach { x2 =>
                                        if (coordlist(x2)(y2)) {
                                            if (x+x2-coordlist.length/2 > 0 && x+x2-coordlist.length/2 < width && y-i+y2-coordlist(0).length/2 > 0 && y-i+y2-coordlist(0).length/2 < height) {
                                                blocksB[y-i+y2-coordlist(0).length/2][x+x2-coordlist.length/2] = 16 // leaves
                                                blocksF[y-i+y2-coordlist(0).length/2][x+x2-coordlist.length/2] = 16 // leaves
                                            }
                                        }
                                    }
                                }
                                break
                            }
                        }
                        break
                    }
                }
            }
            if (biomes(x) == "desert") {
                (1 until terrain(x)+10).foreach { y =>
                    if (blocks(y)(x) == 45 && blocks(y-1)(x) == 0 && random.nextInt(30) == 0) { // dirt
                        i = 1
                        blocksB(y)(x) = 30
                        while (true) {
                            blocks(y-i)(x) = 15 // tree
                            i += 1
                            if (i >= 10 && random.nextInt(3) == 0 || y-i-1 < 0 || y - i == 0 || blocks(y-i-1)(x) != 0) {
                                break
                            }
                        }
                        break
                    }
                }
            }
            if (biomes(x) == "jungle") {
                (1 until terrain(x)+10).foreach { y =>
                    if (blocks(y)(x) == 73 && blocks(y-1)(x) == 0 && random.nextInt(3) == 0) { // jungle_grass
                        i = 1
                        blocksB(y)(x) = 30
                        while (true) {
                            blocks(y-i)(x) = 15 // tree
                            i += 1
                            if (i >= 10 && random.nextInt(4) == 0 || y-i-1 < 0 || y - i == 0 || blocks(y-i-1)(x) != 0) {
                                n = random.nextInt(2) + 3
                                coordlist = blob(n, n, 0.5)
                                (0 until coordlist(0).length).foreach { y2 =>
                                    (0 until coordlist.length).foreach { x2 =>
                                        if (coordlist(x2)(y2)) {
                                            if (x+x2-coordlist.length/2 > 0 && x+x2-coordlist.length/2 < width && y-i+y2-coordlist(0).length/2 > 0 && y-i+y2-coordlist(0).length/2 < height) {
                                                blocksB[y-i+y2-coordlist(0).length/2][x+x2-coordlist.length/2] = 16 // leaves
                                                blocksF[y-i+y2-coordlist(0).length/2][x+x2-coordlist.length/2] = 16 // leaves
                                            }
                                        }
                                    }
                                }
                                break
                            }
                        }
                        break
                    }
                }
            }
            if (biomes(x) == "swamp") {
                (1 until terrain(x)+10).foreach { y =>
                    if (blocks(y)(x) == 74 && blocks(y-1)(x) == 0 && random.nextInt(10) == 0) { // swamp_grass
                        i = 1
                        blocksB(y)(x) = 30
                        while (true) {
                            blocks(y-i)(x) = 15 // tree
                            i += 1
                            if (i >= 10 && random.nextInt(3) == 0 || y-i-1 < 0 || y - i == 0 || blocks(y-i-1)(x) != 0) {
                                n = random.nextInt(2) + 3
                                coordlist = blob(n, n, 0.5)
                                (0 until coordlist(0).length).foreach { y2 =>
                                    (0 until coordlist.length).foreach { x2 =>
                                        if (coordlist(x2)(y2)) {
                                            if (x+x2-coordlist.length/2 > 0 && x+x2-coordlist.length/2 < width && y-i+y2-coordlist(0).length/2 > 0 && y-i+y2-coordlist(0).length/2 < height) {
                                                blocksB[y-i+y2-coordlist(0).length/2][x+x2-coordlist.length/2] = 16 // leaves
                                                blocksF[y-i+y2-coordlist(0).length/2][x+x2-coordlist.length/2] = 16 // leaves
                                            }
                                        }
                                    }
                                }
                                break
                            }
                        }
                        break
                    }
                }
            }
            if (biomes(x) == "frost") {
                (1 until terrain(x)+10).foreach { y =>
                    if (blocks(y)(x) == 46 && blocks(y-1)(x) == 0 && random.nextInt(10) == 0) { // dirt
                        i = 1
                        blocksB(y)(x) = 30
                        while (true) {
                            blocks(y-i)(x) = 15 // tree
                            i += 1
                            if (i >= 10 && random.nextInt(3) == 0 || y-i-1 < 0 || y - i == 0 || blocks(y-i-1)(x) != 0) {
                                break
                            }
                        }
                        break
                    }
                }
            }
        }
        ((int)(height*0.98) until height).foreach { y =>
            (0 until width).foreach { x =>
                if (random.nextInt((int)(height*0.02))+height*0.98 <= y) {
                    blocks(y)(x) = 0
                }
            }
        }
        (0 until height-1).foreach { y =>
            (0 until width).foreach { x =>
                if (blocks(y)(x) == 0) {
                    if (blocks(y+1)(x) == 72 && random.nextInt(100) == 0) {
                        blocks(y)(x) = 50
                    }
                    if (blocks(y+1)(x) == 72 && random.nextInt(100) == 0) {
                        blocks(y)(x) = 53
                    }
                    if (blocks(y+1)(x) == 45 && random.nextInt(100) == 0) {
                        blocks(y)(x) = 56
                    }
                    if (blocks(y+1)(x) == 73 && random.nextInt(100) == 0) {
                        blocks(y)(x) = 59
                    }
                    if (blocks(y+1)(x) == 46 && random.nextInt(100) == 0) {
                        blocks(y)(x) = 62
                    }
                    if (blocks(y+1)(x) == 2 && random.nextInt(400) == 0 && y >= height * 0.2) {
                        blocks(y)(x) = 65
                    }
                    if (blocks(y+1)(x) == 93 && random.nextInt(20) == 0 && y <= height * 0.05) {
                        blocks(y)(x) = 68
                    }
                    if (blocks(y+1)(x) == 2 && random.nextInt(300) == 0 && y >= height * 0.98) {
                        blocks(y)(x) = 71
                    }
                    if (blocks(y+1)(x) == 74 && random.nextInt(100) == 0) {
                        blocks(y)(x) = 79
                    }
                }
            }
        }

        blocks_rv: Array3D[Int] = {blocksB, blocks, blocksF}

        rv: Array[AnyRef] = {blocks_rv, new DoubleContainer(terrain), new DoubleContainer(stonelayer)}
        return rv
    }
*/

  def generate2(blocks: Array2D[Int], msg: Boolean): Array2D[Byte] = {
    var x: Int = 0
    val width: Int = blocks(0).length
    val height: Int = blocks.length
    if (msg) {
      pmsg("-> Creating outlines...")
    }
    val blockds: Array2D[Byte] = Array.ofDim(height,width)
    //val blockdns: Array2D[Byte] = Array.ofDim(height,width)
    var left, right, up, down, upleft, upright, downleft, downright: Boolean = false
    (0 until height).foreach { y =>
      (0 until width).foreach { x2 =>
        x = mod(x2,width)
        if (y > 0 && y < height-1 && blocks(y)(x) != 0) {
          left = connect(x-1, y, x, y, blocks)
          right = connect(x+1, y, x, y, blocks)
          up = connect(x, y-1, x, y, blocks)
          down = connect(x, y+1, x, y, blocks)
          upleft = connect(x-1, y-1, x, y, blocks)
          upright = connect(x+1, y-1, x, y, blocks)
          downleft = connect(x-1, y+1, x, y, blocks)
          downright = connect(x+1, y+1, x, y, blocks)
          if (left) {
            if (right) {
              if (up) {
                if (down) {
                  blockds(y)(x) = 0
                }
                else {
                  if (upleft) {
                    if (upright) {
                      blockds(y)(x) = 1
                    }
                    else {
                      blockds(y)(x) = 2
                    }
                  }
                  else {
                    if (upright) {
                      blockds(y)(x) = 3
                    }
                    else {
                      blockds(y)(x) = 4
                    }
                  }
                }
              }
              else {
                if (down) {
                  if (downright) {
                    if (downleft) {
                      blockds(y)(x) = 5
                    }
                    else {
                      blockds(y)(x) = 6
                    }
                  }
                  else {
                    if (downleft) {
                      blockds(y)(x) = 7
                    }
                    else {
                      blockds(y)(x) = 8
                    }
                  }
                }
                else {
                  blockds(y)(x) = 9
                }
              }
            }
            else {
              if (up) {
                if (down) {
                  if (downleft) {
                    if (upleft) {
                      blockds(y)(x) = 10
                    }
                    else {
                      blockds(y)(x) = 11
                    }
                  }
                  else {
                    if (upleft) {
                      blockds(y)(x) = 12
                    }
                    else {
                      blockds(y)(x) = 13
                    }
                  }
                }
                else {
                  if (upleft) {
                    blockds(y)(x) = 14
                  }
                  else {
                    blockds(y)(x) = 15
                  }
                }
              }
              else {
                if (down) {
                  if (downleft) {
                    blockds(y)(x) = 16
                  }
                  else {
                    blockds(y)(x) = 17
                  }
                }
                else {
                  blockds(y)(x) = 18
                }
              }
            }
          }
          else {
            if (right) {
              if (up) {
                if (down) {
                  if (upright) {
                    if (downright) {
                      blockds(y)(x) = 19
                    }
                    else {
                      blockds(y)(x) = 20
                    }
                  }
                  else {
                    if (downright) {
                      blockds(y)(x) = 21
                    }
                    else {
                      blockds(y)(x) = 22
                    }
                  }
                }
                else {
                  if (upright) {
                    blockds(y)(x) = 23
                  }
                  else {
                    blockds(y)(x) = 24
                  }
                }
              }
              else {
                if (down) {
                  if (downright) {
                    blockds(y)(x) = 25
                  }
                  else {
                    blockds(y)(x) = 26
                  }
                }
                else {
                  blockds(y)(x) = 27
                }
              }
            }
            else {
              if (up) {
                if (down) {
                  blockds(y)(x) = 28
                }
                else {
                  blockds(y)(x) = 29
                }
              }
              else {
                if (down) {
                  blockds(y)(x) = 30
                }
                else {
                  blockds(y)(x) = 31
                }
              }
            }
          }
        }
      }
    }
    return blockds
  }

  def generate2b(blocks: Array2D[Int], blockds: Array2D[Byte], xpos: Int, ypos: Int): Array2D[Byte] = {
    var x: Int = 0
    val width: Int = blocks(0).length
    val height: Int = blocks.length
    var left, right, up, down, upleft, upright, downleft, downright: Boolean = false
    (ypos-1 until ypos+2).foreach { y =>
      (xpos-1 until xpos+2).foreach { x2 =>
        x = mod(x2,width)
        if (y > 0 && y < height-1 && blocks(y)(x) != 0) {
          left = connect(x-1, y, x, y, blocks)
          right = connect(x+1, y, x, y, blocks)
          up = connect(x, y-1, x, y, blocks)
          down = connect(x, y+1, x, y, blocks)
          upleft = connect(x-1, y-1, x, y, blocks)
          upright = connect(x+1, y-1, x, y, blocks)
          downleft = connect(x-1, y+1, x, y, blocks)
          downright = connect(x+1, y+1, x, y, blocks)
          if (left) {
            if (right) {
              if (up) {
                if (down) {
                  blockds(y)(x) = 0
                }
                else {
                  if (upleft) {
                    if (upright) {
                      blockds(y)(x) = 1
                    }
                    else {
                      blockds(y)(x) = 2
                    }
                  }
                  else {
                    if (upright) {
                      blockds(y)(x) = 3
                    }
                    else {
                      blockds(y)(x) = 4
                    }
                  }
                }
              }
              else {
                if (down) {
                  if (downright) {
                    if (downleft) {
                      blockds(y)(x) = 5
                    }
                    else {
                      blockds(y)(x) = 6
                    }
                  }
                  else {
                    if (downleft) {
                      blockds(y)(x) = 7
                    }
                    else {
                      blockds(y)(x) = 8
                    }
                  }
                }
                else {
                  blockds(y)(x) = 9
                }
              }
            }
            else {
              if (up) {
                if (down) {
                  if (downleft) {
                    if (upleft) {
                      blockds(y)(x) = 10
                    }
                    else {
                      blockds(y)(x) = 11
                    }
                  }
                  else {
                    if (upleft) {
                      blockds(y)(x) = 12
                    }
                    else {
                      blockds(y)(x) = 13
                    }
                  }
                }
                else {
                  if (upleft) {
                    blockds(y)(x) = 14
                  }
                  else {
                    blockds(y)(x) = 15
                  }
                }
              }
              else {
                if (down) {
                  if (downleft) {
                    blockds(y)(x) = 16
                  }
                  else {
                    blockds(y)(x) = 17
                  }
                }
                else {
                  blockds(y)(x) = 18
                }
              }
            }
          }
          else {
            if (right) {
              if (up) {
                if (down) {
                  if (upright) {
                    if (downright) {
                      blockds(y)(x) = 19
                    }
                    else {
                      blockds(y)(x) = 20
                    }
                  }
                  else {
                    if (downright) {
                      blockds(y)(x) = 21
                    }
                    else {
                      blockds(y)(x) = 22
                    }
                  }
                }
                else {
                  if (upright) {
                    blockds(y)(x) = 23
                  }
                  else {
                    blockds(y)(x) = 24
                  }
                }
              }
              else {
                if (down) {
                  if (downright) {
                    blockds(y)(x) = 25
                  }
                  else {
                    blockds(y)(x) = 26
                  }
                }
                else {
                  blockds(y)(x) = 27
                }
              }
            }
            else {
              if (up) {
                if (down) {
                  blockds(y)(x) = 28
                }
                else {
                  blockds(y)(x) = 29
                }
              }
              else {
                if (down) {
                  blockds(y)(x) = 30
                }
                else {
                  blockds(y)(x) = 31
                }
              }
            }
          }
        }
      }
    }
    return blockds
  }

  def connect(b1: Int, b2: Int): Boolean = {
    return (b1 != 0 && b1 == b2 ||
      b1 == 1 && b2 == 72 ||
      b2 == 1 && b1 == 72 ||
      b1 == 1 && b2 == 73 ||
      b2 == 1 && b1 == 73 ||
      b1 == 75 && b2 == 74 ||
      b2 == 75 && b1 == 74 ||
      b1 == 91 && b2 == 93 ||
      b2 == 91 && b1 == 93 ||
      b2 >= 94 && b2 <= 99 && TerraFrame.wirec(b1) ||
      b1 == 103 && b2 == 104 ||
      b2 == 103 && b1 == 104 ||
      b1 == 15 && b2 == 83 ||
      b2 == 83 && b1 == 15)
  }

  def connect(x1: Int, y1: Int, x2: Int, y2: Int, blocks: Array2D[Int]): Boolean = {
    return y1 > 0 && y1 < blocks.length-1 && connect(blocks(y1)(mod(x1,blocks(0).length)), blocks(y2)(mod(x2,blocks(0).length)))
    /*        WIDTH: Int = blocks(0).length
            HEIGHT: Int = blocks.length
            blockcds: Array[Boolean] = TerraFrame.getBLOCKCDS()
            b1: Short = blocks(y1)(x1)
            b2: Short = blocks(y2)(x2)
            if (b1 == b2) return true
            if (!(x1 > 0 && x1 < WIDTH-1 &&
                  y1 > 0 && y1 < HEIGHT-1 &&
                  x2 > 0 && x2 < WIDTH-1 &&
                  y2 > 0 && y2 < HEIGHT-1)) return false
            return (
                b1 == 1 && b2 == 72 && (blocks(2*y2-y1)(2*x2-x1) == 0 || !blockcds(blocks(2*y2-y1)(2*x2-x1)) || blocks(2*y2-y1)(2*x2-x1) == 72) ||
                b2 == 1 && b1 == 72 && (blocks(2*y1-y2)(2*x1-x2) == 0 || !blockcds(blocks(2*y1-y2)(2*x1-x2)) || blocks(2*y1-y2)(2*x1-x2) == 72) ||
                b1 == 1 && b2 == 73 && (blocks(2*y2-y1)(2*x2-x1) == 0 || !blockcds(blocks(2*y2-y1)(2*x2-x1)) || blocks(2*y2-y1)(2*x2-x1) == 73) ||
                b2 == 1 && b1 == 73 && (blocks(2*y1-y2)(2*x1-x2) == 0 || !blockcds(blocks(2*y1-y2)(2*x1-x2)) || blocks(2*y1-y2)(2*x1-x2) == 73) ||
                b1 == 75 && b2 == 74 && (blocks(2*y2-y1)(2*x2-x1) == 0 || !blockcds(blocks(2*y2-y1)(2*x2-x1)) || blocks(2*y2-y1)(2*x2-x1) == 74) ||
                b2 == 75 && b1 == 74 && (blocks(2*y1-y2)(2*x1-x2) == 0 || !blockcds(blocks(2*y1-y2)(2*x1-x2)) || blocks(2*y1-y2)(2*x1-x2) == 74))
    */    }

  def generate3(blocks: Array2D[Int], terrain: Array[Double], stonelayer: Array[Double], inst: TerraFrame): Array2D[Byte] = {
    pmsg("-> Creating background...")
    val width: Int = blocks(0).length
    val height: Int = blocks.length
    val blockbgsi: Array2D[Byte] = Array.ofDim(height, width)
    (0 until height).foreach { y =>
      (0 until width).foreach { x =>
        blockbgsi(y)(x) = 0
        if (y >= terrain(x)) {
          blockbgsi(y)(x) = 8
        }
        if (y >= stonelayer(x)) {
          blockbgsi(y)(x) = 16
        }
      }
    }
    (0 until height).foreach { y =>
      (0 until width).foreach { x =>
        if (!(x == 0 || x == width-1 || y == 0 || y == height-1)) {
          if (blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) == 0) {
            blockbgsi(y)(x) = 0
          }
        }
      }
    }
    val blockbgs: Array2D[Byte] = Array.ofDim(height, width)
    (0 until height).foreach { y =>
      (0 until width).foreach { x =>
        blockbgs(y)(x) = blockbgsi(y)(x)
        if (!(x == 0 || x == width-1 || y == 0 || y == height-1)) {
          if (blockbgsi(y)(x) == 8) {
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) == 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) == 0) {
              blockbgs(y)(x) = 1
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) == 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) == 0) {
              blockbgs(y)(x) = 1
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) == 0) {
              blockbgs(y)(x) = 1
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) == 0) {
              blockbgs(y)(x) = 1
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) == 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 2
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) == 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 2
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 2
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 2
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) == 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 3
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) == 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 3
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 3
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) == 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 3
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) == 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) == 0) {
              blockbgs(y)(x) = 4
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) == 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) == 0) {
              blockbgs(y)(x) = 4
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) == 0) {
              blockbgs(y)(x) = 4
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) == 0) {
              blockbgs(y)(x) = 4
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 5
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 5
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 5
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) == 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 5
            }
            if (blockbgsi(y-1)(x-1) == 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) != 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 6
            }
            if (blockbgsi(y-1)(x-1) != 0 && blockbgsi(y+1)(x-1) != 0 &&
              blockbgsi(y-1)(x+1) == 0 && blockbgsi(y+1)(x+1) != 0 &&
              blockbgsi(y-1)(x) != 0 && blockbgsi(y+1)(x) != 0 &&
              blockbgsi(y)(x-1) != 0 && blockbgsi(y)(x+1) != 0) {
              blockbgs(y)(x) = 7
            }
          }
        }
      }
    }
    (0 until height).foreach { y =>
      (0 until width).foreach { x =>
        if (y == (height * 0.975).toInt) {
          blockbgs(y)(x) = 17
        }
        else if (y > (height * 0.975).toInt) {
          blockbgs(y)(x) = 0
        }
      }
    }
    val rv: Array2D[Byte] = Array.ofDim(height,width)
    (0 until height).foreach { y =>
      (0 until width).foreach { x =>
        rv(y)(x) = blockbgs(y)(x)
      }
    }
    return rv
  }

  def blob(cwidth: Int, cheight: Int, erosion: Double): Array2D[Boolean] = {
    coordlist = Array.ofDim(cwidth*2+1,cheight*2+1)
    (-cwidth to cwidth).foreach { x =>
      (-cheight to cheight).foreach { y =>
        coordlist(x+cwidth)(y+cheight) = Math.pow(x*1.0 / cwidth, 2) + Math.pow(y*1.0 / cheight, 2) < 1 - random.nextDouble() * erosion
      }
    }
    (0 until 2).foreach { n =>
      (-cwidth to cwidth).foreach { x =>
        (-cheight to cheight).foreach { y =>
          if ((x+cwidth <= 0 || !coordlist(x+cwidth-1)(y+cheight)) &&
          (x+cwidth+1 >= coordlist.length || !coordlist(x+cwidth+1)(y+cheight)) &&
          (y+cheight <= 0 || !coordlist(x+cwidth)(y+cheight-1)) &&
          (y+cheight+1 >= coordlist(0).length || !coordlist(x+cwidth)(y+cheight+1))) {
            coordlist(x+cwidth)(y+cheight) = false
          }
        }
      }
      (-cwidth to cwidth).foreach { x =>
        (-cheight to cheight).foreach { y =>
          if ((x+cwidth <= 0 || coordlist(x+cwidth-1)(y+cheight)) &&
          (x+cwidth+1 >= coordlist.length || coordlist(x+cwidth+1)(y+cheight)) &&
          (y+cheight <= 0 || coordlist(x+cwidth)(y+cheight-1)) &&
          (y+cheight+1 >= coordlist(0).length || coordlist(x+cwidth)(y+cheight+1))) {
            coordlist(x+cwidth)(y+cheight) = true
          }
        }
      }
    }
    return coordlist
  }

  def pmsg(msg: String): Unit = {
    TerraFrame.pmsg(msg)
  }

  def mod(a: Int, q: Int): Int = {
    return TerraFrame.mod(a, q)
  }

  def print(text: String): Unit = {
    System.out.println(text)
  }

  def print(text: Int): Unit = {
    System.out.println(text)
  }

  def print(text: Double): Unit = {
    System.out.println(text)
  }

  def print(text: Short): Unit = {
    System.out.println(text)
  }

  def print(text: Boolean): Unit = {
    System.out.println(text)
  }

  def print(text: AnyRef): Unit = {
    System.out.println(text)
  }
}
