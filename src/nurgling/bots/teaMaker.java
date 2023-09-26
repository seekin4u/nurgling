package nurgling.bots;

import nurgling.NGameUI;
import nurgling.tools.NArea;
import nurgling.bots.actions.teaMakerAction;

import java.util.concurrent.atomic.AtomicBoolean;

public class teaMaker extends Bot {

    public teaMaker(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Fill Waterskins";
        win_sz.y = 100;
        runActions.add ( new teaMakerAction() );
        
    }

    @Override
    public void initAction ()
            throws InterruptedException { super.initAction();

    }

    @Override
    public void endAction () {
        super.endAction ();
    }

    private AtomicBoolean _start = new AtomicBoolean ( false );
    private NArea area = new NArea ();
    private AtomicBoolean m_selection_start = new AtomicBoolean ( false );

}
