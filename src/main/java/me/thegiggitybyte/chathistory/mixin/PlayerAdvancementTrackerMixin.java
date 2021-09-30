package me.thegiggitybyte.chathistory.mixin;

import me.thegiggitybyte.chathistory.ChatHistory;
import me.thegiggitybyte.chathistory.ChatMessage;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {
    
    @Shadow
    private ServerPlayerEntity owner;
    
    @Inject(
            method = "grantCriterion(Lnet/minecraft/advancement/Advancement;Ljava/lang/String;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"
            )
    )
    public void cacheAdvancementMessage(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        var translationKey = "chat.type.advancement." + advancement.getDisplay().getFrame().getId();
        var content = new TranslatableText(translationKey, this.owner.getDisplayName(), advancement.toHoverableText());
        var message = new ChatMessage(content, MessageType.SYSTEM, Util.NIL_UUID);
        
        ChatHistory.MESSAGE_CACHE.add(message);
    }
}
