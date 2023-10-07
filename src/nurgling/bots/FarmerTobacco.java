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


public class FarmerTobacco extends Bot {


    public FarmerTobacco(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerTobacco";
        win_sz.y = 100;
        /// доливаем воды
        NAlias seed = new NAlias ( Arrays.asList ( "pipeweed" ),
                Arrays.asList ( "tobacco") );
        NAlias cult = new NAlias ( Arrays.asList ( "pipeleaves", "tobacco-fresh" ),
                Arrays.asList ( "pipeweed", "plants" ) );
        runActions.add ( new HarvestSeedAction(seed, AreasID.tobacco , true));
        runActions.add ( new nurgling.bots.actions.EquipTSacks () );
        runActions.add ( new CollectItemsToPile(AreasID.tobacco_leafs, AreasID.tobacco, cult));
        runActions.add ( new SeederSeed(new HarvestOut( seed, AreasID.tobacco )) );
        
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
