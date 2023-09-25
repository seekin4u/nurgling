package nurgling.bots;

import haven.Button;
import haven.Coord;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.DigCellarAction;
import nurgling.bots.actions.GlassJugWaterOut;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;


public class JugWaterOut extends Bot {
    
    
    public JugWaterOut(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "GlassJugWaterOut";
        win_sz.y = 100;
        
        runActions.add ( new GlassJugWaterOut( build_area , new NAlias("water")) );
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException {
        super.initAction ();
        int y = 0;
        window.add ( new Button ( window.buttons_size, "Barrels" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter( gameUI, _start, m_selection_start, build_area ),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord ( 0, y ) );
        y += 25;

        
        while ( !_start.get ()  ) {
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
    private NArea build_area = new NArea();
    private AtomicBoolean m_selection_start = new AtomicBoolean ( false );
}
