package nurgling.bots;


import nurgling.NGameUI;
import nurgling.bots.actions.FishFinder;
import nurgling.bots.actions.NomadFinder;
import nurgling.bots.actions.Returner;


public class FinderTestBot extends Bot {


    public FinderTestBot(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Finder test bot";
        win_sz.y = 150;
        //заменить травелера на  того который тревелит и смотрит за орками 2 в 1
        runActions.add(new FishFinder());
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
