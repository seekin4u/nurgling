package nurgling.bots;

import nurgling.NGameUI;
import nurgling.bots.actions.TestAction;
import nurgling.bots.actions.TreeplanterWaterIn;
import nurgling.tools.NArea;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestBot_ extends Bot {

    public TestBot_(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Fill Treeplanter";
        win_sz.y = 100;
        runActions.add(new TestAction());
        
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
