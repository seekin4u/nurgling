package nurgling.bots.actions;

import haven.Coord;
import haven.GItem;
import haven.Gob;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.Arrays;

import static haven.OCache.posres;

public class LyeBoiler implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        ArrayList<Gob> cauldrons = Finder
                .findObjectsInArea ( new NAlias ( "cauldron" ), Finder.findNearestMark ( AreasID.boilers ) );
        ArrayList<Gob> cauldronToFill = new ArrayList<>();
        boolean toContinue = false;
        while(true){
            new WaitAction ( () -> {
                for ( Gob gob : cauldrons ) {
                    if ( ((gob.getModelAttribute() & 4) != 0) &&
                         ((gob.getModelAttribute() & 2) != 0)
                    ) {
                        return true;
                    }
                }
                return false;
            }, 100 ).run ( gui );
                for(Gob cauldron : cauldrons){
                new PathFinder( gui, cauldron ).run ();
                new CauldronAction ( cauldron, new NAlias ( "ash" ), new NAlias ( new ArrayList<> ( Arrays.asList ( "lye", "Lye" ) ) ),
                        AreasID.barrels, AreasID.barrels, true ).run(gui);
                cauldronToFill.add(cauldron);
                new FillFuelFromPiles(2, new NAlias("block"), cauldronToFill, new NAlias("block"),
                        AreasID.block, 1).run(gui);
                cauldronToFill.clear();
                new LightGob (2, cauldron).run(gui);
            }
            ArrayList<Gob> barrels = Finder
                    .findObjectsInArea(new NAlias("barrel"), Finder.findNearestMark(AreasID.barrels));

            for (Gob gob : barrels){
                if (!NUtils.isOverlay(gob, new NAlias("ash"))){
                    return new Results ( Results.Types.SUCCESS );
                }
            }
        }

    }
}
