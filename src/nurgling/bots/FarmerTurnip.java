package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestDropAction;
import nurgling.bots.actions.SeederSeedPiles;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;


public class FarmerTurnip extends Bot {


    public FarmerTurnip(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerTurnip";
        win_sz.y = 100;
        /// доливаем воды

        runActions.add ( new HarvestDropAction(new NAlias("turnip"), AreasID.turnip , true));
        runActions.add ( new CollectItemsToPile(AreasID.turnip_tuber,AreasID.turnip,new NAlias("turnip")));
        runActions.add ( new SeederSeedPiles(new HarvestOut( new NAlias( "turnip"), AreasID.turnip, AreasID.turnip_tuber )) );
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
