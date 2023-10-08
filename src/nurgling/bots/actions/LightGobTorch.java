package nurgling.bots.actions;

import haven.GItem;
import haven.Gob;
import haven.WItem;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;

public class LightGobTorch implements Action {
    public LightGobTorch(
            NAlias name,
            AreasID id,
            int flame_flag
    ) {
        this.name = name;
        this.id = id;
        this.flame_flag = flame_flag;
    }

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        Gob brazier = Finder.findObject ( new NAlias ( "brazier" ));
        new PathFinder(gui, brazier).run();
        NUtils.freeHands (new NAlias("bucket"));
        WItem wbelt = Finder.findDressedItem ( new NAlias ("belt") );
        GItem item = null;
        if(wbelt!=null) {
            NInventory belt = ((NInventory)wbelt.item.contents);
            item = belt.getItem(new NAlias ("torch"));
            if (item != null) {
                NUtils.takeItemToHand(item);
                NUtils.activateItem(brazier);
                NUtils.waitEvent(() -> NUtils.getProg() >= 0, 100);
                NUtils.waitEvent(() -> !gui.hand.isEmpty() && NUtils.isIt(gui.vhand, new NAlias("torch-l")), 200);
            }}
        ArrayList<Gob> gobs = Finder.findObjectsInArea (name, Finder.findNearestMark (id));
        boolean fireNeeded = false;
        for ( Gob gob : gobs ) {
            if ( ( gob.getModelAttribute() & flame_flag ) == 0 ) {
                fireNeeded = true;
                break;
            }
        }
        if ( fireNeeded ) {
            for ( Gob gob : gobs ) {
                if ( ( gob.getModelAttribute() & flame_flag ) == 0 ) {
                    new LightFire ( gob, brazier).run ( gui );
                    if (  ((Finder.findObject ( gob.id )).getModelAttribute() &
                            flame_flag ) == 0 ) {
                        return new Results ( Results.Types.FAIL );
                    }

                }
            }
            if ( brazier != null ) {
                NUtils.getEquipment().wdgmsg("drop", -1);
                NUtils.waitEvent(() -> gui.vhand == null, 50);
                NUtils.waitEvent(() -> Finder.findDressedItem(new NAlias ("torch-l")) != null, 50);
                NUtils.freeHands (new NAlias("bucket"));
            }
        }
        return new Results ( Results.Types.SUCCESS );
    }

    NAlias name;
    AreasID id;
    int flame_flag;
}
