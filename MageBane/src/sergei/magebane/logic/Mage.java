package sergei.magebane.logic;

import sergei.magebane.view.GameView;
import sergei.magebane.view.Sprite;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Mage {
	private static final String TAG = "Mage";
	private static final float START_X = 30;
	private static final float START_Y = 30;
	private float x;
	private float y;
	private Sprite sprite;
	
	public Mage(GameView gameView, Bitmap sprite){
		this.sprite = new Sprite(this, gameView, sprite);
		x = START_X;
		y = START_Y;
	}
	
	public void move(float top, float left){
		x += left;
		y += top;
	}
	
	public void draw(Canvas canvas){
		sprite.draw(canvas);
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}
