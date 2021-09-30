package me.thegiggitybyte.chathistory;

import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

import java.util.UUID;

public class ChatMessage {
    private final Text content;
    private final MessageType type;
    private final UUID senderUuid;
    
    public ChatMessage(Text content, MessageType type, UUID sender) {
        this.content = content;
        this.type = type;
        this.senderUuid = sender;
    }
    
    public Text getContent() {
        return content;
    }
    
    public MessageType getType() {
        return type;
    }
    
    public UUID getSender() {
        return senderUuid;
    }
}
