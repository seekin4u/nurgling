package nurgling.bots;

import haven.Button;
import nurgling.NGameUI;
import nurgling.bots.actions.TanningFluidMake;
import nurgling.bots.actions.WaterSkinWaterIn;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;

public class FillWaterskin extends Bot {

    public FillWaterskin(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Fill Waterskins";
        win_sz.y = 100;
        runActions.add ( new WaterSkinWaterIn() );
        
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
