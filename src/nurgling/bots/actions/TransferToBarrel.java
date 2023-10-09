package nurgling.bots.actions;

import haven.GItem;
import haven.Gob;

import haven.Resource;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.PathFinder;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;

public class TransferToBarrel implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        
        while ( !gui.getInventory ().getWItems( items ).isEmpty () ) {
            ArrayList<Gob> gobs;
            if(targetBarrel==null) {
                gobs = Finder.findObjectsInArea(new NAlias("barrel"),
                        (area == null) ? Finder.findNearestMark(id) : area);
            }else{
                gobs = new ArrayList<>();
                gobs.add(targetBarrel);
            }
            Gob gob = null;
            for ( Gob candidate : gobs ) {
                if ( !full.contains ( gob ) ) {
                    if ( NUtils.isOverlay ( candidate, items ) ) {
                        gob = candidate;
                        break;
                    }
                }
            }
            if ( gob == null ) {
                for ( Gob candidate : gobs ) {
                    if ( !full.contains ( gob ) ) {
                        if ( !NUtils.isOverlay ( candidate ) ) {
                            gob = candidate;
                            break;
                        }
                    }
                }
            }
            if ( gob == null ) {
                return new Results ( Results.Types.NO_CONTAINER );
            }
            boolean empty = true;
            for ( Gob.Overlay ol : gob.ols ) {
                if ( ol.res != null ) {
                    empty = false;
                }
            }
            new PathFinder( gui, gob ).run ();
            for ( GItem item : gui.getInventory ().getWItems( items ) ) {
                if ( gui.hand.isEmpty () ) {
                    new TakeToHand ( item ).run ( gui );
                }
                NUtils.waitEvent(()->!gui.hand.isEmpty (),200);
                NUtils.activateItem ( gob , true);
                NUtils.waitEvent(()->gui.hand.isEmpty (),100);
                if ( !gui.hand.isEmpty () && !empty) {
                    full.add ( gob );
                    break;
                }
                if ( gui.getInventory().getNumberItem( items )==0 ) {
                    break;
                }
            }
        }
        return new Results ( Results.Types.SUCCESS );
    }
    
    public TransferToBarrel(
            AreasID id,
            NAlias items
    ) {
        this.id = id;
        this.items = items;
    }
    
    public TransferToBarrel(
            NArea area,
            NAlias items
    ) {
        this.area = area;
        this.items = items;
    }

    public TransferToBarrel(
            Gob targetBarrel,
            NAlias items
    ) {
        this.targetBarrel = targetBarrel;
        this.items = items;
    }

    AreasID id;
    NArea area = null;
    int size;
    ArrayList<Gob> full = new ArrayList<> ();
    NAlias items;

    Gob targetBarrel = null;
}
