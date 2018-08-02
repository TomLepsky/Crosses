package tomlepsky.Dots;

public abstract class Player {
	
	/*
	 * sign - id игрока, уникален;
	 * ready - пределяет чей ход 	
	 */
	protected static int count = 1;
	
	protected int    	 sign;
	protected boolean 	 ready;
	protected String 	 name;
	protected Field  	 field = null;
	
	public Player( String name, boolean ready ) {
		this.ready = ready;
		this.name  = name;
		sign       = count++;
		field 	   = Field.getInstance();
	}
	
	public boolean isWin() {
		return field.checkLines( sign );
	}
	
	public void changeState() {
		ready = !ready;
	}
	
	public boolean getReady() {
		return ready;
	}
	
	public int getSign() {
		return sign;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	abstract public boolean makeMove( int index );
	
}
