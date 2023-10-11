package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.HiveSmokerAction;

import java.util.ArrayList;
import java.util.Arrays;


public class HiveSmoker extends Bot {


    public HiveSmoker(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "HiveSmoker";
        win_sz.y = 100;


        runActions.add ( new HiveSmokerAction() );

        
    }
    
    
    @Override
    public void initAction () {
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }

}
