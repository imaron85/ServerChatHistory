package nl.dyonb.chathistory.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import nl.dyonb.chathistory.ChatHistory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.UUID;

@Environment(EnvType.SERVER)
@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;onSpawn()V"), method = "onPlayerConnect")
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        for (HashMap<String,String> message : ChatHistory.CHAT_HISTORY) {
            UUID uuid = UUID.fromString(message.get("uuid"));
            player.sendMessage(new LiteralText(message.get("message")), MessageType.CHAT, uuid);
        }
    }

}
