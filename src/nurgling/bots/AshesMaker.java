package nurgling.bots;

import haven.Button;
import haven.Coord;
import nurgling.NGameUI;
import nurgling.bots.actions.AshesMakerAction;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;


public class AshesMaker extends Bot {

    public AshesMaker(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Ashes Maker";
        win_sz.y = 100;

        runActions.add(new AshesMakerAction());
    }

    @Override
    public void initAction () {
    }

    @Override
    public void endAction () {
        super.endAction ();
    }
}
