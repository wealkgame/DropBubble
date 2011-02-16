package wealk.game.DropBubble;
/**
 * @author dalong
 * 
 */
import java.util.ArrayList;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import wealk.game.DropBubble.Constant;
import wealk.game.DropBubble.Tools;

public class DropBubble extends BaseExample implements IOnSceneTouchListener {
	// ===========================================================
	// 常量
	// ===========================================================
	private static final int CAMERA_WIDTH = 320;
	private static final int CAMERA_HEIGHT = 480;
	public Sprite spriteone;
	//定义触摸点
	private static int tx;
	private static int ty;
	//等级动态数组
	public ArrayList<Sprite>  level = new ArrayList<Sprite>();
	// ===========================================================
	// 字段
	// ===========================================================
	private Camera mCamera;
	private Texture mAutoParallaxBackgroundTexture;
	private TextureRegion mParallaxLayerBack;
	private TextureRegion mGridRegion;
	public TextureRegion mDropRegionOne;
	private TextureRegion mDropRegionTwo;
	private TextureRegion mDropRegionThree;
	public  Scene scene;
	private Texture mDropTextureTwo;
	public Texture mDropTextureOne;
	private Texture mDropTextureThree;
	private Texture mDropTextureFour;
	private TextureRegion mDropRegionFour;
	private float touchX;
	private float touchY;
	private Sprite mSpriteNextLevel;
	// ===========================================================
	// 构造函数
	// ===========================================================
	private Texture smalldropTexture;
	private TextureRegion DownUpDropRegion;
	private TextureRegion LeftRightDropRegion;
	private Texture smallleftdropTexture;
    public Tools tool;
	private Texture mScoreFontTexture;
	private Font mFont;
	private Text textCenter;
	private Texture mNextLevelTexture;
	private TextureRegion mNextLevelRegion;
	private Sprite Gridground;
	private Font mFontLevel;
	private Text textLevel;
	private Sprite Background;
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {		
		//加载背景图片和网格图片
		this.mAutoParallaxBackgroundTexture = new Texture(512, 512, TextureOptions.DEFAULT);
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/testback.png", 0, 0);
		this.mGridRegion = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/testback_2.png", 0, 0);
		this.mDropTextureOne = new Texture(64,64,TextureOptions.DEFAULT);
		this.mDropRegionOne = TextureRegionFactory.createFromAsset(this.mDropTextureOne, this, "gfx/onedrop.png", 0, 0);
		this.mDropTextureTwo = new Texture(64,64,TextureOptions.DEFAULT);
		this.mDropRegionTwo = TextureRegionFactory.createFromAsset(this.mDropTextureTwo, this, "gfx/twodrop.png", 0, 0);
		this.mDropTextureThree = new Texture(64,64,TextureOptions.DEFAULT);
		this.mDropRegionThree = TextureRegionFactory.createFromAsset(this.mDropTextureThree, this, "gfx/threedrop.png", 0, 0);
		this.mDropTextureFour = new Texture(64,64,TextureOptions.DEFAULT);
		this.mDropRegionFour = TextureRegionFactory.createFromAsset(this.mDropTextureFour, this, "gfx/four.png", 0, 0);
		//加载爆破后小水滴的图片
		this.smalldropTexture = new Texture(64,64,TextureOptions.DEFAULT);
		this.DownUpDropRegion = TextureRegionFactory.createFromAsset(this.smalldropTexture, this, "gfx/up_down.png", 0, 0);
		this.smallleftdropTexture = new Texture(64,64,TextureOptions.DEFAULT);
		this.LeftRightDropRegion = TextureRegionFactory.createFromAsset(this.smallleftdropTexture, this, "gfx/left_right.png", 0, 0);
		//加载字体(水滴数量)
		this.mScoreFontTexture = new Texture(256, 256, TextureOptions.BILINEAR);
		this.mFont = new Font(this.mScoreFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20, true, Color.BLUE);
		this.mFontLevel = new Font(this.mScoreFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48, true, Color.BLUE);
		this.mEngine.getTextureManager().loadTexture(this.mScoreFontTexture);
		this.mEngine.getFontManager().loadFonts(this.mFont,this.mFontLevel);
		//加载NextLevel图片
		this.mNextLevelTexture = new Texture(512,512,TextureOptions.DEFAULT);
		this.mNextLevelRegion = TextureRegionFactory.createFromAsset(this.mNextLevelTexture, this, "gfx/nextlevel.png",0,0);
		this.mEngine.getTextureManager().loadTextures(this.mAutoParallaxBackgroundTexture,this.mDropTextureOne,this.mDropTextureTwo,
				this.mDropTextureThree,this.mDropTextureFour,this.smalldropTexture,this.smallleftdropTexture,this.mNextLevelTexture);
	}
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.scene = new Scene(4);
		//初始资源
		InitialResources();
	    //随机生成小水珠的状态
	    tool = new Tools(this.mDropRegionOne,this.mDropRegionTwo,this.mDropRegionThree,
	    		this.mDropRegionFour,this.DownUpDropRegion,this.LeftRightDropRegion,this.scene);
	    //初始化函数
	    Initialization();
	    //主线程
	    MainThread();
	    //游戏开始时候加载(准备阶段)
	    scene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				scene.unregisterUpdateHandler(pTimerHandler);
			}
		}));
	    this.scene.setOnSceneTouchListener(this);
		return scene;
	}
	@Override
	public void onLoadComplete() {
	}
	// ===========================================================
	// Methods
	// ===========================================================
	//游戏开始需要资源
	public void InitialResources(){
		//画出背景
		this.Background = new Sprite(0, 0,this.mParallaxLayerBack);
	    this.scene.getLayer(Constant.LAYER_BACKGROUND).addEntity(this.Background);
	    //画出网格
	    this.Gridground = new Sprite(0, 0,this.mGridRegion);
	    this.scene.getLayer(Constant.LAYER_LAND).addEntity(this.Gridground);
	}

	//初始化函数
	public void Initialization(){
		//随机生成小水珠的状态(初始化)
	    tool.RandomDrop();
	    //由小水珠的状态画出不同的水滴（初始化）
	    tool.RandomLocationDrop();
	}
	//主线程(程序一开始就循环执行,只到游戏结束)
	public void MainThread(){
		//定时删除棋盘水珠和爆破水珠
	    this.Timerdeletedrop();
	    //更新分数和等级
	    this.TimerUpdateScore();
	    //线程检测是否到下一级画面和游戏结束画面
	    this.TimerUpdateNextOver();
	}
	//画出下一关
	private void OnDrawNextLevel(){
		this.mSpriteNextLevel = new Sprite(0, 0, this.mNextLevelRegion);
		level.add(mSpriteNextLevel);
		this.scene.getTopLayer().addEntity(mSpriteNextLevel);
	}
	//画出水滴数量
	private void OnDrawScore(){
		this.textCenter = new Text(150, 360, this.mFont, ""+(int)(Constant.score), HorizontalAlign.CENTER);
		scene.getLayer(Constant.LAYER_SCORE).addEntity(this.textCenter);
	}
	//画出等级
	private void OnDrawLevel(){
		this.textLevel = new Text(210, 410, this.mFont, ""+(Constant.level), HorizontalAlign.CENTER);
		scene.getLayer(Constant.LAYER_SCORE).addEntity(this.textLevel);
	}
	//线程删除一直执行
	public void Timerdeletedrop(){
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }
			@Override
			public void onUpdate(final float pSecondsElapsed) {
				//消去不需要棋盘水珠和爆破水珠
				if(tool.drop != null){
					   tool.CrossBorderDropLeftRight();
					   tool.CrossBorderDropUpDown();
					}
				if(Constant.GameStatus == 1)
					{  		for(int i=0;i<level.size();i++){
							if(level.get(i)!= null){
							scene.getTopLayer().removeEntity(level.get(i));
							level.remove(i);
							level.trimToSize();
							}
						} 
					}
				if(Constant.GameStatus == 1 && Constant.BlastStatic == true){
				   tool.CollisionDrop();
			      }
				tool.StaticDropAfter();
				}
		}); 
	}
	//用线程更新分数和等级
	public void TimerUpdateScore(){
		  scene.registerUpdateHandler(new TimerHandler(0.5f, true, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					if(textCenter != null){
						scene.getLayer(Constant.LAYER_SCORE).removeEntity(textCenter);
					}
					OnDrawScore();			
					if(textLevel != null){
					   scene.getLayer(Constant.LAYER_SCORE).removeEntity(textLevel);
					}
					//绘制等级
					OnDrawLevel();
				}
			}));
	}
	//线程检测是否到下一级画面和游戏结束画面
	public void TimerUpdateNextOver(){
		 scene.registerUpdateHandler(new TimerHandler(1.0f, true, new ITimerCallback() {	
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					if(tool.isClean())
					{  
					  tool.AllDropEliminate();
					  if(tool.AllLeft()){ 
					  Constant.GameStatus = 2;
					  OnDrawNextLevel();
					}
					}
					if(Constant.score<= 0){
						Constant.GameStatus = 3;
						Constant.BlastStatic = false;
					}
				}
			}));
	}
//	单击屏幕绘制根据水滴状态绘制小水滴（线程）
	public void TimerRedrawDrop(final int x,final int y){
		this.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				tool.RedrawDrop(x,y);
			}
		});
	}
	//单击屏幕删除不需要的水滴（线程）
	public void TimerRedrawDropOne(final int x,final int y){
		this.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				tool.EliminationDrop(x,y);
			}
		});
	}
	//下一关
	public void NextLevel(){
		//随机生成小水珠的状态
	    tool.RandomDrop();
	    //由小水珠的状态画出不同的水滴
	    tool.RandomLocationDrop();
	    //进入下一级别奖励1分
	    Constant.score += 1;
	    //游戏等级加1
	    Constant.level += 1;
	    //游戏状态为进行中
	    Constant.GameStatus = 1;
	    Constant.BlastStatic = false;    
	}
	//游戏结束（GameOver）
	public void GameOver(){
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), GameOver.class);
		startActivity(intent);
		DropBubble.this.finish();
		Constant.score = 100;
		Constant.GameStatus = 1;
		Constant.level = 1;
		Constant.BlastStatic = false;
		Constant.BlastCount = 0;
	}
	//删除屏幕上所有的爆破水珠
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		switch(Constant.GameStatus){
			case 1:
			 	touchX = pSceneTouchEvent.getX();
			 	touchY = pSceneTouchEvent.getY();
			 	Constant.BlastStatic = true;
			 	if(Constant.score +(int)(Constant.BlastCount/3) > 100){
			 		Constant.score = 100;
			 	}else{
			 		Constant.score = Constant.score +(int)(Constant.BlastCount/3);
			 	}
			 	Constant.BlastCount = 0;
			 	Log.v("mGridRegion", ""+this.mGridRegion.getWidth());
			 	if(touchY<= this.mGridRegion.getHeight()-5 && touchX< this.mGridRegion.getWidth()-5){	
			 	Constant.score -= 1; 
			 	tx = (int)((int)(touchX-0)/(Constant.mGridWidth));
			 	ty = (int)((int)(touchY-0)/(Constant.mGridWidth));
			 	//单击之后更新相关区域状态
			 	tool.UpdateDropStatic(tx, ty);
			 	//根据状态的改变绘制不同水滴。
			 	TimerRedrawDrop(tx, ty);
			 	//删除不需要的水滴
			 	TimerRedrawDropOne(tx,ty);
			  }
			 	break;
			case 2:
				NextLevel();
				break;
			case 3:
				GameOver();
				break;
			default:
				break;
		}
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Constant.score = 100;
			Constant.level = 1;
			Constant.GameStatus = 1;
			Constant.BlastStatic = false;
			Constant.BlastCount = 0;
		}
		return super.onKeyDown(keyCode, event);
	}

}
