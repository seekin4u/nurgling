package nurgling.bots.actions;

import haven.Coord2d;
import haven.Gob;

import nurgling.*;
import nurgling.NExceptions.NoFreeSpace;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

public class PlaceLifted implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        try {
            if(plObj == null) {
                plObj = Finder.findObject(item);
            }
            if(plObj == null) {
                NUtils.waitEvent ( () -> Finder.findObject ( item ) != null, 200 );
                plObj = Finder.findObject ( item);
                if(plObj==null)
                    return new Results ( Results.Types.NO_WORKSTATION );
            }
            if ( coord == null ) {
                if ( !withId ) {
                    if(item!=null){
                        coord = Finder.findPlace2 ( hitBox, (area==null)?Finder.findNearestMark ( area_id ):area, item.keys.get ( 0 ) );
                    }else{
                        coord = Finder.findPlace2 ( hitBox, (area==null)?Finder.findNearestMark ( area_id ):area, "" );
                    }

                }
                else {
                    coord = Finder.findPlace ( hitBox, (area==null)?Finder.findNearestMark ( area_id ):area, "" );
                }
            }
            PathFinder pf = new PathFinder( gui, coord );
            pf.setPhantom ( coord, hitBox );
            pf.ignoreGob ( plObj );
            pf.setHardMode ( true );
            pf.setOneSize ( true );
            pf.run ();
            //double shift_x = hitBox.end.x - hitBox.begin.x;
            //double shift_y = hitBox.end.y - hitBox.begin.y;
            NUtils.place ( coord );
            NUtils.waitEvent(()->!NUtils.isPose(gui.map.player(),new NAlias("banzai")),200);
            if ( !NUtils.isPose(gui.map.player(),new NAlias("banzai"))) {
                return new Results ( Results.Types.SUCCESS );
            }
        }
        catch ( NoFreeSpace noFreeSpace ) {
            return new Results ( Results.Types.NO_FREE_SPACE );
        }
        return new Results ( Results.Types.FAIL );
    }

    public PlaceLifted(NArea output_area, NHitBox hitBox, Gob gob) {
        plObj = gob;
        this.area = output_area;
        this.hitBox = new NHitBox(hitBox);
    }

    public PlaceLifted (
            AreasID area_id,
            NHitBox hitBox,
            NAlias item
    ) {
        this.area_id = area_id;
        this.hitBox = new NHitBox(hitBox);
        this.item = item;
    }

    public PlaceLifted (
            NArea area,
            NHitBox hitBox,
            NAlias item
    ) {
        this.area = area;
        this.hitBox = new NHitBox(hitBox);
        this.item = item;
    }

    public PlaceLifted (
            AreasID area_id,
            NHitBox hitBox,
            Gob obj
    ) {
        this.area_id = area_id;
        this.hitBox = new NHitBox(hitBox);
        this.plObj = obj;
    }

    public PlaceLifted (
            Coord2d place,
            NHitBox hitBox,
            NAlias item
    ) {
        this.coord = place;
        this.hitBox = new NHitBox(hitBox);
        this.item = item;
    }

    AreasID area_id;
    NArea area = null;
    long id;
    boolean withId;
    NHitBox hitBox;
    NAlias item = null;
    Coord2d coord = null;

    Gob plObj = null;
}
