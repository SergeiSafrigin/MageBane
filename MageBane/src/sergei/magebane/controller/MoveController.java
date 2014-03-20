package sergei.magebane.controller;

import java.util.Vector;

import sergei.magebane.logic.Mage;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;


public class MoveController {
	private static final String TAG = "Move Controller";
	private Mage mage;
	private Paint innerCirclePaint;
	private Paint outerCirclePaint;
	private float x;
	private float y;
	private int numOfPointers;
	private Vector<Boolean> pointers;


	public MoveController(Mage mage){
		this.mage = mage;
		init();
	}

	public void init(){
		numOfPointers = 0;
		pointers = new Vector<Boolean>();
		
		innerCirclePaint = new Paint();
		innerCirclePaint.setColor(Color.BLACK);

		outerCirclePaint = new Paint();
		outerCirclePaint.setColor(Color.BLACK);
		outerCirclePaint.setStyle(Style.STROKE);
		outerCirclePaint.setStrokeWidth(5);
	}
	
	public void move(float x, float y){
		float left = x - this.x;
		float top = y - this.y;
		if (left > 5)
			left = 5;
		else if (left < -5)
			left = -5;
		if (top > 5)
			top = 5;
		else if (top < -5)
			top = -5;
		mage.move(top, left);
		Log.e(TAG, "top = "+top+", left = "+left);
	}

	public void setPosition(float x, float y){
		if (numOfPointers == 1){
			this.x = x;
			this.y = y;
		}
	}
	
	public void addPointer(int index, boolean goodPosition){
		pointers.add(goodPosition);
		if (goodPosition)
			numOfPointers++;
	}
	
	public void removePointer(int index){
		if (pointers.size() > index){
			if (pointers.get(index))
				numOfPointers--;
			pointers.remove(index);
		}
	}

	public void draw(Canvas canvas){
		if (numOfPointers > 0){
			canvas.drawCircle(x, y, 50, innerCirclePaint);
			canvas.drawCircle(x, y, 150, outerCirclePaint);
		}
	}


}
