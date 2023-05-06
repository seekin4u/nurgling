package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToSwill;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.tools.AreasID;



public class FarmerBarley extends Bot {


    public FarmerBarley(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerBarley";
        win_sz.y = 100;
        
        runActions.add ( new HarvestSeedAction(new NAlias("Barley"), AreasID.barley , true));
        runActions.add ( new CollectItemsToSwill(AreasID.barley,new NAlias("straw")));

    }
    
    
    @Override
    public void initAction () {
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
}
