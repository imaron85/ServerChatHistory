package nl.dyonb.chathistory.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.dyonb.chathistory.ChatHistory;
import nl.dyonb.chathistory.registry.ChatHistoryConfig;
import nl.dyonb.chathistory.util.ChatMessage;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Environment(EnvType.SERVER)
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(at = @At("TAIL"), method = "sendSystemMessage")
    public void sendSystemMessage(Text message, UUID senderUuid, CallbackInfo ci) {
        if (message instanceof TranslatableText) {
            TranslatableText translatableText = (TranslatableText) message;
            String key = translatableText.getKey();

            if (ChatHistoryConfig.CONFIG.verboseMode) {
                ChatHistory.LOGGER.log(Level.ALL, key);
            }

            // Check if the key is in whitelistedKeys
            for (String whitelistedKey : ChatHistoryConfig.CONFIG.keysToRemember) {
                if (key.startsWith(whitelistedKey)) {
                    ChatMessage chatMessage = new ChatMessage(translatableText, senderUuid);
                    ChatHistory.CHAT_HISTORY.add(chatMessage);
                }
            }

        }
    }

}
