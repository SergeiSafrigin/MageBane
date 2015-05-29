package sergei.magebane.entitysystem.Systems;

import java.util.HashMap;
import java.util.Vector;

import sergei.magebane.entitysystem.framework.EntityManager;
import sergei.magebane.entitysystem.framework.components.CameraComponent;
import sergei.magebane.entitysystem.framework.components.Component;
import sergei.magebane.entitysystem.framework.components.InputComponent;
import sergei.magebane.entitysystem.framework.components.MovementComponent;
import sergei.magebane.utils.MathUtil;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class InputSystem extends Thread implements MySystem,OnTouchListener{
	private static final String TAG = "Input System";
	private static final boolean LEFT_SCREEN = true;
	private static final boolean RIGHT_SCREEN = false;
	private static final int MOVE_JOYSTICK_MIN_DIS = 50;
	private EntityManager entityManager;
	private boolean moveJoystick, attackJoystick;
	private float moveJoystickStartX, moveJoystickStartY;
	private float moveJoystickCurrX, moveJoystickCurrY;
	private Vector<Boolean> pointers;
	private int leftPointers, rightPointers;
	private boolean finished;
	//	private long lastInfoTime;

	public InputSystem(EntityManager entityManager){
		this.entityManager = entityManager;
		pointers = new Vector<Boolean>(10);
	}

	@Override
	public void run(){
		finished = false;
		while(!finished){
			Vector<HashMap<String, Component>> componentsByEnteties = entityManager.getComponentsByEntities();
			if (moveJoystick && MathUtil.distance(moveJoystickStartX, moveJoystickStartY, moveJoystickCurrX, moveJoystickCurrY) >= MOVE_JOYSTICK_MIN_DIS){
				for(HashMap<String, Component> components: componentsByEnteties){
					InputComponent inputComponent = (InputComponent) components.get(InputComponent.NAME);
					if (inputComponent != null){
						MovementComponent movmentComponent = (MovementComponent) components.get(MovementComponent.NAME);
						if (movmentComponent != null){

							movmentComponent.velocity += movmentComponent.ACCELERATION;
							if (movmentComponent.velocity > movmentComponent.MAX_SPEED){
								movmentComponent.velocity = movmentComponent.MAX_SPEED;
							}

							double r = movmentComponent.velocity;
							double alpha = MathUtil.hAngle(moveJoystickStartX, moveJoystickStartY, moveJoystickCurrX, moveJoystickCurrY);
							float moveX = Math.round(r * Math.cos(Math.toRadians(alpha)));
							float moveY = Math.round(r * Math.sin(Math.toRadians(alpha)));

							movmentComponent.move(moveX, moveY);
							
							CameraComponent cameraComponent = (CameraComponent) components.get(CameraComponent.NAME);
							if (cameraComponent != null){
								cameraComponent.move(moveX, moveY);
							}
						}
					}
				}
			} else {
				for(HashMap<String, Component> components: componentsByEnteties){
					InputComponent inputComponent = (InputComponent) components.get(InputComponent.NAME);
					if (inputComponent != null){
						MovementComponent movmentComponent = (MovementComponent) components.get(MovementComponent.NAME);
						if (movmentComponent != null){

							movmentComponent.velocity -= movmentComponent.DECELERATION;
							if (movmentComponent.velocity <= 0){
								movmentComponent.velocity = 0;
							}

							if (movmentComponent.velocity > 0){
								double r = movmentComponent.velocity;
								double alpha = MathUtil.hAngle(moveJoystickStartX, moveJoystickStartY, moveJoystickCurrX, moveJoystickCurrY);
								float moveX = Math.round(r * Math.cos(Math.toRadians(alpha)));
								float moveY = Math.round(r * Math.sin(Math.toRadians(alpha)));

								movmentComponent.move(moveX, moveY);
							}
						}
					}
				}
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				Log.e(TAG,e.toString());
			}
		}
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

					//					lastInfoTime = System.currentTimeMillis();

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

	@Override public void update() {}
}
