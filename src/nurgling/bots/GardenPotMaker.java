package nurgling.bots;


import haven.Gob;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.bots.actions.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.Arrays;


public class GardenPotMaker extends Bot {

    public GardenPotMaker ( NGameUI gameUI ) {
        super ( gameUI );
        win_title = "GardenPotMaker";
        win_sz.y = 100;

        runActions.add (new TakeAndPlace());
    }
}
