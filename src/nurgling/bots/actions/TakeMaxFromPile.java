package nurgling.bots.actions;


import haven.Gob;

import haven.Window;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.PathFinder;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

public class TakeMaxFromPile implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        if ( inPile == null ) {
            if ( area == null ) {
                inPile = Finder.findObjectInArea ( new NAlias( "stockpile" ), 1000, Finder.findNearestMark ( input ) );
            }
            else {
                inPile = Finder.findObjectInArea ( new NAlias ( "stockpile" ), 1000, area );
            }
        }
        
        Results res;
        do {
            if ( inPile == null ) {
                return new Results ( Results.Types.SUCCESS );
            }
            new PathFinder( gui, inPile ).run ();
            if (fast){
                NUtils.activate(inPile, 1, 1);
                Thread.sleep(500);
            } else {
                new OpenTargetContainer ( inPile, "Stockpile" ).run ( gui );
                while ( NUtils.takeItemFromPile () ) {
                    ;
                }
            }
            if ( Finder.findObject ( inPile.id ) == null ) {
                inPile = Finder.findObjectInArea ( new NAlias ( "stockpile" ), 1000, Finder.findNearestMark ( input ) );
            }
            else {
                Window wpile = gui.getWindow ("Stockpile"  );
                if(wpile!=null)
                {
                    wpile.destroy();
                }
                return new Results ( Results.Types.SUCCESS );
            }
        }
        while ( inPile != null );
        return new Results ( Results.Types.NO_ITEMS );
    }
    
    public TakeMaxFromPile(AreasID input ) {
        this.input = input;
    }
    public TakeMaxFromPile(AreasID input, boolean fast) {
        this.input = input;
        this.fast=true;
    }
    
    public TakeMaxFromPile(Gob gob ) {
        this.inPile = gob;
    }
    public TakeMaxFromPile(Gob gob , boolean fast) {
        this.inPile = gob;
        this.fast=true;
    }
    
    public TakeMaxFromPile(NArea area ) {
        this.area = area;
    }

    public TakeMaxFromPile(NArea area , boolean fast) {
        this.area = area;
        this.fast=true;
    }

    AreasID input;
    Gob inPile = null;
    NArea area = null;
    boolean fast = false;
}
