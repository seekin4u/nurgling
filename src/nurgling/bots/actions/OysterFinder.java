package nurgling.bots.actions;

import haven.*;
import nurgling.*;
import nurgling.bots.settings.DiscordWebhookWrap;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static haven.MCache.tilesz;
import static haven.OCache.posres;
import static nurgling.bots.actions.NomadCalibration.anchors;

public class OysterFinder implements Action {
    @Override
    public Results run(NGameUI gui)
            throws InterruptedException {
         marks.clear();

        try {
            if(!NConfiguration.getInstance().nomadPath.isEmpty()){
                gui.msg(NConfiguration.getInstance().nomadPath);
            }
                DataInputStream in =
                     new DataInputStream(new FileInputStream(!NConfiguration.getInstance().nomadPath.isEmpty()?
                                                             NConfiguration.getInstance().nomadPath :
                                                             nomadPath));
                while (true) {
                     try {
                          if (!(in.available() > 0))
                               break;
                               marks.add(new Coord2d(in.readInt(), in.readInt()));
                     } catch (IOException e) {
                                 break;
                         }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        if(NUtils.alarm()) {
            //нашли животное, можно выключиться
        }
        if(NUtils.alarmFoe(gui)) {
            //нашли пидора, пишем там сообщение и можем выходить или просканить его.
        }
        Coord2d shift = (mark_area!=null)?Finder.findObjectInArea(anchors, 3000, mark_area).rc:Finder.findObject(anchors).rc;
        Coord2d prev_pos = new Coord2d();

        Gob ship = Finder.findObject(new NAlias("rowboat"));
        if(gui.hand.isEmpty()){
            NUtils.activate(ship);
            NUtils.waitEvent(()->NUtils.isPose(NUtils.getGameUI().getMap().player(),new NAlias("rowboat-d")),5000);
        }
        for (Coord2d coord : marks) {
            //fix the ship
            if(((GobHealth)ship.getattr(GobHealth.class)).hp <=0.5) {
                for (ChatUI.Selector.DarkChannel chan : gui.chat.chat.chansel.chls) {
                    if (chan.chan.name().equals(NConfiguration.getInstance().village)) {
                        gui.chat.chat.select(chan.chan);
                        DiscordWebhookWrap.Push("Fix the ship! <@196302145706786816>");
                        return new Results(Results.Types.NO_WORKSTATION);
                    }
                }

                Thread.sleep(2000);
            }
            Coord2d pos = coord.add(shift);
            Coord poscoord = pos.div(MCache.tilesz).floor();
            pos = new Coord2d((poscoord).x * tilesz.x + tilesz.x / 2, (poscoord).y * tilesz.y + tilesz.y / 2);
            gui.map.wdgmsg("click", Coord.z, pos.floor(posres), 1, 0);
            Coord2d finalPos = pos;
            prev_pos = pos;
            do {
                NUtils.waitEvent(() -> gui.map.player().rc.dist(finalPos) < 5, 10);
                if(gui.map.player().rc.dist(finalPos) >= 5)
                    gui.map.wdgmsg("click", Coord.z, pos.floor(posres), 1, 0);

                if(NUtils.alarm()){
                    while(!NUtils.alarm()){
                        if(NUtils.alarmFoe(gui)){
                            NUtils.logOut();
                        }
                        if(NUtils.alarmOrcalot()){
                            DiscordWebhookWrap.Push("I have found OrcaWhaleGreyseal! <@196302145706786816>");
                        }
                        Thread.sleep(1000);
                    }
                }
                if(NUtils.alarmFoe(gui)){
                    NUtils.logOut();
                    return new Results(Results.Types.FAIL);
                }
                if(NUtils.alarmOrcalot()){
                    DiscordWebhookWrap.Push("I have found OrcaWhaleGreyseal! <@196302145706786816>");
                }
                //gfx/terobjs/herbs/oystermushroom
                if (NUtils.alarmOyster()) {
                    ArrayList<Gob> gobs;
                    gobs = Finder.findObjectsInArea(
                            new NAlias("oyster"),
                            new NAlias("oystermushroom"),
                            new NArea(gui.map.player().rc, 3999));
                    if(!gobs.isEmpty()) {
                        for(Gob gob : gobs) {
                            if(NUtils.alarmFoe(gui)){
                                NUtils.logOut();
                                return new Results(Results.Types.FAIL);
                            }
                            if (gob != null) {
                                //заменить участок на обычный клик к каждой мидии по её координате, ПФ по воде долгий
                                gui.map.wdgmsg("click", Coord.z, gob.rc.floor(posres), 1, 0);
                                double temp_dist = gui.map.player().rc.dist(gob.rc);
                                while( (gui.map.player().rc.dist(gob.rc) >= 2) ){
                                    if(NUtils.alarmFoe(NUtils.getGameUI())){
                                        //log out
                                    }
                                    Thread.sleep(500);

                                    if(NUtils.alarmFoe(NUtils.getGameUI())){
                                        //log out
                                    }
                                    Thread.sleep(500);
                                    if(gui.map.player().rc.dist(gob.rc) == temp_dist){
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
                                if(NUtils.alarmFoe(NUtils.getGameUI())){
                                    //log out
                                }
                                new SelectFlowerAction(gob, "Pick", SelectFlowerAction.Types.Gob).run(gui);
                                if(!NUtils.waitEvent(() -> NUtils.getGob(gob.id) == null, 500)){
                                    //we can reroute there with pf again
                                    continue;
                                }
                            }
                        }
                    }
                    gui.map.wdgmsg("click", Coord.z, pos.floor(posres), 1, 0);
                    double temp_dist = gui.map.player().rc.dist(pos);
                    while( (gui.map.player().rc.dist(pos) >= 2) ){
                        if(NUtils.alarmFoe(NUtils.getGameUI())){

                        }
                        Thread.sleep(500);
                        if(NUtils.alarmFoe(NUtils.getGameUI())){

                        }
                        if(gui.map.player().rc.dist(pos) == temp_dist){
                            //we do not move
                            PathFinder pf = new PathFinder(gui, pos);
                            pf.enableAllWater(true);
                            pf.setHardMode(true);
                            pf.setDefaultDelta(4);
                            pf.setPhantom ( pos, NHitBox.getByName ( "cupboard" ) );
                            pf.run();
                            break;
                        }
                        temp_dist = gui.map.player().rc.dist(pos);
                    }
                }

                if(NUtils.alarmFoe(gui)){
                    NUtils.logOut();
                }
            }while(gui.map.player().rc.dist(finalPos) >= 5);

        }
        return new Results(Results.Types.SUCCESS);
    }

    public OysterFinder(NArea mark_area
    ) {
        this.mark_area = mark_area;
    }

    public OysterFinder(String nomadPath){
        this.nomadPath = nomadPath;
    }

    public OysterFinder() {
    }

    ArrayList<Coord2d> marks = new ArrayList<>();
    NArea mark_area = null;

    String nomadPath = null;
}
