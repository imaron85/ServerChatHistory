package me.thegiggitybyte.chathistory.message;


import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Predicate;


public class PlayerMessage implements Message {
    private final Predicate<ServerPlayerEntity> shouldSendFiltered;
    
    private final SignedMessage message;
    
    private final MessageType.Parameters params;
    
    public PlayerMessage(SignedMessage message, Predicate<ServerPlayerEntity> shouldSendFiltered, MessageType.Parameters params) {
        
        this.message = message;
        this.shouldSendFiltered = shouldSendFiltered;
        this.params = params;
    }
    
    public SignedMessage getMessage() {
        return message;
    }
    
    public MessageType.Parameters getParams() {
        return params;
    }
    
    public Predicate<ServerPlayerEntity> getShouldSendFiltered() {
        return shouldSendFiltered;
    }
}
