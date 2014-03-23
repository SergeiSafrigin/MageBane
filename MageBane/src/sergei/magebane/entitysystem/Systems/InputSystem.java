package sergei.magebane.entitysystem.Systems;

import java.util.Vector;

import sergei.magebane.entitysystem.framework.EntityManager;
import sergei.magebane.entitysystem.framework.components.Component;
import sergei.magebane.entitysystem.framework.components.ComponentsByClass;
import sergei.magebane.entitysystem.framework.components.InputComponent;
import sergei.magebane.entitysystem.framework.components.RenderComponent;
import sergei.magebane.utils.MathUtil;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class InputSystem extends Thread implements System,OnTouchListener{
	private static final String TAG = "Input System";
	private static final boolean LEFT_SCREEN = true;
	private static final boolean RIGHT_SCREEN = false;
	private static final int MOVE_JOYSTICK_MIN_DIS = 20;
	private ComponentsByClass componentsByClass;
	private EntityManager entityManager;
	private boolean moveJoystick, attackJoystick;
	private float moveJoystickStartX, moveJoystickStartY;
	private float moveJoystickCurrX, moveJoystickCurrY;
	private Vector<Boolean> pointers;
	private int leftPointers, rightPointers;
	private boolean finished;

	public InputSystem(EntityManager entityManager){
		this.entityManager = entityManager;
		componentsByClass = entityManager.getComponentsByClass(InputComponent.NAME);

		pointers = new Vector<Boolean>(10);
	}

	@Override
	public void run(){
		finished = false;
		while(!finished){
			if (moveJoystick){
				if (MathUtil.distance(moveJoystickStartX, moveJoystickStartY, moveJoystickCurrX, moveJoystickCurrY) >= MOVE_JOYSTICK_MIN_DIS){
					for(Component inputComponent: componentsByClass.components){
						ComponentsByClass renderComponents = entityManager.getComponentsByClass(RenderComponent.NAME);
						for(Component renderComponent: renderComponents.components){
							if (renderComponent.getEid() == inputComponent.getEid()){
								RenderComponent renComponent = (RenderComponent) renderComponent;

								int speed = renComponent.getSpeed();
								double r = Math.pow(speed, 2);
								double alpha = MathUtil.hAngle(moveJoystickStartX, moveJoystickStartY, moveJoystickCurrX, moveJoystickCurrY);
								float moveX = Math.round(r * Math.cos(Math.toRadians(alpha)));
								float moveY = Math.round(r * Math.sin(Math.toRadians(alpha)));
								
								renComponent.move(moveX, moveY);
							}
						}
					}
				}
			}

			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				Log.e(TAG,e.toString());
			}
		}
	}

	@Override
	public void update() {
		//TODO add position update for all the entities that have Input Component
	}



	@Override
	public boolean onTouch(View v, MotionEvent e) {
		final int action = e.getAction();
		final int index = e.getActionIndex();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_MOVE:
			moveJoystickCurrX = e.getX(index);
			moveJoystickCurrY = e.getY(index);
			break;

		case MotionEvent.ACTION_CANCEL:
			Log.e(TAG,"ACTION_CANCEL");
			break;

		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			if (e.getX(index) <= v.getWidth()/2){ //left part of the screen is touched
				pointers.add(LEFT_SCREEN);
				leftPointers++;
				if (leftPointers == 1){
					moveJoystick = true;
					moveJoystickStartX = e.getX(index);
					moveJoystickStartY = e.getY(index);
					moveJoystickCurrX = moveJoystickStartX;
					moveJoystickCurrY =moveJoystickStartY;
				}
			} else {
				pointers.add(RIGHT_SCREEN);
			}
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			if (pointers.size() > index){
				if (pointers.get(index) == LEFT_SCREEN)
					leftPointers--;
				else
					rightPointers--;
				pointers.remove(index);
			}

			if (leftPointers == 0){
				moveJoystick = false;
			}
			if (rightPointers == 0){
				attackJoystick = false;
			}

			break;
		}
		
		try {
			Thread.sleep(16);
		} catch (InterruptedException e2) {
			Log.e(TAG,e.toString());
		}
		
		return true;
	}

	public boolean finished(){
		return finished;
	}
	
	public float getMoveJoystickStartX(){
		return moveJoystickStartX;
	}

	public float getMoveJoystickStartY(){
		return moveJoystickStartY;
	}

	public boolean moveJoystickState(){
		return moveJoystick;
	}

	public void onPause(){
		finished = true;
	}
}
