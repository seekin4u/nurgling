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
        NAlias seed = new NAlias(new ArrayList<String>(Arrays.asList("beet")),
                                new ArrayList<String>(Arrays.asList("leaves")));
        NAlias cult = new NAlias(new ArrayList<String>(Arrays.asList("beet")),
                new ArrayList<String>(Arrays.asList( "plants", "leaves")));
        NAlias leaves = new NAlias(new ArrayList<String>(Arrays.asList("beetleaves", "leaf")),
                new ArrayList<String>(Arrays.asList( "plants")));

        runActions.add ( new HarvestDropAction(seed, AreasID.beet , true));
        runActions.add ( new nurgling.bots.actions.EquipTSacks () );
        runActions.add ( new CollectItemsToPile(AreasID.beet_bulb,AreasID.beet, cult));
        runActions.add ( new CollectItemsToPile(AreasID.beet_leafs,AreasID.beet, leaves));
        runActions.add ( new SeederSeedPiles(new HarvestOut( seed, AreasID.beet, AreasID.beet_bulb )) );
        
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
