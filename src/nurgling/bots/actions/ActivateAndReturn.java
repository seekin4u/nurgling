package nurgling.bots.actions;

import haven.*;
import haven.render.RenderTree;
import nurgling.*;
import nurgling.NUtils.*;
import nurgling.tools.Finder;

import java.awt.*;
import java.util.ArrayList;

import static nurgling.NUtils.waitEvent;

public class ActivateAndReturn implements Action {
    private Gob objectToActivate;

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        Return(gui);
        objectToActivate = Finder.findObject(name);

        while (true) {
            if(Close())
                Return(gui);
            if(CheckForFriend(gui)){
                if(Open())
                    Return(gui);
                Thread.sleep(4000);
            }
            Thread.sleep(1000);
        }
    }
    private boolean CheckForFriend(NGameUI gui){
        ArrayList<Gob> gobs = Finder.findObjects(new NAlias("borka"));
        for (Gob gob : gobs) {
            if(gob.id!=gui.map.player().id) {
                int group = KinInfo.getGroup(gob);
                if (group == 0 || group == -1 && gob.getattr(NGobHealth.class) == null) {
                    return false;
                } else if (KinInfo.getGroup(gob) == 2) {
                    return false;
                } else if(KinInfo.getGroup(gob) == 1) {
                    synchronized ( NUtils.getGameUI().ui.sess.glob.oc ){
                        double dist = NUtils.getGameUI().map.player ().rc.dist ( gob.rc );
                        if (dist < 150)
                            return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
    private void Return(NGameUI gui) throws InterruptedException {
        Coord2d start = Finder.findObject(new NAlias("runestone")).rc;
        PathFinder pf = new PathFinder ( gui, start );
        pf.run ();
        Thread.sleep(400);
    }
    private boolean Open() throws InterruptedException {
        objectToActivate = Finder.findObject(name);
        if(objectToActivate == null){
            return false;
        }
        long att = objectToActivate.getModelAttribute();//closed attr=2
        if(att==2) {//if gates are closed - open them
            NUtils.activate(objectToActivate);
            Thread.sleep(400);
            return true;
        } else return false;//bro fuck off they are already opened somehow. Get rekt.
    }

    private boolean Close() throws InterruptedException {
        objectToActivate = Finder.findObject(name);
        if(objectToActivate == null){
            return false;
        }
        long att = objectToActivate.getModelAttribute();//closed attr=2
        if(att==1) {//opened so we need to close them
            NUtils.activate(objectToActivate);
            Thread.sleep(400);
            return true;
        } else return false;
    }
    public ActivateAndReturn(
            NAlias name
    ) {
        this.name = name;
    }
    NAlias name;
}
