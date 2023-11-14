package nurgling.bots;


import nurgling.NGameUI;
import nurgling.bots.actions.BackerAction;
import nurgling.bots.actions.BoneAshBurner;


public class BoneToAsh extends Bot {

    public BoneToAsh(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Bone to Ash";
        win_sz.y = 100;
        
        runActions.add (new BoneAshBurner());
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
