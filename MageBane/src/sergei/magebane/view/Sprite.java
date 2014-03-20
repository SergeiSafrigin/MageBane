package sergei.magebane.view;

import sergei.magebane.logic.Mage;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {
	private Bitmap sprite;
	private int x, y;
	private int xSpeed;
	private GameView gameView;
	private Mage mage;
	
	public Sprite(Mage mage, GameView gameView, Bitmap sprite){
		this.gameView = gameView;
		this.sprite = sprite;
		this.mage = mage;
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(sprite, mage.getX(), mage.getY(), null);
	}
}
