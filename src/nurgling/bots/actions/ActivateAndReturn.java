package nurgling.bots.actions;

import haven.*;
import haven.render.RenderTree;
import nurgling.*;
import nurgling.tools.Finder;

import java.awt.*;
import java.util.ArrayList;

public class ActivateAndReturn implements Action {
    private Gob objectToActivate;

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        Return(gui);
        objectToActivate = Finder.findObject(name);

        while (true) {
            if(CheckForFriend(gui)){
                Open();
                Return(gui);
                Thread.sleep(10000);
                Close();
                Return(gui);
            }

            Thread.sleep(5000);
        }
    }

    private boolean CheckForFriend(NGameUI gui){
        //objects in area!
        ArrayList<Gob> gobs = Finder.findObjects(new NAlias("borka"));
        for (Gob gob : gobs) {
            if(gob.id!=gui.map.player().id) {
                int group = KinInfo.getGroup(gob);
                Color color;

                if (group == 0 || group == -1 && gob.getattr(NGobHealth.class) == null) {
                    color = Color.WHITE;
                    //держим ворота закрытыми
                    return false;
                } else if (KinInfo.getGroup(gob) == 2) {
                    color = Color.RED;
                    //держим ворота закрытыми
                    return false;
                } else if(KinInfo.getGroup(gob) == 1){
                    //проверяем на зеленый цвет, открываем.
                    return true;
                }
            }
        }
        return false;
    }
    private void Return(NGameUI gui) throws InterruptedException {
        Coord2d start = Finder.findObject(new NAlias("runestone")).rc;
        PathFinder pf = new PathFinder ( gui, start );
        pf.run ();//go to runestone
    }
    private void Open(){
        objectToActivate = Finder.findObject(name);
        if(objectToActivate == null){
            //return new Results ( Results.Types.NO_WORKSTATION );
            return;
        }
        long att = objectToActivate.getModelAttribute();//closed attr=2
        if(att==2){
            NUtils.activate(objectToActivate);
        }
    }

    private void Close(){
        objectToActivate = Finder.findObject(name);
        if(objectToActivate == null){
            //return new Results ( Results.Types.NO_WORKSTATION );
            return;
        }
            long att = objectToActivate.getModelAttribute();//closed attr=2
        if(att==1){
            NUtils.activate(objectToActivate);
        }

    }
    private void Activate(){
        objectToActivate = Finder.findObject(name);
        if(objectToActivate == null){
            //return new Results ( Results.Types.NO_WORKSTATION );
            return;
        }
        long att = objectToActivate.getModelAttribute();//closed attr=2
        ChatUI.Channel chat = NUtils.getGameUI().chat.chat.sel;
        if (chat.getClass().getName().contains("Area")){
            ((ChatUI.EntryChannel) chat).send("Att:" + att);
            ((ChatUI.EntryChannel) chat).send("-----------");
        }
        NUtils.activate(objectToActivate);
        att = objectToActivate.getModelAttribute(); //opened attr=1
        if (chat.getClass().getName().contains("Area")){
            ((ChatUI.EntryChannel) chat).send("Att:" + att);
        }
    }
    public ActivateAndReturn(
            NAlias name
    ) {
        this.name = name;
    }
    
    NAlias name;
    
}
