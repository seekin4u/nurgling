package nurgling.bots.actions;

import haven.ChatUI;
import haven.Coord;
import haven.Gob;
import nurgling.*;
import nurgling.bots.settings.DiscordWebhookWrap;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.Arrays;

import static haven.OCache.posres;

public class MonitorGob implements Action {
    @Override
    public Results run(NGameUI gui) throws InterruptedException {
        while(true){
            Thread.sleep(1000);
            if (NUtils.alarmOrcalot()) {
                Gob target;
                target = Finder.findObject(new NAlias(new ArrayList<>(Arrays.asList("/orca", "/spermwhale")), new ArrayList<>(Arrays.asList("beef", "skeleton"))));
                if(target!=null && target.isTag(NGob.Tags.kritter_is_ready)) {
                    String name = target.getResName();
                    Long id = target.id;
                    for (ChatUI.Selector.DarkChannel chan : gui.chat.chat.chansel.chls) {
                        if (chan.chan.name().equals(NConfiguration.getInstance().village)) {
                            gui.chat.chat.select(chan.chan);
                            gui.chat.chat.sel.wdgmsg("msg", "I found : " + name + "\040" + "!");
                            DiscordWebhookWrap.Push("I have found OrcaWhale! <@196302145706786816>");
                        }
                    }

                }
            }
        }


        //return new Results(Results.Types.SUCCESS);
    }
}
