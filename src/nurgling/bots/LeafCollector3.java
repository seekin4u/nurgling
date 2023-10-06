package nurgling.bots;

import haven.Button;
import haven.Coord;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.NomadTraveller;
import nurgling.bots.actions.ReturnerNoLogout;
import nurgling.bots.actions.TreeItemsCollection2;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.concurrent.atomic.AtomicBoolean;


public class LeafCollector3 extends Bot {


    public LeafCollector3(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Leaf Collector";
        win_sz.y = 100;


//        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./tt1.dat", 22));
        ///Добавление цикла в действия бота
        //runActions.add ( new TreeItemsCollection2( tree_area , pile_area, new NAlias("apple"),"stockpile-apple","Pick" ) );
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException { super.initAction();
        int y = 0;
        window.add ( new Button ( window.buttons_size, "Trees with leafs" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter( gameUI, _start, m_selection_start, tree_area ),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord ( 0, y ) );
        y+=25;
        window.add ( new Button ( window.buttons_size, "Output piles" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter ( gameUI, _zone, m_selection_start, pile_area ),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord ( 0, y ) );
        while ( !_start.get () || !_zone.get () ) {
            Thread.sleep ( 100 );
        }
    }
    
    @Override
    public void endAction () {
        _start.set ( false );
        _zone.set ( false );
        m_selection_start.set ( false );
        super.endAction ();
    }
    
    private AtomicBoolean _start = new AtomicBoolean ( false );
    private AtomicBoolean _zone = new AtomicBoolean ( false );
    private NArea tree_area = new NArea ();
    private NArea pile_area = new NArea ();
    private AtomicBoolean m_selection_start = new AtomicBoolean ( false );
}
