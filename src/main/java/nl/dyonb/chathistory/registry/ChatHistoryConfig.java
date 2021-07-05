package nl.dyonb.chathistory.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import nl.dyonb.chathistory.ChatHistory;

@Environment(EnvType.SERVER)
@Config(name = ChatHistory.MOD_ID)
public class ChatHistoryConfig implements ConfigData {

    public static ChatHistoryConfig CONFIG = new ChatHistoryConfig();

    @Comment("Makes Chat History log the message key")
    public boolean verboseMode = false;

    @Comment("Maximum amount of remembered chat messages")
    public int maxRememberedChatMessages = 25;

    @Comment("The TranslatableText keys of the chat messages to remember")
    public String[] keysToRemember = {"death", "multiplayer", "chat.type.text", "chat.type.advancement", "chat.type.announcement"};

    public static void initialize() {
        AutoConfig.register(ChatHistoryConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ChatHistoryConfig.class).getConfig();
    }

}
