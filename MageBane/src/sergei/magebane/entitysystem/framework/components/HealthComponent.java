package sergei.magebane.entitysystem.framework.components;

public class HealthComponent extends Component {
	public static final String NAME = "HealthComponent";
	private int eId;
	private int curHp;
	private int maxHp;
	private boolean alive;
	
	public HealthComponent(int curHp, int maxHp, boolean alive, int eId){
		super(NAME, eId);
		this.curHp = curHp;
		this.maxHp = maxHp;
		this.alive = alive;
	}
	
	public int getMaxHp(){
		return maxHp;
	}
	
	public int getCurHp(){
		return curHp;
	}
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}

}
