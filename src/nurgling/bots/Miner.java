package nurgling.bots;

import haven.*;
import nurgling.NGameUI;
import nurgling.bots.actions.ChipperAction;

import nurgling.bots.actions.WaitAction;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;


public class Miner extends Bot {

    public Miner(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Miner";
        win_sz.y = 300;
        win_sz.x = 180;
        runActions.add ( new WaitAction( () -> false, 1000 ) );
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException {
        super.initAction ();
        int y = 0;
        window.add ( new Label("Current Direction: East"), new Coord ( 0, y ));
        y += 25;
        window.add ( new Button ( window.buttons_size, "Change Direction" ), new Coord(0, y) );
        y += 40;

        window.add ( new CheckBox("Automine"), new Coord ( 0, y ));
        window.add ( new CheckBox("Autofight"), new Coord ( 50, y ));
        y += 25;

        window.add ( new CheckBox("Autoroad"), new Coord ( 0, y ));
        y += 25;

        window.add ( new Label("Minerals in current grid:"), new Coord ( 0, y ));
        y += 25;
        window.add ( new Label("Black Ore: 0"), new Coord ( 20, y ));
        y += 25;
        window.add ( new Label("Bloodstone: 0"), new Coord ( 20, y ));
        y += 25;
        window.add ( new Label("Leaf Ore: 0"), new Coord ( 20, y ));
        y += 25;
        window.add ( new Label("Schrifterz: 0"), new Coord ( 20, y ));
        y += 25;
        window.add ( new Label("Direvein: 0"), new Coord ( 20, y ));
        y += 25;
        window.add ( new Label("Horn Silver: 0"), new Coord ( 20, y ));
        y += 25;
        window.add ( new Label("Silvershine: 0"), new Coord ( 20, y ));
        y += 25;

    }
    
    @Override
    public void endAction () {

        super.endAction ();
    }
    

}
