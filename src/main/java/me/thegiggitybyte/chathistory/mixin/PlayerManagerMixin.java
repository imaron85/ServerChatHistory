package me.thegiggitybyte.chathistory.mixin;

import com.mojang.authlib.GameProfile;
import me.thegiggitybyte.chathistory.ChatHistory;
import me.thegiggitybyte.chathistory.ChatMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.util.UserCache;
import net.minecraft.util.Util;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.WorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Environment(EnvType.SERVER)
@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    
    @Inject(
            method = "onPlayerConnect",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"
            )
    )
    public void cacheJoinMessage(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci, GameProfile gameProfile, UserCache userCache, Optional optional, String string, NbtCompound nbtCompound, RegistryKey registryKey, ServerWorld serverWorld, ServerWorld serverWorld3, String string2, WorldProperties worldProperties, ServerPlayNetworkHandler serverPlayNetworkHandler, MutableText content) { // :(
        var message = new ChatMessage(content, MessageType.SYSTEM, Util.NIL_UUID);
        ChatHistory.MESSAGE_CACHE.add(message);
    }
    
    @Inject(
            method = "onPlayerConnect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;onSpawn()V"
            )
    )
    public void sendCachedMessages(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        for (var message : ChatHistory.MESSAGE_CACHE) {
            player.sendMessage(message.getContent(), MessageType.CHAT, message.getSender());
        }
    }
}
