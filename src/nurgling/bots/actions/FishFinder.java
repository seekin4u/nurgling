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

public class FishFinder implements Action {
    @Override
    public Results run(NGameUI gui)
            throws InterruptedException {
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
                        gui.chat.chat.sel.wdgmsg("msg", "I found : " + name + "\040" + "!");
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

        }else{

        }
        return new Results(Results.Types.SUCCESS);
    }

    public FishFinder(NArea mark_area
    ) {
        this.mark_area = mark_area;
    }

    public FishFinder(String nomadPath){
        this.nomadPath = nomadPath;
    }

    public FishFinder() {
    }

    ArrayList<Coord2d> marks = new ArrayList<>();
    NArea mark_area = null;

    String nomadPath = null;
}
