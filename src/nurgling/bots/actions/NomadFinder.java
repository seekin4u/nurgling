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
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static haven.MCache.tilesz;
import static haven.OCache.posres;
import static nurgling.bots.actions.NomadCalibration.anchors;

public class NomadFinder implements Action {
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
        Gob ship = Finder.findObject(new NAlias("snekkja", "knarr"));
        Coord2d shift = (mark_area!=null)?Finder.findObjectInArea(anchors, 3000, mark_area).rc:Finder.findObject(anchors).rc;
        for (Coord2d coord : marks) {
            //fix the ship
            if(((GobHealth)ship.getattr(GobHealth.class)).hp <=0.15) {
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
            do {
                NUtils.waitEvent(() -> gui.map.player().rc.dist(finalPos) < 5, 10);
                if(gui.map.player().rc.dist(finalPos) >= 5)
                    gui.map.wdgmsg("click", Coord.z, pos.floor(posres), 1, 0);
                if (NUtils.alarmOrcalot()) {
                    Gob target;
                    if(NConfiguration.getInstance().alarmGreyseal)
                        target = Finder.findObject(new NAlias(new ArrayList<>(Arrays.asList("/orca", "/spermwhale", "/greyseal")), new ArrayList<>(Arrays.asList("beef", "skeleton"))));
                    else
                        target = Finder.findObject(new NAlias(new ArrayList<>(Arrays.asList("/orca", "/spermwhale")), new ArrayList<>(Arrays.asList("beef", "skeleton"))));
                    if(target!=null && target.isTag(NGob.Tags.kritter_is_ready)) {
                        String name = target.getResName();
                        Long id = target.id;
                        for (ChatUI.Selector.DarkChannel chan : gui.chat.chat.chansel.chls) {
                            if (chan.chan.name().equals(NConfiguration.getInstance().village)) {
                                gui.chat.chat.select(chan.chan);
                                //gui.chat.chat.sel.wdgmsg("msg", "");
                                DiscordWebhookWrap.Push("I have found OrcaWhale! <@196302145706786816>");
                                Thread.sleep(2000);
                            }
                        }
                        if(NConfiguration.getInstance().alarmGreyseal)
                            if (Finder.findObject(new NAlias(new ArrayList<>(Arrays.asList("/greyseal")), new ArrayList<>(Arrays.asList("beef", "skeleton")))) != null) {
                                while (NUtils.getGob(id) != null) {
                                    Gob targ = NUtils.getGob(target.id);
                                    if (targ != null) {
                                        gui.map.wdgmsg("click", Coord.z, targ.rc.floor(posres), 1, 0);
                                        Thread.sleep(1000);
                                    }
                                }
                                Thread.sleep(1000);
                            }

                        return new Results(Results.Types.FULL);
                    }
                }
                //here IF for picking up floatsam
                Gob floatsam = Finder.findObject(new NAlias("flotsam"));
                if(floatsam != null){
                    PathFinder pf = new PathFinder(gui, floatsam.rc);
                    pf.setWithAlarm(true);
                    pf.run();
                    new SelectFlowerAction ( floatsam, "Pick" , SelectFlowerAction.Types.Gob).run ( gui );
                    NUtils.waitEvent(()->NUtils.getGob(floatsam.id)==null, 1000);
                    //go back anyway
                    pf = new PathFinder(gui, pos);
                    pf.run();
                }
            }while(gui.map.player().rc.dist(finalPos) >= 5);

        }
        return new Results(Results.Types.SUCCESS);
    }

    public NomadFinder(NArea mark_area
    ) {
        this.mark_area = mark_area;
    }

    public NomadFinder(String nomadPath){
        this.nomadPath = nomadPath;
    }

    public NomadFinder() {
    }

    ArrayList<Coord2d> marks = new ArrayList<>();
    NArea mark_area = null;

    String nomadPath = null;
}
