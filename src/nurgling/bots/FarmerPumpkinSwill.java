package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.bots.actions.SeederSeed;
import nurgling.bots.actions.SlicePumpkinSwill;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerPumpkinSwill extends Bot {


    public FarmerPumpkinSwill(NGameUI gameUI ) {
        super(gameUI);
        win_title = "FarmerPumpkin";
        win_sz.y = 100;

        runActions.add(new HarvestSeedAction(new NAlias(new ArrayList<String>(Arrays.asList("pumpkin", "Pumpkin")), new ArrayList<String>(Arrays.asList("items/pumpkin", "pumpkinflesh"))), AreasID.pumpkin, true));
        runActions.add ( new nurgling.bots.actions.EquipTSacks () );
        runActions.add(new SlicePumpkinSwill());
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
