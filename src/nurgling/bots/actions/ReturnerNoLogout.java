package nurgling.bots.actions;

import haven.Gob;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.tools.Finder;

public class ReturnerNoLogout implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        if(!isShip) {
            NUtils.command(new char[]{'a', 'h', 'h'});
            NUtils.waitEvent(() -> NUtils.getProg() >= 0, 200);
            NUtils.waitEvent(() -> NUtils.getProg() < 0, 10000);
            NUtils.waitEvent(() -> NUtils.getGob(NUtils.getGameUI().map.player().id) != null, 2000);
            Thread.sleep(2000);
        }
        else
        {
            Gob ship = Finder.findObject(new NAlias("snekkja", "knarr"));
            if(ship!=null){
                new SelectFlowerAction(ship,"Travel to home dock", SelectFlowerAction.Types.Gob).run(gui);
                NUtils.waitEvent(() -> NUtils.getProg() >= 0, 200);
                NUtils.waitEvent(() -> NUtils.getProg() < 0, 10000);
                NUtils.waitEvent(() -> Finder.findObject(new NAlias("knarrdock"))!=null, 10000);
            }
        }
        return new Results ( Results.Types.SUCCESS );
    }

    public ReturnerNoLogout(boolean isShip) {
        this.isShip = isShip;
    }

    public ReturnerNoLogout(){
    }

    boolean isShip = false;
}
