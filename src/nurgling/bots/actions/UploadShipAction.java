package nurgling.bots.actions;

import nurgling.*;
import nurgling.tools.NArea;

import java.util.Arrays;

public class UploadShipAction implements Action {
    NAlias ship = new NAlias("knarr", "snekkja");
    @Override
    public Results run ( NGameUI gui ) throws InterruptedException {
        while (NUtils.takeGobFromCargo(gui, ship, cargoGob)) {
            Thread.sleep(200);
            new PlaceLifted(tree, NHitBox.get(pile_name), cargoGob).run(gui);
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
