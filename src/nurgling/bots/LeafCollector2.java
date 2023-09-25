package nurgling.bots;

import haven.Button;
import haven.CheckBox;
import haven.Coord;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.bots.actions.LeafsToFloor;
import nurgling.bots.actions.Results;
import nurgling.bots.actions.TransferToPileFromContainer;
import nurgling.tools.AreaSelecter;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;


public class LeafCollector2 extends Bot {


    public LeafCollector2(NGameUI gameUI ) {
        super ( gameUI );
        win_title = "Leaf to Stock";
        win_sz.y = 100;
        ///Добавление цикла в действия бота
        runActions.add(new LeafsToFloor());

    }


    @Override
    public void initAction () {
    }

    @Override
    public void endAction () {
        super.endAction ();
    }
}
