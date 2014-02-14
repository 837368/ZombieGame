package com.jackpf.zombiegame;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.math.MathUtils;

import com.jackpf.zombiegame.Entity.BaseSprite;
import com.jackpf.zombiegame.Entity.PlayerSprite;
import com.jackpf.zombiegame.Entity.ZombieSprite;
import com.jackpf.zombiegame.Manager.ResourceManager;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener
{
    public static final boolean DEBUG = true;
    
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;
    public static final int STEP = 60;
    
    private Camera camera;
    
    private ResourceManager resourceManager;
    
    public MainActivity()
    {
        resourceManager = ResourceManager.getInstance();
    }
 
    @Override
    public EngineOptions onCreateEngineOptions()
    {
        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        
        EngineOptions engineOptions = new EngineOptions(
        	true,
        	ScreenOrientation.LANDSCAPE_SENSOR,
            new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
            camera
        );
        
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        
        //engineOptions.getAudioOptions().setNeedsMusic(true);
        //engineOptions.getAudioOptions().setNeedsSound(true);
        
        return engineOptions;
    }
 
    @Override
    protected void onCreateResources()
    {
        resourceManager.loadTextures(mEngine, this);
        resourceManager.loadFonts(mEngine, this);
    }
 
    @Override
    protected Scene onCreateScene()
    {
        Scene scene = new Scene();
        
        scene.setOnSceneTouchListener(this);
        
        ITextureRegion skyTextureRegion = resourceManager.textures.get("sky");
        skyTextureRegion.setTextureSize(CAMERA_WIDTH, CAMERA_HEIGHT / 2);
        resourceManager.sprites.put(
            "sky",
            new Sprite(0, 0, skyTextureRegion, this.getVertexBufferObjectManager())
        );
        scene.attachChild(resourceManager.sprites.get("sky"));

        ITextureRegion groundTextureRegion = resourceManager.textures.get("ground");
        groundTextureRegion.setTextureSize(CAMERA_WIDTH, CAMERA_HEIGHT / 2);
        resourceManager.sprites.put(
            "ground",
            new Sprite(0, CAMERA_HEIGHT / 2, groundTextureRegion, getVertexBufferObjectManager())
        );
        scene.attachChild(resourceManager.sprites.get("ground"));
        
        ITextureRegion houseTextureRegion = resourceManager.textures.get("house");
        resourceManager.sprites.put(
            "house",
            new BaseSprite(0, CAMERA_HEIGHT / 2, houseTextureRegion, getVertexBufferObjectManager())
        );
        scene.attachChild(resourceManager.sprites.get("house"));

        ITextureRegion treeTextureRegion = resourceManager.textures.get("tree");
        int treeCoords[][] = {
            {CAMERA_WIDTH - 64, (int) (CAMERA_HEIGHT / 2 - treeTextureRegion.getHeight() / 2)},
            {CAMERA_WIDTH - 128, (int) (CAMERA_HEIGHT / 2 + 150)},
            {CAMERA_WIDTH - 256, (int) (CAMERA_HEIGHT / 2 + 32)}
        };
        for (int i = 0; i < treeCoords.length; i++) {
            resourceManager.sprites.put(
                "tree" + i,
                new Sprite(treeCoords[i][0], treeCoords[i][1], treeTextureRegion, getVertexBufferObjectManager())
            );
            scene.attachChild(resourceManager.sprites.get("tree" + i));
        }
        
        ITiledTextureRegion playerTextureRegion = (ITiledTextureRegion) resourceManager.textures.get("player");
        
        resourceManager.sprites.put(
            "player",
            new PlayerSprite(
                256, CAMERA_HEIGHT * 3 / 4,
                playerTextureRegion,
                getVertexBufferObjectManager()
            )
        );
        
        ((AnimatedSprite) resourceManager.sprites.get("player")).animate(
            new long[]{100, 100, 100, 100, 100, 100},
            18,
            23,
            true
        );
                     
        scene.attachChild(resourceManager.sprites.get("player"));
        
        ITiledTextureRegion zombieTextureRegion = (ITiledTextureRegion) resourceManager.textures.get("zombie");
        
        resourceManager.sprites.put(
            "zombie",
            new ZombieSprite(
                CAMERA_WIDTH - 64, CAMERA_HEIGHT * 3 / 4,
                zombieTextureRegion,
                getVertexBufferObjectManager()
            )
        );
        
        ((AnimatedSprite) resourceManager.sprites.get("zombie")).animate(
            new long[]{300, 300, 300, 300},
            8,
            11,
            true
        );
                     
        scene.attachChild(resourceManager.sprites.get("zombie"));
        
        if (DEBUG) {
            float x = resourceManager.sprites.get("house").getWidth();
            Line line = new Line(x, 0f, x, (float) CAMERA_HEIGHT, getVertexBufferObjectManager());
            scene.attachChild(line);
        }
        
        return scene;
    }
    
    @Override
    public Engine onCreateEngine(EngineOptions engineOptions)
    {
        return new FixedStepEngine(engineOptions, STEP);
    }
    
    @Override
    public boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent)
    {
        if (touchEvent.isActionMove()) {
            Sprite player = resourceManager.sprites.get("player");
            Sprite zombie = resourceManager.sprites.get("zombie");
            
            zombie.setPosition(touchEvent.getX(), touchEvent.getY());
            
            float dX = zombie.getX() - player.getX(), dY = zombie.getY() - player.getY();
            float angle = (float) Math.atan2(dY, dX);
            float rotation = MathUtils.radToDeg(angle);
            
            player.setRotation(rotation);
            
            return true;
        }
        
        return false;
    }
}