package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestDropAction;
import nurgling.bots.actions.SeederSeedPiles;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;


public class FarmerYellowOnion extends Bot {


    public FarmerYellowOnion(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerYellowOnion";
        win_sz.y = 100;

        runActions.add ( new HarvestDropAction(new NAlias("yellowonion"), AreasID.y_onion , true));
        runActions.add ( new CollectItemsToPile(AreasID.y_onion_bulb,AreasID.y_onion,new NAlias("yellowonion")));
        runActions.add ( new SeederSeedPiles(new HarvestOut( new NAlias( "yellowonion"), AreasID.y_onion, AreasID.y_onion_bulb )) );
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
