/*package nurgling.bots;

import haven.Gob;

import haven.Area;
import haven.GItem;

import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.Action;
import nurgling.bots.actions.WaitAction;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import static nurgling.NGob.*;
import java.util.ArrayList;
import java.util.Arrays;


public class BoneAsh2 extends Bot {
    

    public BoneAsh2 ( NGameUI gameUI ) {
        super ( gameUI );
        win_title = "BoneAsh2";
        win_sz.y = 100;
        ArrayList<Action> boneloop = new ArrayList<> ();
        /// Ждем пока килны не потухнут
        boneloop.add ( new WaitAction( () -> {
            for ( Gob gob : Finder
                    .findObjectsInArea ( new NAlias ( "kiln" ), Finder.findNearestMark ( AreasID.kiln ) ) ) {
                try {
                    if ( Special.getGobModelAttribute ( gob ) == 1 ) {
                        return true;
                    }
                }
                catch ( InterruptedException e ) {
                }
            }
            return false;
        }, 200 ) );
        /// Забираем кости
        boneloop.add (
                new TransferFromContainerToContainer ( null, null, new NAlias ( "boneash" ), AreasID.kiln, "Kiln",
                        AreasID.boneash, "Chest", 16 ) );
        /// Заполняем килны с пайлов
        boneloop.add ( new FillContainersFromPiles ( 10, new NAlias ( "stockpile-bone" ), new NAlias ( "kiln" ),
                new NAlias ( new ArrayList<> ( Arrays.asList ( "bone", "antlers", "claw", "tusk", "tooth", "horn" ) ) ),
                AreasID.kiln,
                AreasID.lqbone, "Kiln" ) );
        boneloop.add ( new TransferToPile ( AreasID.lqbone, NHitBox.getByName ( "stockpile-bone" ),
                new NAlias ( "stockpile" + "-bone" ),
                new NAlias ( new ArrayList<> ( Arrays.asList ( "bone", "antlers", "claw", "tusk", "tooth", "horn" ) ) ) ) );
        /// Заполняем килны топливом с пайлов
        boneloop.add ( new FillFuelFromPiles ( 3, new NAlias ( "coal" ), new NAlias ( "kiln" ), new NAlias ( "coal" ),
                AreasID.kiln, AreasID.coal ) );
        /// Поджигаем
        boneloop.add ( new LightGob ( new NAlias ( "kiln" ), AreasID.kiln, 1 ) );
        
        
        runActions.add ( new Loop ( boneloop,
                () -> !Finder.findObjectsInArea ( new NAlias ( "bone" ), Finder.findNearestMark ( AreasID.lqbone ) )
                             .isEmpty () ) );
    }
    
    
    @Override
    public void initAction ()
            throws InterruptedException {
        super.initAction ();
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }

}*/
