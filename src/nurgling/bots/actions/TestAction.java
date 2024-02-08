package nurgling.bots.actions;

import haven.Gob;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NGob;
import nurgling.NUtils;
import nurgling.bots.settings.QuestWebhook;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.io.IOException;
import java.util.ArrayList;


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

        NUtils.isPose(NUtils.getGameUI().getMap().player(),new NAlias("idle"));

        //<gfx/borka/rowboat-d(v5)>
        //NUtils.waitEvent(()->NUtils.isPose(NUtils.getGameUI().getMap().player(),new NAlias("idle")),6000);

        NUtils.getGameUI().getDrinkList();
        return new Results(Results.Types.SUCCESS);
    }

    public TestAction() {
    }
}
