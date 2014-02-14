package com.jackpf.zombiegame.Entity;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jackpf.zombiegame.Manager.ResourceManager;

public class ZombieSprite extends AnimatedSprite implements IUpdateHandler
{
    private static final int MOVE_SPEED = 1;
    
    public ZombieSprite(int x, int y, ITiledTextureRegion tr, VertexBufferObjectManager vbom)
    {
        super(x, y, tr, vbom);
    }
    
    @Override
    protected void onManagedUpdate(float secondsElapsed)
    {
        super.onManagedUpdate(secondsElapsed);
        
        // Has zombie got to the base?
        BaseSprite house = (BaseSprite) ResourceManager.getInstance().sprites.get("house");
        
        if (!house.isInfiltratedBy(this)) {
            setX(getX() - MOVE_SPEED);
        } else {
            house.attack();
        }
    }
}