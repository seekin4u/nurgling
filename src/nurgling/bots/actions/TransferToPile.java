package nurgling.bots.actions;

import haven.GItem;
import haven.Gob;
import nurgling.*;
import nurgling.NExceptions.NoFreeSpace;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;
import nurgling.tools.PileMaker;

import java.util.ArrayList;
import java.util.List;

public class TransferToPile implements Action {

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        if(NUtils.checkName(name.getDefault(),new NAlias("soil")))
        {
            name = new NAlias("gfx/terobjs/stockpile-soil");
        }else if(NUtils.checkName(name.getDefault(),new NAlias("board")))
        {
            name = new NAlias("gfx/terobjs/stockpile-board");
        }else if(NUtils.checkName(name.getDefault(),new NAlias("pumpkin")))
        {
            name = new NAlias("gfx/terobjs/stockpile-pumpkin");
        }else if(NUtils.checkName(name.getDefault(),new NAlias("metal")))
        {
            name = new NAlias("gfx/terobjs/stockpile-metal");
        }else if(NUtils.checkName(name.getDefault(),new NAlias("brick")))
        {
            name = new NAlias("gfx/terobjs/stockpile-brick");
        }else if(NUtils.checkName(name.getDefault(),new NAlias("leaf")))
        {
            name = new NAlias("gfx/terobjs/stockpile-leaf");
        }else if(NUtils.checkName(name.getDefault(),new NAlias("flesh")))
        {
            name = new NAlias("gfx/terobjs/stockpile-trash");
        }
        if (!gui.getInventory().getWItems(items).isEmpty() ) {
            if (area == null)
                area = Finder.findNearestMark(id);
            if (area != null) {
                Gob target = null;
                for (Gob gob : Finder.findObjectsInArea(name, area)) {
                    if (gob.getModelAttribute() != 31) {
                        target = gob;
                        PathFinder pf = new PathFinder(gui, target);
                        pf.setHardMode(false);
                        pf.run();

                        if (!gui.hand.isEmpty()) {
                            NUtils.activateItemToPile(gob, fast);
                            NUtils.waitEvent(() -> NUtils.getGameUI().hand.isEmpty(), 100);
                            if (NOCache.getgob(gob).getModelAttribute() == 31)
                                continue;
                        }
                        break;
                    }
                }
                ArrayList<GItem> witems = gui.getInventory().getWItems(items);
                boolean qIsFind = false;
                for (GItem witem : witems) {
                    if (((NGItem)witem).quality() >= q) {
                        qIsFind = true;
                        break;
                    }
                }
                if (!qIsFind) {
                    return new Results(Results.Types.SUCCESS);
                }
                if (target == null) {
                    PileMaker maker = new PileMaker(gui, area, hitBox, name);
                    maker.setItemName(items);
                    try {
                        target = maker.create();
                    } catch (NoFreeSpace noFreeSpace) {
                        return new Results(Results.Types.NO_FREE_SPACE);
                    }
                }else {
                    if (new OpenTargetContainer(target, "Stockpile").run(gui).type != Results.Types.SUCCESS) {
                        return new Results(Results.Types.OPEN_FAIL);
                    }
                }
                ArrayList<GItem> itemsToPile = gui.getInventory().getWItems(items);
                if (gui.getInventory().getWItems(items).isEmpty()){
                    return new Results(Results.Types.FILL_FAIL);
                }
                Thread.sleep(100);
                NUtils.takeItemToHand(itemsToPile.get(0));
                NUtils.waitEvent(() -> !gui.hand.isEmpty(), 500);
                NUtils.activateItemToPile(target, true);
                NUtils.waitEvent(() -> gui.hand.isEmpty(), 500);
//                NUtils.transferAlltoStockPile(items, q);
                if (!gui.getInventory().getWItems(items).isEmpty()) {
                    run(gui);
                }
            }
        }
        return new Results(Results.Types.SUCCESS);
    }
    
    public TransferToPile(
            AreasID id,
            NHitBox hitBox,
            NAlias name,
            NAlias items
    ) {
        this.id = id;
        this.hitBox = hitBox;
        this.name = name;
        this.items = items;
    }
    public TransferToPile(
            AreasID id,
            NHitBox hitBox,
            NAlias name,
            NAlias items,
            boolean fast
    ) {
        this.id = id;
        this.hitBox = hitBox;
        this.name = name;
        this.items = items;
        this.fast = fast;
    }
    public TransferToPile(
            NArea area,
            NHitBox hitBox,
            NAlias name,
            NAlias items
    ) {
        this.area =area;
        this.hitBox = hitBox;
        this.name = name;
        this.items = items;
    }
    public TransferToPile(
            NArea area,
            NHitBox hitBox,
            NAlias name,
            NAlias items,
            boolean fast
    ) {
        this.area =area;
        this.hitBox = hitBox;
        this.name = name;
        this.items = items;
        this.fast = fast;
    }
    public TransferToPile(
            AreasID id,
            NHitBox hitBox,
            NAlias name,
            NAlias items,
            double q
    ) {
        this.id = id;
        this.hitBox = hitBox;
        this.name = name;
        this.items = items;
        this.q = q;
    }
    public TransferToPile(
            AreasID id,
            NHitBox hitBox,
            NAlias name,
            NAlias items,
            double q,
            boolean fast
    ) {
        this.id = id;
        this.hitBox = hitBox;
        this.name = name;
        this.items = items;
        this.q = q;
        this.fast = fast;
    }
    public TransferToPile(
            NArea area,
            NHitBox hitBox,
            NAlias name,
            NAlias items,
            double q
    ) {
        this.area =area;
        this.hitBox = hitBox;
        this.name = name;
        this.items = items;
        this.q = q;
    }
    public TransferToPile(
            NArea area,
            NHitBox hitBox,
            NAlias name,
            NAlias items,
            double q,
            boolean fast
    ) {
        this.area =area;
        this.hitBox = hitBox;
        this.name = name;
        this.items = items;
        this.q = q;
        this.fast = fast;
    }
    AreasID id;
    NHitBox hitBox;
    NAlias name;
    NAlias items;
    NArea area = null;

    private double q =-1;
    boolean fast = false;
}
