package me.thegiggitybyte.chathistory.message;

import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.util.registry.RegistryKey;

public class PlayerMessage extends Message {
    private final SignedMessage message;
    private final MessageSender sender;
    
    public PlayerMessage(SignedMessage message, MessageSender sender, RegistryKey<MessageType> typeKey) {
        super(typeKey);
        
        this.message = message;
        this.sender = sender;
    }
    
    public SignedMessage getSignedMessage() {
        return message;
    }
    
    public MessageSender getSender() {
        return sender;
    }
}
