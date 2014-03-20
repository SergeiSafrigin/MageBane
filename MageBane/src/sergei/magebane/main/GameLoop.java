package sergei.magebane.main;

import sergei.magebane.view.GameView;
import android.graphics.Canvas;
import android.util.Log;

public class GameLoop extends Thread {
	private static final String TAG = "Game Loop";
	private static final int FPS = 100;
	private GameView gameView;
	private boolean finished;
	
	
	public GameLoop(GameView gameView){
		this.gameView = gameView;
	}
	
	@Override
	public void run(){
		finished = false;
		loop();
	}
	
	private void loop(){
		long ticksPS = 1000 / FPS;
		
		long startTime;
		long sleepTime;
		
		while(!finished){
			startTime = System.currentTimeMillis();
			Canvas canvas = null;
			try{
				canvas = gameView.getHolder().lockCanvas();
				synchronized (gameView.getHolder()) {
					gameView.draw(canvas);
				}
			} finally {
				if (canvas != null) {
					gameView.getHolder().unlockCanvasAndPost(canvas);
				}
			}
			
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try {
				if (sleepTime > 0)
					sleep(sleepTime);
				else
					sleep(10);
			} catch (InterruptedException e) {
				Log.e(TAG,""+e.toString());
			}
			
			
		}
	}
	
	public void onPause(){
		finished = true;
	}
}
