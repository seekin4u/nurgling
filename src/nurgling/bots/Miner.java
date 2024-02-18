package nurgling.bots;

import haven.*;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.bots.actions.ChipperAction;

import nurgling.bots.actions.TestAction;
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
//        NUtils.getGameUI().getDrinkmMod();
        //runActions.add ( new TestAction());
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException {
        super.initAction ();
//        int y = 0;
//        window.add ( new Label("Current Direction: East"), new Coord ( 0, y ));
//        y += 25;
//        window.add ( new Button ( window.buttons_size, "Change Direction" ), new Coord(0, y) );
//        y += 40;
//
//        window.add ( new CheckBox("Automine"), new Coord ( 0, y ));
//        window.add ( new CheckBox("Autofight"), new Coord ( 50, y ));
//        y += 25;
//
//        window.add ( new CheckBox("Autoroad"), new Coord ( 0, y ));
//        y += 25;
//
//        window.add ( new Label("Minerals in current grid:"), new Coord ( 0, y ));
//        y += 25;
//        window.add ( new Label("Black Ore: 0"), new Coord ( 20, y ));
//        y += 25;
//        window.add ( new Label("Bloodstone: 0"), new Coord ( 20, y ));
//        y += 25;
//        window.add ( new Label("Leaf Ore: 0"), new Coord ( 20, y ));
//        y += 25;
//        window.add ( new Label("Schrifterz: 0"), new Coord ( 20, y ));
//        y += 25;
//        window.add ( new Label("Direvein: 0"), new Coord ( 20, y ));
//        y += 25;
//        window.add ( new Label("Horn Silver: 0"), new Coord ( 20, y ));
//        y += 25;
//        window.add ( new Label("Silvershine: 0"), new Coord ( 20, y ));
//        y += 25;
        Widget prev = window.add (new Button ( window.buttons_size, "Drink" ) {
            @Override
            public void click () {
                //drink
                _start.set(true);
            }
        } );

        prev = window.add (new Label ( "Wine: " ), prev.c.add(0,80));

        window.add( new TextEntry(20, String.valueOf(NUtils.getGameUI().getDrinkList().n)),
                prev.c.add(40, 0));

        while ( !_start.get () ) {
            Thread.sleep ( 1000 );
        }
    }
    
    @Override
    public void endAction () {

        super.endAction ();
    }

    private AtomicBoolean _start = new AtomicBoolean ( false );
}
