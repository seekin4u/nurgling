package nurgling.bots.actions;

import haven.Gob;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

public class HarvestToPileAction implements Action {


    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        NArea input = Finder.findNearestMark ( harvest_area );

        if(output == null)
        {
            output = Finder.findNearestMark(out);
        }
//        new DropFromInventory(crop).run(gui);
        new TransferToPile ( output, NHitBox.getByName ( items.keys.get ( 0 ) ), items, items ).run ( gui );
        /// Выкапываем и роняем урожай. Рассчитано на плоды.
        while ( !Finder.findCropsInArea ( crop, input, isMaxStage ).isEmpty () ) {
            Gob plant = Finder.findCropInArea ( crop, 3000, input, isMaxStage );
            if ( plant != null ) {
                if(!gui.hand.isEmpty()){
                    NUtils.drop();
                    NUtils.waitEvent(() -> gui.hand.isEmpty(), 100);
                }
                if ( gui.getInventory ().getFreeSpace () < 5 ) {
                    new TransferToPile ( output, NHitBox.getByName ( items.keys.get ( 0 ) ), items, items ).run ( gui );
//                    new DropFromInventory(crop).run(gui);
                }
                if ( NUtils.getStamina() <= 0.3 ) {
                    new Drink ( 0.9, false ).run ( gui );
                }

                PathFinder pf =  new PathFinder(gui, plant);
//                pf.setTrellis(true);
                pf.run ();
                int size = gui.getInventory ().getFreeSpace ();
                new SelectFlowerAction ( plant, "Harvest", SelectFlowerAction.Types.Gob ).run ( gui );
                NUtils.waitEvent ( () -> NUtils.getProg() < 0 && size != gui.getInventory ().getFreeSpace (), 60 );
            }

        }
        return new Results ( Results.Types.SUCCESS );
    }

    public HarvestToPileAction(NAlias crop, NAlias items, AreasID harvest_area, AreasID output, boolean isMaxStage) {
        this.crop = crop;
        this.items = items;
        this.harvest_area = harvest_area;
        this.out = output;
        this.isMaxStage = isMaxStage;
    }

    AreasID harvest_area;
    AreasID out;
    NArea output = null;
    NAlias crop;
    NAlias items;
    boolean isMaxStage = false;
}
