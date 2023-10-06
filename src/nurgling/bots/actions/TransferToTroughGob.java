package nurgling.bots.actions;

import haven.GItem;
import haven.Gob;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.PathFinder;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class TransferToTroughGob implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {

        NArea input = Finder.findNearestMark ( harvest_area );

        if(gob == null) gob = Finder
                .findObjectInArea(new NAlias("trough"), 10000, Finder.findSubArea(harvest_area, AreasID.swill)/*Finder.findNearestMark(AreasID.swill)*/);

        if(closest)
            gob = Finder.findObject(new NAlias("trough"));

        if(gob == null){
            gui.msg("TransferToTrough/gob is too far away or epsent.");
            return new Results(Results.Types.FAIL);
        }

        while ( !gui.getInventory ().getWItems( items ).isEmpty () ) {


            new PathFinder( gui, gob ).run ();
            for ( GItem item : gui.getInventory ().getWItems( items ) ) {
                do {
                    if ( gob.getModelAttribute() != 7 ) {
                        if ( gui.hand.isEmpty () ) {
                            new TakeToHand ( item ).run ( gui );
                        }
                        int counter = 0;
                        while ( !gui.hand.isEmpty () && counter < 20 ) {
                            NUtils.activateItem ( gob , true);
                            Thread.sleep ( 50 );
                            counter++;
                        }
                    }
                    else {
                        if(!gui.hand.isEmpty ())
                            NUtils.transferToInventory ();
                        NUtils.waitEvent ( ()->gui.hand.isEmpty (),60 );
                        new LiftObject ( gob ).run ( gui );
                        Gob cistern = Finder.findObjectInArea ( new NAlias ( new ArrayList<> ( Arrays
                                        .asList ( "cistern")) ),
                                1000,
                                Finder.findNearestMark ( AreasID.swill) );
                        PathFinder pf = new PathFinder ( gui, cistern );
                        pf.ignoreGob ( gob );
                        pf.run ();
                        NUtils.activate ( cistern );
                        int counter = 0;
                        while ( Finder.findObject ( gob.id ).getModelAttribute() == 7  &&
                                counter < 20) {
                            counter++;
                            Thread.sleep ( 50 );
                        }
                        new PlaceLifted ( Finder.findSubArea(harvest_area, AreasID.swill), gob.getHitBox(), new NAlias ("trough") ).run ( gui );
                    }
                }
                while ( !gui.hand.isEmpty () );
            }
        }
        return new Results ( Results.Types.SUCCESS );
    }

    public TransferToTroughGob(
            NAlias items
    ) {
        this.items = items;
    }
    public TransferToTroughGob(
            NAlias items,
            Gob troughToUse
    ) {
        this.items = items;
        this.gob = troughToUse;
    }

    public TransferToTroughGob(
            NAlias items,
            AreasID harvest_area
    ) {
        this.items = items;
        this.harvest_area = harvest_area;
    }

    public TransferToTroughGob(
            NAlias items,
            boolean closest
    ){
        this.items = items;
        this.closest = closest;
    }
    
    NAlias items;
    AreasID harvest_area = null;

    Gob gob = null;
    boolean closest = false;
}
