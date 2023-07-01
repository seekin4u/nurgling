package nurgling.bots.settings;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class QuestWebhook {

    private final String url;
    private String content;

    private String character;

    private EmbedObject quest = new EmbedObject();

    /**
     * Constructs a new DiscordWebhook instance
     *
     * @param url The webhook URL obtained in Discord
     */
    public QuestWebhook(String url) {
        this.url = url;
    }

    public void setCharacter(String character){ this.character = character; }

    public void setContent(String content) {
        this.content = content;
    }

    public void addEmbed(EmbedObject embed) {
        this.quest = embed;
    }

    public void execute() throws IOException {
        if (this.content == null && this.quest == null) {
            throw new IllegalArgumentException("Set content or add at least one EmbedObject");
        }

        JSONObject json = new JSONObject();

        json.put("content", this.content);
        json.put("character", this.character);

        if (this.quest != null) {
            JSONObject jsonEmbed = new JSONObject();
            //questgiverName, rewardLp, rewardExp, rewardLocalQuality
            jsonEmbed.put("questgiverName", quest.getQuestgiverName());
            jsonEmbed.put("rewardLp", quest.getRewardLp());
            jsonEmbed.put("rewardExp", quest.getRewardExp());
            jsonEmbed.put("rewardLocalQuality", quest.getRewardLocalQuality());
            jsonEmbed.put("rewardLocalQualityAdditional", quest.getRewardLocalQualityAdditional());
            jsonEmbed.put("rewardBy", quest.getRewardBy());
            jsonEmbed.put("rewardItem", quest.getRewardItem());

            json.put("quest", jsonEmbed);
        }

        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-QuestWebhook");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();

        connection.getInputStream().close(); //I'm not sure why but it doesn't work without getting the InputStream
        connection.disconnect();
    }

    public static class EmbedObject {
        //questgiverName, rewardLp, rewardExp, rewardLocalQuality
        private String questgiverName;

        private String rewardLp;

        private String rewardExp;

        private String rewardLocalQuality;

        private String rewardLocalQualityAdditional;

        private String rewardItem;

        private String rewardBy;

        public String getQuestgiverName(){ return questgiverName; }
        public String getRewardLp(){ return rewardLp; }
        public String getRewardExp(){ return rewardExp; }
        public String getRewardLocalQuality(){ return rewardLocalQuality; }

        public String getRewardLocalQualityAdditional(){ return rewardLocalQualityAdditional; }

        public String getRewardItem(){return rewardItem;}

        public String getRewardBy(){return rewardBy;}

        public EmbedObject setQuestgiverName(String questgiverName){
            this.questgiverName = questgiverName;
            return this;
        }

        public EmbedObject setRewardLp(String rewardLp){
            this.rewardLp = rewardLp;
            return this;
        }

        public EmbedObject setRewardExp(String rewardExp) {
            this.rewardExp = rewardExp;
            return this;
        }

        public EmbedObject setRewardLocalQuality(String rewardLocalQuality) {
            this.rewardLocalQuality = rewardLocalQuality;
            return this;
        }

        public EmbedObject setRewardLocalQualityAdditional(String rewardLocalQualityAdditional) {
            this.rewardLocalQualityAdditional = rewardLocalQualityAdditional;
            return this;
        }

        public EmbedObject setRewardItem(String rewardItem){
            this.rewardItem = rewardItem;
            return this;
        }

        public EmbedObject setRewardBy(String rewardBy){
            this.rewardBy = rewardBy;
            return this;
        }
    }

    private class JSONObject {

        private final HashMap<String, Object> map = new HashMap<>();

        void put(String key, Object value) {
            if (value != null) {
                map.put(key, value);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            builder.append("{");

            int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(quote(entry.getKey())).append(":");

                if (val instanceof String) {
                    builder.append(quote(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof JSONObject) {
                    builder.append(val.toString());
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);
                    for (int j = 0; j < len; j++) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }
                    builder.append("]");
                }

                builder.append(++i == entrySet.size() ? "}" : ",");
            }

            return builder.toString();
        }

        private String quote(String string) {
            return "\"" + string + "\"";
        }
    }
}
