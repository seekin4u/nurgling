package nurgling.bots.actions;

import haven.Coord2d;
import haven.GItem;
import haven.Gob;
import nurgling.*;
import nurgling.bots.tools.Ingredient;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.Arrays;


public class ButchingShip implements Action {

    private NAlias hides = new NAlias(new ArrayList<String>(Arrays.asList("blood", "raw")),
            new ArrayList<String>(Arrays.asList("stern")));

    static ArrayList<String> tools = new ArrayList<String>(Arrays.asList("stoneaxe", "axe-m"));


    @Override
    public Results run(NGameUI gui)
            throws InterruptedException {
        NAlias kritter = new NAlias("fox", "bear", "badger", "deer", "beaver", "raindeer", "lynx", "wolverine", "greyseal", "walrus", "moose", "wolf",
                "boar", "roe_deer", "mouflon", "wildgoat", "wildhorse", "reddeer", "cattle", "aurochs");
        NAlias ship = new NAlias("knarr", "snekkja");
        if (new Equip(new NAlias("butcherscleaver")).run(gui).type != Results.Types.SUCCESS)
            new Equip(new NAlias(tools, new ArrayList<String>())).run(gui);
        Coord2d placeTile = Finder.findNearestMark (AreasID.kritter).center;
        boolean CargoFull = true;
        while (NUtils.takeGobFromCargo(gui, ship, kritter)){
            Gob lifted = Finder.findLifted();
            //from res take name of gob
            //String nameShip = ship.getResName().substring(ship.getResName().lastIndexOf('/') + 1);
            new PlaceLifted(placeTile, NHitBox.get("moose"), new NAlias(lifted.getResName())).run(gui);
            ArrayList<Gob> gobs = Finder.findObjectsInArea(new NAlias("kritter"),
                    Finder.findNearestMark(AreasID.kritter));


            for (Gob gob : gobs) {
                if (gob == null) {
                    return new Results(Results.Types.NO_ITEMS);
                }

                /// Снимаем шкуры
                new CollectFromGob("Skin", gob, new NAlias("butch")).run(gui);
                NUtils.waitEvent(() -> !gui.getInventory().getWItems(hides).isEmpty(), 5);
                new CollectFromGob("Scale", gob, new NAlias("butch")).run(gui);
                NUtils.waitEvent(() -> !gui.getInventory().getWItems(hides).isEmpty(), 5);
                new CollectFromGob("Break", gob).run(gui);
                NUtils.waitEvent(() -> !gui.getInventory().getWItems(hides).isEmpty(), 5);


                /// Чистим
                new CollectFromGob("Clean", gob, new NAlias("butch")).run(gui);
                new TransferRawHides().run(gui);
                new TransferTrash().run(gui);

                new TransferIngredient("Suckling's Maw").run(gui);

               // new TransferButCury().run(gui);
                if (!NUtils.isIt(gob, new NAlias(new ArrayList<>(Arrays.asList("orca", "spermwhale"))))) {
                    /// Собираем мясо
                    new CollectFromGob("Butcher", gob, new NAlias("butch")).run(gui);
                } else {
                    new CollectFromGob("Cut", gob, new NAlias("bushpi")).run(gui);
                }
                new TransferMeat().run(gui);

                ArrayList<GItem> fat_and_other = gui.getInventory().getWItems(new NAlias("animalfat", "fishyeyeball"));
                if(fat_and_other.size()>0) {
                    if (Finder.findObjectInArea(new NAlias("barter"), 1000, Finder.findNearestMark(AreasID.fat)) !=
                            null) {
                        new TransferItemsToBarter(AreasID.fat, new NAlias("animalfat"), false).run(gui);
                    } else {
                        new FillContainers(new NAlias("animalfat", "fishyeyeball"), AreasID.fat, new ArrayList<>()).run(gui);
                    }
                }

                for(GItem item : gui.getInventory().getWItems(new NAlias("Bollock")))
                {
                    new TransferItemsToBarter(Ingredient.get(item).barter_out, new NAlias(NUtils.getInfo(item)),true).run(gui);
                }

                new TransferIngredient("Beast Unborn").run(gui);

                new CollectFromGob("Collect bones", gob).run(gui);

                new TransferBones().run(gui);


            }
        }
        new PathFinder(gui, placeTile).run();
        return new Results(Results.Types.SUCCESS);
    }

    public ButchingShip(
    ) {
    }

}
