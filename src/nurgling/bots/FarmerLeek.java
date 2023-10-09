package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.EquipTSacks;
import nurgling.bots.actions.HarvestDropAction;
import nurgling.bots.actions.SeederSeedPiles;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerLeek extends Bot {


    public FarmerLeek(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerLeek";
        win_sz.y = 100;
        NAlias seed = new NAlias(new ArrayList<String>(Arrays.asList("beet")),
                new ArrayList<String>(Arrays.asList("leaves")));
        NAlias cult = new NAlias(new ArrayList<String>(Arrays.asList("leek")),
                new ArrayList<String>(Arrays.asList( "plants")));

        runActions.add ( new HarvestDropAction(new NAlias("leek"), AreasID.leek , true));
        runActions.add ( new EquipTSacks() );
        runActions.add ( new CollectItemsToPile(AreasID.leek_bulb, AreasID.leek, cult));
        runActions.add ( new SeederSeedPiles(new HarvestOut( new NAlias( "leek"), AreasID.leek, AreasID.leek_bulb )) );
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
