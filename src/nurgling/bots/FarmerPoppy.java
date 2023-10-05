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


public class FarmerPoppy extends Bot {


    public FarmerPoppy(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerPoppy";
        win_sz.y = 100;
        /// Семена
        AreasID field = AreasID.poppy;
        String seed = "poppy";
        /// Урожай
        AreasID stockpile = AreasID.poppy_flower;
        String cult = "flower";

        
        runActions.add ( new HarvestSeedAction(new NAlias(new ArrayList<String>(Arrays.asList(seed)),new ArrayList<String>(Arrays.asList(cult))), field , true));
        runActions.add ( new CollectItemsToPile(stockpile, field, new NAlias(cult)));
        runActions.add ( new SeederSeed(new HarvestOut( new NAlias( seed ), field )) );
        
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
