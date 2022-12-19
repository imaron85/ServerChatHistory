package me.thegiggitybyte.chathistory.message;


import net.minecraft.text.Text;


public class GameMessage implements Message {
    private final boolean overlay;
    
    private final Text message;
    
    public GameMessage(Text message, boolean overlay) {
        this.overlay = overlay;
        
        this.message = message;
    }
    
    public Text getMessage() {
        return message;
    }
    
    public boolean isOverlay() {
        return overlay;
    }
}
