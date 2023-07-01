package nurgling.bots.settings;

import haven.BuddyWnd;
import haven.GameUI;
import haven.Widget;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QuestWebhookWrap {

    public QuestWebhookWrap(String Character, String Questgiver, String RewardLp, String RewardExp, String RewardQuality,
                            String RewardQualityAdditional, String RewardBy, String RewardItem)
            throws InterruptedException{

        QuestWebhook webhook = new QuestWebhook("http://localhost:5000");

        webhook.setContent("Nurgling");//here i set client
        if(!Character.isEmpty())
            webhook.setCharacter(Character);

        QuestWebhook.EmbedObject questObject = new QuestWebhook.EmbedObject();
        if(!Questgiver.isEmpty())
            questObject.setQuestgiverName(Questgiver);
        if(!RewardLp.isEmpty())
            questObject.setRewardLp(RewardLp);
        if(!RewardExp.isEmpty())
            questObject.setRewardExp(RewardExp);
        if(!RewardQuality.isEmpty())
            questObject.setRewardLocalQuality(RewardQuality);
        if(!RewardQuality.isEmpty())
            questObject.setRewardLocalQualityAdditional(RewardQualityAdditional);
        if(!RewardBy.isEmpty())
            questObject.setRewardBy(RewardBy);
        if(!RewardItem.isEmpty())
            questObject.setRewardItem(RewardItem);
        webhook.addEmbed(questObject);

        try {
            webhook.execute();
        } catch ( ConnectException e){
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassCastException e){
            throw new RuntimeException(e);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
