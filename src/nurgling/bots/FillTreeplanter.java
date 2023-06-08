package nurgling.bots;

import nurgling.NGameUI;
import nurgling.bots.actions.TreeplanterWaterIn;
import nurgling.bots.actions.WaterSkinWaterIn;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;

public class FillTreeplanter extends Bot {

    public FillTreeplanter(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Fill Treeplanter";
        win_sz.y = 100;
        runActions.add ( new TreeplanterWaterIn() );
        
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
