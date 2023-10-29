package nurgling.bots;


import haven.Gob;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.Arrays;


//public class GardenPotMaker extends Bot {
//
//    public GardenPotMaker ( NGameUI gameUI ) {
//        super ( gameUI );
//        win_title = "GardenPotMaker";
//        win_sz.y = 100;
//
//        ArrayList<Action> gardenLoop = new ArrayList<>();
//        /// Ждем пока килны не потухнут
//        gardenLoop.add ( new WaitAction( () -> {
//            for ( Gob gob : Finder.findObjectsInArea ( new NAlias( "kiln" ), Finder.findNearestMark (AreasID.kilns) ) ) {
//                try {
//                    if ( Special.getGobModelAttribute ( gob ) == 1 ) {
//                        return true;
//                    }
//                }
//                catch ( InterruptedException e ) {
//                    e.printStackTrace ();
//                }
//            }
//            return false;
//        }, 500 ) );
//        gardenLoop.add ( new TakeAndPlace ( new NAlias ( "kiln" ), "Kiln", new NAlias ( "gardenpot" ),
//                AreasID.unmakedGarden,
//                AreasID.kilns) );
//        /// Заполняем килны горшками
//        gardenLoop.add ( new CraftInMachineAndTransfer ( new NAlias ( "kiln" ), "Kiln", AreasID.kilns, new char[]{ 'c',
//                't',
//                'g' },
//                new NAlias ( "potterswheel" ), 4, new ArrayList<TakeFromContainers> ( Arrays.asList (
//                new TakeFromContainers ( new NAlias ( "clay" ), new NAlias ( "clay" ), 10, AreasID.clay, "Stockpile" ) ) ),
//                "Garden Pot", new NAlias ( "gardenpot-u" ) ) );
//        /// Заполняем поленьями
//        gardenLoop
//                .add ( new FillFuelFromPiles( 12, new NAlias ( "coal" ), new NAlias ( "kiln" ), new NAlias ( "coal" ),
//                        AreasID.kilns, AreasID.coal ) );
//        /// Поджигаем
//        gardenLoop.add ( new LightGob( new NAlias ( "kiln" ), AreasID.kilns, 1 ) );
//
//        runActions.add ( new Loop ( gardenLoop,
//                () -> !Finder.findObjectsInArea ( new NAlias ( "clay" ), Finder.findNearestMark ( AreasID.clay ) ).isEmpty () ) );
//    }
//}
