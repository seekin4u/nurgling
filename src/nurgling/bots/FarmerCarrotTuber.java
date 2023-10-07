package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestDropAction;
import nurgling.bots.actions.SeederSeed;
import nurgling.bots.actions.SeederSeedPiles;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerCarrotTuber extends Bot {


    public FarmerCarrotTuber(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerFlax";
        win_sz.y = 100;
        NAlias seed = new NAlias ( Arrays.asList ( "carrot" ),
                Arrays.asList ( "items") );
        NAlias cult = new NAlias ( Arrays.asList ( "carrot" ),
                Arrays.asList ( "plants") );

        runActions.add ( new HarvestDropAction(seed, AreasID.carrot , true));
        runActions.add ( new nurgling.bots.actions.EquipTSacks () );
        runActions.add ( new CollectItemsToPile(AreasID.carrotTuber,AreasID.carrot, cult));
        runActions.add ( new SeederSeedPiles(new HarvestOut(seed, AreasID.carrot, AreasID.carrotTuber )) );
        
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
