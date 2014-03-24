package sergei.magebane.entitysystem.framework.components;

import android.graphics.Bitmap;

public class RenderComponent extends Component{
	public static final String NAME = "RenderComponent";
	private Bitmap sprite;
	private float x,y;
	
	public RenderComponent(Bitmap sprite, int eId, float x, float y){
		super(NAME, eId);
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}
	
	public Bitmap getSprite(){
		return sprite;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}