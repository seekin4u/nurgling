package nurgling.bots.actions;

import haven.Coord;
import haven.GItem;
import haven.WItem;

import nurgling.NAlias;
import nurgling.NGItem;
import nurgling.NGameUI;
import nurgling.NUtils;

import static haven.OCache.posres;

public class CheckWater implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        NAlias water_cup = new NAlias( "woodencup");
        GItem item = gui.getInventory ().getItem ( water_cup );
        new TakeToHand ( item ).run ( gui );
        gui.map.wdgmsg ( "itemact", Coord.z, gui.getMap ().player ().rc.floor ( posres ), 0 );
        Thread.sleep ( 500 );

        NUtils.transferToInventory ();
        gui.msg ( "Water q = " + String.valueOf ( NUtils.getContentQuality ( gui.getInventory ().getItem ( water_cup) ) ) );
        gui.map.wdgmsg ( "click", Coord.z, gui.getMap ().player ().rc.floor ( posres ), 3, 0, 0);
        NUtils.waitEvent ( () -> gui.getInventory ().getItem ( water_cup ) != null, 100 );

        new SelectFlowerAction ( (NGItem) item, "Empty", SelectFlowerAction.Types.Item ).run ( gui );
        NUtils.waitEvent ( () -> NUtils.getContent (gui.getInventory ().getItem ( water_cup) ) == null, 200 );
        return new Results ( Results.Types.SUCCESS );
    }
}
