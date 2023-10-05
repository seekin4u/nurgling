package nurgling.bots.build;

import haven.Button;
import haven.Coord;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.Bot;
import nurgling.bots.actions.Build;
import nurgling.bots.tools.CraftCommand;
import nurgling.bots.tools.Ingredient;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class BuildHTable extends Bot {


    public BuildHTable(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "BuildHTable";
        win_sz.y = 125;
        
        CraftCommand command = new CraftCommand();
        command.command = new char[]{ 'b', 'p', 'h' };
        command.name = "Herbalist Table";
        command.ingredients = new ArrayList<Ingredient> ();
        Ingredient board = new Ingredient();
        board.item = new NAlias( "board" );
        board.isGroup = true;
        command.ingredients.add (board);
        command.spec_in_area.put(board,board_area);
        command.ing_count.put(board,4);
        Ingredient block = new Ingredient();
        block.item = new NAlias( "block" );
        block.isGroup = true;
        command.ingredients.add (block);
        command.spec_in_area.put(block,block_area);
        command.ing_count.put(block,4);
        Ingredient fibre = new Ingredient();
        fibre.item = new NAlias( "fibre" );
        fibre.isGroup = true;
        command.ingredients.add (fibre);
        command.spec_in_area.put(fibre,fibres_area);
        command.ing_count.put(fibre,8);
        ///Добавление цикла в действия бота
        runActions.add ( new Build( build_area, "htable", command ) );
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException {
        super.initAction ();
        int y = 0;
        window.add ( new Button ( window.buttons_size, "Building area" ) {
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
        window.add ( new Button ( window.buttons_size, "Boards/logs" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter ( gameUI, _zone1, m_selection_start, board_area ),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord ( 0, y ) );
        y += 25;
        window.add ( new Button ( window.buttons_size, "Blocks/logs" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter ( gameUI, _zone2, m_selection_start, block_area ),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord ( 0, y ) );
        y += 25;
        window.add ( new Button ( window.buttons_size, "Fibres" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    new Thread ( new AreaSelecter ( gameUI, _zone3, m_selection_start, fibres_area ),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord ( 0, y ) );
        y += 25;
        while ( !_start.get () || !_zone1.get () || !_zone2.get () || !_zone3.get () ) {
            Thread.sleep ( 100 );
        }
    }
    
    @Override
    public void endAction () {
        _start.set ( false );
        _zone1.set ( false );
        _zone2.set ( false );
        _zone3.set ( false );
        m_selection_start.set ( false );
        super.endAction ();
    }
    
    private AtomicBoolean _start = new AtomicBoolean ( false );
    private AtomicBoolean _zone1 = new AtomicBoolean ( false );
    private AtomicBoolean _zone2 = new AtomicBoolean ( false );
    private AtomicBoolean _zone3 = new AtomicBoolean ( false );
    private NArea build_area = new NArea ();
    private NArea board_area = new NArea ();
    private NArea block_area = new NArea ();
    private NArea fibres_area = new NArea ();
    private AtomicBoolean m_selection_start = new AtomicBoolean ( false );
}