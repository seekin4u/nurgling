package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.EquipTSacks;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.bots.actions.SeederSeed;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;
import java.util.Arrays;


public class HiveSmoker extends Bot {


    public HiveSmoker(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "HiveSmoker";
        win_sz.y = 100;


        runActions.add ( new EquipTSacks() );

        
    }
    
    
    @Override
    public void initAction () {
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }

}
