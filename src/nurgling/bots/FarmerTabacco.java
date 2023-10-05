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


public class FarmerTabacco extends Bot {


    public FarmerTabacco(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerTabacco";
        win_sz.y = 100;
        /// доливаем воды
        
        
        runActions.add ( new HarvestSeedAction(new NAlias(new ArrayList<String>(Arrays.asList("pipeweed")),new ArrayList<String>(Arrays.asList("tabacco"))), AreasID.tabacco , true));
        runActions.add ( new CollectItemsToPile(AreasID.tabacco_leafs,AreasID.tabacco,new NAlias("tabacco")));
        runActions.add ( new SeederSeed(new HarvestOut( new NAlias( "pipeweed" ), AreasID.tabacco )) );
        
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
