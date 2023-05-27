package nurgling.bots;


import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.*;
import nurgling.bots.tools.HarvestOut;
import nurgling.tools.AreasID;

import java.util.ArrayList;


public class FarmerBarleyFields extends Bot {


    public FarmerBarleyFields(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "FarmerBarley";
        win_sz.y = 100;

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./first_field.dat", 22));
        runActions.add(new HarvestSeedAction(new NAlias("Barley"), AreasID.barley , true));
        runActions.add(new CollectItemsToSwill(AreasID.barley,new NAlias("straw")));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./second_field.dat", 22));
        runActions.add(new HarvestSeedAction(new NAlias("Barley"), AreasID.barley , true));
        runActions.add(new CollectItemsToSwill(AreasID.barley,new NAlias("straw")));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./third_field.dat", 22));
        runActions.add(new HarvestSeedAction(new NAlias("Barley"), AreasID.barley , true));
        runActions.add(new CollectItemsToSwill(AreasID.barley,new NAlias("straw")));
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
