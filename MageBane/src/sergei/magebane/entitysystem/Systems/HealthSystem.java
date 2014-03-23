package sergei.magebane.entitysystem.Systems;

import sergei.magebane.entitysystem.framework.EntityManager;
import sergei.magebane.entitysystem.framework.components.Component;
import sergei.magebane.entitysystem.framework.components.ComponentsByClass;
import sergei.magebane.entitysystem.framework.components.HealthComponent;

public class HealthSystem implements System {
	private static final String TAG = "Health System";
	private ComponentsByClass componentsByClass;
	private EntityManager entityManager;

	public HealthSystem(EntityManager entityManager){
		this.entityManager = entityManager;
		componentsByClass = entityManager.getComponentsByClass(HealthComponent.NAME);
	}

	public void update(){
		for(Component component: componentsByClass.components){
			HealthComponent healthComponent = (HealthComponent)component;
			if (!healthComponent.isAlive())
				continue;
			if (healthComponent.getCurHp() <= 0){
				healthComponent.setAlive(false);
			}
		}
	}
}
