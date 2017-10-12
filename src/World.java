import java.lang.Math;
import java.util.Random;

public class World {

    static int x, y;
    static int x2, y2, i, j, n, xpos, ypos, xpos2, ypos2;
    static double f;
    static short type;

    static Random random;

    static boolean[][] coordlist;
    static boolean[][] coordlist2;

    public static Object[] generateChunk(int cx, int cy, Random random) {
        int size = TerrariaClone.CHUNKBLOCKS;
        Integer[][][] blocks = new Integer[3][size][size];
        Byte[][][] blockds = new Byte[3][size][size];
        Byte[][] blockdns = new Byte[size][size];
        Byte[][] blockbgs = new Byte[size][size];
        Byte[][] blockts = new Byte[size][size];
        Float[][] lights = new Float[size][size];
        Float[][][] power = new Float[3][size][size];
        Boolean[][] lsources = new Boolean[size][size];
        Byte[][] zqn = new Byte[size][size];
        Byte[][][] pzqn = new Byte[3][size][size];
        Boolean[][][] arbprd = new Boolean[3][size][size];
        Boolean[][] wcnct = new Boolean[size][size];
        Boolean[][] drawn = new Boolean[size][size];
        Boolean[][] rdrawn = new Boolean[size][size];
        Boolean[][] ldrawn = new Boolean[size][size];
        for (y=0; y<size; y++) {
            for (x=0; x<size; x++) {
                for (int l=0; l<3; l++) {
                    if (l == 1 && cy*size+y >= PerlinNoise.perlinNoise((cx*size+x) / 10.0, 0.5, 0) * 30 + 50) {
                        blocks[l][y][x] = 1; // dirt
                    }
                    else {
                        blocks[l][y][x] = 0;
                    }
                    arbprd[l][y][x] = false;
                    power[l][y][x] = (float)0;
                }
                blockdns[y][x] = (byte)random.nextInt(5);
                blockbgs[y][x] = 0;
                blockts[y][x] = (byte)random.nextInt(8);
                lights[y][x] = (float)19;
                lsources[y][x] = false;
                wcnct[y][x] = false;
                drawn[y][x] = false;
                rdrawn[y][x] = false;
                ldrawn[y][x] = false;
                blockds[0][y][x] = (byte)0;
                blockds[2][y][x] = (byte)0;
            }
        }
        blockds[1] = World.generateOutlines(blocks[1]);
        Object[] rv = {blocks, blockds, blockdns, blockbgs, blockts, lights, power, lsources, zqn, pzqn, arbprd, wcnct, drawn, rdrawn, ldrawn};
        return rv;
    }

    public static Byte[][] generateOutlines(Integer[][] blocks) {
        return World.generate2(blocks, false);
    }

/*    public static Object[] generate(int width, int height, int sealevel, int stonelevel, TerrariaClone inst) {
        pmsg("Generating new world...");
        random = TerrariaClone.getRandom();
        pmsg("-> Creating blocks...");
        Integer[][] blocks = new Integer[height][width];
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                blocks[y][x] = 0;
            }
        }
        Integer[][] blocksB = new Integer[height][width];
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                blocksB[y][x] = 0;
            }
        }
        Integer[][] blocksF = new Integer[height][width];
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                blocksF[y][x] = 0;
            }
        }
        String biome = "plains";
        int bCount;
        int rnum;
        String[] biomes = new String[width];
        boolean[] verify = {false, false, false, false, false};
        boolean bvar = false;
        while (true) {
            bCount = 0;
            for (i=0; i<5; i++) {
                verify[i] = false;
            }
            bvar = true;
            for (x=0; x<width; x++) {
                if (bCount == 0) {
                    rnum = random.nextInt(100);
                    if (rnum >= 0 && rnum < 50) {
                        biome = "plains"; // 50%
                        if (x < width - 300) verify[0] = true;
                    }
                    if (rnum >= 50 && rnum < 65) {
                        biome = "desert"; // 15%
                        if (x < width - 300) verify[1] = true;
                    }
                    if (rnum >= 65 && rnum < 80) {
                        biome = "jungle"; // 15%
                        if (x < width - 300) verify[2] = true;
                    }
                    if (rnum >= 80 && rnum < 90) {
                        biome = "swamp"; // 10%
                        if (x < width - 300) verify[3] = true;
                    }
                    if (rnum >= 90 && rnum < 100) {
                        biome = "frost"; // 10%
                        if (x < width - 300) verify[4] = true;
                    }
                    bCount = random.nextInt(200) + 100;
                }
                biomes[x] = biome;
                bCount -= 1;
            }
            for (i=0; i<5; i++) {
                if (!verify[i]) {
                    bvar = false;
                }
            }
            if (bvar || width < 1500) {
                break;
            }
        }

        double[] terrain = new double[width];
        for (x=0; x<width; x++) {
            terrain[x] = PerlinNoise.perlinNoise(x / 10.0, 0.5, 0) * 30 + sealevel;
        }
        double[] stonelayer = new double[width];
        for (x=0; x<width; x++) {
            stonelayer[x] = PerlinNoise.perlinNoise(x / 10.0, 0.5, 0) * 30 + stonelevel;
        }
        for (x=0; x<width; x++) {
            for (y=0; y<height; y++) {
                if (y >= (int)terrain[x]) {
                    blocks[y][x] = 1; // dirt
                    if (biomes[x] == "desert") {
                        if (y <= terrain[x] + random.nextInt(5) + 30) {
                            blocks[y][x] = 45; // sand
                        }
                    }
                    if (biomes[x] == "swamp") {
                        if (y <= terrain[x] + random.nextInt(5) + 35) {
                            blocks[y][x] = 75; // mud
                        }
                    }
                    if (biomes[x] == "frost") {
                        if (y <= terrain[x] + random.nextInt(5) + 35) {
                            blocks[y][x] = 46; // snow
                        }
                    }
                }
            }
        }
        for (x=1; x<width-1; x++) {
            for (y=1; y<height-1; y++) {
                if (biomes[x] == "plains") {
                    if (blocks[y][x] == 1) { // dirt
                        if (blocks[y-1][x] == 0 || blocks[y+1][x] == 0 ||
                            blocks[y][x-1] == 0 || blocks[y][x+1] == 0 ||
                            blocks[y-1][x-1] == 0 || blocks[y+1][x+1] == 0 ||
                            blocks[y-1][x+1] == 0 || blocks[y+1][x-1] == 0) {
                            blocks[y][x] = 72; // grass
                        }
                    }
                }
                if (biomes[x] == "jungle") {
                    if (blocks[y][x] == 1) { // dirt
                        if (blocks[y-1][x] == 0 || blocks[y+1][x] == 0 ||
                            blocks[y][x-1] == 0 || blocks[y][x+1] == 0 ||
                            blocks[y-1][x-1] == 0 || blocks[y+1][x+1] == 0 ||
                            blocks[y-1][x+1] == 0 || blocks[y+1][x-1] == 0) {
                            blocks[y][x] = 73; // jungle_grass
                        }
                    }
                }
                if (biomes[x] == "swamp") {
                    if (blocks[y][x] == 75) { // mud
                        if (blocks[y-1][x] == 0 || blocks[y+1][x] == 0 ||
                            blocks[y][x-1] == 0 || blocks[y][x+1] == 0 ||
                            blocks[y-1][x-1] == 0 || blocks[y+1][x+1] == 0 ||
                            blocks[y-1][x+1] == 0 || blocks[y+1][x-1] == 0) {
                            blocks[y][x] = 74; // swamp_grass
                        }
                    }
                }
            }
        }
        for (x=0; x<width; x++) {
            for (y=0; y<height; y++) {
                if (y >= stonelayer[x]) {
                    blocks[y][x] = 2; // stone
                }
            }
        }
        pmsg("-> Adding underground deposits, caverns, and floating islands...");
        int v = (int)(height*0.04);
        for (i=0; i<width*height/2000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(stonelevel - sealevel) + sealevel;
            coordlist = blob(random.nextInt(7) + 5, random.nextInt(7) + 5, 1);
            for (y=0; y<coordlist[0].length; y++) {
                for (x=0; x<coordlist.length; x++) {
                    if (coordlist[x][y]) {
                        if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                            blocks[ypos+y][xpos+x] = 2; // stone
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/2000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height - stonelevel - v) + stonelevel;
            coordlist = blob(random.nextInt(7) + 5, random.nextInt(7) + 5, 1);
            for (y=0; y<coordlist[0].length; y++) {
                for (x=0; x<coordlist.length; x++) {
                    if (coordlist[x][y]) {
                        if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                            blocks[ypos+y][xpos+x] = 1; // dirt
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/2000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(stonelevel - sealevel) + sealevel;
            if (biomes[xpos].equals("swamp")) {
                coordlist = blob(random.nextInt(7) + 5, random.nextInt(7) + 5, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 88; // clay
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/400; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (true) {
                coordlist = blob(random.nextInt(2) + 2, random.nextInt(2) + 2, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                        if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 18; // coal
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/1500; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.45) {
                coordlist = blob(random.nextInt(2) + 2, random.nextInt(2) + 2, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 19; // lumenstone
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/3500; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (true) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 3; // copper_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/4000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (true) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 4; // iron_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/5500; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.25) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 5; // silver_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/7000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.4) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 6; // gold_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/8500; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.55) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 31; // zinc_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/10500; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.7) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 32; // rhymestone_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/12500; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.85) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 33; // obdurite_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/6000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.2) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 34; // aluminum_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/8000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.45) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 35; // lead_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/15000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.65) {
                coordlist = blob(random.nextInt(3) + 2, random.nextInt(3) + 2, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 36; // uranium_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/11000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.4) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 37; // zythium_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/13000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (ypos >= height*0.6) {
                coordlist = blob(random.nextInt(5) + 4, random.nextInt(5) + 4, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height && blocks[ypos+y][xpos+x] != 0) {
                                blocks[ypos+y][xpos+x] = 39; // silicon_ore
                            }
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/20000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            if (blocks[ypos][xpos] != 0) {
                blocks[ypos][xpos] = 40; // unobtainium_ore
            }
        }
        for (i=0; i<width*height/1500; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(height-sealevel-v)+sealevel;
            coordlist = blob(random.nextInt(15) + 7, random.nextInt(13) + 7, 0.5);
            for (y=0; y<coordlist[0].length; y++) {
                for (x=0; x<coordlist.length; x++) {
                    if (coordlist[x][y]) {
                        if (xpos+x > 0 && xpos+x < width && ypos+y > 0 && ypos+y < height) {
                            blocks[ypos+y][xpos+x] = 0; // air
                        }
                    }
                }
            }
        }
        for (i=0; i<width*height/672000; i++) {
            xpos = random.nextInt(width);
            ypos = random.nextInt(sealevel-(int)(height*0.03));
            coordlist = blob(random.nextInt(25) + 10, random.nextInt(20) + 10, 0.5);
            for (y=0; y<coordlist[0].length; y++) {
                for (x=0; x<coordlist.length; x++) {
                    if (coordlist[x][y]) {
                        if (ypos+y > 0 && ypos+y < height) {
                            blocks[ypos+y][mod(xpos+x,width)] = 91; // dirt_trans
                        }
                    }
                }
            }
            for (j=0; j<30; j++) {
                xpos2 = xpos+random.nextInt(70);
                ypos2 = ypos+random.nextInt(60);
                coordlist = blob(random.nextInt(4) + 3, random.nextInt(4) + 3, 1);
                for (y=0; y<coordlist[0].length; y++) {
                    for (x=0; x<coordlist.length; x++) {
                        if (coordlist[x][y]) {
                            if (ypos2+y > 0 && ypos2+y < height && blocks[ypos2+y][mod(xpos2+x,width)] == 91) { // dirt_trans
                                blocks[ypos2+y][mod(xpos2+x,width)] = 92; // magnetite_ore_trans
                            }
                        }
                    }
                }
            }
        }
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                if (blocks[y][x] == 91 && TerrariaClone.hasOpenSpace(x, y, blocks)) {
                    blocks[y][x] = 93; // grass_trans
                }
            }
        }
        pmsg("-> Adding vegetation...");
        for (x=0; x<width; x++) {
            for (y=(int)(terrain[x]+35); y>5; y--) {
                if (blocks[y][x] == 45 && blocks[y-1][x] == 45 && blocks[y-2][x] == 45 && blocks[y-3][x] == 45 && blocks[y+1][x] != 45) { // sand
                    for (i=0; i<random.nextInt(5)+5; i++) {
                        blocks[y+i][x] = 76; // sandstone
                    }
                    break;
                }
            }
        }
        for (i=0; i<width*height*24; i++) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            if (x >= 1 && x < width-1 && y >= 1 && y < height-1) {
                if (blocks[y][x] == 1 && TerrariaClone.hasOpenSpace(x, y, blocks) && blocks[y+random.nextInt(3)-1][x+random.nextInt(3)-1] == 72) {
                    blocks[y][x] = 72;
                }
                if (blocks[y][x] == 1 && TerrariaClone.hasOpenSpace(x, y, blocks) && blocks[y+random.nextInt(3)-1][x+random.nextInt(3)-1] == 73) {
                    blocks[y][x] = 73;
                }
                if (blocks[y][x] == 75 && TerrariaClone.hasOpenSpace(x, y, blocks) && blocks[y+random.nextInt(3)-1][x+random.nextInt(3)-1] == 74) {
                    blocks[y][x] = 74;
                }
            }
        }

        for (x=0; x<width; x++) {
            if (biomes[x] == "plains") {
                for (y=2; y<terrain[x]+10; y++) {
                    if (blocks[y][x] == 72 && blocks[y-1][x] == 0 && blocks[y-2][x] == 0 && random.nextInt(10) == 0) { // grass
                        i = 1;
                        blocksB[y][x] = 30;
                        while (true) {
                            blocks[y-i][x] = 15; // tree
                            i += 1;
                            if (i >= 10 && random.nextInt(3) == 0 || y-i-1 < 0 || y - i == 0 || blocks[y-i-1][x] != 0) {
                                n = random.nextInt(2) + 3;
                                coordlist = blob(n, n, 0.5);
                                for (y2=0; y2<coordlist[0].length; y2++) {
                                    for (x2=0; x2<coordlist.length; x2++) {
                                        if (coordlist[x2][y2]) {
                                            if (x+x2-coordlist.length/2 > 0 && x+x2-coordlist.length/2 < width && y-i+y2-coordlist[0].length/2 > 0 && y-i+y2-coordlist[0].length/2 < height) {
                                                blocksB[y-i+y2-coordlist[0].length/2][x+x2-coordlist.length/2] = 16; // leaves
                                                blocksF[y-i+y2-coordlist[0].length/2][x+x2-coordlist.length/2] = 16; // leaves
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            if (biomes[x] == "desert") {
                for (y=1; y<terrain[x]+10; y++) {
                    if (blocks[y][x] == 45 && blocks[y-1][x] == 0 && random.nextInt(30) == 0) { // dirt
                        i = 1;
                        blocksB[y][x] = 30;
                        while (true) {
                            blocks[y-i][x] = 15; // tree
                            i += 1;
                            if (i >= 10 && random.nextInt(3) == 0 || y-i-1 < 0 || y - i == 0 || blocks[y-i-1][x] != 0) {
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            if (biomes[x] == "jungle") {
                for (y=1; y<terrain[x]+10; y++) {
                    if (blocks[y][x] == 73 && blocks[y-1][x] == 0 && random.nextInt(3) == 0) { // jungle_grass
                        i = 1;
                        blocksB[y][x] = 30;
                        while (true) {
                            blocks[y-i][x] = 15; // tree
                            i += 1;
                            if (i >= 10 && random.nextInt(4) == 0 || y-i-1 < 0 || y - i == 0 || blocks[y-i-1][x] != 0) {
                                n = random.nextInt(2) + 3;
                                coordlist = blob(n, n, 0.5);
                                for (y2=0; y2<coordlist[0].length; y2++) {
                                    for (x2=0; x2<coordlist.length; x2++) {
                                        if (coordlist[x2][y2]) {
                                            if (x+x2-coordlist.length/2 > 0 && x+x2-coordlist.length/2 < width && y-i+y2-coordlist[0].length/2 > 0 && y-i+y2-coordlist[0].length/2 < height) {
                                                blocksB[y-i+y2-coordlist[0].length/2][x+x2-coordlist.length/2] = 16; // leaves
                                                blocksF[y-i+y2-coordlist[0].length/2][x+x2-coordlist.length/2] = 16; // leaves
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            if (biomes[x] == "swamp") {
                for (y=1; y<terrain[x]+10; y++) {
                    if (blocks[y][x] == 74 && blocks[y-1][x] == 0 && random.nextInt(10) == 0) { // swamp_grass
                        i = 1;
                        blocksB[y][x] = 30;
                        while (true) {
                            blocks[y-i][x] = 15; // tree
                            i += 1;
                            if (i >= 10 && random.nextInt(3) == 0 || y-i-1 < 0 || y - i == 0 || blocks[y-i-1][x] != 0) {
                                n = random.nextInt(2) + 3;
                                coordlist = blob(n, n, 0.5);
                                for (y2=0; y2<coordlist[0].length; y2++) {
                                    for (x2=0; x2<coordlist.length; x2++) {
                                        if (coordlist[x2][y2]) {
                                            if (x+x2-coordlist.length/2 > 0 && x+x2-coordlist.length/2 < width && y-i+y2-coordlist[0].length/2 > 0 && y-i+y2-coordlist[0].length/2 < height) {
                                                blocksB[y-i+y2-coordlist[0].length/2][x+x2-coordlist.length/2] = 16; // leaves
                                                blocksF[y-i+y2-coordlist[0].length/2][x+x2-coordlist.length/2] = 16; // leaves
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            if (biomes[x] == "frost") {
                for (y=1; y<terrain[x]+10; y++) {
                    if (blocks[y][x] == 46 && blocks[y-1][x] == 0 && random.nextInt(10) == 0) { // dirt
                        i = 1;
                        blocksB[y][x] = 30;
                        while (true) {
                            blocks[y-i][x] = 15; // tree
                            i += 1;
                            if (i >= 10 && random.nextInt(3) == 0 || y-i-1 < 0 || y - i == 0 || blocks[y-i-1][x] != 0) {
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        for (y=(int)(height*0.98); y<height; y++) {
            for (x=0; x<width; x++) {
                if (random.nextInt((int)(height*0.02))+height*0.98 <= y) {
                    blocks[y][x] = 0;
                }
            }
        }
        for (y=0; y<height-1; y++) {
            for (x=0; x<width; x++) {
                if (blocks[y][x] == 0) {
                    if (blocks[y+1][x] == 72 && random.nextInt(100) == 0) {
                        blocks[y][x] = 50;
                    }
                    if (blocks[y+1][x] == 72 && random.nextInt(100) == 0) {
                        blocks[y][x] = 53;
                    }
                    if (blocks[y+1][x] == 45 && random.nextInt(100) == 0) {
                        blocks[y][x] = 56;
                    }
                    if (blocks[y+1][x] == 73 && random.nextInt(100) == 0) {
                        blocks[y][x] = 59;
                    }
                    if (blocks[y+1][x] == 46 && random.nextInt(100) == 0) {
                        blocks[y][x] = 62;
                    }
                    if (blocks[y+1][x] == 2 && random.nextInt(400) == 0 && y >= height * 0.2) {
                        blocks[y][x] = 65;
                    }
                    if (blocks[y+1][x] == 93 && random.nextInt(20) == 0 && y <= height * 0.05) {
                        blocks[y][x] = 68;
                    }
                    if (blocks[y+1][x] == 2 && random.nextInt(300) == 0 && y >= height * 0.98) {
                        blocks[y][x] = 71;
                    }
                    if (blocks[y+1][x] == 74 && random.nextInt(100) == 0) {
                        blocks[y][x] = 79;
                    }
                }
            }
        }

        Integer[][][] blocks_rv = {blocksB, blocks, blocksF};

        Object[] rv = {blocks_rv, new DoubleContainer(terrain), new DoubleContainer(stonelayer)};
        return rv;
    }
*/
    public static Byte[][] generate2(Integer[][] blocks, boolean msg) {
        int x, y;
        int width = blocks[0].length;
        int height = blocks.length;
        if (msg) {
            pmsg("-> Creating outlines...");
        }
        Byte[][] blockds = new Byte[height][width];
//        Byte[][] blockdns = new Byte[height][width];
        boolean left, right, up, down, upleft, upright, downleft, downright;
        for (y=0; y<height; y++) {
            for (x2=0; x2<width; x2++) {
                left = false; right = false; up = false; down = false;
                upleft = false; upright = false; downleft = false; downright = false;
                x = mod(x2,width);
                if (y > 0 && y < height-1 && blocks[y][x] != 0) {
                    left = connect(x-1, y, x, y, blocks);
                    right = connect(x+1, y, x, y, blocks);
                    up = connect(x, y-1, x, y, blocks);
                    down = connect(x, y+1, x, y, blocks);
                    upleft = connect(x-1, y-1, x, y, blocks);
                    upright = connect(x+1, y-1, x, y, blocks);
                    downleft = connect(x-1, y+1, x, y, blocks);
                    downright = connect(x+1, y+1, x, y, blocks);
                    if (left) {
                        if (right) {
                            if (up) {
                                if (down) {
                                    blockds[y][x] = 0;
                                }
                                else {
                                    if (upleft) {
                                        if (upright) {
                                            blockds[y][x] = 1;
                                        }
                                        else {
                                            blockds[y][x] = 2;
                                        }
                                    }
                                    else {
                                        if (upright) {
                                            blockds[y][x] = 3;
                                        }
                                        else {
                                            blockds[y][x] = 4;
                                        }
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downright) {
                                        if (downleft) {
                                            blockds[y][x] = 5;
                                        }
                                        else {
                                            blockds[y][x] = 6;
                                        }
                                    }
                                    else {
                                        if (downleft) {
                                            blockds[y][x] = 7;
                                        }
                                        else {
                                            blockds[y][x] = 8;
                                        }
                                    }
                                }
                                else {
                                    blockds[y][x] = 9;
                                }
                            }
                        }
                        else {
                            if (up) {
                                if (down) {
                                    if (downleft) {
                                        if (upleft) {
                                            blockds[y][x] = 10;
                                        }
                                        else {
                                            blockds[y][x] = 11;
                                        }
                                    }
                                    else {
                                        if (upleft) {
                                            blockds[y][x] = 12;
                                        }
                                        else {
                                            blockds[y][x] = 13;
                                        }
                                    }
                                }
                                else {
                                    if (upleft) {
                                        blockds[y][x] = 14;
                                    }
                                    else {
                                        blockds[y][x] = 15;
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downleft) {
                                        blockds[y][x] = 16;
                                    }
                                    else {
                                        blockds[y][x] = 17;
                                    }
                                }
                                else {
                                    blockds[y][x] = 18;
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
                                            blockds[y][x] = 19;
                                        }
                                        else {
                                            blockds[y][x] = 20;
                                        }
                                    }
                                    else {
                                        if (downright) {
                                            blockds[y][x] = 21;
                                        }
                                        else {
                                            blockds[y][x] = 22;
                                        }
                                    }
                                }
                                else {
                                    if (upright) {
                                        blockds[y][x] = 23;
                                    }
                                    else {
                                        blockds[y][x] = 24;
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downright) {
                                        blockds[y][x] = 25;
                                    }
                                    else {
                                        blockds[y][x] = 26;
                                    }
                                }
                                else {
                                    blockds[y][x] = 27;
                                }
                            }
                        }
                        else {
                            if (up) {
                                if (down) {
                                    blockds[y][x] = 28;
                                }
                                else {
                                    blockds[y][x] = 29;
                                }
                            }
                            else {
                                if (down) {
                                    blockds[y][x] = 30;
                                }
                                else {
                                    blockds[y][x] = 31;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blockds;
    }

    public static Byte[][] generate2b(Integer[][] blocks, Byte[][] blockds, int xpos, int ypos) {
        int x, y;
        int width = blocks[0].length;
        int height = blocks.length;
        boolean left, right, up, down, upleft, upright, downleft, downright;
        left = false; right = false; up = false; down = false;
        upleft = false; upright = false; downleft = false; downright = false;
        for (y=ypos-1; y<ypos+2; y++) {
            for (x2=xpos-1; x2<xpos+2; x2++) {
                x = mod(x2,width);
                if (y > 0 && y < height-1 && blocks[y][x] != 0) {
                    left = connect(x-1, y, x, y, blocks);
                    right = connect(x+1, y, x, y, blocks);
                    up = connect(x, y-1, x, y, blocks);
                    down = connect(x, y+1, x, y, blocks);
                    upleft = connect(x-1, y-1, x, y, blocks);
                    upright = connect(x+1, y-1, x, y, blocks);
                    downleft = connect(x-1, y+1, x, y, blocks);
                    downright = connect(x+1, y+1, x, y, blocks);
                    if (left) {
                        if (right) {
                            if (up) {
                                if (down) {
                                    blockds[y][x] = 0;
                                }
                                else {
                                    if (upleft) {
                                        if (upright) {
                                            blockds[y][x] = 1;
                                        }
                                        else {
                                            blockds[y][x] = 2;
                                        }
                                    }
                                    else {
                                        if (upright) {
                                            blockds[y][x] = 3;
                                        }
                                        else {
                                            blockds[y][x] = 4;
                                        }
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downright) {
                                        if (downleft) {
                                            blockds[y][x] = 5;
                                        }
                                        else {
                                            blockds[y][x] = 6;
                                        }
                                    }
                                    else {
                                        if (downleft) {
                                            blockds[y][x] = 7;
                                        }
                                        else {
                                            blockds[y][x] = 8;
                                        }
                                    }
                                }
                                else {
                                    blockds[y][x] = 9;
                                }
                            }
                        }
                        else {
                            if (up) {
                                if (down) {
                                    if (downleft) {
                                        if (upleft) {
                                            blockds[y][x] = 10;
                                        }
                                        else {
                                            blockds[y][x] = 11;
                                        }
                                    }
                                    else {
                                        if (upleft) {
                                            blockds[y][x] = 12;
                                        }
                                        else {
                                            blockds[y][x] = 13;
                                        }
                                    }
                                }
                                else {
                                    if (upleft) {
                                        blockds[y][x] = 14;
                                    }
                                    else {
                                        blockds[y][x] = 15;
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downleft) {
                                        blockds[y][x] = 16;
                                    }
                                    else {
                                        blockds[y][x] = 17;
                                    }
                                }
                                else {
                                    blockds[y][x] = 18;
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
                                            blockds[y][x] = 19;
                                        }
                                        else {
                                            blockds[y][x] = 20;
                                        }
                                    }
                                    else {
                                        if (downright) {
                                            blockds[y][x] = 21;
                                        }
                                        else {
                                            blockds[y][x] = 22;
                                        }
                                    }
                                }
                                else {
                                    if (upright) {
                                        blockds[y][x] = 23;
                                    }
                                    else {
                                        blockds[y][x] = 24;
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downright) {
                                        blockds[y][x] = 25;
                                    }
                                    else {
                                        blockds[y][x] = 26;
                                    }
                                }
                                else {
                                    blockds[y][x] = 27;
                                }
                            }
                        }
                        else {
                            if (up) {
                                if (down) {
                                    blockds[y][x] = 28;
                                }
                                else {
                                    blockds[y][x] = 29;
                                }
                            }
                            else {
                                if (down) {
                                    blockds[y][x] = 30;
                                }
                                else {
                                    blockds[y][x] = 31;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blockds;
    }

    public static boolean connect(int b1, int b2) {
        return (b1 != 0 && b1 == b2 ||
            b1 == 1 && b2 == 72 ||
            b2 == 1 && b1 == 72 ||
            b1 == 1 && b2 == 73 ||
            b2 == 1 && b1 == 73 ||
            b1 == 75 && b2 == 74 ||
            b2 == 75 && b1 == 74 ||
            b1 == 91 && b2 == 93 ||
            b2 == 91 && b1 == 93 ||
            b2 >= 94 && b2 <= 99 && TerrariaClone.wirec[b1] ||
            b1 == 103 && b2 == 104 ||
            b2 == 103 && b1 == 104 ||
            b1 == 15 && b2 == 83 ||
            b2 == 83 && b1 == 15);
    }

    public static boolean connect(int x1, int y1, int x2, int y2, Integer[][] blocks) {
        return y1 > 0 && y1 < blocks.length-1 && connect(blocks[y1][mod(x1,blocks[0].length)], blocks[y2][mod(x2,blocks[0].length)]);
/*        int WIDTH = blocks[0].length;
        int HEIGHT = blocks.length;
        boolean[] blockcds = TerrariaClone.getBLOCKCDS();
        short b1 = blocks[y1][x1];
        short b2 = blocks[y2][x2];
        if (b1 == b2) return true;
        if (!(x1 > 0 && x1 < WIDTH-1 &&
              y1 > 0 && y1 < HEIGHT-1 &&
              x2 > 0 && x2 < WIDTH-1 &&
              y2 > 0 && y2 < HEIGHT-1)) return false;
        return (
            b1 == 1 && b2 == 72 && (blocks[2*y2-y1][2*x2-x1] == 0 || !blockcds[blocks[2*y2-y1][2*x2-x1]] || blocks[2*y2-y1][2*x2-x1] == 72) ||
            b2 == 1 && b1 == 72 && (blocks[2*y1-y2][2*x1-x2] == 0 || !blockcds[blocks[2*y1-y2][2*x1-x2]] || blocks[2*y1-y2][2*x1-x2] == 72) ||
            b1 == 1 && b2 == 73 && (blocks[2*y2-y1][2*x2-x1] == 0 || !blockcds[blocks[2*y2-y1][2*x2-x1]] || blocks[2*y2-y1][2*x2-x1] == 73) ||
            b2 == 1 && b1 == 73 && (blocks[2*y1-y2][2*x1-x2] == 0 || !blockcds[blocks[2*y1-y2][2*x1-x2]] || blocks[2*y1-y2][2*x1-x2] == 73) ||
            b1 == 75 && b2 == 74 && (blocks[2*y2-y1][2*x2-x1] == 0 || !blockcds[blocks[2*y2-y1][2*x2-x1]] || blocks[2*y2-y1][2*x2-x1] == 74) ||
            b2 == 75 && b1 == 74 && (blocks[2*y1-y2][2*x1-x2] == 0 || !blockcds[blocks[2*y1-y2][2*x1-x2]] || blocks[2*y1-y2][2*x1-x2] == 74));
*/    }

    public static Byte[][] generate3(Integer[][] blocks, double[] terrain, double[] stonelayer, TerrariaClone inst) {
        int x, y;
        pmsg("-> Creating background...");
        int width = blocks[0].length;
        int height = blocks.length;
        byte[][] blockbgsi = new byte[height][width];
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                blockbgsi[y][x] = 0;
                if (y >= terrain[x]) {
                    blockbgsi[y][x] = 8;
                }
                if (y >= stonelayer[x]) {
                    blockbgsi[y][x] = 16;
                }
            }
        }
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                if (!(x == 0 || x == width-1 || y == 0 || y == height-1)) {
                    if (blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] == 0) {
                        blockbgsi[y][x] = 0;
                    }
                }
            }
        }
        byte[][] blockbgs = new byte[height][width];
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                blockbgs[y][x] = blockbgsi[y][x];
                if (!(x == 0 || x == width-1 || y == 0 || y == height-1)) {
                    if (blockbgsi[y][x] == 8) {
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] == 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] == 0) {
                            blockbgs[y][x] = 1;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] == 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] == 0) {
                            blockbgs[y][x] = 1;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] == 0) {
                            blockbgs[y][x] = 1;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] == 0) {
                            blockbgs[y][x] = 1;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] == 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 2;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] == 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 2;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 2;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 2;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] == 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 3;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] == 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 3;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 3;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] == 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 3;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] == 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] == 0) {
                            blockbgs[y][x] = 4;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] == 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] == 0) {
                            blockbgs[y][x] = 4;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] == 0) {
                            blockbgs[y][x] = 4;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] == 0) {
                            blockbgs[y][x] = 4;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 5;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 5;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 5;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] == 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 5;
                        }
                        if (blockbgsi[y-1][x-1] == 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] != 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 6;
                        }
                        if (blockbgsi[y-1][x-1] != 0 && blockbgsi[y+1][x-1] != 0 &&
                            blockbgsi[y-1][x+1] == 0 && blockbgsi[y+1][x+1] != 0 &&
                            blockbgsi[y-1][x] != 0 && blockbgsi[y+1][x] != 0 &&
                            blockbgsi[y][x-1] != 0 && blockbgsi[y][x+1] != 0) {
                            blockbgs[y][x] = 7;
                        }
                    }
                }
            }
        }
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                if (y == (int)(height * 0.975)) {
                    blockbgs[y][x] = 17;
                }
                else if (y > (int)(height * 0.975)) {
                    blockbgs[y][x] = 0;
                }
            }
        }
        Byte[][] rv = new Byte[height][width];
        for (y=0; y<height; y++) {
            for (x=0; x<width; x++) {
                rv[y][x] = (Byte)blockbgs[y][x];
            }
        }
        return rv;
    }

    public static boolean[][] blob(int cwidth, int cheight, double erosion) {
        int x, y;
        coordlist = new boolean[cwidth*2+1][cheight*2+1];
        for (x=-cwidth; x<=cwidth; x++) {
            for (y=-cheight; y<=cheight; y++) {
                coordlist[x+cwidth][y+cheight] = Math.pow(x*1.0 / cwidth, 2) + Math.pow(y*1.0 / cheight, 2) < 1 - random.nextDouble() * erosion;
            }
        }
        for (n=0; n<2; n++) {
            for (x=-cwidth; x<=cwidth; x++) {
                for (y=-cheight; y<=cheight; y++) {
                    if ((x+cwidth <= 0 || !coordlist[x+cwidth-1][y+cheight]) &&
                        (x+cwidth+1 >= coordlist.length || !coordlist[x+cwidth+1][y+cheight]) &&
                        (y+cheight <= 0 || !coordlist[x+cwidth][y+cheight-1]) &&
                        (y+cheight+1 >= coordlist[0].length || !coordlist[x+cwidth][y+cheight+1])) {
                        coordlist[x+cwidth][y+cheight] = false;
                    }
                }
            }
            for (x=-cwidth; x<=cwidth; x++) {
                for (y=-cheight; y<=cheight; y++) {
                    if ((x+cwidth <= 0 || coordlist[x+cwidth-1][y+cheight]) &&
                        (x+cwidth+1 >= coordlist.length || coordlist[x+cwidth+1][y+cheight]) &&
                        (y+cheight <= 0 || coordlist[x+cwidth][y+cheight-1]) &&
                        (y+cheight+1 >= coordlist[0].length || coordlist[x+cwidth][y+cheight+1])) {
                        coordlist[x+cwidth][y+cheight] = true;
                    }
                }
            }
        }
        return coordlist;
    }

    public static void pmsg(String msg) {
        TerrariaClone.pmsg(msg);
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
