package nurgling.bots.actions;

import haven.Gob;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.PathFinder;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class CartInActions implements Action {
    public CartInActions(NArea input_area) {
        this.input_area = input_area;
    }

    @Override
    public Results run(NGameUI gui) throws InterruptedException {
        NAlias gobsName = new NAlias(new ArrayList<String>(Arrays.asList("log", "barrel", "crate", "chest")),
                new ArrayList<String>(Arrays.asList("leaves")));


        ArrayList<Gob> gobs = Finder.findObjectsInArea(gobsName, input_area);
        Gob cart = Finder.findNearestObjectToObject (new NAlias("cart"), gui.map.player());
//        Gob cart = Finder.findObjectInArea(new NAlias("cart"),1000, output_area);
        int max = 6;
        for (Gob gob : gobs){
            new PathFinder(gui, gob).run();
            NUtils.lift(gob);
            NUtils.waitEvent(()->NUtils.isPose(gui.map.player(),new NAlias("banzai")),200);
            NUtils.waitEvent(()->Finder.findLifted()!=null,200);
            new PathFinder(gui, cart).run();
            NUtils.activate(cart);
            NUtils.waitEvent(()->!NUtils.isPose(gui.map.player(),new NAlias("banzai")),200);
            max -= 1;
            if (max ==0){
                break;
            }
        }
        return new Results(Results.Types.SUCCESS);
    }

    NArea input_area;
}
