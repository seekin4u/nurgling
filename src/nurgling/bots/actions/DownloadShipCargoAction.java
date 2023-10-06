package nurgling.bots.actions;

import haven.Gob;
import nurgling.*;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

public class DownloadShipCargoAction implements Action {
    NAlias ship = new NAlias("knarr", "snekkja");
    @Override
    public Results run ( NGameUI gui ) throws InterruptedException {
        while (NUtils.takeGobFromCargo(gui, ship, new NAlias("/"))) {
            Thread.sleep(200);
            Gob gob = Finder.findLifted();
            new PlaceLifted(download_area, NHitBox.get(gob.getResName()), new NAlias(gob.getResName())).run(gui);
        }
        return new Results(Results.Types.SUCCESS);
    }


    public DownloadShipCargoAction(
            NArea download_area
    ) {
        this.download_area = download_area;
    }
    
    NArea download_area;
}
