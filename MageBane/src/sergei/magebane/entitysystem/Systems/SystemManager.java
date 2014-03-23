package sergei.magebane.entitysystem.Systems;

public class SystemManager {
	private InputSystem inputSystem;
	
	public void setInputSystem(InputSystem inputSystem){
		this.inputSystem = inputSystem;
	}
	
	public InputSystem getInputSystem(){
		return inputSystem;
	}
	
	public void onPause(){
		inputSystem.onPause();
	}
}
