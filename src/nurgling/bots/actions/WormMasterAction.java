package nurgling.bots.actions;

import haven.Gob;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class WormMasterAction implements Action {
    public WormMasterAction(NArea input_area, NArea output_area, NArea output_area_worms) {
        this.input_area = input_area;
        this.output_area = output_area;
        this.output_area_worms = output_area_worms;
    }

    @Override
    public Results run(NGameUI gui) throws InterruptedException {
        NAlias wormName = new NAlias(new ArrayList<String>(Arrays.asList("earthworm")),
                new ArrayList<String>(Arrays.asList("leaves")));
        NAlias earthName = new NAlias(new ArrayList<String>(Arrays.asList("mulch", "soil")),
                new ArrayList<String>(Arrays.asList("leaves")));
    while (Finder.findObjectInArea(new NAlias("stockpile-soil"),1000, input_area) != null) {
            new TakeMaxFromPile(input_area, true, new NAlias("stockpile-soil")).run(gui);
            new TransferToPile(output_area_worms, NHitBox.get("gfx/terobjs/stockpile-soil"), new NAlias("stockpile-soil"), wormName).run(gui);
            new TransferToPile(output_area, NHitBox.get("gfx/terobjs/stockpile-soil"), new NAlias("stockpile-soil"), earthName).run(gui);
        }
//        new FillContainers(wormName, output_area_worms, new ArrayList<>(),
//                new TakeMaxFromContainers(wormName, input_area, new ArrayList<>())).run(gui);
//        new FillContainers(wormName, output_area_worms, new ArrayList<>(),
//                new TakeMaxFromContainers(wormName, input_area, new ArrayList<>())).run(gui);
        return new Results(Results.Types.SUCCESS);
    }


    NArea input_area;
    NArea output_area;
    NArea output_area_worms;
}
