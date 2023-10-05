package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.HarvestToPileAction;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;


public class FarmerHope extends Bot {


    public FarmerHope(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerHope";
        win_sz.y = 100;
        /// Семена
        AreasID field = AreasID.hops;
        String seed = "hops";
//        String seed = "wine";
        /// Урожай
        AreasID stockpile = AreasID.cones;
        String cult = "cones";

        
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
