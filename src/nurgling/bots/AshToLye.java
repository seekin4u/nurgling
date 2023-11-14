package nurgling.bots;


import nurgling.NGameUI;
import nurgling.bots.actions.BoneAshBurner;
import nurgling.bots.actions.LyeBoiler;


public class AshToLye extends Bot {

    public AshToLye(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Ash to lye";
        win_sz.y = 100;
        
        runActions.add (new LyeBoiler());
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException {
        super.initAction ();
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
}
