package nl.dyonb.chathistory.util;

import net.minecraft.text.TranslatableText;

import java.util.UUID;

public class ChatMessage {

    public ChatMessage(TranslatableText translatableText, UUID uuid) {
        this.translatableText = translatableText;
        this.uuid = uuid;
    }

    public TranslatableText translatableText;
    public UUID uuid;

}
