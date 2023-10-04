package nurgling.bots;

import nurgling.NGameUI;
import nurgling.bots.actions.Butching;
import nurgling.bots.actions.ButchingShip;


public class ButcherShip extends Bot {

    public ButcherShip(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Butcher";
        win_sz.y = 100;
 
        
        runActions.add ( new ButchingShip() );
        
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException { super.initAction();
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
}
