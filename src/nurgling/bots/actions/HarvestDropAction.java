package nurgling.bots.actions;

import haven.Gob;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.PathFinder;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

public class HarvestDropAction implements Action {


    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        NArea input = Finder.findNearestMark ( harvest_area );
        new DropFromInventory(crop).run(gui);

        /// Выкапываем и роняем урожай. Рассчитано на плоды.
        while ( !Finder.findCropsInArea ( crop, input, isMaxStage ).isEmpty () ) {
            Gob plant = Finder.findCropInArea ( crop, 3000, input, isMaxStage );
            if ( plant != null ) {
                if(!gui.hand.isEmpty()){
                    NUtils.drop();
                    NUtils.waitEvent(() -> gui.hand.isEmpty(), 100);
                }
                if ( gui.getInventory ().getFreeSpace () < 5 ) {
                    new DropFromInventory(crop).run(gui);
                }
                if ( NUtils.getStamina() <= 0.3 ) {
                    new Drink ( 0.9, false ).run ( gui );
                }

                new PathFinder( gui, plant ).run ();
                int size = gui.getInventory ().getFreeSpace ();
                new SelectFlowerAction ( plant, "Harvest", SelectFlowerAction.Types.Gob ).run ( gui );
                NUtils.waitEvent ( () -> NUtils.getProg() < 0 && size != gui.getInventory ().getFreeSpace (), 60 );
            }

        }
        return new Results ( Results.Types.SUCCESS );
    }

    public HarvestDropAction(NAlias crop, AreasID harvest_area, boolean isMaxStage) {
        this.crop = crop;
        this.harvest_area = harvest_area;
        this.isMaxStage = isMaxStage;
    }

    AreasID harvest_area;
    NAlias crop;

    boolean isMaxStage = false;
}
