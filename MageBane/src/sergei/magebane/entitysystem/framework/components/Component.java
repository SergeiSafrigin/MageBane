package sergei.magebane.entitysystem.framework.components;

public abstract class Component {
	protected String name;
	protected int eId;
	
	
	public Component(String name, int eId){
		this.name = name;
		this.eId = eId;
	}
	
	public String getName(){
		return name;
	}
	
	public int getEid(){
		return eId;
	}
}
