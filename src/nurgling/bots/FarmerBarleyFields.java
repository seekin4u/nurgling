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
        runActions.add(new NomadTraveller("./first_field.dat"));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./second_field.dat"));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./third_field.dat"));

        /*runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./fourth_field.dat"));

        runActions.add(new ReturnerNoLogout());
        runActions.add(new NomadTraveller("./fifth_field.dat"));*/

        //runActions.add ( new HarvestSeedAction(new NAlias("Barley"), AreasID.barley , true));

        //runActions.add ( new TransferToTrough(new NAlias("Straw")) );
        //runActions.add ( new CollectItemsToTrough(AreasID.barley,new NAlias("straw")));

        
    }
    
    
    @Override
    public void initAction () {
    }
    
    @Override
    public void endAction () {
        super.endAction ();
    }
    
    HarvestOut seed;
    ArrayList<HarvestOut> harvestOuts = new ArrayList<> ();
}
