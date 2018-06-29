package environment;

import java.util.ArrayList;
import java.util.Random;

public class Running {

	// Environment variables
    private static int POPULATION_SIZE = 100;
    private static double ALLOWED_BREED = 0.4; // Parents percentage
    private static int NUMBER_OF_GENERATIONS = 1000000;
    private static double MUTATION_RATE = 0.1; // Mutation percentage
    
    // Tourney definitions
    private static int OPPONENTS_AMOUNT = 3; // He will play an opponent twice. Putting first piece/not putting first piece.
    private static int WIN_POINTS = 3;
    private static int DRAW_POINTS = 1;
    private static int LOSE_POINTS = 0;
    
    private static Random random = new Random();

	public static void main(String[] args) {

        // Generate the board/position representations
		Representation representation = new Representation();
		representation.create_all_possibilities();

        // Players arraylist
        ArrayList <Player> players = new ArrayList<Player>();
    
        // Generate first generation        
        System.out.println("Generating first generation!");
        for (int i=0; i<Running.POPULATION_SIZE; i++){
            players.add(new ArrayPlayer(representation));
        }
        
        // Generate benchmark player
        System.out.println("Generating benchmark player");
        Player opponent = new ArrayPlayer(representation);
        
        for (int i=0; i<1 /*Running.NUMBER_OF_GENERATIONS*/; i++) {
        	
        	// EVALUATION METRICS
    		int MAX_FITNESS = 0;
    		double AVG_FITNESS = 0;

        	// Each player plays in a tourney. The ALLOWED_BREED% best breed.
        	for (int j=0; j<1/*Running.POPULATION_SIZE*/; j++) {
        		Player tourneyPlayer = players.get(j);
        		tourneyPlayer.setFitness(0); // Reset fitness from previous tourneys
        		
        		// Select opponents and play games to evaluate fitness;
        		int newFitness = 0;
        		for (int k=0; k<1 /*Running.OPPONENTS_AMOUNT*/; k++) {
        			// Player opponent = players.get(random.nextInt(Running.POPULATION_SIZE)); uncomment this line to coevolutive
        			Game game = new Game(tourneyPlayer, opponent, representation);
        			System.out.println("Playing game " + k);
                    int winner = game.play();
        			if (winner == tourneyPlayer.getId()) {
        				newFitness += Running.WIN_POINTS;
        			} else if (winner == -1) {
        				newFitness += Running.DRAW_POINTS;
        			} else {
        				newFitness += Running.LOSE_POINTS;
        			}
        			game = new Game(opponent, tourneyPlayer, representation);
        			winner = game.play();
        			if (winner == tourneyPlayer.getId()) {
        				newFitness += Running.WIN_POINTS;
        			} else if (winner == -1) {
        				newFitness += Running.DRAW_POINTS;
        			} else {
        				newFitness += Running.LOSE_POINTS;
        			}
        		}
        		
        		// Give newFitness
        		tourneyPlayer.setFitness(newFitness);
        		
        		// EVALUATION METRICS
        		if (newFitness > MAX_FITNESS) {
        			MAX_FITNESS = newFitness;
        		}
        		AVG_FITNESS += newFitness;
        	}

        	AVG_FITNESS /= Running.POPULATION_SIZE;
        	
        	System.out.println(MAX_FITNESS);
        	System.out.println(AVG_FITNESS);
        	
    		// After everyone played their games, sort based on fitness and breed the best.
    		players.sort(null);
    		
    		// Breed
    		int parent = 0;
    		for (int b = (int) (Running.POPULATION_SIZE * Running.ALLOWED_BREED); b < Running.POPULATION_SIZE; b++) {
    			// Parent one is determined. Parent two is random.
    			// TODO: Decrease changes from parent two according to arraylist index.
    			Player newPlayer = new ArrayPlayer(representation, (ArrayPlayer) players.get(parent), (ArrayPlayer) players.get(random.nextInt(Running.POPULATION_SIZE)), Running.MUTATION_RATE, random);
    			players.set(b, newPlayer);
    			parent++;
    		}
        } // Generations loop end
	} // Main end
} // Class end
