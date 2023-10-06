package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToPile;
import nurgling.bots.actions.CollectItemsToSwill;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.bots.actions.SeederSeed;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;


public class FarmerWheat extends Bot {


    public FarmerWheat(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerWheat";
        win_sz.y = 100;
        
        runActions.add ( new HarvestSeedAction(new NAlias("Wheat"), AreasID.wheat , true));
        runActions.add ( new nurgling.bots.actions.EquipTSacks () );
        runActions.add ( new CollectItemsToPile(AreasID.straw, AreasID.wheat, new NAlias("straw")));
        runActions.add ( new SeederSeed(new HarvestOut( new NAlias( "wheat" ), AreasID.wheat )) );

    }
    
    
    @Override
    public void initAction () {
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
}
