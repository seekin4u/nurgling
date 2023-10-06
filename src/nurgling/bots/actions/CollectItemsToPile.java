package nurgling.bots.actions;

import haven.*;

import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectItemsToPile implements Action {
    
    NArea output = null;
    NArea input = null;

    AreasID in;
    AreasID out;

    NAlias items;
    NAlias ignor;
    
    public CollectItemsToPile(
            NArea output,
            NArea input,
            NAlias items
    ) {
        this.output = output;
        this.input = input;
        this.items = items;
    }

    public CollectItemsToPile(
            AreasID output,
            AreasID input,
            NAlias items
    ) {
        this.out = output;
        this.in = input;
        this.items = items;
    }

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        if(input == null){
            input = Finder.findNearestMark(in);
        }
        if(output == null)
        {
            output = Finder.findNearestMark(out);
        }
        ArrayList ext = items.exceptions;
        ext.add("stockpile");
        ext.add("barrel");

        NAlias collected_items = new NAlias(items.keys, ext);
        NAlias itemsS = new NAlias(items.keys);
        /// Выполняем процедуру подбора для каждого элемента в массиве
        while ( !Finder.findObjectsInArea ( itemsS, input ).isEmpty () ){
            GItem it = gui.getInventory ().getItem(itemsS);
//            gui.msg("free: " + gui.getInventory().getNumberFreeCoord(it));
            if ( (gui.getInventory ().getFreeSpace () <= 1 && !gui.getInventory ().getWItems().isEmpty ()) || gui.getInventory().getNumberFreeCoord(it)<=2) {
                new TransferToPile ( output, NHitBox.getByName ( items.keys.get ( 0 ) ), itemsS, itemsS ).run ( gui );
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
       
        new TransferToPile ( output, NHitBox.getByName ( items.keys.get ( 0 ) ), itemsS, itemsS ).run ( gui );
        return new Results ( Results.Types.SUCCESS );
    }
    
    
}
