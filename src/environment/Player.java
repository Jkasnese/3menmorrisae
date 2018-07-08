package environment;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Comparable, Serializable {
	protected static int id;
	protected ArrayList<String> plays;
	protected int playerId;
	private int fitness;
	protected int wins;
	protected int draws;
	protected int losses;
	
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

