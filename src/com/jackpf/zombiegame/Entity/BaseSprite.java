package com.jackpf.zombiegame.Entity;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jackpf.zombiegame.Manager.ResourceManager;

public class BaseSprite extends Sprite implements IUpdateHandler
{
    private int health = 100;
    private Text label;
    
    public BaseSprite(int x, int y, ITextureRegion tr, VertexBufferObjectManager vbom)
    {
        super(x, y, tr, vbom);
        
        label = new Text(32, 32, ResourceManager.getInstance().font, health + "hp", 32, getVertexBufferObjectManager());
        this.attachChild(label);
    }
    
    public boolean isInfiltratedBy(Sprite zombie)
    {
        return zombie.getX() <= getWidth();
    }
    
    public void attack()
    {
        if (health > 0) {
            health--;
        }
    }
    
    @Override
    protected void onManagedUpdate(float secondsElapsed)
    {
        super.onManagedUpdate(secondsElapsed);
        
        label.setText(health + "hp");
    }
}
