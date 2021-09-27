package me.thegiggitybyte.chathistory;

import net.minecraft.text.Text;

import java.util.UUID;

public class ChatMessage {
    private final Text content;
    private final UUID senderUuid;
    
    public ChatMessage(Text content, UUID sender) {
        this.content = content;
        this.senderUuid = sender;
    }
    
    public Text getContent() {
        return content;
    }
    
    public UUID getSender() {
        return senderUuid;
    }
}
