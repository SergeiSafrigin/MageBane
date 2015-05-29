package sergei.magebane.main;

import java.util.Vector;

import sergei.magebane.entitysystem.Systems.CameraSystem;
import sergei.magebane.entitysystem.Systems.HealthSystem;
import sergei.magebane.entitysystem.Systems.InputSystem;
import sergei.magebane.entitysystem.Systems.MySystem;
import sergei.magebane.entitysystem.Systems.RenderSystem;
import sergei.magebane.entitysystem.Systems.SystemManager;
import sergei.magebane.entitysystem.framework.Entity;
import sergei.magebane.entitysystem.framework.EntityManager;
import sergei.magebane.entitysystem.framework.components.CameraComponent;
import sergei.magebane.entitysystem.framework.components.HealthComponent;
import sergei.magebane.entitysystem.framework.components.InputComponent;
import sergei.magebane.entitysystem.framework.components.MovementComponent;
import sergei.magebane.entitysystem.framework.components.RenderComponent;
import sergei.magebane.view.GameView;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class World {
	private GameView gameView;
	private Resources resources;
	private SystemManager systemManager;
	private EntityManager entityManager;
	private Entity player;
	private Entity enemy;
	private Vector<MySystem> systems;
	private GameLoop gameloop;
	private WindowManager windowManager;

	public World(WindowManager windowManager, GameView gameView, Resources resources){
		this.resources = resources;
		this.gameView = gameView;
		this.windowManager = windowManager;
		entityManager = new EntityManager();
		systems = new Vector<MySystem>();

		initEntities();
		initComponents();
		initSystems();
	}

	private void initEntities() {
		player = entityManager.createEntity();
		enemy = entityManager.createEntity();
	}

	private void initComponents() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displaymetrics);
		int screenHeight = displaymetrics.heightPixels;
		int screenWidth = displaymetrics.widthPixels;
		
		int cameraWidth = 640;
		int cameraHeight = 640;
		
		float playerXPosition = 400;
		float playerYPosition = 100;
		float playerMaxSpeed = 2;
		int playerMaxHealth = 100;
		int playerCurrentHealth = 100;
		
		//Player
		RenderComponent playerRenderComponent = new RenderComponent(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher), player.getEid(), 400, 100);
		entityManager.addComponent(playerRenderComponent);

		HealthComponent playerHealthComponent = new HealthComponent(playerMaxHealth, playerCurrentHealth, true, player.getEid());
		entityManager.addComponent(playerHealthComponent);

		InputComponent playerInputComponent = new InputComponent(player.getEid());
		entityManager.addComponent(playerInputComponent);
		
		MovementComponent movementComponent = new MovementComponent(player.getEid(), playerXPosition, playerYPosition, playerMaxSpeed);
		entityManager.addComponent(movementComponent);
		
		CameraComponent cameraComponent = new CameraComponent(player.getEid(), playerXPosition, playerYPosition, cameraWidth, cameraHeight, screenWidth, screenHeight);
		entityManager.addComponent(movementComponent);
		
		//Enemy
		RenderComponent enemyRenderComponent = new RenderComponent(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher), enemy.getEid(), 150, 200);
		entityManager.addComponent(enemyRenderComponent);

		HealthComponent enemyHealthComponent = new HealthComponent(80, 100, true, enemy.getEid());
		entityManager.addComponent(enemyHealthComponent);
	}

	private void initSystems() {
		systemManager = new SystemManager();

		InputSystem inputSystem = new InputSystem(entityManager);
		gameView.setOnTouchListener(inputSystem);
		systems.add(inputSystem);
		systemManager.setInputSystem(inputSystem);
		inputSystem.start();

		RenderSystem renderSystem = new RenderSystem(BitmapFactory.decodeResource(resources, R.drawable.layer2), gameView, entityManager, systemManager);
		systems.add(renderSystem);

		HealthSystem healthSystem = new HealthSystem(entityManager);
		systems.add(healthSystem);
		
		CameraSystem cameraSystem = new CameraSystem(entityManager);
		systems.add(cameraSystem);
	}

	public void onPause(){
		gameloop.onPause();
		systemManager.onPause();
	}

	public void onResume(){
		gameloop = new GameLoop(systems, 100);
		gameloop.start();

		if (!systemManager.getInputSystem().isAlive()){
			for(int i = 0; i < systems.size(); i++){
				if (systems.get(i) instanceof InputSystem){
					InputSystem inputSystem = new InputSystem(entityManager);
					systems.set(i, inputSystem);
					systemManager.setInputSystem(inputSystem);
					gameView.setOnTouchListener(inputSystem);
					inputSystem.start();
					break;
				}
			}
		}
	}
}
