package me.thegiggitybyte.chathistory.mixin;

import me.thegiggitybyte.chathistory.ChatHistory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.SERVER)
@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;onSpawn()V"), method = "onPlayerConnect")
    public void sendCachedMessages(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        for (var message : ChatHistory.MESSAGE_CACHE) {
            player.sendMessage(message.getContent(), MessageType.CHAT, message.getSender());
        }
    }
    
}
