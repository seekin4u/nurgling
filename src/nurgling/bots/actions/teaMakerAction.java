package nurgling.bots.actions;

import haven.Coord;
import haven.Coord2d;
import haven.GItem;
import haven.Gob;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import java.util.ArrayList;

public class teaMakerAction implements Action {

    private boolean haveFull(NGameUI gui) throws InterruptedException {
        ArrayList<GItem> wlaysprs = gui.getInventory ().getWItems( new NAlias ( "layspr" ) );
        for (GItem witem : wlaysprs) {
            if (NUtils.getContent(witem)!= null){
                if (NUtils.checkName(NUtils.getContent(witem), new NAlias ("tea"))) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean haveEmpty(NGameUI gui) throws InterruptedException {
        ArrayList<GItem> wlaysprs = gui.getInventory ().getWItems( new NAlias ( "layspr" ) );
        for (GItem witem : wlaysprs) {
            if (NUtils.getContent(witem)== null){
                    return true;
            }
        }
        return false;
    }
    private boolean sliv(NGameUI gui) throws InterruptedException {
        while (haveFull(gui)) {
            Gob tea = Finder.findSign(new NAlias("iconsign"), 5000, AreasID.special1);
            Gob cisternOfTea = Finder.findNearestObjectToObject ( new NAlias("cistern") , tea);

            PathFinder pf = new PathFinder(gui, cisternOfTea);
            pf.setHardMode(false);
            pf.run();
            ArrayList<GItem> wlaysprs = gui.getInventory ().getWItems( new NAlias ( "layspr" ) );
            for (GItem witem : wlaysprs) {
                if (NUtils.getContent(witem)!= null){
                    if (NUtils.checkName(NUtils.getContent(witem), new NAlias ("tea"))) {
                        Coord wit =witem.sz;
                        new TakeToHand(witem).run(gui);
                        Thread.sleep(200);
                        NUtils.waitEvent(() -> !gui.hand.isEmpty(), 200);
                        NUtils.activateItem(cisternOfTea);
                        NUtils.waitEvent(() -> !(NUtils.isContent(gui.vhand.item, "tea")), 1000);
                        if (!gui.hand.isEmpty()) {
                            NUtils.transferToInventory();
                            NUtils.waitEvent(() -> gui.hand.isEmpty(), 100);
                        }}}
            }
        }
        return true;
    }
    private boolean makeTea(NGameUI gui) throws InterruptedException {
            Gob cauldron = Finder.findObject(new NAlias("cauldron"));
            PathFinder pf = new PathFinder(gui, cauldron);
            pf.setHardMode(false);
            pf.run();
            Gob leafs = Finder.findObject(new NAlias("stockpile-leaf"));
            NUtils.activate(leafs);
            if(NUtils.checkGobFlower(new NAlias("Open"), cauldron)) {
                NFlowerMenu flowerMenu = NUtils.getFlowerMenu();
                flowerMenu.select("Open");
            }
            NUtils.waitEvent(() -> NUtils.getFlowerMenu() == null, 3000);
            NUtils.activate(leafs);
            new Craft("Brew tea", new char[]{'c', 'f', 't'}).run(gui);
            return true;
    }
    private boolean fill_water(NGameUI gui) throws InterruptedException {
        Gob cauldron = Finder.findObject(new NAlias("cauldron"));
        Gob barrel = Finder.findObject(new NAlias("barrel"));
        Gob water = Finder.findSign(new NAlias("iconsign"), 5000, AreasID.special2);
        Gob cisternOfWater = Finder.findNearestObjectToObject ( new NAlias("cistern") , water);
        Coord2d rcoord = barrel.rc;
        NUtils.lift(barrel);

        PathFinder pf = new PathFinder(gui, cisternOfWater);
        pf.setHardMode(false);
        pf.run();
        NUtils.activate(cisternOfWater);
        Thread.sleep(200);

        PathFinder pf2 = new PathFinder(gui, cauldron);
        pf2.setHardMode(false);
        pf2.run();
        NUtils.activate(cauldron);
        Thread.sleep(200);

        PathFinder pf3 = new PathFinder(gui, cisternOfWater);
        pf3.setHardMode(false);
        pf3.run();
        NUtils.activate(cisternOfWater);
        Thread.sleep(200);
        NUtils.place(rcoord);
        Thread.sleep(300);

        return true;
    }
    @Override
    public Results run ( NGameUI gui ) throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            fill_water(gui);
            makeTea(gui);
            sliv(gui);
        }
        return new Results(Results.Types.SUCCESS);
    }
//        while (haveFull(gui)) {
//            Gob gob = Finder.findObjectWithCoontent(new NAlias("barrel", "cistern"), new NAlias("tea"), 500);
//            PathFinder pf = new PathFinder(gui, gob);
//            pf.setHardMode(false);
//            pf.run();
//            ArrayList<GItem> wlaysprs = gui.getInventory ().getWItems( new NAlias ( "layspr" ) );
//            for (GItem witem : wlaysprs) {
//                if (NUtils.getContent(witem)!= null){
//                if (NUtils.checkName(NUtils.getContent(witem), "Tea")) {
//                    gui.msg ("Find!");
//                    new TakeToHand(witem).run(gui);
//                    NUtils.waitEvent(() -> !gui.hand.isEmpty(), 200);
//                    NUtils.activateItem(gob);
//                    NUtils.waitEvent(() -> !(NUtils.isContent(gui.vhand.item, "tea")), 4000);
//                    if (!gui.hand.isEmpty()) {
//                        NUtils.transferToInventory();
//                        NUtils.waitEvent(() -> gui.hand.isEmpty(), 50);
//                        GItem in_inventory = NUtils.getGameUI().getInventory().getItem(new NAlias("layspr"));
//                        in_inventory.wdgmsg("transfer", Coord.z, 1);
//                        NUtils.waitEvent(() -> NUtils.getGameUI().getInventory().getItem(in_inventory) == null, 50);
//                    }}}
//            }
//        }
//        return new Results(Results.Types.SUCCESS);
//    }

    public teaMakerAction() {
    }
}
