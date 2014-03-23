package sergei.magebane.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView{
	private static final String TAG = "Game View";
	private SurfaceHolder holder;

	public GameView(Context context) {
		super(context);
		init(this);
	}

	private void init(final GameView gameView){		
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			@Override public void surfaceDestroyed(SurfaceHolder holder) {}
			@Override public void surfaceCreated(SurfaceHolder holder) {}
			@Override public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}
		});
	}

}
