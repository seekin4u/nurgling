package nurgling.bots;


import nurgling.NGameUI;
import nurgling.bots.actions.NomadFinder;
import nurgling.bots.actions.OysterFinder;
import nurgling.bots.actions.Returner;


public class OysterFinderBot extends Bot {


    public OysterFinderBot (NGameUI gameUI) {
        super (gameUI);
        win_title = "OysterFinder";
        win_sz.y = 100;
        runActions.add (new OysterFinder("./oyster1.dat"));
        runActions.add ( new Returner( false ) );

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
