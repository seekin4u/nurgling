package nurgling.bots.actions;

import haven.*;
import nurgling.*;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static haven.MCache.tilesz;
import static haven.OCache.posres;
import static nurgling.bots.actions.NomadCalibration.anchors;

public class NomadTraveller implements Action {
    @Override
    public Results run(NGameUI gui)
            throws InterruptedException {
        marks.clear();

        URL url = NUtils.class.getProtectionDomain ().getCodeSource ().getLocation ();
        if(url != null) {
            try {
                if((NConfiguration.botmod!=null)){
                    if(NConfiguration.getInstance().nomadPath.length() > 0)
                        gui.msg("NomadTraveller/nomadPath:[" + NConfiguration.getInstance().nomadPath + "]");
                    else{
                        gui.msg("NomadTraveller/Botmod is !null but nomadPath is emtpy.");
                    }
                }else{
                    if(path.length()>0)
                        gui.msg("NomadTraveller/nomadPath:[" + path + "]");
                }
                DataInputStream in =
                        new DataInputStream( new FileInputStream ((NConfiguration.botmod!=null)?NConfiguration.botmod.nomad:path ));

                while (true) {
                    try {
                        if (!(in.available() > 0))
                            break;
                        marks.add(new Coord2d(in.readInt(), in.readInt()));
                    } catch (IOException e) {
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                gui.msg("No such file was found!");
                e.printStackTrace();
                return new Results(Results.Types.FAIL);
            }
            gui.msg("File is loaded");
        }

        //TODO: energy and water check;

        //ПРИВЯЗКА К ХФУ
        Gob pow = Finder.findObject(new NAlias("pow"));
        Coord2d shift = (mark_area!=null)?
                Finder.findObjectInArea(anchors, 3000, mark_area).rc : Finder.findObject(new NAlias("milestone-wood")).rc;
        for (Coord2d coord : marks) {
            Coord2d pos = coord.add(shift);
            Coord poscoord = pos.div(MCache.tilesz).floor();
            pos = new Coord2d((poscoord).x * tilesz.x + tilesz.x / 2, (poscoord).y * tilesz.y + tilesz.y / 2);
            gui.map.wdgmsg("click", Coord.z, pos.floor(posres), 1, 0);
            Coord2d finalPos = pos;
            do {
                NUtils.waitEvent(() -> gui.map.player().rc.dist(finalPos) < 5, 10);
                if(gui.map.player().rc.dist(finalPos) >= 5)
                    gui.map.wdgmsg("click", Coord.z, pos.floor(posres), 1, 0);
            }while(gui.map.player().rc.dist(finalPos) >= 5);

        }
        return new Results(Results.Types.SUCCESS);
    }

    public NomadTraveller(NArea mark_area
    ) {
        this.mark_area = mark_area;
    }

    public NomadTraveller(String path) {
        this.path = path;
    }
    public NomadTraveller(String path, int distance){
        this.path = path;
        this.distance = distance;
    }

    int distance = 110;
    String path;
    ArrayList<Coord2d> marks = new ArrayList<>();
    NArea mark_area = null;
}
