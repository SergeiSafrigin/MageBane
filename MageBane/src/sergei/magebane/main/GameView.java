package sergei.magebane.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View{
	private Bitmap screen;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public GameView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		screen = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawColor(Color.RED);
		canvas.drawBitmap(screen, 50, 50, null);
	}

}
