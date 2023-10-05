package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestDropAction;
import nurgling.bots.actions.SeederSeedPiles;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;


public class FarmerBeet extends Bot {


    public FarmerBeet(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerBeet";
        win_sz.y = 100;

        runActions.add ( new HarvestDropAction(new NAlias("beet"), AreasID.beet , true));
        runActions.add ( new CollectItemsToPile(AreasID.beet_bulb,AreasID.beet,new NAlias("beet")));
        runActions.add ( new CollectItemsToPile(AreasID.beet_leafs,AreasID.beet,new NAlias("beetleaves")));
        runActions.add ( new SeederSeedPiles(new HarvestOut( new NAlias( "beet"), AreasID.beet, AreasID.beet_bulb )) );
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
