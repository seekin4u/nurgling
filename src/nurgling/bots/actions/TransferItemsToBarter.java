package nurgling.bots.actions;

import haven.GItem;
import haven.Gob;
import haven.Widget;
import haven.Window;
import haven.res.ui.barterbox.Shopbox;

import haven.res.ui.tt.defn.DefName;
import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NUtils;
import nurgling.PathFinder;
import nurgling.bots.tools.Warhouse;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class TransferItemsToBarter implements Action {

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        if(!manyBarters){
            gobs.add(gob);
        }
        for(Gob gob : gobs) {
            if (gob != null) {
                if (!isInfo && gui.getInventory().getWItems(items, q, true).size() > 0 || isInfo && gui.getInventory().getGItems(items, q, true).size() > 0) {
                    PathFinder pf = new PathFinder(gui, gob);
                    pf.run();

                    if (new OpenTargetContainer(gob, "Barter Stand").run(gui).type != Results.Types.SUCCESS) {
                        return new Results(Results.Types.OPEN_FAIL);
                    }

                    Window spwnd = gui.getWindow("Barter Stand");
                    if (spwnd != null) {
                        for (Widget sp = spwnd.lchild; sp != null; sp = sp.prev) {
                            if (sp instanceof Shopbox) {
                                Shopbox sb = (Shopbox) sp;
                                if (sb.price != null) {
                                    NUtils.waitEvent(() -> sb.spr != null, 50);
                                    NAlias alias = (!(isInfo) ? new NAlias((String) sb.price.res.res.get().name) : new NAlias(DefName.getname(sb.price)));
                                    try {
                                        String name = (String) sb.price.spr().getClass().getField("name")
                                                .get(sb.price.spr());
                                        alias.keys.add(name);
                                        if (NUtils.checkName(name, new NAlias("Raw Beef"))) {
                                            alias.keys.add("Raw Wild Beef");
                                        } else if (NUtils.checkName(name, new NAlias("Raw Pork"))) {
                                            alias.keys.add("Raw Wild Pork");
                                        } else if (NUtils.checkName(name, new NAlias("Raw Horse"))) {
                                            alias.keys.add("Raw Wildhorse");
                                            alias.keys.add("Raw Horse");
                                        } else if (NUtils.checkName(name, new NAlias("Raw Mutton"))) {
                                            alias.keys.add("Raw Wild Mutton");
                                        }
                                    } catch (NoSuchFieldException | IllegalAccessException ignore) {
                                    }
                                    if (NUtils.checkName("gfx/invobjs/food/offal", alias) &&
                                            NUtils.checkName(items.keys.get(0), new NAlias("Bollock"))) {
                                        alias = items;
                                    } else if (NUtils.checkName("gfx/invobjs/bone", alias) &&
                                            NUtils.checkName("/bone", items)) {
                                        alias = items;
                                    } else if (NUtils.checkName("gfx/invobjs/glass", alias) &&
                                            NUtils.checkName(items.keys.get(0), new NAlias("glass"))) {
                                        alias = items;
                                    } else if (NUtils.checkName("gfx/invobjs/rawhide", alias) &&
                                            NUtils.checkName(items.keys.get(0), new NAlias("blood"))) {
                                        alias = items;
                                    } else if (NUtils.checkName("gfx/invobjs/small/fossilcollection", alias) &&
                                            NUtils.checkName(items.keys.get(0), new NAlias("fossil"))) {
                                        alias = items;
                                    } else if (NUtils.checkName("gfx/invobjs/small/bearhide", alias) &&
                                            NUtils.checkName(items.keys.get(0), new NAlias("bearhide"))) {
                                        alias = items;
                                    }
                                    boolean isFind = false;
                                    for (String keys : alias.keys) {
                                        if (NUtils.checkName(keys, items)) {
                                            isFind = true;
                                        }
                                    }
                                    if (isFind) {
                                        int size = (!isInfo) ? gui.getInventory().getWItems(items, q, true).size() : gui.getInventory().getGItems(items, q, true).size();
                                        for (GItem item : (!isInfo) ? gui.getInventory().getWItems(items, q, true) : gui.getInventory().getGItems(items, q, true)) {
                                            if (item.contents != null) {
                                                NUtils.destroyFCNbndl(item);
                                                NUtils.waitEvent(() -> NUtils.getGameUI().getInventory().wmap.get(item) == null, 50);
                                            }
                                        }
                                        while (((!isInfo) ? gui.getInventory().getWItems(items, q, true) : gui.getInventory().getGItems(items, q, true)).size() > 0) {
                                            //int old_size = gui.getInventory().getWItems(items, q, true).size();
                                            int err = NUtils.getGameUI().chatError.getCounter();
                                            sb.bbtn.click();
                                            Thread.sleep(50);
//                                            if(NUtils.waitEvent(() -> gui.getInventory().getWItems(items, q, true).size() == old_size, 100)){
//                                                break;
//                                            }
                                            if(NUtils.getGameUI().chatError.getCounter() > err)
                                            {
                                                break;
                                            }
//                                            if (gui.getInventory().getWItems(items, q, true).size() == old_size) {
//                                                break;
//                                            }
                                            if (sb.res == null) {
                                                if (paving == null) {
                                                    if (area == null) {
                                                        fcknbranchbundle();
                                                        new TransferItemsToContainers(id, new NAlias(
                                                                new ArrayList<>(Arrays.asList("Branch", "branch"))),
                                                                true).run(gui);
                                                    } else {
                                                        fcknbranchbundle();
                                                        new TransferItemsToContainers(area, new NAlias(
                                                                new ArrayList<>(Arrays.asList("Branch", "branch"))),
                                                                true).run(gui);
                                                    }
                                                } else {
                                                    fcknbranchbundle();
                                                    new TransferItemsToContainers(Finder.findNearestMark(id, paving),
                                                            new NAlias(new ArrayList<>(
                                                                    Arrays.asList("Branch", "branch"))), true).run(
                                                            gui);
                                                }
                                                if (area == null) {
                                                    new TransferItemsToBarter(gob, items, id, isInfo, q, paving).run(gui);
                                                } else {
                                                    new TransferItemsToBarter(gob, items, area, isInfo, q, paving).run(gui);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Thread.sleep(300);
                    if (paving == null) {
                        if (area == null) {
                            fcknbranchbundle();
                            new TransferItemsToContainers(id,
                                    new NAlias(new ArrayList<>(Arrays.asList("Branch", "branch"))), true).run(gui);
                        } else {
                            fcknbranchbundle();
                            new TransferItemsToContainers(area,
                                    new NAlias(new ArrayList<>(Arrays.asList("Branch", "branch"))), true).run(gui);
                        }
                    } else {
                        fcknbranchbundle();
                        new TransferItemsToContainers(Finder.findNearestMark(id, paving),
                                new NAlias(new ArrayList<>(Arrays.asList("Branch", "branch"))), true).run(gui);
                    }
                }
            } else {
                return new Results(Results.Types.NO_BARTER);
            }
        }
        return new Results(Results.Types.SUCCESS);
    }

    void fcknbranchbundle() throws InterruptedException {
        for(GItem branch: NUtils.getGameUI().getInventory().getWItems(new NAlias("branch")))
            if(branch.contents!=null) {
                NUtils.destroyFCNbndl(branch);
                NUtils.waitEvent(()->NUtils.getGameUI().getInventory().wmap.get(branch)==null,50);
            }
    }
    
    public TransferItemsToBarter(
            Gob gob,
            NAlias items,
            AreasID id,
            boolean isInfo
    ) {
        this.gob = gob;
        this.items = items;
        this.id = id;
        this.isInfo = isInfo;
    }
    
    public TransferItemsToBarter(
            Warhouse warhouse
    ) {
        this.gob = Finder.findObjectInArea ( new NAlias ( "barterstand" ), 3000,
                Finder.findNearestMark ( warhouse.barter ) );
        this.items = warhouse.item;
        this.id = warhouse.barter;
        this.isInfo = warhouse.isInfo;
    }

    public TransferItemsToBarter(
            AreasID id,
            NAlias items,
            boolean isInfo
    ) {
        this.gob = Finder.findObjectInArea ( new NAlias ( "barterstand" ), 3000,
                Finder.findNearestMark ( id ));
        this.items = items;
        this.id = id;
        this.isInfo = isInfo;
    }

    public TransferItemsToBarter(
            AreasID id,
            NAlias items,
            boolean isInfo,
            boolean manyBarters
    ) {
        this.gob = Finder.findObjectInArea ( new NAlias ( "barterstand" ), 3000,
                Finder.findNearestMark ( id ));
        this.gobs = Finder.findObjectsInArea(new NAlias("barterstand"), Finder.findNearestMark ( id ) );
        this.items = items;
        this.id = id;
        this.isInfo = isInfo;
        this.manyBarters = manyBarters;
    }

    public TransferItemsToBarter(
            NArea area,
            NAlias items,
            boolean isInfo
    ) {
        this.gob = Finder.findObjectInArea ( new NAlias ( "barterstand" ), 3000,
                area);
        this.items = items;
        this.area = area;
        this.isInfo = isInfo;
    }

    public TransferItemsToBarter(
            AreasID id,
            NAlias items,
            boolean isInfo,
            double q
    ) {
        this.gob = Finder.findObjectInArea ( new NAlias ( "barterstand" ), 3000,
                Finder.findNearestMark ( id ));
        this.items = items;
        this.id = id;
        this.isInfo = isInfo;
        this.q = q;
    }
    
    public TransferItemsToBarter(
            Warhouse warhouse,
            double q
    ) {
        this.gob = Finder.findObjectInArea ( new NAlias ( "barter" ), 3000,
                Finder.findNearestMark ( warhouse.barter ) );
        this.items = warhouse.item;
        this.id = warhouse.barter;
        this.isInfo = warhouse.isInfo;
        this.q = q;
    }
    
    public TransferItemsToBarter(
            Gob gob,
            NAlias items,
            AreasID id,
            boolean isInfo,
            String paving
    ) {
        this.gob = gob;
        this.items = items;
        this.id = id;
        this.isInfo = isInfo;
        this.paving = paving;
    }

    public TransferItemsToBarter(Gob gob, NAlias items, NArea area, boolean isInfo, double q, String paving) {
        this.gob = gob;
        this.items = items;
        this.area = area;
        this.isInfo = isInfo;
        this.paving = paving;
    }


    public TransferItemsToBarter(
            Gob gob,
            NAlias items,
            AreasID id,
            boolean isInfo,
            double q
    ) {
        this.gob = gob;
        this.items = items;
        this.id = id;
        this.isInfo = isInfo;
        this.q = q;
    }
    
    public TransferItemsToBarter(
            Gob gob,
            NAlias items,
            AreasID id,
            boolean isInfo,
            double q,
            String paving
    ) {
        this.gob = gob;
        this.items = items;
        this.id = id;
        this.isInfo = isInfo;
        this.q = q;
        this.paving = paving;
    }
    
    
    String paving = null;
    Gob gob;
    ArrayList<Gob> gobs = new ArrayList<>();
    boolean manyBarters = false;
    AreasID id;
    NArea area = null;
    NAlias items;
    boolean isInfo;
    double q = -1;
}
