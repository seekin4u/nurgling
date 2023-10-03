package nurgling.bots.actions;

import haven.GItem;

import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.bots.tools.AItem;
import nurgling.bots.tools.Ingredient;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class TransferMeat implements Action {
    @Override

    public Results run ( NGameUI gui )
            throws InterruptedException {
        ArrayList<GItem> items = gui.getInventory().getWItems(new NAlias("meat"));

        NAlias raw_meat = new NAlias ( new ArrayList<String> ( Arrays.asList ( "meat" ) ) );

        for(GItem item:  items){
            if(item.contents!=null) {
                NUtils.destroyFCNbndl(item);
                NUtils.waitEvent(()->NUtils.getGameUI().getInventory().wmap.get(item)==null,50);
            }
        }
        for(GItem item : gui.getInventory().getWItems(new NAlias("meat"))){
            AItem ingredient = Ingredient.get(item);
            NAlias name = new NAlias(NUtils.getInfo(item));
            if(ingredient!=null) {
                new TransferItemsToBarter(ingredient.barter_out, name, true).run(gui);
                new FillContainers(name, ingredient.area_out, new ArrayList<>()).run(gui);
            }
        }


        new TransferItemsToBarter(AreasID.raw_meat, raw_meat, false).run(gui);
//        new FillContainers(new NAlias("meat"), AreasID.raw_meat,new ArrayList<>()).run(gui);

        return new Results ( Results.Types.SUCCESS );
    }
}
