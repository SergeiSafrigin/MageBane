package sergei.magebane.entitysystem.framework.components;


public class MovementComponent extends Component{
	public static final String NAME = "MovementComponent";
	public float x,y;
	public final float MAX_SPEED = 5.0f;
	public final float ACCELERATION = 0.1f;
	public double velocity = 0.0f;
	
	public MovementComponent(int eId, float x, float y, double velocity) {
		super(NAME, eId);
		this.x = x;
		this.y = y;
		this.velocity = velocity;
	}

	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public void move(float x, float y){
		this.x += x;
		this.y += y;
	}
	
}