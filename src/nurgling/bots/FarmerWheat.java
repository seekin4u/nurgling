package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToSwill;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.tools.AreasID;


public class FarmerWheat extends Bot {


    public FarmerWheat(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerWheat";
        win_sz.y = 100;
        
        runActions.add ( new HarvestSeedAction(new NAlias("Wheat"), AreasID.wheat , true));
        runActions.add ( new CollectItemsToSwill(AreasID.wheat,new NAlias("straw")));

    }
    
    
    @Override
    public void initAction () {
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
}
