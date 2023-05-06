package nurgling.bots.actions;

import haven.Gob;
import haven.MCache;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectItemsToSwill implements Action {

    NArea input = null;

    AreasID in;

    NAlias items;

    public CollectItemsToSwill(
            NArea input,
            NAlias items
    ) {
        this.input = input;
        this.items = items;
    }

    public CollectItemsToSwill(
            AreasID input,
            NAlias items
    ) {
        this.in = input;
        this.items = items;
    }
    
    
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        if(input == null){
            input = Finder.findNearestMark(in);
        }
        NAlias collected_items = new NAlias(items.keys, new ArrayList<> ( Arrays.asList ( "stockpile" , "barrel") ));

        Gob swill_trough = Finder
                .findObjectInArea(new NAlias("trough"), 10000, Finder.findNearestMark(AreasID.swill));
        
        /// Выполняем процедуру подбора для каждого элемента в массиве
        while ( !Finder.findObjectsInArea ( items, input ).isEmpty () ){
            
            if ( gui.getInventory ().getFreeSpace () <= 1 && !gui.getInventory ().getWItems().isEmpty () ) {
                new TransferToTroughGob ( items, swill_trough ).run ( gui );
            }
            
            Gob item = Finder.findObjectInArea ( collected_items, 2000, input );
            if(item == null)
                break;
            /// Если предмет далеко, идем к нему с помощью ПФ
            if(item.rc.dist(gui.map.player().rc)> MCache.tilesz2.x) {
                PathFinder pf = new PathFinder(gui, item);
                pf.run();
            }
            /// Подбираем предмет
            NUtils.takeFromEarth ( item );
        }

        new TransferToTroughGob ( items, swill_trough ).run ( gui );
        //new TransferToPile ( output, NHitBox.getByName ( items.keys.get ( 0 ) ), items, items ).run ( gui );
        return new Results ( Results.Types.SUCCESS );
    }
    
    
}
