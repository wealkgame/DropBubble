package wealk.game.DropBubble;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import android.content.Intent;
/**
 * @author dalong
 * 
 */
public class GameOver extends BaseGameActivity implements IOnMenuItemClickListener{
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final int CAMERA_WIDTH = 320;
	private static final int CAMERA_HEIGHT = 480;
	
	protected static final int MENU_RESTART = 0;
	protected static final int MENU_HELP = MENU_RESTART +1;
	protected static final int MENU_QUIT = MENU_HELP + 1;
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Camera mCamera;
	private MenuScene mMenuScene;
	private Scene mMainScene;
	private Texture mStartBackGroundTexture;
	private TextureRegion mStartBackGroundTextureRegion;
	private Texture mStartGameTexture;
	private TextureRegion mStartGameTextureRegion;
	private TextureRegion mHelpTextureRegion;
	private TextureRegion mExitTextureRegion;
	
	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));	
	}

	@Override
	public void onLoadResources() {
		//导入背景资源
		this.mStartBackGroundTexture = new Texture(512, 512, TextureOptions.DEFAULT);
		this.mStartBackGroundTextureRegion = TextureRegionFactory.createFromAsset(this.mStartBackGroundTexture, this, "gfx/gameoverbackgroup.png", 0, 0);
		//导入开始游戏资源
		this.mStartGameTexture = new Texture(256,256,TextureOptions.BILINEAR);
		this.mStartGameTextureRegion = TextureRegionFactory.createFromAsset(this.mStartGameTexture, this, "gfx/menu_restart.png",0,0);
		this.mHelpTextureRegion = TextureRegionFactory.createFromAsset(this.mStartGameTexture, this, "gfx/menu_help.png",0,50);
        this.mExitTextureRegion = TextureRegionFactory.createFromAsset(this.mStartGameTexture, this, "gfx/menu_quit.png",0,100);
		
		this.mEngine.getTextureManager().loadTextures(this.mStartBackGroundTexture,this.mStartGameTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.createMenuScene();
		this.mMainScene = new Scene(1);
		final int centerX = (CAMERA_WIDTH - this.mStartBackGroundTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mStartBackGroundTextureRegion.getHeight()) / 2;
		final Sprite bg = new Sprite(centerX, centerY, this.mStartBackGroundTextureRegion);	
		this.mMainScene.setChildScene(this.mMenuScene, false, true, true);	
		this.mMainScene.getTopLayer().addEntity(bg);
		return this.mMainScene;
	}

	@Override
	public void onLoadComplete() {}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
		case MENU_RESTART:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), DropBubble.class);
			startActivity(intent);
			this.finish();
			return true;
		case MENU_HELP:
			Intent intent01 = new Intent();
			intent01.setClass(getApplicationContext(), Help.class);
			startActivity(intent01);
			GameOver.this.finish();
			return true;
		case MENU_QUIT:
			Constant.score = 100;
			Constant.level = 1;
			Constant.GameStatus = 1;
			Constant.BlastStatic = false;
			Constant.BlastCount = 0;
			GameOver.this.finish();
			System.exit(0);
			return false;
		default:
			return false;
	}
		
	}
	private void createMenuScene(){
		this.mMenuScene = new MenuScene(this.mCamera);
		this.mMenuScene.addMenuItem(new SpriteMenuItem(MENU_RESTART, this.mStartGameTextureRegion));
		this.mMenuScene.addMenuItem(new SpriteMenuItem(MENU_HELP, this.mHelpTextureRegion));
		this.mMenuScene.addMenuItem(new SpriteMenuItem(MENU_QUIT, this.mExitTextureRegion));
		this.mMenuScene.buildAnimations();
		this.mMenuScene.setBackgroundEnabled(false);
		this.mMenuScene.setOnMenuItemClickListener(this);
	}

	
}
