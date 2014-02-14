package com.jackpf.zombiegame.Entity;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PlayerSprite extends AnimatedSprite
{
    public PlayerSprite(int x, int y, ITiledTextureRegion tr, VertexBufferObjectManager vbom)
    {
        super(x, y, tr, vbom);
    }
}
