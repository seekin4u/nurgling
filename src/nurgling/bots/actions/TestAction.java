package nurgling.bots.actions;

import nurgling.NGameUI;
import nurgling.bots.settings.QuestWebhook;

import java.io.IOException;


public class TestAction implements Action {

    @Override
    public Results run ( NGameUI gui )
            throws InterruptedException {
        QuestWebhook webhook = new QuestWebhook("http://localhost:3000/api/create");

        webhook.setContent("Nurgling");
        webhook.setCharacter(nurgling.NQuestsStats.getPname());
        webhook.addEmbed(new QuestWebhook.EmbedObject()
                .setQuestgiverName("Nirnland")
                .setRewardLp("1024")
                .setRewardLocalQuality("Moose")
                        .setRewardBy("1")
                .setRewardItem("Gilding leaf")
        );
        try {
            webhook.execute(); //Handle exception
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassCastException e){
            throw new RuntimeException(e);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return new Results(Results.Types.SUCCESS);
    }

    public TestAction() {
    }
}
