package nurgling.bots.actions;

import haven.Coord2d;
import haven.Gob;
import haven.res.lib.tree.TreeScale;
import nurgling.*;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class TreeItemsCollection2 implements Action {
    NAlias trees = new NAlias ( Arrays.asList ( "tree", "bushes" ),
            Arrays.asList ( "log", "block", "oldtrunk", "stump" ) );

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        NAlias winName = new NAlias("snekkja");
        Gob shipGob = Finder.findNearestObjectToObject (winName, gui.map.player());
        Coord2d ship = shipGob.rc;
//        int th = NUtils.checkName("leaf", item_name) ? 3 : 2;
        gui.msg("hitB x: "+ (shipGob.getHitBox().end.x -shipGob.getHitBox().begin.x) + "hitB x: "+(shipGob.getHitBox().end.y -shipGob.getHitBox().begin.y));
        ArrayList<Gob> logs = Finder.findObjectsInArea(new NAlias("barrel"), tree);
        for(Gob log : logs){
            new LiftObject(log).run(gui);
            PathFinder pf = new PathFinder( gui, shipGob);
            pf.ignoreGob ( log );
//            pf.setPhantom(ship, NHitBox.get("snekkja"));
            pf.setHardMode ( true);
            pf.run ();
            //double shift_x = hitBox.end.x - hitBox.begin.x;
            //double shift_y = hitBox.end.y - hitBox.begin.y;
            NUtils.activate(shipGob);
            NUtils.waitEvent(()->!NUtils.isPose(gui.map.player(),new NAlias("banzai")),5000);
        }


        return new Results(Results.Types.SUCCESS);
    }


    public TreeItemsCollection2(
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
