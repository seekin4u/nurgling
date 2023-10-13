package nurgling.bots.actions;

import haven.Gob;
import nurgling.*;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class ChipperAction implements Action {
    public static ArrayList<String> chip_tools = new ArrayList<String> ( Arrays.asList ( "pickaxe", "stoneaxe" ) );
    public static NAlias stones = new NAlias ( new ArrayList<String> (
            Arrays.asList ( "alabaster", "apatite", "arkose", "basalt", "bat rock", "black coal", "black ore", "bloodstone", "breccia", "cassiterite",
                    "cat gold", "chalcopyrite", "chert", "cinnabar", "diabase", "diorite", "direvein", "dolomite", "dross", "eclogite", "feldspar",
                    "flint", "fluorospar", "gabbro", "galena", "gneiss", "granite", "graywacke", "greenschist", "heavy earth", "horn silver", "hornblende",
                    "iron ochre", "jasper", "korund", "kyanite", "lead glance", "leaf ore", "limestone", "malachite", "marble", "meteorite", "mica",
                    "microlite", "olivine", "orthoclase", "peacock ore", "pegmatite", "porphyry", "pumice", "quarryartz", "quartz", "rhyolite",
                    "rock crystal", "sandstone", "schist", "schrifterz", "serpentine", "shard of conch", "silvershine", "slag", "slate", "soapstone",
                    "sodalite", "sunstone", "wine glance", "zincspar"
            ) ) );
    
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        NAlias bum = new NAlias("bumlings");
        boolean withDrop = false;
        ArrayList<Gob> bumblings = Finder.findObjectsInArea(bum, bumbl_area);
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
