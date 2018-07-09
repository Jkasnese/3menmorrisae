package environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/***
	Player that has all possible board configurations and the play to it.
***/
public class ArrayPlayer extends Player implements Serializable {

	private ArrayList<String> playbook;
	
	public ArrayPlayer () {
		this.playerId = Player.id;
		Player.id++;
	}

	public ArrayPlayer (Representation representation){
		this.playerId = Player.id;
		Player.id++;
		this.playbook = Mutation.generatePlays(representation);
	}
	
	/***
	 * Breeding function. Because parentOne is determined by fitness, he is guaranteed at least half the genes.
	 * @param representation
	 * @param parentOne
	 * @param parentTwo
	 * @param mutationRate
	 * @param random
	 */
	public ArrayPlayer (Representation representation, ArrayPlayer parentOne, ArrayPlayer parentTwo, double mutationRate, Random random) {
		this.playerId = Player.id;
		Player.id++;
	
		this.playbook = new ArrayList<String>(parentOne.getPlaybook());
		
		// 564 positions on the playbook. Raffle how many from parentTwo:
		int genesFromTwo = random.nextInt(282) + 1;
		
		// Raffle which genes will come from parentTwo:
		for (int i=0; i<genesFromTwo; i++) {
			int index = random.nextInt(564);
			this.playbook.set(index, parentTwo.getPlaybook().get(index));
		}
		
		// Mutate based on mutation rate
		for (int i=0; i<(int)(564*mutationRate); i++ ) {
			int index = random.nextInt(564);
			this.playbook.set(index, Mutation.generatePlay(representation, index));
		}

	}
	

	public ArrayList<String> getPlaybook() {
		return this.playbook;
	}

	/***
		This function gets the next play.
		In a given board configuration, first find what is the "correct" representation in the hashmap.
		Pass the hash to the hashmap and obtain the array index.
		Use the given index to find the play in the player chromossome
	***/
	@Override
	public String getNextPlay(Representation representation, char[] board) {
		// Get current position
		String currentBoard = new String(board);
		
		// Check current position definitive representation index
		int playIndex = representation.getIndex(currentBoard);

		String nextPlay = this.playbook.get(playIndex);

		// Get next play from player`s playbook from previous index
		return this.playbook.get(playIndex);
	}
	// Uncomment to normal evolution
	@Override
	public int compareTo(Object opponent) {
		opponent = (Player) opponent;
		if (this.getFitness() > ((Player) opponent).getFitness()) {
			return -1;
		}
		else if (this.getFitness() < ((Player) opponent).getFitness() ) {
			return 1;
		} else {
			return 0;
		}
	}
	
	// Uncomment to coevolution
	/*
	@Override
	public int compareTo(Object opponent) {
		opponent = (Player) opponent;
		if (this.coevoFitness > ((Player) opponent).coevoFitness) {
			return -1;
		}
		else if (this.coevoFitness < ((Player) opponent).coevoFitness ) {
			return 1;
		} else {
			return 0;
		}
	}
	*/
	
}
