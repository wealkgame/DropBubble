package wealk.game.DropBubble;
import java.util.ArrayList;
import java.util.Random;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import wealk.game.DropBubble.drop;
/**
 * @author dalong
 * 
 */
public class Tools{
	/***********************************************************
	 * 声明
	 ***************************************************/
	public Random mRandom;	
	//水滴状态数组
	public int[][] dropstatus = new int[6][6];
	//水滴drop类
	public drop mdrop;
	public Sprite mDropSprite;
	public Sprite mBlastDropSpriteLR;
	public Sprite mBlastSpriteThreeUD;
	public Sprite mDropSpriteFour;
	//主场景对象
	public Scene Mainscene;
	public TextureRegion mTextureRegionOne;
	public TextureRegion mTextureRegionTwo;
	public TextureRegion mTextureRegionThree;
	public TextureRegion mTextureRegionFour;
	public TextureRegion mLeftRightDropRegion;
	public TextureRegion mDownUpDropRegion;
	public ArrayList<Sprite>  list = new ArrayList<Sprite>();
	public ArrayList<Sprite>  drop = new ArrayList<Sprite>();
	public ArrayList<Integer> intx = new ArrayList<Integer>();
	public ArrayList<Integer> inty = new ArrayList<Integer>();

	//析构函数
	public Tools(TextureRegion TextureRegionOne,TextureRegion TextureRegionTwo,TextureRegion TextureRegionThree,
			TextureRegion TextureRegionFour,TextureRegion DownUpDropRegion,TextureRegion LeftRightDropRegion,Scene scence){
		
		this.mTextureRegionOne = TextureRegionOne;
		this.mTextureRegionTwo = TextureRegionTwo;
		this.mTextureRegionThree = TextureRegionThree;
		this.mTextureRegionFour = TextureRegionFour;
		this.mLeftRightDropRegion = LeftRightDropRegion;
		this.mDownUpDropRegion = DownUpDropRegion;
		this.Mainscene = scence;
		mdrop = new drop();
	}
	//绘制水珠
	public void DrawDrop(float x,float y,TextureRegion mTextureRegion){
		this.mDropSprite = mdrop.handleDrop(x, y, mTextureRegion);
		list.add(this.mDropSprite);
		this.Mainscene.getLayer(Constant.LAYER_DROP).addEntity(this.mDropSprite);
		
	}
	//绘制爆破小水滴（上下）
	public void BlastDropUpDown(int Direction,float x,float y,TextureRegion mTextureRegion){
		this.mBlastSpriteThreeUD = mdrop.handleDrop(x, y, mTextureRegion);
		this.mBlastSpriteThreeUD.setVelocityY(Direction*(Constant.SpeedDrop));
		drop.add(this.mBlastSpriteThreeUD);
		this.Mainscene.getLayer(Constant.LAYER_DROP).addEntity(this.mBlastSpriteThreeUD);
		
	}
	//绘制爆破小水滴左右
	public void BlastDropLeftRight(int Direction,float x,float y,TextureRegion mTextureRegion){
		this.mBlastDropSpriteLR = mdrop.handleDrop(x, y, mTextureRegion);
		this.mBlastDropSpriteLR.setVelocityX(Direction*(Constant.SpeedDrop));
		drop.add(this.mBlastDropSpriteLR);
		this.Mainscene.getLayer(Constant.LAYER_DROP).addEntity(this.mBlastDropSpriteLR);
	}
	//生成四个不同方向小水滴
	public void FourDirectionDrop(float x,float y){
		BlastDropUpDown(Constant.DirectionUpLeft,x,y,this.mDownUpDropRegion);
		BlastDropUpDown(Constant.DirectionDownRight,x,y,this.mDownUpDropRegion);
		BlastDropLeftRight(Constant.DirectionUpLeft,x,y,this.mLeftRightDropRegion);
		BlastDropLeftRight(Constant.DirectionDownRight,x,y,this.mLeftRightDropRegion);
	}
	public void TimerFourDirectionDrop(final float x,final float y){
		 this.Mainscene.registerUpdateHandler(new TimerHandler(0.2f, false, new ITimerCallback() {	
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					FourDirectionDrop(x,y);
				}
			}));
	}
	//随机生成小水珠的状态(初始化)
	public void RandomDrop(){
		mRandom = new Random();
		for(int i=0;i<Constant.mCount;i++){
			for(int j=0;j<Constant.mCount;j++){
				dropstatus[i][j] = mRandom.nextInt(5);
			}
		}
	}
	//根据随机出现的小水珠状态绘制不同的小水珠(初始化)
	public void RandomLocationDrop(){
		for(int i=0;i< Constant.mCount; i++){
			for(int j=0;j< Constant.mCount; j++){
				switch(dropstatus[i][j]){
				case 0:
					break;
				case 1:
					DrawDrop(i*(Constant.mGridWidth), j*(Constant.mGridWidth),this.mTextureRegionOne);
					break;
				case 2:
					DrawDrop(i*(Constant.mGridWidth), j*(Constant.mGridWidth),this.mTextureRegionTwo);
					break;
				case 3:
					DrawDrop(i*(Constant.mGridWidth), j*(Constant.mGridWidth),this.mTextureRegionThree);
					break;
				case 4:
					DrawDrop(i*(Constant.mGridWidth), j*(Constant.mGridWidth),this.mTextureRegionFour);
					break;
				default:
					break;
				}
			}
		}	
	}
	//更新小水珠的状态
	public void UpdateDropStatic(int x,int y){
		switch(dropstatus[x][y]){
		case 0:
			dropstatus[x][y] = 1;
			break;
		case 1:
			dropstatus[x][y] = 2;
			break;
		case 2:
			dropstatus[x][y] = 3;
			break;
		case 3:
			dropstatus[x][y] = 4;
			break;
		case 4:
			dropstatus[x][y] = 0;
			TimerFourDirectionDrop(x*(Constant.mGridWidth)+5,y*(Constant.mGridWidth)+5);
//			FourDirectionDrop(x*(Constant.mGridWidth),y*(Constant.mGridWidth));
			break;
		default:
			break;
		}
	}
	//重新绘制小水珠
	public void RedrawDrop(int x,int y){
		switch(dropstatus[x][y]){
		case 1:
			DrawDrop(x*(Constant.mGridWidth),y*(Constant.mGridWidth),this.mTextureRegionOne);
			break;
		case 2:
			DrawDrop(x*(Constant.mGridWidth),y*(Constant.mGridWidth),this.mTextureRegionTwo);
			break;
		case 3:
			DrawDrop(x*(Constant.mGridWidth),y*(Constant.mGridWidth),this.mTextureRegionThree);
			break;
		case 4:
			DrawDrop(x*(Constant.mGridWidth),y*(Constant.mGridWidth),this.mTextureRegionFour);
			break;
		default:
			break;
		}
	}

	//消去不需要的小水珠
	public void EliminationDrop(int x,int y){
				for(int i=0;i<list.size();i++){
					if(list.get(i).getX()== x*(Constant.mGridWidth) && list.get(i).getY()== y*(Constant.mGridWidth)){
					this.Mainscene.getLayer(Constant.LAYER_DROP).removeEntity(list.get(i));
				    list.remove(list.get(i));
				    list.trimToSize();
					}
				}
	}
	//消除越界水滴（上下）
	public void CrossBorderDropUpDown(){
		if(mBlastSpriteThreeUD!= null){
			for(int i=0;i<drop.size();i++){
				if(drop.get(i).getY()+(Constant.mGridWidth/2)>= ((Constant.mCount)*(Constant.mGridWidth)) || drop.get(i).getY()<-(Constant.mGridWidth)){
					this.Mainscene.getLayer(Constant.LAYER_DROP).removeEntity(drop.get(i));
					drop.remove(drop.get(i));
					drop.trimToSize();
				}
			}
		}
	}
	//消除越界水滴(左右)
	public void CrossBorderDropLeftRight(){
		if(mBlastDropSpriteLR!= null){
			for(int i=0;i<drop.size();i++){
				if(drop.get(i).getX()+(Constant.mGridWidth/2)>= ((Constant.mCount)*(Constant.mGridWidth)) || drop.get(i).getX()<-(Constant.mGridWidth)){
					this.Mainscene.getLayer(Constant.LAYER_DROP).removeEntity(drop.get(i));
					drop.remove(drop.get(i));
					drop.trimToSize();	
					
				}
			}
		}
	}
	//碰撞检测
	public void CollisionDrop(){
				for(int i=0;i<list.size();i++){
					for(int j=0;j<drop.size();j++){
						 if(drop.get(j)!=null && list.get(i)!=null && drop.get(j).collidesWith(list.get(i))){
							 this.Mainscene.getLayer(Constant.LAYER_DROP).removeEntity(drop.get(j));	 
							 drop.remove(drop.get(j));
							 drop.trimToSize();
							 int mx = (int)((int)(list.get(i).getX()-0)/(Constant.mGridWidth));
							 int my = (int)((int)(list.get(i).getY()-0)/(Constant.mGridWidth));
							 intx.add(mx);
							 inty.add(my);
						}
					}	
				}
			}
	//删除所有的爆破小水珠
	public void AllDropEliminate(){ 
		for(int i=0; i< drop.size(); i++){
			{    
			 this.Mainscene.getLayer(Constant.LAYER_DROP).removeEntity(drop.get(i));	 
			 drop.remove(drop.get(i));
			 drop.trimToSize();
			}
		}
		for(int j=0;j<intx.size();j++){
			 intx.remove(j);
			 intx.trimToSize();	 
		}
		for(int i = 0;i< inty.size();i++){
			inty.remove(i);
			inty.trimToSize();
		}
		
	}
	//消去水珠状态改变
	public void StaticDropAfter(){
		for(int i=0;i<intx.size();i++){
			switch (dropstatus[intx.get(i)][inty.get(i)]) {
			case 1:
				dropstatus[intx.get(i)][inty.get(i)] = 2;
				EliminationDrop(intx.get(i),inty.get(i));
				DrawDrop(intx.get(i)*(Constant.mGridWidth), inty.get(i)*(Constant.mGridWidth),this.mTextureRegionTwo);
				intx.remove(i);
				inty.remove(i);
				intx.trimToSize();
				inty.trimToSize();
				break;
			case 2:
				dropstatus[intx.get(i)][inty.get(i)] = 3;
				EliminationDrop(intx.get(i),inty.get(i));
				DrawDrop(intx.get(i)*(Constant.mGridWidth), inty.get(i)*(Constant.mGridWidth),this.mTextureRegionThree);
				intx.remove(i);
				inty.remove(i);
				intx.trimToSize();
				inty.trimToSize();
				break;
			case 3:
				dropstatus[intx.get(i)][inty.get(i)] = 4;
				EliminationDrop(intx.get(i),inty.get(i));
				DrawDrop(intx.get(i)*(Constant.mGridWidth), inty.get(i)*(Constant.mGridWidth),this.mTextureRegionFour);
				intx.remove(i);
				inty.remove(i);
				intx.trimToSize();
				inty.trimToSize();
				break;
			case 4:
				dropstatus[intx.get(i)][inty.get(i)] = 0;
				EliminationDrop(intx.get(i),inty.get(i));
				TimerFourDirectionDrop(intx.get(i)*(Constant.mGridWidth)+5,inty.get(i)*(Constant.mGridWidth)+5);
				Constant.BlastCount += 1;
				intx.remove(i);
				inty.remove(i);
				intx.trimToSize();
				inty.trimToSize();
				break;
			default:
				break;
			}
		}	
	}

	//屏幕上的水珠是否全部消去
	public boolean isClean(){
		for (int i = 0; i < Constant.mCount; i++) {
			for (int j = 0; j < Constant.mCount; j++) {
				if (dropstatus[i][j] == 1 || dropstatus[i][j] == 2 || dropstatus[i][j] == 3
						|| dropstatus[i][j] == 4) {
						return false;
				}
			}
		}
		return true;
	}
	//判断屏幕上的水珠是否已经全部历离开棋盘
	public boolean AllLeft(){
		for (int i = 0; i < drop.size(); i++) {
				if (drop.get(i).getX()< 320 && drop.get(i).getX()> 0) {
						return false;
				}
		}
		return true;
	}
}

