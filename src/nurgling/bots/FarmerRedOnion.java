package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestDropAction;
import nurgling.bots.actions.SeederSeedPiles;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;


public class FarmerRedOnion extends Bot {


    public FarmerRedOnion(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerRedOnion";
        win_sz.y = 100;

        runActions.add ( new HarvestDropAction(new NAlias("redonion"), AreasID.r_onion , true));
        runActions.add ( new CollectItemsToPile(AreasID.r_onion_bulb,AreasID.r_onion,new NAlias("redonion")));
        runActions.add ( new SeederSeedPiles(new HarvestOut( new NAlias( "redonion"), AreasID.r_onion, AreasID.r_onion_bulb )) );
        //runActions.add ( new HarvestSeedAction(new NAlias(new ArrayList<String>(Arrays.asList("flax", "Flax"))), AreasID.flax , true));
        //runActions.add ( new CollectItemsToPile(AreasID.flaxFibre,AreasID.flax,new NAlias("fibre")));
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
