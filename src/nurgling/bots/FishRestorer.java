package nurgling.bots;

import haven.Button;
import haven.CheckBox;
import haven.Coord;
import haven.Widget;
import nurgling.NGameUI;
import nurgling.bots.actions.Fishing;
import nurgling.bots.actions.RestoreFishrod;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;

public class FishRestorer extends Bot{


    public FishRestorer(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FishRestorer";
        win_sz.y = 150;

        ///Добавление цикла в действия бота
        runActions.add ( new RestoreFishrod());
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException { super.initAction();
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }

    private AtomicBoolean m_selection_start = new AtomicBoolean ( false );

    private AtomicBoolean disable_dropper = new AtomicBoolean ( false );
}
