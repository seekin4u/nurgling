package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestDropAction;
import nurgling.bots.actions.SeederSeedPiles;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerBeet extends Bot {


    public FarmerBeet(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerBeet";
        win_sz.y = 100;
        NAlias beet = new NAlias(new ArrayList<String>(Arrays.asList("beet")),
                                new ArrayList<String>(Arrays.asList("leaves", "plants")));

        runActions.add ( new HarvestDropAction(beet, AreasID.beet , true));
        runActions.add ( new CollectItemsToPile(AreasID.beet_bulb,AreasID.beet, beet));
        runActions.add ( new CollectItemsToPile(AreasID.beet_leafs,AreasID.beet,new NAlias("beetleaves")));
        runActions.add ( new SeederSeedPiles(new HarvestOut( new NAlias( "beet"), AreasID.beet, AreasID.beet_bulb )) );
        
    }
    
    
    @Override
    public void initAction () {
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
    
    HarvestOut seed;
    ArrayList<HarvestOut> harvestOuts = new ArrayList<> ();
}
