//
// Decompiled by Procyon v0.5.36
//

package nurgling.bots.actions;

import java.util.Map;
import haven.FightWnd;
import haven.Resource;
import haven.Buff;
import haven.Fightsess;
import java.util.ConcurrentModificationException;
import haven.Fightview;
import haven.Equipory;
import haven.Inventory;
import haven.WItem;
import java.util.Iterator;
import haven.OCache;
import haven.Coord2d;
import haven.Loading;
import haven.Button;
import haven.Widget;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashMap;
import haven.Gob;
import java.util.ArrayList;
import haven.GameUI;
import haven.MCache;
import haven.Coord;
import haven.CheckBox;
import java.util.HashSet;
import haven.Label;
import haven.Window;
import nurgling.NAlias;
import nurgling.NUtils;
import nurgling.PathFinder;
import nurgling.tools.Finder;

public class MiningBot extends Window implements Runnable
{
    private final Label blackore;
    private final Label bloodstone;
    private final Label leafore;
    private final Label schrifterz;
    private final Label direvein;
    private final Label hornsilver;
    private final Label silvershine;
    private final Label directionLabel;
    private final HashSet<String> ROCKS;
    private final CheckBox mineBox;
    private boolean autoMineActive;
    private boolean autoFightActive;
    private boolean autoRoadActive;
    private Coord direction;
    private Coord dirPerpen;
    private int milestoneRot;
    private MCache map;
    private GameUI gui;
    private boolean stop;
    private ArrayList<Gob> columns;
    private int phase;
    private int clock;
    private Coord ccc;
    private HashMap<String, Integer> fightMovesMap;

    public MiningBot(final GameUI gui) {
        super(new Coord(220, 265), "Mining Assistant");
        this.ROCKS = new HashSet<String>(Arrays.asList("basalt", "dolomite", "feldspar", "flint", "gneiss", "granite", "chalcopyrite", "cinnabar", "malachite", "ilmenite", "hornblende", "limestone", "marble", "porphyry", "quartz", "sandstone", "schist", "cassiterite", "galena", "stone", "olivine", "alabaster", "gabbro", "apatite", "sodalite", "soapstone", "microlite", "mica", "kyanite", "orthoclase", "korund", "fluorospar", "zincspar"));
        this.direction = new Coord(1, 0);
        this.dirPerpen = new Coord(0, -1);
        this.milestoneRot = -30720;
        this.columns = new ArrayList<Gob>();
        this.fightMovesMap = new HashMap<String, Integer>();
        this.gui = gui;
        this.stop = false;
        this.map = gui.map.glob.map;
        this.ccc = gui.map.player().rc.floor();
        this.phase = 0;
        this.autoMineActive = false;
        this.autoFightActive = true;
        this.updateCombatMoves();

        // add variables to all buttons and checkboxes
        this.add((Widget)(this.directionLabel = new Label("Current Direction: East")), new Coord(10, 10));
        final Button dirSwitchBtn = new Button( 140, "Change Direction");
        this.add((Widget)dirSwitchBtn, new Coord(20, 30));

        this.add((Widget)(this.mineBox = new CheckBox("Automine")), new Coord(20, 65));
        final CheckBox fightBox = new CheckBox("Autofight");
        this.add((Widget)fightBox, new Coord(110, 65));

        final CheckBox roadBox = new CheckBox("Autoroad");
        this.add((Widget)roadBox, new Coord(20, 85));

        final Label lblstxt = new Label("Minerals in current grid:");
        this.add((Widget)lblstxt, new Coord(10, 105));

        this.add((Widget)(this.blackore = new Label("Black Ore: 0")), new Coord(10, 125));
        this.add((Widget)(this.bloodstone = new Label("Bloodstone: 0")), new Coord(10, 145));
        this.add((Widget)(this.leafore = new Label("Leaf Ore: 0")), new Coord(10, 165));
        this.add((Widget)(this.schrifterz = new Label("Schrifterz: 0")), new Coord(10, 185));
        this.add((Widget)(this.direvein = new Label("Direvein: 0")), new Coord(10, 205));
        this.add((Widget)(this.hornsilver = new Label("Horn Silver: 0")), new Coord(10, 225));
        this.add((Widget)(this.silvershine = new Label("Silvershine: 0")), new Coord(10, 245));
    }

    public void run() {
        try {
            Thread.sleep(4000L);
            while (!this.stop) {
                if (this.gui.fv != null && this.gui.fv.current != null && this.autoFightActive) {
                    this.clearhand();
                    this.equipWeapon();
                    this.fight();
                    this.phase = 0;
                }
                else if (this.autoMineActive) {
                    System.out.println("phase: " + this.phase);
                    if (NUtils.getStamina() < 0.4) {
                        new Drink(0.9, false).run(NUtils.getGameUI());
                        while (NUtils.getStamina() < 0.9) {
                            Thread.sleep(50);
                        }
                    }
                    this.clearhand();
                    if (this.phase == 0) {
                        this.columns = Finder.findObjects( new NAlias("column"));//(ArrayList<Gob>)Utils.getGobs("gfx/terobjs/column", this.gui);
                        if (this.columns.isEmpty()) {
                            System.out.println("No columns found..");
                            continue;
                        }
                        final Gob centerColumn = Finder.findObject( new NAlias("column"));//Utils.closestGob( (ArrayList)this.columns, this.gui.map.player().rc.floor() );
                        this.ccc = centerColumn.rc.floor().add(new Coord(this.direction).add(this.dirPerpen).mul(11));
                        final int nextLine = this.checkLinesMined();
                        System.out.println("nextline is: " + nextLine);
                        switch (nextLine) {
                            case 0: {
                                this.phase = 4;
                                break;
                            }
                            case 1: {
                                this.phase = 1;
                                break;
                            }
                            case 2: {
                                this.phase = 2;
                                break;
                            }
                            case 3: {
                                this.phase = 3;
                                break;
                            }
                        }
                        if (this.phase != 4 && !this.goToNearestColumn()) {
                            Thread.sleep(5000L);
                            continue;
                        }
                    }
                    else if (this.phase == 1) {
                        if (this.mineLine(this.ccc, this.direction, 10)) {
                            this.phase = 0;
                        }
                    }
                    else if (this.phase == 2) {
                        if (this.mineLine(this.ccc, this.dirPerpen, 10)) {
                            this.phase = 0;
                        }
                    }
                    else if (this.phase == 3) {
                        if (this.mineLine(this.ccc, this.dirPerpen.inv(), 12)) {
                            this.phase = 0;
                        }
                    }
                    else if (this.phase == 4) {
                        final ArrayList<Gob> milestones = Finder.findObjects(new NAlias("milestone-stone-m"));
                        //(ArrayList<Gob>)Utils.getGobs("gfx/terobjs/road/milestone-stone-m", this.gui);
                        final Coord2d playercood = this.gui.map.player().rc;
                        final Gob closestMilestone = Finder.findObject( new NAlias("milestone-stone-m"));
                                //Utils.closestGob((ArrayList)milestones, playercood.floor());
                        if (this.autoRoadActive && closestMilestone != null && closestMilestone.rc.floor().dist(this.ccc) > 209.0) {
                            this.phase = 5;
                        }
                        else {
                            System.out.println("all lines mined, building new column");
                            final Coord nextColumnAdd = this.direction.mul(11).mul(10);
                            final Coord nextColumnPos = this.ccc.add(nextColumnAdd);
                            if (this.checkForNearbyColumn(nextColumnPos)) {
                                PathFinder pf = new PathFinder(NUtils.getGameUI(), Coord2d.of(nextColumnPos));
                                pf.setHardMode(true);
                                pf.run();
                                /*this.gui.map.pfLeftClick(nextColumnPos, (String)null);
                                Utils.waitPf(this.gui);*/
                                this.phase = 0;
                            }
                            else {
                                this.buildNextColumn(this.ccc);
                            }
                        }
                    }
                    else if (this.phase == 5) {
                        System.out.println("building milestone");
                        this.buildMilestone();
                    }
                    else if (this.phase == -1) {
                        this.flee();
                    }
                }
                if (this.clock > 6) {
                    try {
                        this.updateOre();
                    }
                    catch (NullPointerException ex) {}
                    catch (Loading loading) {}
                    this.clock = 0;
                }
                ++this.clock;
                Thread.sleep(500L);
            }
        }
        catch (InterruptedException e) {
            System.out.println("interrupted..");
        }
    }

    private void buildMilestone() throws InterruptedException {
        final ArrayList<Gob> milestones = (ArrayList<Gob>)Utils.getGobs("gfx/terobjs/road/milestone-stone-m", this.gui);
        final Coord2d playercood = this.gui.map.player().rc;
        final Gob closestMilestone = Utils.closestGob((ArrayList)milestones, playercood.floor());
        final Coord addcoord = this.direction.sub(this.dirPerpen).mul(11);
        final Coord newMilestonePos = this.ccc.add(addcoord);
        if (closestMilestone.rc.floor().dist(this.ccc) < 55.0) {
            this.phase = 0;
        }
        else if (!Utils.getTileName(newMilestonePos, this.map).equals("mine")) {
            this.gui.map.pfLeftClick(this.ccc.add(this.direction.mul(11)), (String)null);
            Utils.waitPf(this.gui);
            Utils.clickUiButton("paginae/act/mine", this.gui);
            this.gui.map.wdgmsg("sel", new Object[] { newMilestonePos.div(11), newMilestonePos.div(11), 0 });
            int timeout = 0;
            while (timeout < 100 && !Utils.getTileName(newMilestonePos, this.map).equals("mine")) {
                ++timeout;
                Thread.sleep(100L);
            }
        }
        else if (!this.hasRocksInInv(5)) {
            System.out.println("need to find more rocks");
            this.findRocks();
        }
        else if (this.isClearPath(playercood, closestMilestone.rc.add(new Coord2d(this.dirPerpen).mul(5.0)))) {
            this.gui.map.wdgmsg("click", new Object[] { closestMilestone.sc, closestMilestone.rc.floor(OCache.posres), 3, 0, 0, (int)closestMilestone.id, closestMilestone.rc.floor(OCache.posres), 0, -1 });
            Thread.sleep(500L);
            Button extendButton = null;
            try {
                final Window milestoneWindow = this.gui.getwnd("Milestone");
                if (milestoneWindow != null) {
                    for (Widget wi = milestoneWindow.lchild; wi != null; wi = wi.prev) {
                        if (wi instanceof Button) {
                            final Button btn = (Button)wi;
                            if (btn.text.text.equals("Extend")) {
                                extendButton = btn;
                            }
                        }
                    }
                }
            }
            catch (NullPointerException ex) {}
            if (extendButton != null) {
                extendButton.click();
                Thread.sleep(500L);
                this.gui.map.wdgmsg("place", new Object[] { new Coord2d((double)this.ccc.x, (double)this.ccc.y).floor(OCache.posres), this.milestoneRot, 1, 2 });
                int timeout2 = 0;
                while (this.gui.map.player().rc.floor().dist(this.ccc) > 11.0 && timeout2 < 100) {
                    ++timeout2;
                    Thread.sleep(100L);
                }
                Coord2d buildPos = new Coord2d((double)newMilestonePos.x, (double)newMilestonePos.y);
                final Coord2d buildAdd = new Coord2d((double)(this.dirPerpen.x * 11), (double)(this.dirPerpen.y * 11)).mul(0.52);
                buildPos = buildPos.sub(buildPos.x % MCache.tilesz.x, buildPos.y % MCache.tilesz.y).add(MCache.tilesz.x / 2.0, MCache.tilesz.y / 2.0).add(buildAdd).sub(MCache.tilesz);
                this.gui.map.wdgmsg("place", new Object[] { buildPos.floor(OCache.posres), this.milestoneRot, 1, 0 });
                Thread.sleep(1000L);
                Utils.activateSign("Milestone", this.gui);
                this.waitBuildingConstruction("gfx/terobjs/road/milestone-stone-m");
            }
            else {
                this.gui.error("error when trying to extend road, the closest milestone cannot be extended!");
            }
        }
        else {
            final Coord milestonevision = closestMilestone.rc.floor();
            if (this.direction.y == 0) {
                milestonevision.y = this.ccc.y;
            }
            else if (this.direction.x == 0) {
                milestonevision.x = this.ccc.x;
            }
            this.gui.map.pfLeftClick(this.ccc, (String)null);
            Utils.waitPf(this.gui);
            Utils.leftClick(this.gui, milestonevision);
            Thread.sleep(100L);
            while (this.gui.map.player().getv() > 0.0 && !this.isClearPath(playercood, closestMilestone.rc.add(new Coord2d(this.dirPerpen).mul(5.0)))) {
                Thread.sleep(100L);
            }
        }
    }

    private void clearhand() {
        if (this.gui.vhand != null) {
            final String itemname = this.gui.vhand.item.getname();
            if (!itemname.contains("Battleaxe") && !itemname.contains("Cutblade") && !itemname.contains("Sword")) {
                this.gui.vhand.item.wdgmsg("drop", new Object[] { Coord.z });
            }
            else {
                System.out.println("CANNOT DROP WEAPON");
            }
        }
        Utils.rightClick(this.gui);
    }

    private boolean checkForNearbyColumn(final Coord pos) {
        this.columns = (ArrayList<Gob>)Utils.getGobs("gfx/terobjs/column", this.gui);
        for (final Gob gob : this.columns) {
            if (gob.rc.floor().dist(pos) < 44.0) {
                return true;
            }
        }
        return false;
    }

    private void buildNextColumn(final Coord fromCenter) throws InterruptedException {
        Utils.rightClick(this.gui);
        final Coord addCoord = this.direction.mul(11).mul(10);
        final Coord columnCoord = fromCenter.add(addCoord);
        final Coord columnOffset = this.dirPerpen.inv().mul(11);
        final ArrayList<Gob> constructions = (ArrayList<Gob>)Utils.getGobs("gfx/terobjs/consobj", this.gui);
        try {
            constructions.sort((gob1, gob2) -> (int)(gob1.rc.dist(this.gui.map.player().rc) - gob2.rc.dist(this.gui.map.player().rc)));
        }
        catch (Exception ex) {}
        if (!Utils.getTileName(columnCoord.add(columnOffset), this.map).equals("mine")) {
            System.out.println("no spot for column, need to mine it out");
            this.gui.map.pfLeftClick(columnCoord, (String)null);
            Utils.waitPf(this.gui);
            Utils.clickUiButton("paginae/act/mine", this.gui);
            this.gui.map.wdgmsg("sel", new Object[] { columnCoord.add(columnOffset).div(11), columnCoord.add(columnOffset).div(11), 0 });
            int timeout = 0;
            while (timeout < 100 && !Utils.getTileName(columnCoord.add(columnOffset), this.map).equals("mine")) {
                ++timeout;
                Thread.sleep(100L);
            }
        }
        else if (!this.hasRocksInInv(15)) {
            System.out.println("need to find more rocks");
            this.findRocks();
        }
        else if (!constructions.isEmpty()) {
            System.out.println("found construction sign building..");
            this.gui.map.pfLeftClick(columnCoord, (String)null);
            Utils.waitPf(this.gui);
            final Gob closeConstr = constructions.get(0);
            this.gui.map.wdgmsg("click", new Object[] { closeConstr.sc, closeConstr.rc.floor(OCache.posres), 3, 0, 0, (int)closeConstr.id, closeConstr.rc.floor(OCache.posres), 0, -1 });
            Thread.sleep(1000L);
            Utils.activateSign("Stone Column", this.gui);
            this.waitBuildingConstruction("gfx/terobjs/column");
        }
        else {
            this.gui.map.pfLeftClick(columnCoord, (String)null);
            Utils.waitPf(this.gui);
            Utils.clickUiButton("paginae/bld/column", this.gui);
            Thread.sleep(300L);
            Coord2d buildPos = new Coord2d((double)columnCoord.add(columnOffset).x, (double)columnCoord.add(columnOffset).y);
            buildPos = buildPos.sub(buildPos.x % MCache.tilesz.x, buildPos.y % MCache.tilesz.y).add(MCache.tilesz.x / 2.0, MCache.tilesz.y / 2.0).sub(MCache.tilesz);
            System.out.println("buildpos floor posres = " + buildPos.floor(OCache.posres));
            System.out.println("buildpos = " + buildPos.floor());
            System.out.println("pos res = " + OCache.posres);
            this.gui.map.wdgmsg("place", new Object[] { buildPos.floor(OCache.posres), 0, 1, 0 });
            Thread.sleep(1000L);
            Utils.activateSign("Stone Column", this.gui);
            this.waitBuildingConstruction("gfx/terobjs/column");
        }
    }

    private void waitBuildingConstruction(final String name) throws InterruptedException {
        for (int timeout = 0; timeout < 30 && this.hasRocksInInv(0) && !this.checkIfConstructed(name); ++timeout) {
            Thread.sleep(200L);
        }
    }

    private boolean checkIfConstructed(final String name) {
        final ArrayList<Gob> colmns = (ArrayList<Gob>)Utils.getGobs(name, this.gui);
        return Utils.closestGob((ArrayList)colmns, this.gui.map.player().rc.floor()).rc.dist(this.gui.map.player().rc) < 20.0;
    }

    private void findRocks() throws InterruptedException {
        final ArrayList<Gob> gobs = (ArrayList<Gob>)Utils.getAllGobs(this.gui);
        final Coord2d playerC = this.gui.map.player().rc;
        try {
            final Coord2d coord2d;
            gobs.sort((gob1, gob2) -> (int)(gob1.rc.dist(coord2d) - gob2.rc.dist(coord2d)));
        }
        catch (Exception ex) {}
        for (final Gob gob3 : gobs) {
            if (this.ROCKS.contains(gob3.getres().basename())) {
                this.gui.map.pfLeftClick(gob3.rc.floor(), (String)null);
                Utils.waitPf(this.gui);
                this.gui.map.wdgmsg("click", new Object[] { gob3.sc, gob3.rc.floor(OCache.posres), 3, 1, 0, (int)gob3.id, gob3.rc.floor(OCache.posres), 0, -1 });
                Thread.sleep(2000L);
                return;
            }
        }
        for (final WItem wItem : this.gui.maininv.getAllItems()) {
            try {
                if (this.ROCKS.contains(wItem.item.getres().basename())) {}
            }
            catch (Loading loading) {}
        }
    }

    private boolean hasRocksInInv(final int num) {
        int rocksAmount = 0;
        for (final WItem wItem : this.gui.maininv.getAllItems()) {
            if (this.ROCKS.contains(wItem.item.getres().basename())) {
                ++rocksAmount;
            }
        }
        return rocksAmount > num || this.gui.maininv.getFreeSpace() == 0;
    }

    private Integer checkLinesMined() {
        final Coord dir1 = this.direction;
        final Coord dir2 = this.dirPerpen;
        final Coord dir3 = this.dirPerpen.inv();
        if (!this.checkLineMined(this.ccc, dir2, 10)) {
            return 2;
        }
        if (!this.checkLineMined(this.ccc, dir3, 12)) {
            return 3;
        }
        if (!this.checkLineMined(this.ccc, dir1, 10)) {
            return 1;
        }
        return 0;
    }

    private boolean checkLineMined(final Coord place, final Coord dir, final int length) {
        System.out.println("Checking direction " + dir);
        for (int i = 0; i <= length; ++i) {
            final Coord dirmul = dir.mul(11).mul(i);
            System.out.print("+");
            if (!Utils.getTileName(place.add(dirmul), this.map).equals("mine")) {
                System.out.print("X");
                return false;
            }
        }
        return true;
    }

    private boolean mineLine(final Coord place, final Coord dir, final int length) throws InterruptedException {
        System.out.println("Mining line!");
        final Coord end = dir.mul(11).mul(length);
        for (int i = 0; i <= length; ++i) {
            final Coord dirmul = dir.mul(11).mul(i);
            final Coord mineplace = place.add(dirmul);
            if (!Utils.getTileName(mineplace, this.map).equals("mine")) {
                if (!this.isClearPath(this.gui.map.player().rc, new Coord2d((double)place.x, (double)place.y))) {
                    this.gui.map.pfLeftClick(this.ccc, (String)null);
                    Utils.waitPf(this.gui);
                }
                System.out.println("Clicking mine on " + mineplace);
                Utils.clickUiButton("paginae/act/mine", this.gui);
                this.gui.map.wdgmsg("sel", new Object[] { place.div(11), place.add(end).div(11), 0 });
                Thread.sleep(500L);
                return false;
            }
        }
        return true;
    }

    private boolean isClearPath(final Coord2d fromd, final Coord2d tod) {
        System.out.println("Start: " + fromd + " end: " + tod);
        final Coord2d direction = tod.sub(fromd);
        final double dirLen = fromd.dist(tod);
        System.out.println("Length: " + dirLen);
        if (dirLen < 21.0) {
            return true;
        }
        final Coord2d directionNorm = direction.div(dirLen);
        for (int i = 1; i < dirLen / 3.0 - 4.0; ++i) {
            final Coord2d addCoord = directionNorm.mul(3.0).mul((double)i);
            System.out.println(fromd.add(addCoord).floor());
            if (!Utils.getTileName(fromd.add(addCoord).floor(), this.map).equals("mine")) {
                System.out.println("no clear path!");
                return false;
            }
        }
        return true;
    }

    private boolean goToNearestColumn() throws InterruptedException {
        if (Utils.getTileName(this.ccc, this.map).equals("mine")) {
            this.gui.map.pfLeftClick(this.ccc, (String)null);
            Utils.waitPf(this.gui);
            return true;
        }
        if (this.ccc.dist(this.gui.map.player().rc.floor()) < 22.0) {
            System.out.println();
            Utils.clickUiButton("paginae/act/mine", this.gui);
            this.gui.map.wdgmsg("sel", new Object[] { this.gui.map.player().rc.floor().div(11), this.ccc.div(11), 0 });
            Thread.sleep(500L);
            return true;
        }
        this.gui.error("cannot not walk to nearest column, try to mine around it");
        return false;
    }

    private void equipWeapon() throws InterruptedException {
        final Equipory e = this.gui.getequipory();
        if (e == null) {
            return;
        }
        if (e.quickslots[6] != null) {
            final String curWep = e.quickslots[6].item.getname();
            if (curWep.contains("Cutblade") || curWep.contains("Sword")) {
                return;
            }
        }
        for (final Inventory i : this.gui.getAllInventories()) {
            final WItem weapon = this.getWeapon(i);
            if (weapon != null) {
                System.out.println("found weapon" + weapon.item.getname() + " at " + weapon.c.toString());
                weapon.item.wdgmsg("take", new Object[] { weapon.c });
                Utils.waitForOccupiedHand(this.gui, 1000, "Could not take weapon from inventory");
                e.wdgmsg("drop", new Object[] { 6 });
                Thread.sleep(100L);
                final WItem hand = this.gui.vhand;
                if (hand != null) {
                    final Coord freecoord = i.isRoom(1, 1);
                    i.wdgmsg("drop", new Object[] { freecoord });
                    Utils.waitForEmptyHand(this.gui, 500, "Could not wait for empty hand");
                }
                return;
            }
        }
        if (e.quickslots[5] != null) {
            e.quickslots[5].item.wdgmsg("iact", new Object[] { e.quickslots[5].c, 0 });
        }
    }

    private WItem getWeapon(final Inventory inv) {
        WItem weapon = inv.getItemPartial("Cutblade");
        if (weapon == null) {
            weapon = inv.getItemPartial("Sword");
        }
        return weapon;
    }

    private void fight() throws InterruptedException {
        try {
            final Gob closestGob = this.gui.map.glob.oc.getgob(this.gui.fv.current.gobid);
            try {
                synchronized (this.gui.fv.lsrel) {
                    for (final Fightview.Relation rel : this.gui.fv.lsrel) {
                        final Gob gob = this.gui.map.glob.oc.getgob(rel.gobid);
                        if (this.phase == -1 || gob.getres().basename().equals("troll")) {
                            this.phase = -1;
                            this.flee();
                            return;
                        }
                        if (this.gui.map.player().rc.dist(gob.rc) >= this.gui.map.player().rc.dist(closestGob.rc)) {
                            continue;
                        }
                        Utils.clickUiButton("paginae/act/atk", this.gui);
                        Thread.sleep(100L);
                        this.gui.fv.wdgmsg("click", new Object[] { (int)gob.id, 1 });
                    }
                }
            }
            catch (ConcurrentModificationException ex) {}
            final Gob current = this.gui.map.glob.oc.getgob(this.gui.fv.current.gobid);
            if (current.rc.dist(this.gui.map.player().rc) > 33.0 && this.gui.fv.current.give.state != 1) {
                this.gui.fv.wdgmsg("give", new Object[] { (int)this.gui.fv.current.gobid, 1 });
            }
            this.updateCombatMoves();
            if (this.gui.fv.lsrel.size() == 1) {
                ((Fightsess)this.gui.getchild((Class)Fightsess.class)).wdgmsg("use", new Object[] { this.fightMovesMap.get("sideswipe") });
            }
            else if (this.nearbyEnemiesHaveOpenings("reeling", 20, 10.0)) {
                ((Fightsess)this.gui.getchild((Class)Fightsess.class)).wdgmsg("use", new Object[] { this.fightMovesMap.get("fullcircle") });
            }
            else if (this.gui.fv.current.ip >= 2) {
                ((Fightsess)this.gui.getchild((Class)Fightsess.class)).wdgmsg("use", new Object[] { this.fightMovesMap.get("sos") });
            }
            else {
                ((Fightsess)this.gui.getchild((Class)Fightsess.class)).wdgmsg("use", new Object[] { this.fightMovesMap.get("takeaim") });
            }
            if (this.countCloseEnemies(10.0) > 5) {
                System.out.println("Too many enemies are too close, step back!");
            }
        }
        catch (NullPointerException e) {
            System.out.println("nullpointer when fighting...");
        }
    }

    private int countCloseEnemies(final double distance) {
        int count = 0;
        try {
            synchronized (this.gui.fv.lsrel) {
                for (final Fightview.Relation rel : this.gui.fv.lsrel) {
                    final Gob current = this.gui.map.glob.oc.getgob(rel.gobid);
                    if (current.rc.dist(this.gui.map.player().rc) < distance) {
                        ++count;
                    }
                }
            }
        }
        catch (ConcurrentModificationException ex) {}
        catch (NullPointerException ex2) {}
        return count;
    }

    private boolean nearbyEnemiesHaveOpenings(final String openingname, final int amount, final double distance) {
        try {
            synchronized (this.gui.fv.lsrel) {
                for (final Fightview.Relation rel : this.gui.fv.lsrel) {
                    final Gob current = this.gui.map.glob.oc.getgob(rel.gobid);
                    if (current.rc.dist(this.gui.map.player().rc) < distance && this.getCombatOpening(rel, openingname) > amount) {
                        return true;
                    }
                }
            }
        }
        catch (ConcurrentModificationException ex) {}
        catch (NullPointerException ex2) {}
        return false;
    }

    private int getCombatOpening(final Fightview.Relation rel, final String openingname) {
        try {
            for (final Buff buff : rel.buffs.children((Class)Buff.class)) {
                if (((Resource)buff.res.get()).basename().equals(openingname)) {
                    return buff.ameter;
                }
            }
        }
        catch (Loading loading) {}
        return 0;
    }

    private void updateCombatMoves() {
        final FightWnd fightWnd = this.gui.getfightwnd();
        this.fightMovesMap.clear();
        try {
            for (int i = 0; i < fightWnd.order.length; ++i) {
                if (fightWnd.order[i] != null) {
                    this.fightMovesMap.put(((Resource)fightWnd.order[i].res.get()).basename(), i);
                }
            }
        }
        catch (Loading loading) {}
        if (!this.fightMovesMap.containsKey("sos")) {
            this.gui.error("no storm of swords found on combat deck, cannot fight properly..");
        }
        if (!this.fightMovesMap.containsKey("takeaim")) {
            this.gui.error("no take aim found on combat deck, cannot fight properly..");
        }
        if (!this.fightMovesMap.containsKey("fullcircle")) {
            this.gui.error("no full circle found on combat deck, cannot fight properly..");
        }
        if (!this.fightMovesMap.containsKey("sideswipe")) {
            this.gui.error("no sideswipe found on combat deck, cannot fight properly..");
        }
    }

    private void flee() throws InterruptedException {
        this.columns = (ArrayList<Gob>)Utils.getGobs("gfx/terobjs/column", this.gui);
        Gob centerColumn = Utils.closestGob((ArrayList)this.columns, this.gui.map.player().rc.floor());
        this.ccc = centerColumn.rc.floor().add(new Coord(this.direction).add(this.dirPerpen).mul(11));
        if (Utils.getTileName(this.ccc, this.map).equals("mine")) {
            Utils.leftClick(this.gui, this.ccc);
            Thread.sleep(500L);
            this.gui.map.pfLeftClick(this.ccc, (String)null);
            Utils.waitPf(this.gui);
            final Coord addDirection = this.direction.inv().mul(11).mul(12);
            centerColumn = Utils.closestGob((ArrayList)this.columns, this.ccc.add(addDirection));
            this.ccc = centerColumn.rc.floor().add(new Coord(this.direction).add(this.dirPerpen).mul(11));
            this.gui.map.pfLeftClick(this.ccc, (String)null);
            Utils.waitPf(this.gui);
        }
        else {
            this.gui.error("Cannot find path back! PANIC");
        }
    }

    private void updateOre() {
        final HashMap<Integer, Integer> tilemap = new HashMap<Integer, Integer>();
        final int px = (int)this.gui.map.player().rc.x;
        final int py = (int)this.gui.map.player().rc.x;
        final MCache.Grid grid = this.map.getgridt(this.gui.map.player().rc.floor().div(11));
        for (int i = 0; i < grid.tiles.length; ++i) {
            try {
                final int tile = grid.tiles[i];
                if (tilemap.containsKey(tile)) {
                    tilemap.put(tile, tilemap.get(tile) + 1);
                }
                else {
                    tilemap.put(tile, 1);
                }
            }
            catch (Loading loading) {}
        }
        this.resetLabels();
        tilemap.entrySet().stream().forEach(entry -> this.updateLabel(entry));
    }

    private void updateLabel(final Map.Entry<Integer, Integer> entry) {
        final Resource res = this.map.tilesetr((int)entry.getKey());
        if (res == null) {
            return;
        }
        final String name = res.basename();
        if (name.equals("magnetite")) {
            this.blackore.settext("Black Ore: " + entry.getValue());
        }
        else if (name.equals("hematite")) {
            this.bloodstone.settext("Bloodstone: " + entry.getValue());
        }
        else if (name.equals("nagyagite")) {
            this.leafore.settext("Leaf Ore: " + entry.getValue());
        }
        else if (name.equals("sylvanite")) {
            this.schrifterz.settext("Schrifterz: " + entry.getValue());
        }
        else if (name.equals("petzite")) {
            this.direvein.settext("Direvein: " + entry.getValue());
        }
        else if (name.equals("hornsilver")) {
            this.hornsilver.settext("Horn Silver: " + entry.getValue());
        }
        else if (name.equals("argentite")) {
            this.silvershine.settext("Silvershine: " + entry.getValue());
        }
    }

    private void resetLabels() {
        this.blackore.settext("Black Ore: 0");
        this.bloodstone.settext("Bloodstone: 0");
        this.leafore.settext("Leaf Ore: 0");
        this.schrifterz.settext("Schrifterz: 0");
        this.direvein.settext("Direvein: 0");
        this.hornsilver.settext("Horn Silver: 0");
        this.silvershine.settext("Silvershine: 0");
    }

    private void changeDirection() {
        if (this.direction.x == 1) {
            this.resetParams();
            this.directionLabel.settext("Current Direction: North");
            this.direction = new Coord(0, -1);
            this.dirPerpen = new Coord(-1, 0);
            this.milestoneRot = 18432;
        }
        else if (this.direction.y == -1) {
            this.resetParams();
            this.directionLabel.settext("Current Direction: West");
            this.direction = new Coord(-1, 0);
            this.dirPerpen = new Coord(0, 1);
            this.milestoneRot = 2048;
        }
        else if (this.direction.x == -1) {
            this.resetParams();
            this.directionLabel.settext("Current Direction: South");
            this.direction = new Coord(0, 1);
            this.dirPerpen = new Coord(1, 0);
            this.milestoneRot = -14336;
        }
        else if (this.direction.y == 1) {
            this.resetParams();
            this.directionLabel.settext("Current Direction: East");
            this.direction = new Coord(1, 0);
            this.dirPerpen = new Coord(0, -1);
            this.milestoneRot = -30720;
        }
    }

    private void resetParams() {
        this.map = this.gui.map.glob.map;
        this.phase = 0;
        this.columns = (ArrayList<Gob>)Utils.getGobs("gfx/terobjs/column", this.gui);
        final Gob centerColumn = Utils.closestGob((ArrayList)this.columns, this.gui.map.player().rc.floor());
        if (centerColumn != null) {
            this.ccc = centerColumn.rc.floor().add(new Coord(this.direction).add(this.dirPerpen).mul(11));
        }
        this.mineBox.a = false;
        this.autoMineActive = false;
    }
    //bots end action
    /*public void wdgmsg(final Widget sender, final String msg, final Object... args) {
        if (sender == this.cbtn) {
            this.stop = true;
            this.stop();
            this.reqdestroy();
        }
        else {
            super.wdgmsg(sender, msg, args);
        }
    }*/

    public void stop() {
        NUtils.getGameUI().map.wdgmsg("click", new Object[] { Coord.z, NUtils.getGameUI().map.player().rc.floor(OCache.posres), 1, 0 });
//        if (NUtils.getGameUI().map.pfthread != null) {
//            this.gameui().map.pfthread.interrupt();
//        }
        this.destroy();
    }
}