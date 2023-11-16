package nurgling.bots.actions;

import haven.GItem;
import haven.Gob;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TakeAndPlace implements Action {
    @Override
    public Results run (NGameUI gui )
            throws InterruptedException {
        NAlias pot = new NAlias ( "Garden Pot" );
        if(Finder.findObjectsInArea ( new NAlias("Kiln"), Finder.findNearestMark(AreasID.kilns) ).isEmpty())
            return new Results(Results.Types.NO_WORKSTATION);
        //ожидаем пока килн догорает

        //нужно брать запеченные горшки из инвентаря
        for ( Gob gob : Finder.findObjectsInArea ( new NAlias("Kiln"), Finder.findNearestMark(AreasID.kilns) ) ) {
            //забираем горшок
            new PathFinder( gui, gob ).run ();
            new OpenTargetContainer ( gob, "Kiln" ).run ( gui );
            if(!gui.getInventory("Kiln").getWItems(pot).isEmpty()){
                ArrayList<GItem> items = gui.getInventory("Kiln").getWItems(pot);
                for( GItem item: items){
                    if(NUtils.getName((NGItem) item).contains("Unfired")) continue;
                    new PathFinder( gui, gob ).run ();
                    new OpenTargetContainer ( gob, "Kiln" ).run ( gui );
                    item.wdgmsg("iact", item.sz, 0);
                    new PlaceLifted( AreasID.gpots, NHitBox.getByName ( "gardenpot" ), new NAlias ( "garden pot" ) ).run ( gui );
                    //ловить экзепшен если не смог поставить горшок и придумать как его поставить рядом и закончить жарку.
                }
            }

            //кладем новые горшки  по 4 из сундуков
            //докидываем 23 палки
            //поджигаем

        }


        Thread.sleep ( 500 );
        //если в контейнерах во входной зоне нет больше горшков - кидаем саксес
        return new Results ( Results.Types.SUCCESS );
    }


    public TakeAndPlace() {
    }

}
