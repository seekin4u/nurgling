package nurgling.bots.actions;

import haven.Coord;
import haven.GItem;
import nurgling.*;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static haven.OCache.posres;

public class RestoreFishrod implements Action {
    NAlias bait_item = new NAlias(Arrays.asList("worm", "entrails", "pupae", "larvae", "leech"), new ArrayList<>());


    boolean isSpining = false;

    @Override
    public Results run(NGameUI gui)
            throws InterruptedException {
        try {


            isSpining = false;
            while (true) {
                /// Ищем удочку
                NAlias fish_rod_name = new NAlias("bushpole");
                NAlias primrod_name = new NAlias("primrod");
                GItem fish_rod = gui.getEquipment().getFreeSlot(fish_rod_name);
                if (fish_rod == null) {
                    fish_rod = gui.getInventory().getItem(fish_rod_name);
                }
                if (fish_rod == null) {

                    GItem primrod = gui.getEquipment().getFreeSlot(primrod_name);
                    if (primrod == null) {
                        primrod = gui.getInventory().getItem(primrod_name);
                    }
                    if (primrod == null) {
                        return new Results(Results.Types.NO_WORKSTATION);
                    } else {
                        fish_rod = primrod;
                        isSpining = true;
                    }
                }

                /// Ремонтируем удочку и пополняем наживку
                Results.Types types;
                if (!isSpining) {
                    types = new RestoreRod(new NArea(), new NArea(), fish_rod).run(gui).type;
                } else {
                    types = new RestorePrimRod(new NArea(), new NArea(), fish_rod, true).run(gui).type;
                }
                if (types != Results.Types.SUCCESS) {
                    return new Results(Results.Types.NO_ITEMS);
                }

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
