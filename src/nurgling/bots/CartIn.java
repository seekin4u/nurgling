package nurgling.bots;

import haven.Button;
import haven.Coord;
import nurgling.NGameUI;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;
import nurgling.bots.actions.CartInActions;
import java.util.concurrent.atomic.AtomicBoolean;


public class CartIn extends Bot {


    public CartIn(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Chopper";
        win_sz.y = 100;

        runActions.add ( new CartInActions(in_area) );
    }


    @Override
    public void initAction ()
            throws InterruptedException { super.initAction();
//        int y = 0;
        gameUI.getMap ().isAreaSelectorEnable.set(true);
        if ( !m_selection_start.get () ) {
            m_selection_start.set ( true );
            new Thread ( new AreaSelecter ( gameUI, _start, m_selection_start, in_area),
                    "Cont Area Selecter" ).start ();
        }
//        window.add ( new Button ( window.buttons_size, "Input logs" ) {
//            @Override
//            public void click () {
//                gameUI.getMap ().isAreaSelectorEnable.set(true);
//                if ( !m_selection_start.get () ) {
//                    m_selection_start.set ( true );
//                    new Thread ( new AreaSelecter ( gameUI, _start, m_selection_start, in_area),
//                            "Cont Area Selecter" ).start ();
//                }
//            }
//        }, new Coord ( 0, y ) );
        while ( !_start.get () ) {
            Thread.sleep ( 100 );
        }
    }

    @Override
    public void endAction () {
        _start.set ( false );
        m_selection_start.set ( false );
        super.endAction ();
    }

    private AtomicBoolean _start = new AtomicBoolean ( false );
    private NArea in_area = new NArea ();
    private AtomicBoolean m_selection_start = new AtomicBoolean ( false );
}
