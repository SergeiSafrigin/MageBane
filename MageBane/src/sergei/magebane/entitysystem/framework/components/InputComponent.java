package sergei.magebane.entitysystem.framework.components;

public class InputComponent extends Component {
	public static final String NAME = "InputSystem";
	private int eId;
	
	public InputComponent(int eId){
		super(NAME, eId);
	}
}
