package com.jackpf.zombiegame.Manager;

import java.util.HashMap;
import java.util.Map;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Typeface;
import android.util.Log;

public class ResourceManager
{
    private static ResourceManager instance = null;

    public Map<String, ITextureRegion> textures =
        new HashMap<String, ITextureRegion>();

    public Map<String, Sprite> sprites =
        new HashMap<String, Sprite>();
    
    public Font font;
    
    private ResourceManager()
    {
    }
    
    public static ResourceManager getInstance()
    {
        if (instance == null) {
            instance = new ResourceManager();
        }
        
        return instance;
    }
    
    public synchronized void loadTextures(Engine mEngine, SimpleBaseGameActivity context)
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        BitmapTextureAtlas skyTextureAtlas = new BitmapTextureAtlas(
            context.getTextureManager(),
            32, 32,
            TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA
        );
        
        textures.put(
            "sky",
            BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                skyTextureAtlas,
                context,
                "sky.png",
                0, 0
            )
        );
        
        skyTextureAtlas.load();
        
        BitmapTextureAtlas groundTextureAtlas = new BitmapTextureAtlas(
            context.getTextureManager(),
            32, 32,
            TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA
        );
        
        textures.put(
            "ground",
            BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                groundTextureAtlas,
                context,
                "ground.png",
                0, 0
            )
        );
        
        groundTextureAtlas.load();
        
        BitmapTextureAtlas houseTextureAtlas = new BitmapTextureAtlas(
            mEngine.getTextureManager(),
            240, 174
        );
        
        textures.put(
            "house",
            BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                houseTextureAtlas,
                context,
                "house.png",
                0, 0
            )
        );
        
        houseTextureAtlas.load();
        
        groundTextureAtlas.load();
        
        BitmapTextureAtlas treeTextureAtlas = new BitmapTextureAtlas(
            mEngine.getTextureManager(),
            64, 64
        );
        
        textures.put(
            "tree",
            BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                treeTextureAtlas,
                context,
                "tree.png",
                0, 0
            )
        );
        
        treeTextureAtlas.load();
        
        BuildableBitmapTextureAtlas playerTextureAtlas =
            new BuildableBitmapTextureAtlas(
                mEngine.getTextureManager(),
                192, 256,
                TextureOptions.BILINEAR
            );
        
        textures.put(
            "player",    
            BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                playerTextureAtlas,
                context,
                "player.png",
                6, 4
            )
        );
        
        try {
            playerTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        } catch (TextureAtlasBuilderException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
        
        playerTextureAtlas.load();
        
        BuildableBitmapTextureAtlas zombieTextureAtlas =
            new BuildableBitmapTextureAtlas(
                mEngine.getTextureManager(),
                256, 256,
                TextureOptions.BILINEAR
            );
        
        textures.put(
            "zombie",
            BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                zombieTextureAtlas,
                context,
                "zombie.png",
                4, 4
            )
        );
        
        try {
            zombieTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        } catch (TextureAtlasBuilderException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
        
        zombieTextureAtlas.load();
    }
    
    public synchronized void unloadTextures()
    {
        for (ITextureRegion texture : textures.values()) {
            texture.getTexture().unload();
        }
        
        System.gc();
    }
    
    public synchronized void loadFonts(Engine mEngine, SimpleBaseGameActivity context)
    {
        font = FontFactory.create(
            mEngine.getFontManager(),
            mEngine.getTextureManager(), 256, 256,
            Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32f,
            true
        );
        
        font.load();
    }
}