package sergei.magebane.entitysystem.framework;

import java.util.HashMap;
import java.util.Vector;

import sergei.magebane.entitysystem.framework.components.Component;

public class EntityManager {
	private static final String TAG = "Entity Manager";
	private int lowestUnassignedEid;
	private Vector<HashMap<String, Component>> componentsByEntities;
	private Vector<Entity> entities;

	public EntityManager(){
		componentsByEntities = new Vector<HashMap<String, Component>>();
		entities = new Vector<Entity>();
	}

	public Vector<HashMap<String, Component>> getComponentsByEntities(){
		return componentsByEntities;
	}

	public int generateNewEid(){
		if (lowestUnassignedEid < Integer.MAX_VALUE)
			return lowestUnassignedEid++;
		else {
			for(int i = 0; i < Integer.MAX_VALUE; i++){
				if (!entities.contains(i))
					return i;
			}
		}
		return -1;
	}

	public Entity createEntity(){
		componentsByEntities.add(new HashMap<String, Component>());
		
		int eid = componentsByEntities.size()-1;
		Entity entity = new Entity(eid);
		entities.add(entity);
		return entity;
	}

	public void addComponent(Component component){		
		if (componentsByEntities.size() > component.getEid())
			componentsByEntities.get(component.getEid()).put(component.getName(), component);
		else{
			HashMap<String, Component> newHashMap = new HashMap<String, Component>();
			newHashMap.put(component.getName(), component);
			componentsByEntities.add(newHashMap);
		}
	}
}