package nurgling.bots;


import nurgling.NGameUI;
import nurgling.bots.actions.MonitorGob;
import nurgling.bots.actions.NomadFinder;
import nurgling.bots.actions.NomadTraveller;
import nurgling.bots.actions.Returner;


public class FinderBot extends Bot {


    public FinderBot(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Finder bot";
        win_sz.y = 100;
        runActions.add(new NomadTraveller("./fish1.dat"));
        runActions.add(new MonitorGob());

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
