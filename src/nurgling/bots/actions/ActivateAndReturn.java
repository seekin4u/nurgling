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
            if(CheckForFriend(gui)){
                waitEvent(()->Open(), 100);
                Return(gui);
                Thread.sleep(10000);
                waitEvent(()->Close(), 100);
                Return(gui);
            }
            Thread.sleep(5000);
        }
    }
    private boolean DistanceToFriend(){
        ArrayList<Gob> friend = Finder.findAllinDistance(new NAlias("borka"), 5000);
        return true;
    }
    private boolean CheckForFriend(NGameUI gui){
        //objects in area!
        ArrayList<Gob> gobs = Finder.findObjects(new NAlias("borka"));
        for (Gob gob : gobs) {
            if(gob.id!=gui.map.player().id) {
                int group = KinInfo.getGroup(gob);
                Color color;

                if (group == 0 || group == -1 && gob.getattr(NGobHealth.class) == null) {
                    //держим ворота закрытыми
                    return false;
                } else if (KinInfo.getGroup(gob) == 2) {
                    //держим ворота закрытыми
                    return false;
                } else if(KinInfo.getGroup(gob) == 1){
                    //проверяем на зеленый цвет, открываем.
                    synchronized ( NUtils.getGameUI().ui.sess.glob.oc ){
                        double dist = NUtils.getGameUI().map.player ().rc.dist ( gob.rc );
                        if (dist > 5000) return false;
                    }

                    return true;
                }
            }
        }
        return false;
    }
    private void Return(NGameUI gui) throws InterruptedException {
        Coord2d start = Finder.findObject(new NAlias("runestone")).rc;
        PathFinder pf = new PathFinder ( gui, start );
        pf.run ();
    }
    private boolean Open(){
        objectToActivate = Finder.findObject(name);
        if(objectToActivate == null){
            //return new Results ( Results.Types.NO_WORKSTATION );
            return false;
        }
        long att = objectToActivate.getModelAttribute();//closed attr=2
        if(att==2){
            NUtils.activate(objectToActivate);
        }
        return true;
    }

    private boolean Close(){
        objectToActivate = Finder.findObject(name);
        if(objectToActivate == null){
            //return new Results ( Results.Types.NO_WORKSTATION );
            return false;
        }
            long att = objectToActivate.getModelAttribute();//closed attr=2
        if(att==1){
            NUtils.activate(objectToActivate);
        }
        return true;
    }
    public ActivateAndReturn(
            NAlias name
    ) {
        this.name = name;
    }
    
    NAlias name;
    
}
