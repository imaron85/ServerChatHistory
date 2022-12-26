package me.thegiggitybyte.chathistory.mixin;


import me.thegiggitybyte.chathistory.ChatHistory;
import me.thegiggitybyte.chathistory.message.GameMessage;
import me.thegiggitybyte.chathistory.message.PlayerMessage;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;
import java.util.function.Predicate;


@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    
    @Inject(method = "broadcast(Lnet/minecraft/text/Text;Ljava/util/function/Function;Z)V",
            at = @At("HEAD"))
    public void cacheGameMessage(Text message, Function<ServerPlayerEntity, Text> playerMessageFactory, boolean overlay, CallbackInfo ci) {
        ChatHistory.MESSAGE_CACHE.add(new GameMessage(message, overlay));
    }
    
    @Inject(method = "broadcast(Lnet/minecraft/network/message/SignedMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/network/message/MessageType$Parameters;)V",
            at = @At("HEAD"))
    public void cachePlayerMessage(SignedMessage message, Predicate<ServerPlayerEntity> shouldSendFiltered, @Nullable ServerPlayerEntity sender, MessageType.Parameters params, CallbackInfo ci) {
        ChatHistory.MESSAGE_CACHE.add(new PlayerMessage(message, shouldSendFiltered, params));
    }
    
    @Inject(method = "onPlayerConnect", at = @At(value = "TAIL", target = "Lnet/minecraft/server/network/ServerPlayerEntity;onSpawn()V"))
    public void sendCachedMessages(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        for (var message : ChatHistory.MESSAGE_CACHE) {
            if (message instanceof PlayerMessage playerMessage) {
                player.sendChatMessage(SentMessage.of(playerMessage.getMessage()), playerMessage.getShouldSendFiltered().test(player), playerMessage.getParams());
            } else if (message instanceof GameMessage gameMessage) {
                player.sendMessage(gameMessage.getMessage(), gameMessage.isOverlay());
            }
        }
    }
}
