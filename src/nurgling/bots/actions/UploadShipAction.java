package nurgling.bots.actions;

import haven.Gob;
import nurgling.*;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.Arrays;

public class UploadShipAction implements Action {
    NAlias ship = new NAlias("knarr", "snekkja");
    @Override
    public Results run ( NGameUI gui ) throws InterruptedException {
        while (NUtils.takeGobFromCargo(gui, ship, new NAlias("/"))) {
            Thread.sleep(500);
            Gob gob = Finder.findLifted();
            new PlaceLifted(tree, NHitBox.get(gob.getResName()), new NAlias(gob.getResName())).run(gui);
        }
        return new Results(Results.Types.SUCCESS);
    }


    public UploadShipAction(
            NArea tree,
            NAlias cargoGob,
            String pile_name
    ) {
        this.tree = tree;
        this.cargoGob = cargoGob;
        this.pile_name = pile_name;
    }
    
    NArea tree;
    NAlias cargoGob;
    String pile_name;
}
