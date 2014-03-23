package sergei.magebane.entitysystem.Systems;

import java.util.Vector;
import java.lang.System;

import sergei.magebane.entitysystem.framework.EntityManager;
import sergei.magebane.entitysystem.framework.components.Component;
import sergei.magebane.entitysystem.framework.components.HealthComponent;
import sergei.magebane.entitysystem.framework.components.RenderComponent;
import sergei.magebane.view.GameView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class RenderSystem implements sergei.magebane.entitysystem.Systems.System{
	private static final String TAG = "Render System";
	private GameView gameView;
	private EntityManager entityManager;
	private SystemManager systemManager;
	private Paint redPaint;
	private Paint greenPaint;
	private Paint innerCirclePaint;
	private Paint outerCirclePaint;
	private Paint textPaint;
	
	private int fps;
	private int nextFps;
	private long startFpsTime;

	public RenderSystem(GameView gameView, EntityManager entityManager, SystemManager systemManager){
		this.entityManager = entityManager;
		this.systemManager = systemManager;
		this.gameView = gameView;
		
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
				canvas.drawColor(Color.GRAY);
				
				
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
				
				
				Vector<Vector<Component>> componentsByEntities = entityManager.getComponentsByEntities();
				
				RenderComponent renderComponent = null;
				HealthComponent healthComponent = null;
				
				for(Vector<Component> components: componentsByEntities){
					for(Component component: components){
						if (component instanceof RenderComponent)
							renderComponent = (RenderComponent)component;
						else if (component instanceof HealthComponent)
							healthComponent = (HealthComponent)component;
					}
					
					if (renderComponent != null){
						canvas.drawBitmap(renderComponent.getSprite(), renderComponent.getX(), renderComponent.getY(), null);
						if (healthComponent != null){
							canvas.drawRect(renderComponent.getX(), renderComponent.getY()-50, renderComponent.getX()+healthComponent.getMaxHp(), renderComponent.getY()-30, redPaint);
							canvas.drawRect(renderComponent.getX(), renderComponent.getY()-50, renderComponent.getX()+healthComponent.getCurHp(), renderComponent.getY()-30, greenPaint);
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
