package haven.res.ui.tt.drinkbuff;/* Preprocessed source code */
/* $use: lib/tspec */
/* $use: ui/tt/drink */

import haven.*;
import haven.res.lib.tspec.*;
import haven.res.ui.tt.drink.*;
import java.awt.image.BufferedImage;

/* >tt: haven.res.ui.tt.drinkbuff.Drinkbuff */
@haven.FromResource(name = "ui/tt/drinkbuff", version = 9)
public class Drinkbuff extends ItemInfo.Tip {
    public final BufferedImage img;
    public final String nm;
    public final int n;
    public final Type[] types;

    public Drinkbuff(Owner owner, ResData sdt, int n, Type[] types) {
	super(owner);
	{
	    Spec main = new Spec(sdt, owner, null);
	    GSprite spr = main.spr();
	    if(spr instanceof GSprite.ImageSprite)
		this.img = ((GSprite.ImageSprite)spr).image();
	    else
		this.img = main.res.res.get().layer(Resource.imgc).img;
	    this.nm = main.name();
	}
	this.n = n;
	this.types = types;
    }

    public static ItemInfo mkinfo(Owner owner, Object... args) {
	Resource.Resolver rr = owner.context(Resource.Resolver.class);
	int a = 1;
	ResData sdt = new ResData(rr.getres((Integer)args[a++]), Message.nil);
	int n = (Integer)args[a++];
	int nt = (args.length - a) / 2;
	Type[] types = new Type[nt];
	for(int i = 0; a < args.length; i++, a += 2) {
	    ResData tsdt = new ResData(rr.getres((Integer)args[a + 0]), Message.nil);
	    double m = ((Number)args[a + 1]).doubleValue();
	    types[i] = Type.make(owner, tsdt, m);
	}
	return(new Drinkbuff(owner, sdt, n, types));
    }

    public void layout(Layout l) {
	{
	    BufferedImage icon = PUtils.convolvedown(img, new Coord(16, 16), CharWnd.iconfilter);
	    BufferedImage lbl = Text.render(String.format("%s: %d", nm, n)).img;
	    int x = 10, y = l.cmp.sz.y;
	    l.cmp.add(icon, new Coord(x, y));
	    l.cmp.add(lbl, new Coord(x + 16 + 3, y + ((16 - lbl.getHeight()) / 2)));
	}
	for(Type type : types) {
	    BufferedImage lbl = Text.render(String.format("%s: +%d%%", type.nm, Math.round(type.m * 100))).img;
	    BufferedImage icon = PUtils.convolvedown(type.img, new Coord(lbl.getHeight(), lbl.getHeight()), CharWnd.iconfilter);
	    int x = 20, y = l.cmp.sz.y;
	    l.cmp.add(icon, new Coord(x, y));
	    l.cmp.add(lbl, new Coord(x + icon.getWidth() + 3, y));
	}
    }
}
