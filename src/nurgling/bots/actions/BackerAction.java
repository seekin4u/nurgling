package nurgling.bots.actions;

import haven.Gob;

import nurgling.NAlias;
import nurgling.NGameUI;
import nurgling.PathFinder;
import nurgling.tools.AreasID;
import nurgling.tools.Finder;

import java.util.ArrayList;
import java.util.Arrays;

public class BackerAction implements Action {
    final NAlias backed = new NAlias ( new ArrayList<> (
            Arrays.asList ( "ringofbrodgar", "meatpie", "bread", "jellycake", "pirozhki", "seedcrispbread", "fishpie",
                    "wellplaicedpie", "marrowcake", "mushroompie", "greenleafpie", "lardycake", "strawberrycake",
                    "blueberryslice", "honeybun","shepherdspie", "grubpie", "raisinbuttercake" )),
            new ArrayList<> (Arrays.asList ( "dough" ))  );
    
    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        int totalOven = Finder.findObjects(new NAlias("oven")).size();
        /// Ждем пока смелтеры не потухнут
        ArrayList<Gob> oven = new ArrayList<>();
        while ( true ) {
            new WaitAction ( () -> {
                for ( Gob gob : Finder.findObjects ( new NAlias ( "oven" ) ) ) {
                        if ( ( gob.getModelAttribute() & 4 ) != 0 ) {
                            return true;
                        }
                }
                return false;
            }, 100 ).run ( gui );
            for ( Gob gob : Finder.findObjects ( new NAlias ( "oven" ) ) ) {
                new PathFinder( gui, gob ).run ();
                new OpenTargetContainer ( gob, "Oven" ).run ( gui );
                if ( !gui.getInventory ( "Oven" ).getAll ().isEmpty () )
                /// Забираем булки и переносим их в сундуки
                {
                    new TransferFromContainerToContainer ( backed, gob, "Oven", new NAlias ( "oven" ), AreasID.backed ).run ( gui );
                }
                /// Загружаем новыми булками
                new TakeFromContainers(new NAlias("chest"), new NAlias(new ArrayList<>(Arrays.asList("dough"))), 8, AreasID.unbacked, "Chest").run(gui);
                /// Проверяем есть ли булки
                new PathFinder ( gui, gob ).run ();
                new OpenTargetContainer ( gob, "Oven" ).run ( gui );
                new TransferToContainerIfPossible(new NAlias("dough"), "Oven").run(gui);
                if ( gui.getInventory ( "Oven" ).getAll ().isEmpty () )
                /// Если нет, то выходим
                {
                    return new Results(Results.Types.NO_ITEMS);
                }
                oven.add(gob);
                /// Заполняем овны палочками с пайлов
                new FillFuelFromPiles ( 4, new NAlias ( "branch" ), oven, new NAlias ( "branch" ), AreasID.branch, 0 ).run ( gui );
                oven.clear();
                /// Поджигаем
                new LightGob (4, gob).run ( gui );
            }
            Thread.sleep ( 10 );
        }
        
//        return new Results ( Results.Types.SUCCESS );
    }
    
    
}
