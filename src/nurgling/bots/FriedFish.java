package nurgling.bots;

import haven.Button;
import haven.Coord;
import nurgling.NGameUI;
import nurgling.bots.actions.FriedFishAction;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;


public class FriedFish extends Bot {


    public FriedFish(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FriedFish";
        win_sz.y = 100;
        
        runActions.add (new FriedFishAction(fish_area, out_area));
    }
    
    
    @Override
    public void initAction () throws InterruptedException{
        super.initAction ();
        int y = 0;
        window.add ( new Button( window.buttons_size, "Fresh Fish" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter( gameUI, _start, m_selection_start, fish_area),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord( 0, y ) );
        y+=30;
        window.add ( new Button( window.buttons_size, "Results" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter( gameUI, _out, m_selection_start, out_area),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord( 0, y ) );
        while ( !_start.get () ||  !_out.get ()  ) {
            Thread.sleep ( 100 );
        }
    }

    @Override
    public void endAction () {
        _start.set ( false );
        _out.set ( false );
        m_selection_start.set ( false );
        super.endAction ();
    }

    private AtomicBoolean _start = new AtomicBoolean ( false );
    private AtomicBoolean _out = new AtomicBoolean ( false );
    private NArea fish_area = new NArea ();
    private NArea out_area = new NArea ();
    private AtomicBoolean m_selection_start = new AtomicBoolean ( false );

}
