package environment;

import java.util.ArrayList;

public abstract class Player implements Comparable {
	protected static int id;
	protected ArrayList<String> plays;
	protected int playerId;
	private int fitness;
	
	public abstract String getNextPlay(Representation representation, char[] board);

	public int getId() {
		return this.playerId;
	}
	
	public int getFitness(){
		return this.fitness;
	}
	
	public void setFitness(int fit) {
		this.fitness = fit;
	}
	
}

