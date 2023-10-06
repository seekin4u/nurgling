package nurgling.bots.actions;

import haven.Coord2d;
import haven.GItem;
import haven.Gob;
import haven.WItem;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.PathFinder;
import nurgling.tools.Finder;

import java.util.ArrayList;

import static haven.OCache.posres;
import static java.lang.Math.sqrt;

public class DropFromInventory implements Action {
    public DropFromInventory(NAlias name) {
        this._name = name;
    }


    @Override
    public Results run(NGameUI gui) throws InterruptedException {
        /// Ищем предмет в инвентаре
        ArrayList<GItem> ditems = gui.getInventory("Inventory").getWItems(_name);
        if (ditems.size() != 0) {
            for ( GItem item : ditems ) {
                item.wdgmsg("drop", item.sz, 1);
//                NUtils.drop(item);
            }
        }
        /// Возвращаем true Если предметы были найдены и сброшены
        return (gui.getInventory().getItem(_name) == null) ? new Results(Results.Types.SUCCESS) : new Results(Results.Types.DROP_FAIL);
    }

    NAlias _name;
}
