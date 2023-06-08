package nurgling.bots.actions;

import haven.GItem;
import haven.Gob;
import haven.ItemInfo;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.List;


public class TreeplanterWaterIn implements Action {

    private boolean allFull(NGameUI gui) throws InterruptedException {
        ArrayList<GItem> ar = gui.getInventory().getWItems(new NAlias("treepot"));
        //gui.msg(Integer.toString(ar.size()));
        for(GItem item : ar) {

            if(!hasWater(item)){
                return false;
            }
        }
        return true;
    }

    private boolean hasWater(GItem item){
        List<ItemInfo> info = item.info();
        for (ItemInfo in: info ) {
            if(in instanceof ItemInfo.AdHoc){
                ItemInfo.AdHoc adhoc = (ItemInfo.AdHoc) in;
                if(adhoc.str.text.contains("Water: 0.00/1 l")){
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        while(!allFull(gui)) {
            Gob gob = Finder.findObject(new NAlias("barrel", "cistern"));//Finder.findObjectInArea(new NAlias("barrel", "cistern"), 1000, water_in);

            if (gob == null) {
                return new Results(Results.Types.NO_CONTAINER);
            }
            for (GItem item : gui.getInventory().getWItems(new NAlias("treepot"))) {
                if (!gui.hand.isEmpty()) {
                    NUtils.transferToInventory();
                    NUtils.waitEvent(() -> gui.hand.isEmpty(), 50);
                }
                if (!hasWater(item)) {
                    new TakeToHand(item).run(gui);
                    NUtils.waitEvent(() -> !gui.hand.isEmpty(), 50);
                    NUtils.activateItem(gob);
                    NUtils.waitEvent(() -> hasWater(item), 50);
                }
            }
        }
        if (!gui.hand.isEmpty()) {
            NUtils.transferToInventory();
            NUtils.waitEvent(() -> gui.hand.isEmpty(), 50);
        }
        return new Results(Results.Types.SUCCESS);
    }

    public TreeplanterWaterIn() {
    }
}
