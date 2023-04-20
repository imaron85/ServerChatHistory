package me.thegiggitybyte.chathistory.message;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class CachedMessage {
    private final Text message;
    
    public CachedMessage(Text message) {
        this.message = message;
    }
    
    public void send(ServerPlayerEntity player) {
        player.sendMessage(message, false);
    }
}
