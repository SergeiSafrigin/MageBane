package sergei.magebane.entitysystem.framework.components;

public class CameraComponent extends Component {
	public static final String NAME = "CameraComponent";
	private float xPosition;
	private float yPosition;
	private int cameraWidth;
	private int cameraHeight;
	private int screenWidth;
	private int screenHeight;
	
	public CameraComponent(int eId, float xPosition, float yPosition, int cameraWidth, int cameraHeight, int screenWidth, int screenHeight) {
		super(NAME, eId);
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.cameraHeight = cameraHeight;
		this.cameraWidth = cameraWidth;
	}
	
	public void move(float xPosistion, float yPosistion){
		this.xPosition = xPosistion;
		this.yPosition = yPosistion;
	}

}
