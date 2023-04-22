package nurgling.bots.tools;

import nurgling.NAlias;
import nurgling.tools.AreasID;

public class HarvestOut {
    public NAlias items;
    public AreasID outArea;
    public AreasID fromArea;
    
    public HarvestOut(
            NAlias items,
            AreasID outArea
    ) {
        this.items = items;
        this.outArea = outArea;
    }
    public HarvestOut(
            NAlias items,
            AreasID outArea,
            AreasID fromArea
    ) {
        this.items = items;
        this.outArea = outArea;
        this.fromArea = fromArea;
    }
}
