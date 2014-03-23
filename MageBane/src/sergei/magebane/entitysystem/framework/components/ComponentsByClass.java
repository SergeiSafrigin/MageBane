package sergei.magebane.entitysystem.framework.components;

import java.util.Vector;

public class ComponentsByClass {
	private String name;
	public Vector<Component> components;
	
	public ComponentsByClass(String name){
		this.name = name;
		components = new Vector<Component>();
	}
	
	
	public String getName(){
		return name;
	}
}
