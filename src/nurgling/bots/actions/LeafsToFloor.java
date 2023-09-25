package nurgling.bots.actions;

import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.NHitBox;
import nurgling.tools.AreasID;
import nurgling.tools.NArea;

import java.util.ArrayList;
import java.util.Arrays;

public class LeafsToFloor implements Action {
    NAlias htable = new NAlias(new ArrayList<>(Arrays.asList("htable")),new ArrayList<>(Arrays.asList("tar")));
    @Override
    public Results run(NGameUI gui) throws InterruptedException {
        /// Забираем черные листья и переносим их в пайлы
        new TransferToPileFromContainer(htable, new NAlias("stockpile-leaf"),
                new NAlias("tea-black"), AreasID.l_tea_black, AreasID.l_htable,"Herbalist Table").run(gui);

        /// Берем свежие листья и кладем на стол
        new FillContainers(new NAlias("tea-fresh"), AreasID.l_htable, new ArrayList<>(),
                new TakeMaxFromContainers(new NAlias("tea-fresh"),  AreasID.l_tea_fresh, new ArrayList<>())).run(gui);
        new TransferToPile(AreasID.l_tea_fresh, NHitBox.get(), new NAlias("tea-fresh"), new NAlias("tea-fresh")).run(gui);
        return new Results(Results.Types.SUCCESS);
    }

    public LeafsToFloor() {
    }

}
