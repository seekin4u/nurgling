package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.HarvestToPileAction;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.bots.actions.SeederSeed;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class FarmerGrape extends Bot {


    public FarmerGrape(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerGrape";
        win_sz.y = 100;
        /// Семена
        AreasID field = AreasID.wine;
        String seed = "wine";
//        String seed = "wine";
        /// Урожай
        AreasID stockpile = AreasID.grapes;
        String cult = "grapes";

        
        runActions.add ( new HarvestToPileAction(new NAlias(seed), new NAlias(cult), field , stockpile,  true));

        
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
