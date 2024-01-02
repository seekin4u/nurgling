package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerTobacco extends Bot {


    public FarmerTobacco(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerTobacco";
        win_sz.y = 100;
        
        runActions.add ( new HarvestSeedAction(new NAlias(new ArrayList<String>(Arrays.asList("pipeweed", "Pipeweed"))), AreasID.tobacco , true));
        runActions.add ( new CollectItemsToPile(AreasID.tobaccoLeaves,AreasID.tobacco,new NAlias("tobacco")));
        //runActions.add ( new SeederSeed(new HarvestOut( new NAlias( "flax" ), AreasID.flax )) );
        
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
