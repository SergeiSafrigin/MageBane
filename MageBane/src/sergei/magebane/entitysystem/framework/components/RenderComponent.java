package sergei.magebane.entitysystem.framework.components;

import android.graphics.Bitmap;

public class RenderComponent extends Component{
	public static final String NAME = "RenderComponent";
	private Bitmap sprite;
	private float x, y;
	private int speed;
	
	public RenderComponent(Bitmap sprite, int eId){
		super(NAME, eId);
		this.sprite = sprite;
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
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setSPeed(int speed){
		this.speed = speed;
	}
	
	public void move(float x, float y){
		this.x += x;
		this.y += y;
	}
	
	public int getSpeed(){
		return speed;
	}
}