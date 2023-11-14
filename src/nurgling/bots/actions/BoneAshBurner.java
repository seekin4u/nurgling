package nurgling.bots.actions;

import haven.Gob;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.Arrays;

public class BoneAshBurner implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        ArrayList<Gob> cauldrons = Finder
                .findObjectsInArea ( new NAlias ( "cauldron" ), Finder.findNearestMark ( AreasID.boilers ) );
        final NAlias bones = new NAlias ( new ArrayList<String> (
                Arrays.asList ( "/bone", "antlers", "adderskeleton", "claw", "beartooth", "antlers-moose",
                        "antlers" + "-reddeer", "tusk" ) ));
        ArrayList<Gob> currentKiln = new ArrayList<> ();

         while(true){
             new WaitAction( () -> {
                 for ( Gob gob : Finder.findObjectsInArea ( new NAlias ( "kiln" ), Finder.findNearestMark ( AreasID.kilns ) ) ) {
                     long a = gob.getModelAttribute();
                     long m = a;
                     if ( (gob.getModelAttribute() & 1) != 0 ) {
                         return true;
                     }
                 }
                 return false;
             }, 500 ).run(gui);
             for ( Gob kiln : Finder.findObjectsInArea ( new NAlias ( "kiln" ), Finder.findNearestMark(AreasID.kilns) ) ) {
                 new PathFinder(gui, kiln).run();
                 new OpenTargetContainer(kiln, "Kiln").run(gui);
                 if ( !gui.getInventory ( "Kiln" ).getAll ().isEmpty () ){
                     new TransferFromContainerToContainer ( new NAlias("Bone Ash"), kiln, "Kiln", new NAlias ( "Kiln" ), AreasID.boneashes ).run ( gui );
                 }
                 /*new TransferFromContainerToContainer( null, null, new NAlias ( "bone ash" ), AreasID.kilns, "Kiln",
                         AreasID.boneashes, "Chest", 16 );*/

                 new TakeFromPile(new NAlias("stockpile-bone"), 25, bones, AreasID.lqbone).run(gui);
                 new PathFinder(gui, kiln).run();
                 new OpenTargetContainer(kiln, "Kiln").run(gui);
                 if (gui.getInventory("Kiln").getAll().isEmpty()){
                     //неправильно перекидывает туда кости
                     new TransferToContainerIfPossible(bones, "Kiln").run(gui);
                     //new TransferFromContainerToContainer(new NAlias(new ArrayList<>(Arrays.asList( "bone", "Ash"))), kiln, "Kiln", new NAlias("kiln"), AreasID.boneashes).run(gui);
                 }
                 new TransferToPile(AreasID.lqbone, NHitBox.getByName("stockpile"), new NAlias ("stockpile-bone"),
                         bones).run(gui);

                 currentKiln.add(kiln);
                 new FillFuelFromPiles(6, new NAlias("branch"), currentKiln, new NAlias("branch"), AreasID.branch, 0).run(gui);
                 new LightGob (1, kiln).run(gui) ;
                 currentKiln.clear();

                 if(Finder.findObjectsInArea(new NAlias("bone"), Finder.findNearestMark(AreasID.lqbone )).isEmpty()){
                     return new Results (Results.Types.SUCCESS);
                 }
             }
             Thread.sleep ( 200 );
         }
    }
}
