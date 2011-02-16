package wealk.game.DropBubble;
/**
 * @author dalong
 * 
 */
public class Constant {
	/*******************************************************
	 * 静态常量
	 *******************************************************/
	//游戏场景的分层
	public static int LAYER_BACKGROUND = 0;//背景层
	public static int LAYER_LAND = LAYER_BACKGROUND + 1;//棋盘层
	public static int LAYER_DROP = LAYER_LAND + 1;//水滴层
	public static int LAYER_SCORE = LAYER_DROP + 1;
	//爆破后水珠移动速度
	public static float SpeedDrop = 200;
	//爆破是否开始
	public static boolean BlastStatic = false;
	//爆破后水珠的移动方向(向下和向右移动坐标相加与之相反 )
	public static int DirectionDownRight = 1;
	public static int DirectionUpLeft = -1;
	//游戏是否运行
	protected static boolean mGameRunning;
	//棋盘横竖格子数
	public static int mCount = 6;
	//网格宽度
	public static float mGridWidth = 320/mCount;
	//所剩水滴数量
	public static float score = 100;
	//水珠爆破个数
	protected static int BlastCount = 0;
	//游戏状态1:游戏进行中 2:过关 3：游戏结束
	protected static int GameStatus = 1;
	//记录游戏等级
	public static int level = 1;
    
	public Constant(){
	}

}
