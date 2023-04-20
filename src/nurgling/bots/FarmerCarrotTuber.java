package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.HarvestDropAction;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerCarrotTuber extends Bot {


    public FarmerCarrotTuber(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerFlax";
        win_sz.y = 100;
        /// доливаем воды

        runActions.add ( new HarvestDropAction(new NAlias("Carrot"), AreasID.carrot , true));
        runActions.add ( new CollectItemsToPile(AreasID.carrotTuber,AreasID.carrot,new NAlias("Carrot")));
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
