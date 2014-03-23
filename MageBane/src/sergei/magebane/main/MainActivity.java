package sergei.magebane.main;

import sergei.magebane.view.GameView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private static final String TAG = "Main Activity";
	private GameView gameView;
	private World world;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //make screen always on
		
		init(this);
		setContentView(gameView);
	}
	
	private void init(Context context){
		gameView = new GameView(context);
		world = new World(gameView, getResources());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void onPause(){
		super.onPause();
		world.onPause();
	}
	
	public void onResume(){
		super.onResume();
		world.onResume();
	}
	
}
