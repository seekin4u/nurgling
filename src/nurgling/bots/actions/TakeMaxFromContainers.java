package nurgling.bots.actions;

import haven.GItem;

import nurgling.*;
import nurgling.bots.tools.InContainer;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;

public class TakeMaxFromContainers implements Action {

    public final ArrayList<InContainer> inContainers;

    public TakeMaxFromContainers(NAlias items, Object o, ArrayList<InContainer> inContainers) {
        this.items = items;
        if(o instanceof NArea){
            inArea = (NArea) o;
        }
        else if (o instanceof NAlias)
        {
            inName = (NAlias) o;
        }
        else
        {
            inId = (AreasID) o;
        }
        this.inContainers = inContainers;
        this.isMax = true;
    }

    public TakeMaxFromContainers(NAlias items, Object o, ArrayList<InContainer> inContainers, boolean resname) {
        this.items = items;
        if(o instanceof NArea){
            inArea = (NArea) o;
        }
        else if (o instanceof NAlias)
        {
            inName = (NAlias) o;
        }
        else
        {
            inId = (AreasID) o;
        }
        this.inContainers = inContainers;
        this.isMax = true;
        this.resname = resname;
    }

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {

        /// Получаем имя входного контейнера
        if(inName == null){
            inName = NUtils.getContainerType((inArea!=null)?inArea:Finder.findNearestMark(inId)).name;
        }

        /// Получаем массив всех контейнеров если нет ранее полученного
        if(inContainers.isEmpty()) {
            ArrayList<InContainer> input = new ArrayList<>();
            if (inArea == null && inId == null) {
                input = InContainer.create(Finder.findObjects(inName));
            } else {
                input = InContainer.create(Finder.findObjects(inName, (inArea != null) ? inArea : Finder.findNearestMark(inId)));
            }
            inContainers.addAll(input);
        }

        for ( InContainer in : inContainers ) {
            if(in.gob.isTag(NGob.Tags.free))
            {
                in.isFree = true;
                continue;
            }
            if (NUtils.getGob(in.gob.id) != null && !in.isFree) {
                if (gui.getInventory().getFreeSpace() == 0) {
                    return new Results(Results.Types.SUCCESS);
                }
                new PathFinder(gui, in.gob, true).run();
                String cap = NUtils.getContainerType(in.gob).cap;
                if (!NUtils.checkName(cap, "Stockpile")) {
                    if (new OpenTargetContainer(in.gob, cap).run(gui).type == Results.Types.SUCCESS) {
                        ArrayList<GItem> witems;
                        if (resname) {
                            witems = gui.getInventory(cap).getWItems(items);
                        } else {
                            witems = gui.getInventory(cap).getGItems(items);
                        }
//                        ArrayList<GItem> witems = gui.getInventory(cap).getWItems(items);
                        for(GItem item : witems) {
                            if (!NUtils.transferItem(gui.getInventory(cap), item, gui.getInventory())) {
                                return new Results(Results.Types.SUCCESS);
                            }
                        }
                        gui.getInventory(cap).parent.destroy();
                    }
                } else {
                    if (new TakeMaxFromPile(in.gob).run(gui).type == Results.Types.SUCCESS)
                        return new Results(Results.Types.SUCCESS);
                }

            }
        }
        return new Results ( Results.Types.FAIL );
    }

    NAlias items;
    NArea inArea;
    AreasID inId = null;
    NAlias inName = null;

    int needed;

    boolean isMax = false;
    boolean resname = true;
}
