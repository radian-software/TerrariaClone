import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ItemCollection implements Serializable {

    String type;
    short[] ids, nums, durs;
    transient BufferedImage image;
    double FUELP = 0;
    double SMELTP = 0;
    boolean F_ON = false;
    short recipeNum;

    public ItemCollection(String type, short[] ids, short[] nums, short[] durs) {
        this.type = type;
        this.ids = ids;
        this.nums = nums;
        this.durs = durs;
    }
}
