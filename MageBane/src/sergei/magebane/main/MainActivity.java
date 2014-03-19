package sergei.magebane.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	private GameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init(this);
		setContentView(gameView);
	}
	
	private void init(Context context){
		gameView = new GameView(context);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
