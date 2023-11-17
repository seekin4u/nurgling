package nurgling.bots.actions;

import haven.Gob;
import haven.res.ui.croster.CattleId;
import haven.res.ui.croster.Entry;
import haven.res.ui.croster.RosterWindow;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.function.Predicate;
//
//public class TakeAndPlace implements Action {
//    @Override
//    public Results run ( NGameUI gui )
//            throws InterruptedException {
//        ArrayList<Gob> in = Finder.findObjectsInArea ( output, Finder.findNearestMark ( inArea ) );
//
//        for ( Gob gob : in ) {
//            Results res = new Results ( Results.Types.SUCCESS );
//            do {
//                new PathFinder ( gui, gob ).run ();
//                new OpenTargetContainer ( gob, cap ).run ( gui );
//
//                res = new TakeAndLift ( cap, item ).run ( gui );
//                if ( res.type == Results.Types.NO_ITEMS ) {
//                    break;
//                }
//                Gob lifted = Finder.findObject ( item, 20 );
//                if ( new PlaceLifted ( outArea, NHitBox.getByName ( item.keys.get ( 0 ) ), item, lifted.id )
//                        .run ( gui ).type != Results.Types.SUCCESS ) {
//                    return new Results ( Results.Types.NO_PLACE );
//                }
//            }
//            while ( res.type == Results.Types.SUCCESS );
//        }
//        return new Results ( Results.Types.SUCCESS );
//    }
//
//    public TakeAndPlace (
//            NAlias output,
//            String cap,
//            NAlias item,
//            long outArea,
//            long inArea
//    ) {
//        this.output = output;
//        this.cap = cap;
//        this.item = item;
//        this.outArea = outArea;
//        this.inArea = inArea;
//    }
//
//    NAlias output;
//    String cap;
//    NAlias item;
//    AreasID outArea;
//    AreasID inArea;
//}
