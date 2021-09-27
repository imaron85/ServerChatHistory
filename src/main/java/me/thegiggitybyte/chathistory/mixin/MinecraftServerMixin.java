package me.thegiggitybyte.chathistory.mixin;

import me.thegiggitybyte.chathistory.ChatHistory;
import me.thegiggitybyte.chathistory.ChatMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Environment(EnvType.SERVER)
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At("TAIL"), method = "sendSystemMessage")
    public void cacheMessage(Text message, UUID senderUuid, CallbackInfo ci) {
        ChatHistory.MESSAGE_CACHE.add(new ChatMessage(message, senderUuid));
    }
}
