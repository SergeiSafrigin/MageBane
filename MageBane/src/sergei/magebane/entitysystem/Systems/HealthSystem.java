package sergei.magebane.entitysystem.Systems;

import java.util.HashMap;
import java.util.Vector;

import sergei.magebane.entitysystem.framework.EntityManager;
import sergei.magebane.entitysystem.framework.components.Component;
import sergei.magebane.entitysystem.framework.components.HealthComponent;

public class HealthSystem implements MySystem {
	private static final String TAG = "Health System";
//	private ComponentsByClass componentsByClass;
//	private Vector<Vector<Component>> componentsByEnteties;
	private Vector<HashMap<String, Component>> componentsByEnteties;
	private EntityManager entityManager;

	public HealthSystem(EntityManager entityManager){
		this.entityManager = entityManager;
//		componentsByClass = entityManager.getComponentsByClass(HealthComponent.NAME);
		componentsByEnteties = entityManager.getComponentsByEntities();
	}

	public void update(){
		for(HashMap<String, Component> components: componentsByEnteties){
			HealthComponent healthComponent = (HealthComponent) components.get(HealthComponent.NAME);
			if (!healthComponent.isAlive())
				continue;
			if (healthComponent.getCurHp() <= 0){
				healthComponent.setAlive(false);
			}
		}
		
//		for(Vector<Component> components: componentsByEnteties){
//			for(Component component: components){
//				if (component instanceof HealthComponent){
//					HealthComponent healthComponent = (HealthComponent) component;
//					if (!healthComponent.isAlive())
//						continue;
//					if (healthComponent.getCurHp() <= 0){
//						healthComponent.setAlive(false);
//					}
//				}
//			}
//		}
		
//		for(Component component: componentsByClass.components){
//			HealthComponent healthComponent = (HealthComponent)component;
//			if (!healthComponent.isAlive())
//				continue;
//			if (healthComponent.getCurHp() <= 0){
//				healthComponent.setAlive(false);
//			}
//		}
	}
}
