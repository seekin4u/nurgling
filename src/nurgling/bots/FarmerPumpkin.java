package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.*;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerPumpkin extends Bot {


    public FarmerPumpkin(NGameUI gameUI ) {
        super(gameUI);
        win_title = "FarmerPumpkin";
        win_sz.y = 100;

        runActions.add(new HarvestSeedAction(new NAlias(new ArrayList<String>(Arrays.asList("pumpkin", "Pumpkin")), new ArrayList<String>(Arrays.asList("items/pumpkin", "pumpkinflesh"))), AreasID.pumpkin, true));
        runActions.add ( new nurgling.bots.actions.EquipTSacks () );
        runActions.add(new SlicePumpkinSwill(false));
        runActions.add (new CollectItemsToPile(AreasID.pumpkin_head, AreasID.pumpkin, new NAlias("pumpkin")));
        runActions.add(new SeederSeed(new HarvestOut(new NAlias("pumpkin"), AreasID.pumpkin)));

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
