package nurgling.bots.actions;

import haven.Gob;
import nurgling.*;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;

public class TransferToBarrelFromContainer implements Action {
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        ArrayList<Gob> in;
        if ( input != null ) {
            in = Finder.findObjectsInArea ( iname, Finder.findNearestMark ( input ) );
        }
        else {
            if ( inp != null ) {
                in = Finder.findObjectsInArea ( iname, inp  );
            }
            else {
                in = Finder.findObjects ( iname );
            }

        }
        for ( Gob gob : in ) {
            if(!gob.isTag(NGob.Tags.free) || gob.isTag(NGob.Tags.dframe) ) {
                Results res;
                do {
                    new PathFinder(gui, gob).run();
                    new OpenTargetContainer(gob, cap).run(gui);
                    res = new TakeMaxFromContainer(cap, items).run(gui);
                    if (res.type == Results.Types.FULL) {
                        if (out == null) {
                            new TransferToBarrel(output, items).run(gui);
                        } else {
                            new TransferToBarrel(out, items).run(gui);
                        }
                    }
                }
                while (res.type == Results.Types.FULL);
            }
        }
        if ( !gui.getInventory ().getWItems( items ).isEmpty () ) {
            if(out==null) {
                new TransferToBarrel(output, items).run(gui);
            }else {
                new TransferToBarrel(out, items).run(gui);
            }
        }
        return new Results ( Results.Types.SUCCESS );
    }

    public TransferToBarrelFromContainer(
            NAlias iname,
            NAlias items,
            AreasID output,
            AreasID input,
            String cap
    ) {
        this.iname = iname;
        this.items = items;
        this.output = output;
        this.input = input;
        this.cap = cap;
    }

    public TransferToBarrelFromContainer(
            NAlias iname,
            NAlias items,
            AreasID output,
            String cap
    ) {
        this.iname = iname;
        this.items = items;
        this.output = output;
        this.input = null;
        this.cap = cap;
    }

    public TransferToBarrelFromContainer(
            NAlias iname,
            NAlias items,
            NArea output,
            String cap
    ) {
        this.iname = iname;
        this.items = items;
        this.out = output;
        this.input = null;
        this.cap = cap;
    }

    public TransferToBarrelFromContainer(
            NAlias iname,
            NAlias items,
            NArea input,
            NArea output,
            String cap
    ) {
        this.iname = iname;
        this.items = items;
        this.out = output;
        this.inp = input;
        this.input = null;
        this.cap = cap;
    }
    NAlias iname;
    NAlias items;
    AreasID output;
    AreasID input;
    String cap;

    NArea out = null;
    NArea inp = null;
}
