package nl.dyonb.chathistory.registry;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import nl.dyonb.chathistory.ChatHistory;

@Environment(EnvType.SERVER)
@Config(name = ChatHistory.MOD_ID)
public class ChatHistoryConfig implements ConfigData {

    public static ChatHistoryConfig CONFIG = new ChatHistoryConfig();

    @Comment("Maximum amount of remembered chat messages")
    public int maxRememberedChatMessages = 25;

    @Comment("The TranslatableText keys of the chat messages to remember")
    public String[] keysToRemember = {"death", "multiplayer", "chat.type.text", "chat.type.advancement"};

    public static void initialize() {
        AutoConfig.register(ChatHistoryConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ChatHistoryConfig.class).getConfig();
    }

}
