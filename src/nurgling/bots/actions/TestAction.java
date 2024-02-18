package nurgling.bots.actions;

import haven.Coord;
import haven.Gob;
import nurgling.*;
import nurgling.bots.settings.QuestWebhook;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.io.IOException;
import java.util.ArrayList;

import static haven.OCache.posres;


public class TestAction implements Action {

    public static boolean alarmOyster() {
        ArrayList<Gob> gobs;
        gobs = Finder.findObjectsInArea(
                new NAlias("oyster"),
                new NAlias("oystermushroom"),
                new NArea(NUtils.getGameUI().map.player().rc, 3999));
        if(!gobs.isEmpty())
            return true;
        return false;
    }

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {

        if(alarmOyster()){
            int a = 2;
            int b = 3;
        }

        //NUtils.isPose(NUtils.getGameUI().getMap().player(),new NAlias("idle"));

        //<gfx/borka/rowboat-d(v5)>
        //NUtils.waitEvent(()->NUtils.isPose(NUtils.getGameUI().getMap().player(),new NAlias("idle")),6000);

        if (NUtils.alarmOyster()) {
            ArrayList<Gob> gobs;
            gobs = Finder.findObjectsInArea(
                    new NAlias("oyster"),
                    new NAlias("oystermushroom"),
                    new NArea(gui.map.player().rc, 3999));
            if (!gobs.isEmpty()) {
                for (Gob gob : gobs) {
                    gui.map.wdgmsg("click", Coord.z, gob.rc.floor(posres), 1, 0);
                    double temp_dist = gui.map.player().rc.dist(gob.rc);
                    while ((gui.map.player().rc.dist(gob.rc) >= 2)) {
                        if (NUtils.alarmFoe(NUtils.getGameUI())) {
                            //log out
                        }
                        Thread.sleep(500);

                        if (NUtils.alarmFoe(NUtils.getGameUI())) {
                            //log out
                        }
                        Thread.sleep(500);
                        if (gui.map.player().rc.dist(gob.rc) == temp_dist) {
                            //we do not move
                            PathFinder pf = new PathFinder(gui, gob.rc);
                            pf.enableAllWater(true);
                            pf.setHardMode(true);
                            pf.setDefaultDelta(4);
                            pf.setPhantom ( gob.rc, NHitBox.getByName ( "cupboard" ) );
                            pf.run();
                            break;
                        }
                        temp_dist = gui.map.player().rc.dist(gob.rc);
                    }
                    new SelectFlowerAction(gob, "Pick", SelectFlowerAction.Types.Gob).run(gui);
                    if(!NUtils.waitEvent(() -> NUtils.getGob(gob.id) == null, 500)){
                        //we can reroute there with pf again
                        continue;
                    }
                }
            }
        }

        NUtils.getGameUI().getDrinkList();
        return new Results(Results.Types.SUCCESS);
    }

    public TestAction() {
    }
}
