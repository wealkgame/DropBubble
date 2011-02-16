package wealk.game.DropBubble;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
/**
 * @author dalong
 * 
 */
public class drop {
	public Sprite mDropSprite;
	public Sprite mBlastDropSprite;
	public drop(){
	}
	//画出小水滴
	public Sprite handleDrop(float x,float y,TextureRegion mTextureRegion){	
		this.mDropSprite = new Sprite(x, y, mTextureRegion);
		return this.mDropSprite;
	}
}
