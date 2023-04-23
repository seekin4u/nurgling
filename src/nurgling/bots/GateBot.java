package nurgling.bots;

import haven.*;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.bots.actions.ActivateAndReturn;


public class GateBot extends Bot {

    /**
     * Базовый класс ботов
     *
     * @param gameUI Интерфейс клиента
     */
    public GateBot(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Open/close gates";
        win_sz.y = 100;
        runActions.add ( new ActivateAndReturn(new NAlias( "polebiggate" )));

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
