import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class WorldContainer implements Serializable {

    Integer[][][] blocks;
    Byte[][][] blockds;
    Byte[][] blockdns;
    Byte[][] blockbgs;
    Byte[][] blockts;
    Float[][] lights;
    Float[][][] power;
    Boolean[][] lsources;
    ArrayList<Integer> lqx, lqy;
    Boolean[][] lqd;
    Boolean[][] drawn, ldrawn, rdrawn;
    Player player;
    Inventory inventory;
    ItemCollection cic;
    ArrayList<Entity> entities;
    ArrayList<Double> cloudsx, cloudsy, cloudsv;
    ArrayList<Integer> cloudsn;
    ArrayList<Integer> machinesx, machinesy;

    int rgnc1;
    int rgnc2;
    int layer;
    int layerTemp;
    int blockTemp;

    int mx, my, icx, icy, mining, immune;

    short moveItem, moveNum, moveItemTemp, moveNumTemp;
    int msi;

    double toolAngle, toolSpeed;

    double timeOfDay;
    int currentSkyLight;
    int day;

    int mobCount;

    boolean ready;
    boolean showTool;
    boolean showInv;
    boolean doMobSpawn;

    int WIDTH;
    int HEIGHT;

    Random random;

    int WORLDWIDTH;
    int WORLDHEIGHT;

    int resunlight = WIDTH;

    ItemCollection ic;

    boolean[][] kworlds;

    ItemCollection[][][] icmatrix;

    String version;

    public WorldContainer(Integer[][][] blocks, Byte[][][] blockds, Byte[][] blockdns, Byte[][] blockbgs, Byte[][] blockts,
        Float[][] lights, Float[][][] power, Boolean[][] drawn, Boolean[][] ldrawn, Boolean[][] rdrawn,
        Player player, Inventory inventory, ItemCollection cic,
        ArrayList<Entity> entities, ArrayList<Double> cloudsx, ArrayList<Double> cloudsy, ArrayList<Double> cloudsv, ArrayList<Integer> cloudsn,
        ArrayList<Integer> machinesx, ArrayList<Integer> machinesy, Boolean[][] lsources, ArrayList<Integer> lqx, ArrayList<Integer> lqy, Boolean[][] lqd,
        int rgnc1, int rgnc2, int layer, int layerTemp, int blockTemp,
        int mx, int my, int icx, int icy, int mining, int immune,
        short moveItem, short moveNum, short moveItemTemp, short moveNumTemp, int msi,
        double toolAngle, double toolSpeed, double timeOfDay, int currentSkyLight, int day, int mobCount,
        boolean ready, boolean showTool, boolean showInv, boolean doMobSpawn,
        int WIDTH, int HEIGHT, Random random, int WORLDWIDTH, int WORLDHEIGHT,
        int resunlight,
        ItemCollection ic, boolean[][] kworlds, ItemCollection[][][] icmatrix, String version) {

        this.blocks = blocks;
        this.blockds = blockds;
        this.blockdns = blockdns;
        this.blockbgs = blockbgs;
        this.blockts = blockts;
        this.lights = lights;
        this.power = power;
        this.drawn = drawn;
        this.ldrawn = ldrawn;
        this.rdrawn = rdrawn;
        this.player = player;
        this.inventory = inventory;
        this.cic = cic;
        this.entities = entities;
        this.cloudsx = cloudsx;
        this.cloudsy = cloudsy;
        this.cloudsv = cloudsv;
        this.cloudsn = cloudsn;
        this.machinesx = machinesx;
        this.machinesy = machinesy;
        this.lsources = lsources;
        this.lqx = lqx;
        this.lqy = lqy;
        this.lqd = lqd;
        this.rgnc1 = rgnc1;
        this.rgnc2 = rgnc2;
        this.layer = layer;
        this.layerTemp = layerTemp;
        this.blockTemp = blockTemp;
        this.mx = mx;
        this.my = my;
        this.icx = icx;
        this.icy = icy;
        this.mining = mining;
        this.immune = immune;
        this.moveItem = moveItem;
        this.moveNum = moveNum;
        this.moveItemTemp = moveItemTemp;
        this.moveNumTemp = moveNumTemp;
        this.msi = msi;
        this.toolAngle = toolAngle;
        this.toolSpeed = toolSpeed;
        this.timeOfDay = timeOfDay;
        this.currentSkyLight = currentSkyLight;
        this.day = day;
        this.mobCount = mobCount;
        this.ready = ready;
        this.showTool = showTool;
        this.showInv = showInv;
        this.doMobSpawn = doMobSpawn;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.random = random;
        this.WORLDWIDTH = WORLDWIDTH;
        this.WORLDHEIGHT = WORLDHEIGHT;
        this.resunlight = resunlight;
        this.ic = ic;
        this.kworlds = kworlds;
        this.icmatrix = icmatrix;
        this.version = version;
    }
}
