package nurgling.bots;

import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.ActivateAndReturn;
import nurgling.bots.actions.ActivateCross;


public class CrossBot extends Bot {

    /**
     * Базовый класс ботов
     *
     * @param gameUI Интерфейс клиента
     */
    public CrossBot(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Check on cross";
        win_sz.y = 100;
        runActions.add ( new ActivateCross( "Milestone" ));

    }
    
    @Override
    public void runAction ()
            throws InterruptedException { }
    
    @Override
    public void initAction ()
            throws InterruptedException { super.initAction (); }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
}
