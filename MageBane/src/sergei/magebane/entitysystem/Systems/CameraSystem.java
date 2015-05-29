package sergei.magebane.entitysystem.Systems;

import sergei.magebane.entitysystem.framework.EntityManager;

public class CameraSystem implements MySystem{
	private static final String TAG = "Input System";
	private EntityManager entityManager;
	
	public CameraSystem(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@Override
	public void update() {
		
	}

}
