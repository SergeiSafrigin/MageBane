package sergei.magebane.entitysystem.Systems;

import java.util.HashMap;
import java.util.Vector;

import sergei.magebane.entitysystem.framework.EntityManager;
import sergei.magebane.entitysystem.framework.components.Component;
import sergei.magebane.entitysystem.framework.components.HealthComponent;
import sergei.magebane.entitysystem.framework.components.MovementComponent;
import sergei.magebane.entitysystem.framework.components.RenderComponent;
import sergei.magebane.view.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class RenderSystem implements MySystem{
	private static final String TAG = "Render System";
	private GameView gameView;
	private EntityManager entityManager;
	private SystemManager systemManager;
	private Bitmap background;
	private Paint redPaint;
	private Paint greenPaint;
	private Paint innerCirclePaint;
	private Paint outerCirclePaint;
	private Paint textPaint;
	
	private int fps;
	private int nextFps;
	private long startFpsTime;

	public RenderSystem(Bitmap background, GameView gameView, EntityManager entityManager, SystemManager systemManager){
		this.entityManager = entityManager;
		this.systemManager = systemManager;
		this.gameView = gameView;
		this.background = background;
		
		redPaint = new Paint();
		redPaint.setColor(Color.RED);

		greenPaint = new Paint();
		greenPaint.setColor(Color.GREEN);
		
		innerCirclePaint = new Paint();
		innerCirclePaint.setColor(Color.WHITE);

		outerCirclePaint = new Paint();
		outerCirclePaint.setColor(Color.BLACK);
		outerCirclePaint.setStyle(Style.STROKE);
		outerCirclePaint.setStrokeWidth(8);
		
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(30);
		
		startFpsTime = System.currentTimeMillis();
		fps = 0;
		nextFps = 0;
	}

	public void update(){
		if (System.currentTimeMillis() - startFpsTime >= 1000){
			fps = nextFps;
			nextFps = 0;
			startFpsTime = System.currentTimeMillis();
		}
		nextFps++;
		
		Canvas canvas = null;
		try{
			canvas = gameView.getHolder().lockCanvas();
			if (canvas == null)
				return;

			synchronized (gameView.getHolder()){
				//draw gray background
//				canvas.drawColor(Color.GRAY);
				canvas.drawBitmap(background, null, new RectF(0, 0, 1280, 800), null);
//				canvas.drawBitmap(background, null, new RectF(0, 0, 1280, 800), null);
				
				//draw fps
				canvas.drawText(""+fps, 15, 25, textPaint);
				
				//Movement Controller/Joystick
				InputSystem inputSystem = systemManager.getInputSystem();
				if (inputSystem != null){
					if (inputSystem.moveJoystickState()){
						canvas.drawCircle(inputSystem.getMoveJoystickStartX(), inputSystem.getMoveJoystickStartY(), 50, innerCirclePaint);
						canvas.drawCircle(inputSystem.getMoveJoystickStartX(), inputSystem.getMoveJoystickStartY(), 150, outerCirclePaint);
					}
				}
				//End of Movement Controller
				
				
				Vector<HashMap<String, Component>> componentsByEntities = entityManager.getComponentsByEntities();
				
				for(HashMap<String, Component> components: componentsByEntities){
					RenderComponent renderComponent = (RenderComponent) components.get(RenderComponent.NAME);
					HealthComponent healthComponent = (HealthComponent) components.get(HealthComponent.NAME);
					MovementComponent movementComponent = (MovementComponent) components.get(MovementComponent.NAME);
					
					if (renderComponent != null){
						float xPos, yPos;
						if (movementComponent == null){
							xPos = renderComponent.getX();
							yPos = renderComponent.getY();
						} else {
							xPos = movementComponent.getX();
							yPos = movementComponent.getY();
						}
			
						canvas.drawBitmap(renderComponent.getSprite(), xPos, yPos, null);
						
						if (healthComponent != null){
							canvas.drawRect(xPos, yPos-50, xPos+healthComponent.getMaxHp(), yPos-30, redPaint);
							canvas.drawRect(xPos, yPos-50, xPos+healthComponent.getCurHp(), yPos-30, greenPaint);
						}
					}
				}
			}
		} finally {
			if (canvas != null) {
				gameView.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
}
