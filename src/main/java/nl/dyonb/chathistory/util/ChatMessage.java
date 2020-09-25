package nl.dyonb.chathistory.util;

import net.minecraft.text.Text;

import java.util.UUID;

public class ChatMessage {

    public ChatMessage(Text text, UUID uuid) {
        this.text = text;
        this.uuid = uuid;
    }

    public Text text;
    public UUID uuid;

}
