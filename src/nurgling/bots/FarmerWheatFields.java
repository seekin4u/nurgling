package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.CollectItemsToSwill;
import nurgling.bots.actions.HarvestSeedAction;
import nurgling.bots.actions.NomadTraveller;
import nurgling.bots.actions.ReturnerNoLogout;
import nurgling.tools.AreasID;


public class FarmerWheatFields extends Bot {


    public FarmerWheatFields(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerWheat";
        win_sz.y = 100;

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./first_field.dat", 22));
        runActions.add(new HarvestSeedAction(new NAlias("Wheat"), AreasID.wheat , true));
        runActions.add(new CollectItemsToSwill(AreasID.wheat,new NAlias("straw")));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./second_field.dat", 22));
        runActions.add(new HarvestSeedAction(new NAlias("Wheat"), AreasID.wheat , true));
        runActions.add(new CollectItemsToSwill(AreasID.wheat,new NAlias("straw")));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./third_field.dat", 22));
        runActions.add(new HarvestSeedAction(new NAlias("Wheat"), AreasID.wheat , true));
        runActions.add(new CollectItemsToSwill(AreasID.wheat,new NAlias("straw")));
        /*runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./third_field.dat", 22));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./fourth_field.dat", 22));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./fifth_field.dat", 22));*/

        
    }
    
    
    @Override
    public void initAction () {
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
}
