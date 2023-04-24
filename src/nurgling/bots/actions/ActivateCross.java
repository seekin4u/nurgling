package nurgling.bots.actions;

import haven.*;
import haven.Button;
import haven.Window;
import nurgling.*;
import nurgling.tools.Finder;

import java.awt.*;
import java.util.ArrayList;

import static nurgling.NUtils.getProg;
import static nurgling.NUtils.waitEvent;

public class ActivateCross implements Action {
    @Override
    public Results run ( NGameUI gameUI)
            throws InterruptedException {

        //найти ближайший майлстоун
        //подойти к нему

        Window spwnd = gameUI.getWindow(name);
        waitEvent(()->gameUI.getWindow(name)!=null,100);
        spwnd = gameUI.getWindow(name);


        if (spwnd != null) {
            for (Widget sp = spwnd.lchild; sp != null; sp = sp.prev) {
                if (sp instanceof haven.Button) {
                    if(((Button) sp).text.text == "Travel"){}
                    ((Button) sp).click();
                    Thread.sleep(100);
                }
            }
            waitEvent(()->getProg()>=0, 20);
            /// Ждем завершения крафта
            waitEvent(()->getProg()<0, 1000);
        }
        //подождать прогрузку локации
        Thread.sleep(5000);
        //запустить чекер гобов - найти животное
        //вернуться назад
        //нажать следующую кнопку следующего маршрута.

        return null;
    }

    public ActivateCross(
            String name
    ) {
        this.name = name;
    }
    
    String name;
    
}
