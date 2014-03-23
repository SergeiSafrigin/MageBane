package sergei.magebane.entitysystem.framework;

import java.util.Vector;

import sergei.magebane.entitysystem.framework.components.Component;
import sergei.magebane.entitysystem.framework.components.ComponentsByClass;

public class EntityManager {
	private static final String TAG = EntityManager.class.getName();
	private int lowestUnassignedEid;
	private Vector<Vector<Component>> componentsByEntities;
	private Vector<ComponentsByClass> componentsByClass;
	private Vector<Entity> entities;

	public EntityManager(){
		componentsByEntities = new Vector<Vector<Component>>();
		componentsByClass = new Vector<ComponentsByClass>();
		entities = new Vector<Entity>();
	}

	public Vector<Vector<Component>> getComponentsByEntities(){
		return componentsByEntities;
	}

	public ComponentsByClass getComponentsByClass(String name){
		for(ComponentsByClass components: componentsByClass){
			if (name.equals(components.getName()))
				return components;
		}
		return null;
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
		int eid = generateNewEid();
		Entity entity = new Entity(eid);
		entities.add(entity);
		return entity;
	}

	public void addComponent(Component component){
		//adds the component to componentsByEntities
		boolean found = false;
		for(Vector<Component> components: componentsByEntities){
			if (components.get(0).getEid() == component.getEid()){
				components.add(component);
				found = true;
				break;
			}
		}
		if (!found){
			Vector<Component> newComponentByEntity = new Vector<Component>();
			newComponentByEntity.add(component);
			componentsByEntities.add(newComponentByEntity);
		}

		//adds the component to componentsByClass
		found = false;
		for(ComponentsByClass componentByClass: componentsByClass){
			if (componentByClass.getName().equals(component.getName())){
				componentByClass.components.add(component);
				found = true;
				break;
			}
		}

		if (!found){
			ComponentsByClass newComponentClass = new ComponentsByClass(component.getName());
			newComponentClass.components.add(component);
			componentsByClass.add(newComponentClass);
		}

	}

	public Vector<Component> getComponentsByEntity(int eId) {
		for(Vector<Component> components: componentsByEntities){
			if (components.get(0).getEid() == eId)
				return components;
		}
		return null;
	}
}