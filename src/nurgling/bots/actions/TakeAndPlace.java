package nurgling.bots.actions;

import haven.*;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.NInventory;

import java.util.ArrayList;
import java.util.Arrays;

import static nurgling.NUtils.waitEvent;

public class TakeAndPlace implements Action {
    @Override
    public Results run (NGameUI gui )
            throws InterruptedException {
        NAlias pot = new NAlias ( "pot" );
        if(Finder.findObjectsInArea ( new NAlias("Kiln"), Finder.findNearestMark(AreasID.kilns) ).isEmpty())
            return new Results(Results.Types.NO_WORKSTATION);

        while(true){
        new WaitAction( () -> {
            for ( Gob gob : Finder.findObjectsInArea ( new NAlias ( "Kiln" ), Finder.findNearestMark ( AreasID.kilns ) ) ) {
                if ( (gob.getModelAttribute() & 1) != 0 ) {
                    return true;
                }
            }
            return false;
        }, 500 ).run(gui);
        ArrayList<Gob> kilnToFill = new ArrayList<>();

        for ( Gob gob : Finder.findObjectsInArea ( new NAlias("Kiln"), Finder.findNearestMark(AreasID.kilns) ) ) {
            new PathFinder(gui, gob).run();
            new OpenTargetContainer(gob, "Kiln").run(gui);
            if (!gui.getInventory("Kiln").getGItems(pot).isEmpty()) {
                ArrayList<GItem> items = gui.getInventory("Kiln").getGItems(pot);
                for (int i = 0; i < items.size(); i++) {
                    new PathFinder(gui, gob).run();
                    if (new OpenTargetContainer(gob, "Kiln").run(gui).type == Results.Types.SUCCESS) {
                        waitEvent(() -> gui.getInventory("Kiln") != null, 300);
                        GItem itemmm = gui.getInventory("Kiln").getItem(pot);
                        if (NUtils.getName((NGItem) itemmm).contains("-u")) continue;
                        WItem itemww = gui.getInventory("Kiln").getItem(itemmm);
                        itemww.item.wdgmsg("take", itemww.c);
                        NUtils.waitEvent(()->NUtils.isPose(gui.map.player(),new NAlias("banzai")),200);
                    }
                    new PlaceLifted(AreasID.gpots, NHitBox.getByName("gardenpot"), Finder.getNearestGardenPot()).run(gui);
                }
            }
            new TakeFromContainers(new NAlias("chest"), new NAlias(new ArrayList<>(Arrays.asList("pot"))), 4, AreasID.unbackedgpots, "Chest").run(gui);
            new PathFinder ( gui, gob ).run ();
            new OpenTargetContainer ( gob, "Kiln" ).run ( gui );
            new TransferToContainerIfPossible(new NAlias("pot"), "Kiln").run(gui);
            if ( gui.getInventory ( "Kiln" ).getAll ().isEmpty () ){
                return new Results(Results.Types.NO_ITEMS);
            }
            kilnToFill.add(gob);
            new FillFuelFromPiles ( 23, new NAlias ( "branch" ), kilnToFill, new NAlias ( "branch" ), AreasID.branch, 0 ).run ( gui );
            kilnToFill.clear();
            new LightGob (1, gob).run(gui) ;
        }

        Thread.sleep ( 500 );
        //если в контейнерах во входной зоне нет больше горшков - кидаем саксес
        //return new Results ( Results.Types.SUCCESS );
        }
    }


    public TakeAndPlace() {
    }

}
