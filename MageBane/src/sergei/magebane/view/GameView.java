package sergei.magebane.view;

import sergei.magebane.controller.MoveController;
import sergei.magebane.logic.Mage;
import sergei.magebane.main.GameLoop;
import sergei.magebane.main.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView{
	private static final String TAG = "Game View";
	private Paint textPaint;
	private SurfaceHolder holder;
	private MoveController moveController;
	private GameLoop gameLoop;
	private int fps;
	private int nextFps;
	private long startFpsTime;
	private Mage mage;

	public GameView(Context context) {
		super(context);
		init(this);
	}

	private void init(final GameView gameView){
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(30);
		

		gameLoop = new GameLoop(this);
		mage = new Mage(this, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		moveController = new MoveController(mage);

		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				gameLoop.onPause();
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				gameLoop = new GameLoop(gameView);
				gameLoop.start();
				startFpsTime = System.currentTimeMillis();
				fps = 0;
				nextFps = 0;

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void draw(Canvas canvas){
		if (canvas != null){
			canvas.drawColor(Color.RED);
			mage.draw(canvas);
			moveController.draw(canvas);

			calcAndDrawFps(canvas);
		}
	}

	private void calcAndDrawFps(Canvas canvas){
		if (System.currentTimeMillis() - startFpsTime >= 1000){
			fps = nextFps;
			nextFps = 0;
			startFpsTime = System.currentTimeMillis();
		}
		nextFps++;
		canvas.drawText(""+fps, 15, 25, textPaint);
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		final int action = e.getAction();
		final int index = e.getActionIndex();
		boolean goodPosition;
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_MOVE:
			moveController.move(e.getX(index), e.getY(index));
			break;
			
		case MotionEvent.ACTION_CANCEL:
			Log.e(TAG,"ACTION_CANCEL");
			break;
			
		case MotionEvent.ACTION_DOWN:			
			goodPosition = e.getX(index) <= getWidth()/2;
			moveController.addPointer(index, goodPosition);
			if (goodPosition)
				moveController.setPosition(e.getX(index), e.getY(index));
			//TODO add pointer to attack controller
			break;
			
		case MotionEvent.ACTION_POINTER_DOWN:
			goodPosition = e.getX(index) <= getWidth()/2;
			moveController.addPointer(index, goodPosition);
			if (goodPosition)
				moveController.setPosition(e.getX(index), e.getY(index));
			//TODO add pointer to attack controller
			break;
			
		case MotionEvent.ACTION_UP:
			moveController.removePointer(index);
			//TODO remove pointer to attack controller
			
			break;
			
		case MotionEvent.ACTION_POINTER_UP:
			moveController.removePointer(index);
			//TODO remove pointer to attack controller

			break;
		
		}
		return true;
	}
}
