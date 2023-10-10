package nurgling.bots;

import haven.Button;
import haven.Coord;
import haven.Coord2d;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.bots.actions.NomadCalibration;
import nurgling.bots.actions.NomadTraveller;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WalkerMenu extends Bot {
    boolean m_start = false;

    /**
     * Базовый класс ботов
     *
     * @param gameUI Интерфейс клиента
     */

    public WalkerMenu(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Nomad Calibration";
        m_start = false;
        win_sz.y = 100;
        File rootDir = new File(".");
        List<File> datFiles = new ArrayList<>();
        findDatFiles(rootDir, datFiles);

        for (File datFile : datFiles) {
//            gameUI.msg("p: " + datFile.getName().getAbsolutePath());
//            System.out.println(2);
            startButton = new Button ( 120, datFile.getName() ){
                @Override
                public void click () {
                    path = datFile.getAbsolutePath();
//                    new Thread (runActions.add(new NomadTraveller(path, 22)), "Cont Action").start ();
//                    runActions.add(new NomadTraveller(path, 22));
                }
            };
            buttons.add(startButton);
            win_sz.y +=25;
        }


        startButton = new Button ( 120, "Запуск" ){
            @Override
            public void click () {
                _start.set ( true );
                b.set ( true );
                stopButton.show ();
            }
        };
        buttons.add(startButton);
        stopButton = new Button ( 120, "Стоп" ){
            @Override
            public void click () {
                b.set ( false );
                this.hide ();
                startButton.show ();
            }
        };
        buttons.add(stopButton);
    }
    
    @Override
    public void runAction ()
            throws InterruptedException {
        runActions.add(new NomadTraveller(path, 22));
//        runActions.add ( new NomadCalibration(area, b, coords) );
    }
    @Override
    public void initAction ()
            throws InterruptedException {
        /// Смещение компонентов по вертикали
        int y = 0;
        for (Button button : buttons){
            window.add ( button, new Coord ( 0, y ) );
            y+=25;
            win_sz.y +=25;
        }
//        window.add ( startButton, new Coord ( 0, y ) );
//        window.add ( stopButton, new Coord ( 0, y) );
        startButton.hide ();
        stopButton.hide ();
        window.add ( new Button ( window.buttons_size, "Стартовая зона" ) {
            @Override
            public void click () {
                gameUI.getMap ().isAreaSelectorEnable.set(true);
                if ( !m_selection_start.get () ) {
                    m_selection_start.set ( true );
                    
                    this.hide ();
                    new Thread ( new AreaSelecter( gameUI, _zone, m_selection_start, area ),
                            "Cont Area Selecter" ).start ();
                }
            }
        }, new Coord ( 0, y ) );
        while (!_zone.get ()) {
            Thread.sleep ( 100 );
        }
        startButton.show ();
        while ( !_start.get ()) {
            Thread.sleep ( 100 );
        }
    }
    
    @Override
    public void endAction () {
//        String path = "./nomad.dat";
//
//        DataOutputStream out = null;
//        try {
//            URL url = NUtils.class.getProtectionDomain ().getCodeSource ().getLocation();
//            out = new DataOutputStream(new FileOutputStream(path ));
//            for (Coord2d coord2d: coords) {
//                out.writeInt((int) coord2d.x);
//                out.writeInt((int) coord2d.y);
//            }
//            out.close();
//            gameUI.msg("URL:" + url + " Path:" + path);
//            gameUI.msg("NIO: " + java.nio.file.Paths.get(path).toAbsolutePath());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        _start.set ( false );
//        _zone.set ( false );
//        if(startButton!=null)
//            startButton.hide ();
//        if(stopButton!=null)
//            stopButton.hide ();
//        coords.clear();
//        super.endAction ();
    }
    public void findDatFiles(File directory, List<File> datFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findDatFiles(file, datFiles);
                } else if (file.isFile() && file.getName().endsWith(".dat")) {
                    datFiles.add(file);
                }
            }
        }
    }

    ArrayList<Coord2d> coords = new ArrayList<>();
    AtomicBoolean m_selection_start = new AtomicBoolean (false);
    AtomicBoolean _zone = new AtomicBoolean (false);
    NArea area = new NArea();
    Button startButton = null;
    ArrayList<Button> buttons = new ArrayList<>();
    Button stopButton = null;
    String path;
    private AtomicBoolean _start = new AtomicBoolean ( false );
    AtomicBoolean b = new AtomicBoolean ( false );
}
