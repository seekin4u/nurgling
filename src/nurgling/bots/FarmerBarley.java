package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.bots.actions.SeederSeed;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerBarley extends Bot {


    public FarmerBarley(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerBarley";
        win_sz.y = 100;
        
        runActions.add ( new HarvestSeedAction(new NAlias("Barley"), AreasID.barley , true));
        runActions.add ( new CollectItemsToPile(AreasID.straw, AreasID.barley, new NAlias("straw")));
        runActions.add ( new SeederSeed(new HarvestOut( new NAlias( "barley" ), AreasID.barley )) );
        
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
