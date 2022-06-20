package me.thegiggitybyte.chathistory.message;

import net.minecraft.network.message.MessageType;
import net.minecraft.util.registry.RegistryKey;

public abstract class Message {
    private final RegistryKey<MessageType> typeKey;
    
    protected Message(RegistryKey<MessageType> typeKey) {
        this.typeKey = typeKey;
    }
    
    public RegistryKey<MessageType> getTypeKey() {
        return typeKey;
    }
}
