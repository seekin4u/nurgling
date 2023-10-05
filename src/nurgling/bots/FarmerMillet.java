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


public class FarmerMillet extends Bot {


    public FarmerMillet(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerMillet";
        win_sz.y = 100;
        /// доливаем воды
        
        
        runActions.add ( new HarvestSeedAction(new NAlias(new ArrayList<String>(Arrays.asList("millet", "Millet")),new ArrayList<String>(Arrays.asList("straw"))), AreasID.millet , true));
        runActions.add ( new CollectItemsToPile(AreasID.straw,AreasID.millet, new NAlias("straw")));
        runActions.add ( new SeederSeed(new HarvestOut( new NAlias( "millet" ), AreasID.millet )) );
        
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
