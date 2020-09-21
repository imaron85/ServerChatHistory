package nl.dyonb.chathistory;

import com.google.common.collect.EvictingQueue;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import nl.dyonb.chathistory.registry.ChatHistoryConfig;
import nl.dyonb.chathistory.util.ChatMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;

@Environment(EnvType.SERVER)
public class ChatHistory implements DedicatedServerModInitializer {

    public static final String LOG_ID = "ChatHistory";
    public static final String MOD_ID = "chathistory";

    public static final Logger LOGGER = LogManager.getLogger(LOG_ID);

    public static Queue<ChatMessage> CHAT_HISTORY = EvictingQueue.create(ChatHistoryConfig.CONFIG.maxRememberedChatMessages);

    @Override
    public void onInitializeServer() {
        LOGGER.log(Level.INFO, "Hello ChatHistory world!");

        ChatHistoryConfig.initialize();
    }

    public static Identifier identifier(String name) {
        return new Identifier(MOD_ID, name);
    }

}
