package nl.dyonb.chathistory.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.dyonb.chathistory.ChatHistory;
import nl.dyonb.chathistory.registry.ChatHistoryConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.UUID;

@Environment(EnvType.SERVER)
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(at = @At("TAIL"), method = "sendSystemMessage")
    public void sendSystemMessage(Text message, UUID senderUuid, CallbackInfo ci) {
        if (message instanceof TranslatableText) {
            TranslatableText translatableText = (TranslatableText) message;
            String key = translatableText.getKey();

            // Check if the key is in whitelistedKeys
            for (String whitelistedKey : ChatHistoryConfig.CONFIG.keysToRemember) {
                if (key.startsWith(whitelistedKey)) {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("message", translatableText.getString());
                    hashMap.put("uuid", senderUuid.toString());
                    ChatHistory.CHAT_HISTORY.add(hashMap);
                }
            }

        }
    }

}
