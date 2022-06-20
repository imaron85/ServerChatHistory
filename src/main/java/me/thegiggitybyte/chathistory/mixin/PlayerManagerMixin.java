package me.thegiggitybyte.chathistory.mixin;

import me.thegiggitybyte.chathistory.ChatHistory;
import me.thegiggitybyte.chathistory.message.GameMessage;
import me.thegiggitybyte.chathistory.message.PlayerMessage;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    
    @Inject(method = "broadcast(Lnet/minecraft/text/Text;Ljava/util/function/Function;Lnet/minecraft/util/registry/RegistryKey;)V", at = @At("HEAD"))
    public void cacheGameMessage(Text message, Function<ServerPlayerEntity, Text> playerMessageFactory, RegistryKey<MessageType> typeKey, CallbackInfo ci) {
        ChatHistory.MESSAGE_CACHE.add(new GameMessage(message, typeKey));
    }

    @Inject(method = "broadcast(Lnet/minecraft/network/message/SignedMessage;Ljava/util/function/Function;Lnet/minecraft/network/message/MessageSender;Lnet/minecraft/util/registry/RegistryKey;)V", at = @At("HEAD"))
    public void cachePlayerMessage(SignedMessage message, Function<ServerPlayerEntity, SignedMessage> playerMessageFactory, MessageSender sender, RegistryKey<MessageType> typeKey, CallbackInfo ci) {
        ChatHistory.MESSAGE_CACHE.add(new PlayerMessage(message, sender, typeKey));
    }

    @Inject(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;onSpawn()V"))
    public void sendCachedMessages(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        for (var message : ChatHistory.MESSAGE_CACHE) {
            if (message instanceof PlayerMessage playerMessage) {
                player.sendChatMessage(playerMessage.getSignedMessage(), playerMessage.getSender(), playerMessage.getTypeKey());
            } else if (message instanceof GameMessage gameMessage) {
                player.sendMessage(gameMessage.getTextMessage(), gameMessage.getTypeKey());
            }
        }
    }
}
