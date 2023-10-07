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
        NAlias tobaccoSeed = new NAlias ( Arrays.asList ( "pipeweed" ),
                Arrays.asList ( "tobacco") );
        NAlias tobaccoCult = new NAlias ( Arrays.asList ( "tobacco-fresh" ),
                Arrays.asList ( "pipeweed", "plants" ) );
        runActions.add ( new HarvestSeedAction(tobaccoSeed, AreasID.tobacco , true));
        runActions.add ( new nurgling.bots.actions.EquipTSacks () );
        runActions.add ( new CollectItemsToPile(AreasID.tobacco_leafs, AreasID.tobacco, tobaccoCult));
        runActions.add ( new SeederSeed(new HarvestOut( tobaccoSeed, AreasID.tobacco )) );
        
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
