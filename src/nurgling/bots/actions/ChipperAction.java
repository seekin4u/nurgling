package nurgling.bots.actions;

import haven.Coord;
import haven.Coord2d;
import haven.Gob;
import nurgling.*;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

import static haven.OCache.posres;

public class ChipperAction implements Action {
    public static ArrayList<String> chip_tools = new ArrayList<String> ( Arrays.asList ( "pickaxe", "stoneaxe" ) );
    public static NAlias stones = new NAlias ( new ArrayList<String> (
            Arrays.asList ( "granite", "quartz","graywacke","hornblende", "feldspar", "gabbro", "catgold", "dolomit", "mica", "diorite",
                    "alabaster", "gneiss", "sandstone", "arkose", "schist","sodalite", "olivine" , "diabase", "cinnabar", "chert", "basalt", "porphyry", "flint" ) ) );
    
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        NAlias bum = new NAlias("bumlings");
        boolean withDrop = false;
        ArrayList<Gob> bumblings = Finder.findObjectsInArea(bum, bumbl_area);
        if(!bumblings.isEmpty()) {
            new Equip(new NAlias(chip_tools, new ArrayList<String>())).run(gui);
            /// Цикл рубки
            for (Gob bumbling : bumblings) {
                if (gui.getInventory().getFreeSpace() < 2) {
                    new TransferToPile(out_area, NHitBox.get(), new NAlias("stockpile-stone"),
                            stones).run(gui);
                }
                if (gui.getInventory().getFreeSpace() < 2) {
                    return new Results(Results.Types.NO_FREE_SPACE);
                }
                new PathFinder(gui, bumbling).run();
                if (Finder.findDressedItem(new NAlias("bucket-water")) != null) {
                    new Drop(Drop.Type.Back, new NAlias("bucket")).run(gui);
                    withDrop = true;
                }
                while (Finder.findObject(bumbling.id) != null) {
                    if (gui.getInventory().getFreeSpace() < 2) {
                        new TransferToPile(out_area, NHitBox.get(), new NAlias("stockpile-stone"),
                                stones).run(gui);
                    }
                    /// Пить
                    if (NUtils.getStamina() < 0.3) {
                        new Drink(0.9, withDrop).run(gui);
                    }
                    /// Выбрать рубку
                    new SelectFlowerAction(bumbling, "Chip", SelectFlowerAction.Types.Gob).run(gui);
                    /// Ждать пока дерево не срублено или стамина больше 0.3
                    NUtils.waitEvent(() -> NUtils.getProg() >= 0, 50);
                    do {
                        NUtils.waitEvent(() -> (NUtils.getProg() < 0 || NUtils.getStamina() < 0.3 ||
                                gui.getInventory().getFreeSpace() < 2), 20);
                        if (NUtils.getStamina() < 0.3 ||
                                gui.getInventory().getFreeSpace() < 2) {
                            NUtils.stopWithClick();
                            /// Пить
                            if (NUtils.getStamina() < 0.3) {
                                new Drink(0.9, withDrop).run(gui);
                                new Equip(new NAlias(chip_tools, new ArrayList<String>())).run(gui);
                            }

                        }
                        NUtils.waitEvent(() -> NUtils.getProg() >= 0, 10);
                    }
                    while (NUtils.getProg() >= 0);
                }
            }

            if (withDrop) {
                new TakeAndEquip(new NAlias("bucket-water"), true).run(gui);
            }
        }
        else
        {
            Coord2d mountain = bumbl_area.findMountain();
            if(mountain!=null)
            {
                new PathFinder( gui, mountain ).run ();
                do {
                    if (gui.getInventory().getFreeSpace() < 3) {
                        new TransferToPile(out_area, NHitBox.get(), new NAlias("stockpile-stone"),
                                stones).run(gui);
                        new PathFinder(gui, mountain).run();
                    }

                    NUtils.command(new char[]{'a', 'd'});
                    gui.map.wdgmsg("click", Coord.z, mountain.floor(posres), 1, 0);
                    NUtils.waitEvent(() -> NUtils.getProg() >= 0, 200);
                    NUtils.waitEvent(() -> (NUtils.getProg() < 0 || NUtils.getStamina() < 0.3 ||
                            gui.getInventory().getFreeSpace() < 3), 20000);
                    NUtils.stopWithClick();
                    if (NUtils.getStamina() < 0.3) {
                        /// Пить
                        new Drink(0.9, withDrop).run(gui);
                    }
                }
                while (true);
            }
        }
        new TransferToPile(out_area, NHitBox.get(), new NAlias("stockpile-stone"), stones).run(gui);
        return new Results(Results.Types.SUCCESS);

    }
    
    public ChipperAction(
            NArea tree_area,
            NArea out_area
    ) {
        this.bumbl_area = tree_area;
        this.out_area = out_area;
    }
    
    NArea bumbl_area;
    NArea out_area;
}
