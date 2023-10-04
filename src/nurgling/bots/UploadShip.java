package nurgling.bots;

import haven.Button;
import haven.Coord;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.UploadShipAction;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;


public class UploadShip extends Bot {


    public UploadShip(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Upload Ship Barrel";
        win_sz.y = 100;
        NAlias cargoGob = new NAlias (  "barrel"  );
        
        ///Добавление цикла в действия бота
        runActions.add ( new UploadShipAction( upload_area , cargoGob,"barrel") );
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException { super.initAction();
        int y = 0;
        window.add ( new Button ( window.buttons_size, "Upload Zone" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter( gameUI, _start, m_selection_start, upload_area ),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord ( 0, y ) );
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
    private NArea upload_area = new NArea ();
    private AtomicBoolean m_selection_start = new AtomicBoolean ( false );
}
