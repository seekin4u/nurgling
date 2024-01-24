package nurgling.bots.actions;

import nurgling.NGameUI;
import nurgling.NUtils;


public class TestDrinkAction implements Action {

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        //найти в инвентаре бокал
        //найти бочку с меткой вина
        //пф к бочке
        //взять в руку бокал
        //пкм по бочке
        //пложить бокал
        //пкм по бокалу
        //выбрать петал "пить
        //ждем пока есть песочные часы

        NUtils.getGameUI().getDrinkList();
        return new Results(Results.Types.SUCCESS);
    }

    public TestDrinkAction() {
    }
}
