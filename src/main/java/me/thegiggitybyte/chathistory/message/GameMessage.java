package me.thegiggitybyte.chathistory.message;

import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.registry.RegistryKey;

public class GameMessage extends Message {
    private final Text message;
    
    public GameMessage(Text message, RegistryKey<MessageType> typeKey) {
        super(typeKey);
        
        this.message = message;
    }
    
    public Text getTextMessage() {
        return message;
    }
}
