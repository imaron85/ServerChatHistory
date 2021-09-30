package me.thegiggitybyte.chathistory.mixin;

import me.thegiggitybyte.chathistory.ChatHistory;
import me.thegiggitybyte.chathistory.ChatMessage;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    
    @Inject(at = @At("HEAD"), method = "onDisconnect()V")
    public void cacheLeaveMessage(CallbackInfo info) {
        var player = (ServerPlayerEntity) (Object) this;
        var content = new TranslatableText("multiplayer.player.left", player.getDisplayName()).formatted(Formatting.YELLOW);
        var message = new ChatMessage(content, MessageType.SYSTEM, Util.NIL_UUID);
        
        ChatHistory.MESSAGE_CACHE.add(message);
    }
    
    @Inject(
            method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"
            )
    )
    public void cacheDeathMessage(DamageSource source, CallbackInfo ci) {
        var player = (ServerPlayerEntity) (Object) this;
        var message = new ChatMessage(source.getDeathMessage(player), MessageType.SYSTEM, Util.NIL_UUID);
        
        ChatHistory.MESSAGE_CACHE.add(message);
    }
}
