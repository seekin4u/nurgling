package nurgling.bots.actions;

import haven.*;
import nurgling.*;
import nurgling.bots.tools.CraftCommand;
import nurgling.minimap.NPMarker;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.HashMap;
import java.util.Random;

import static haven.MCache.tilesz;

public class HiveSmokerAction implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {

        Gob wildbeehive = Finder.findObject(new NAlias("wildbeehive"));

        Coord tc = gui.map.player().rc.floor(tilesz);
        Coord2d start = gui.map.player().rc;
        long id = gui.map.player().id;

        if (wildbeehive != null){
            Coord2d coord = new Coord2d(start.x-11, start.y-11);
            PathFinder pf = new PathFinder(gui, coord);
            pf.run();
            pf = new PathFinder(gui, start);
            pf.setPhantom(start, NHitBox.get("gfx/borka/body"));
            pf.run();
            CraftCommand command = new CraftCommand();
            command.command = new char[]{ 'a', 'f', 'y' };
            command.name = "Bough Pyre";
            new Build(new NArea(coord, start), "bpyre", command ).run(gui);
            tc = wildbeehive.rc.floor(tilesz);
            id = wildbeehive.id;
        }

        MapFile.Marker nm = new NPMarker(gui.mapfile.playerSegmentId(), tc, "Hive:"+id, BuddyWnd.gc[new Random().nextInt(BuddyWnd.gc.length)]);
        gui.mapfile.file.add(nm);


        NTimer tt= NTimer.add("Custom:"+id, 900000);
        tt.start();
        gui.timers.pack();


//        Thread.sleep(10000);
//        gui.mapfile.file.remove(nm);
        return new Results(Results.Types.SUCCESS);
    }

}
