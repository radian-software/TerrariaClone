public class Chunk {

    int cx, cy;

    Integer[][][] blocks;
    Byte[][][] blockds;
    Byte[][] blockdns;
    Byte[][] blockbgs;
    Byte[][] blockts;
    Float[][] lights;
    Float[][][] power;
    Boolean[][] lsources;
    Byte[][] zqn;
    Byte[][][] pzqn;
    Boolean[][][] arbprd;
    Boolean[][] wcnct;
    Boolean[][] drawn, rdrawn, ldrawn;

    public Chunk(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;

        Object[] rv = World.generateChunk(cx, cy, TerrariaClone.getRandom());
        blocks = (Integer[][][])rv[0];
        blockds = (Byte[][][])rv[1];
        blockdns = (Byte[][])rv[2];
        blockbgs = (Byte[][])rv[3];
        blockts = (Byte[][])rv[4];
        lights = (Float[][])rv[5];
        power = (Float[][][])rv[6];
        lsources = (Boolean[][])rv[7];
        zqn = (Byte[][])rv[8];
        pzqn = (Byte[][][])rv[9];
        arbprd = (Boolean[][][])rv[10];
        wcnct = (Boolean[][])rv[11];
        drawn = (Boolean[][])rv[12];
        rdrawn = (Boolean[][])rv[13];
        ldrawn = (Boolean[][])rv[14];
    }
}
