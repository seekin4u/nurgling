package nurgling.bots.actions;

import haven.Coord2d;
import haven.GItem;
import haven.Gob;
import haven.WItem;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class AshesMakerAction implements Action {
    NAlias kiln = new NAlias(new ArrayList<>(Arrays.asList("Kiln", "kiln")),new ArrayList<>(Arrays.asList("tar")));

    String blocksName = "block";
    NAlias block = new NAlias ( Arrays.asList (blocksName),
            Arrays.asList ( "tobacco") );
    @Override
    public Results run(NGameUI gui) throws InterruptedException {
        NArea blocks = Finder.findNearestMark (AreasID.blocks);
        NArea kilns = Finder.findNearestMark (AreasID.kilns);
        Coord2d startTile = gui.map.player().rc;

        while (!Finder.findObjectsInArea(new NAlias(blocksName), blocks).isEmpty()) {

            /// Ждем пока килны не потухнут
            new WaitAction(() -> {
                for (Gob gob : Finder.findObjectsInArea(kiln, kilns)) {
                    if ((gob.getModelAttribute() & 1) != 0) {
                        return true;
                    }
                }
                return false;
            }, 500).run(gui);

            /// Одеваем ТСакс
//            new Equip(new NAlias("traveller"), 2).run(gui);

            /// Собираем эш
            new TransferToBarrelFromContainer(kiln, new NAlias("ashes"), AreasID.kilns, AreasID.kilns, "Kiln").run(gui);

            /// Закидываем блоки
            new FillContainers(block, AreasID.kilns, new ArrayList<>(),
                    new TakeMaxFromContainers(block,  AreasID.blocks, new ArrayList<>())).run(gui);

            /// Возращаем блоки
            new TransferToPile(AreasID.blocks, NHitBox.getByName(blocksName), block, block, true).run(gui);

            /// Заполняем топливо
            new FillFuelFromPiles(8, new NAlias("branch"), kiln,
                    new NAlias("branch"), AreasID.branch).run(gui);

            /// поджигаем с помощью факела
            new LightGobTorch(kiln, AreasID.kilns, 1).run(gui);

            /// Возращаемся на старт
            new PathFinder(gui, startTile).run();

        }

        return new Results(Results.Types.SUCCESS);
    }

    public AshesMakerAction() {}
}

