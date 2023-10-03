package nurgling.bots.actions;
import java.util.ArrayList;
import java.util.List;

import haven.Coord;
import haven.GItem;
import haven.WItem;
import haven.Gob;
import nurgling.*;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.Arrays;

public class MakePiles implements Action {
    NAlias trees = new NAlias ( Arrays.asList ( "tree", "bushes" ),
            Arrays.asList ( "log", "block", "oldtrunk", "stump" ) );
    NAlias kritter = new NAlias (  "kritter", "sheep"  );
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {

        Gob cauldron = Finder.findObject(new NAlias("knarr"));
//        gui.msg("knarr");
//        PathFinder pf = new PathFinder(gui, cauldron, true);
//        pf.run();
        if(NUtils.checkGobFlower(new NAlias("Cargo"), cauldron)) {
            NFlowerMenu flowerMenu = NUtils.getFlowerMenu();
            flowerMenu.select("Cargo");
        }
        NUtils.waitEvent(() -> NUtils.getFlowerMenu() == null, 3000);
        NUtils.waitEvent ( ()-> gui.getInventory ( "Knarr" )!=null,4000 );
        Thread.sleep(3000);
        ArrayList<WItem> items = gui.getInventory ("Knarr" ).getAll();
        for (WItem item:  items){
            gui.msg("name: " + item.item.getres().name);
//        GItem item = gui.getInventory ("Knarr" ).getItem ( kritter, 0 , gui.getInventory ( "Knarr" ).getFreeSpace ());
        item.wdgmsg("iact", Coord.z, 0);}



//        new TransferToPile(piles, NHitBox.get(pile_name),
//                                        new NAlias(pile_name), item_name).run(gui);






//        int th = NUtils.checkName("leaf", item_name) ? 3 : 2;
//        int th = 10;
//        ArrayList<Gob> gobs = Finder.findObjectsInArea(trees, tree);
//        if (gui.getInventory().getNumberFreeCoord(gui.getInventory().getItem(item_name)) <=
//                1) {
//            NUtils.stopWithClick();
//            new TransferToPile(piles, NHitBox.get(pile_name),
//                    new NAlias(pile_name), item_name).run(gui);
//        }
//        for (Gob in : gobs) {
//            if (in.getattr(TreeScale.class) == null) {
//                while (NUtils.checkGobFlower(item_name, in)) {
//                    PathFinder pf = new PathFinder(gui, in);
//                    pf.setHardMode(true);
//                    pf.run();
//                    NFlowerMenu fm = NUtils.getFlowerMenu();
//                    if(fm!=null) {
//                        fm.select(item_name);
//                        do {
//                            NUtils.waitEvent(() -> NUtils.getProg() >= 0, 50, 5);
//                            NUtils.waitEvent(() -> NUtils.getProg() < 0, 1000, 10);
//                            if (gui.getInventory()
//                                    .getNumberFreeCoord(gui.getInventory().getItem(item_name)) <= th) {
//                                new TransferToPile(piles, NHitBox.get(pile_name),
//                                        new NAlias(pile_name), item_name).run(gui);
//                            }
//                            Thread.sleep(10);
//                        } while (NUtils.getProg() >= 0);
//                    }
//                }
//            }
//        }
//        NUtils.stopWithClick();
//        new TransferToPile(piles, NHitBox.get(pile_name),
//                new NAlias(pile_name), item_name).run(gui);
        return new Results(Results.Types.SUCCESS);
    }



    public MakePiles(
            NArea tree,
            NArea piles,
            NAlias item_name,
            String pile_name,
            String action
    ) {
        this.tree = tree;
        this.piles = piles;
        this.item_name = item_name;
        this.pile_name = pile_name;
        this.action = action;
    }
    
    NArea tree;
    NArea piles;
    NAlias item_name;
    String pile_name;
    String action;
}
