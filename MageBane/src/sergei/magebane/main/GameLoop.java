package sergei.magebane.main;

import java.util.Vector;

import android.util.Log;

public class GameLoop extends Thread {
	private static final String TAG = "Game Loop";
	private static final int FPS = 100;
	private boolean finished;
	private Vector<sergei.magebane.entitysystem.Systems.System> systems;

	public GameLoop(Vector<sergei.magebane.entitysystem.Systems.System> systems){
		this.systems = systems;
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
			
			for(sergei.magebane.entitysystem.Systems.System system: systems){
				system.update();
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
