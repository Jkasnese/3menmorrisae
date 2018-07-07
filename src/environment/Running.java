package environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Running {

	// Environment variables
    private static int POPULATION_SIZE = 100;
    private static double ALLOWED_BREED = 0.20; // Parents percentage
    private static int NUMBER_OF_GENERATIONS = 1000;
    private static double MUTATION_RATE = 0.5; // Mutation percentage
    
    // Tourney definitions
    private static int OPPONENTS_AMOUNT = 100; // Plays an opponent twice. Putting first piece/not putting first piece.
    private static int WIN_POINTS = 3;
    private static int DRAW_POINTS = 1;
    private static int LOSE_POINTS = 0;
    
    private static Random random = new Random();
    private static GraphGenerator graph_generator = new GraphGenerator("Fitness x Generations");

	public static void main(String[] args) {

        // Generate the board/position representations
		Representation representation = new Representation();

        // Players arraylist
        ArrayList <Player> players = new ArrayList<Player>();

        // Generate benchmark players
        System.out.println("Generating benchmark players");
        ArrayList<Player> opponents = new ArrayList<Player>();
        
        for (int i=0; i<Running.OPPONENTS_AMOUNT; i++){
            opponents.add(new ArrayPlayer(representation));
        }
        
        // Generate first generation        
        System.out.println("Generating first generation!");
        for (int i=0; i<Running.POPULATION_SIZE; i++){
            players.add(new ArrayPlayer(representation));
        }
        
        // BEGINING OF GENERATIONS
        for (int i=0; i<Running.NUMBER_OF_GENERATIONS; i++) {
        	
        	if (i%(Running.NUMBER_OF_GENERATIONS/10) == 0) {
        		System.out.println("Geracao: " + i);
        	}
        	
        	// EVALUATION METRICS
    		int MAX_FITNESS = 0;
    		double AVG_FITNESS = 0;

        	// Each player plays in a tourney to determine fitness
        	for (int j=0; j<Running.POPULATION_SIZE; j++) {
        		Player tourneyPlayer = players.get(j);
        		tourneyPlayer.setFitness(0); // Reset fitness from previous tourneys
        		
        		// Select opponents and play games to evaluate fitness;
        		int newFitness = 0;
        		char[] emptyboard = new char[9];
        		Arrays.fill(emptyboard, '0');
        		for (int k=0; k<Running.OPPONENTS_AMOUNT; k++) {
        			//System.out.println("Primeira jogada do jogador " + tourneyPlayer.getId() + " eh " + tourneyPlayer.getNextPlay(representation, emptyboard));
        			//System.out.println("Primeira jogada do jogador " + opponent.getId() + " eh " + opponent.getNextPlay(representation, emptyboard));
        			// Player opponent = players.get(random.nextInt(Running.POPULATION_SIZE)); uncomment this line to coevolutive
        			Game game = new Game(tourneyPlayer, opponents.get(k), representation);
                    int winner = game.play();
                    //System.out.println("Ganhador do jogo entre " + tourneyPlayer.getId() + " e " + opponent.getId() + " foi: " + winner);
        			if (winner == tourneyPlayer.getId()) {
        				newFitness += Running.WIN_POINTS;
        			} else if (winner == -1) {
        				newFitness += Running.DRAW_POINTS;
        			} else {
        				newFitness += Running.LOSE_POINTS;
        			}
        			game = new Game(opponents.get(k), tourneyPlayer, representation);
        			winner = game.play();
        			//System.out.println("Ganhador do jogo entre " + opponent.getId() + " e " + tourneyPlayer.getId() + " foi: " + winner);
        			if (winner == tourneyPlayer.getId()) {
        				newFitness += Running.WIN_POINTS;
        			} else if (winner == -1) {
        				newFitness += Running.DRAW_POINTS;
        			} else {
        				newFitness += Running.LOSE_POINTS;
        			}
        		}
        		
        		//System.out.println("Player " + tourneyPlayer.getId() + " fitness " + newFitness);
        		// Give newFitness
        		tourneyPlayer.setFitness(newFitness);
        		
        		// EVALUATION METRICS
        		if (newFitness > MAX_FITNESS) {
        			MAX_FITNESS = newFitness;
        		}
        		AVG_FITNESS += newFitness;
        	}

        	AVG_FITNESS /= Running.POPULATION_SIZE;
        	
    		// After everyone played their games, sort based on fitness and breed the best.
    		players.sort(null);
    		
    		graph_generator.add_value((double) i, MAX_FITNESS, 0);
    		graph_generator.add_value((double)i, AVG_FITNESS, 1);
    		
    		// Breed
    		int parent = 0;
    		int allowedToBreed = (int) (Running.POPULATION_SIZE * Running.ALLOWED_BREED);
    		for (int b = allowedToBreed; b < Running.POPULATION_SIZE; b++) {
    			// Parent one is determined. Parent two is random from the allowed to breed ones.
    			// TODO: Decrease changes from parent two according to arraylist index.
    			Player newPlayer = new ArrayPlayer(representation, (ArrayPlayer) players.get(parent), (ArrayPlayer) players.get(random.nextInt(allowedToBreed)), Running.MUTATION_RATE, random);
    			players.set(b, newPlayer);
    			parent++;
    			if (parent > allowedToBreed -1)
    				parent = 0;
    		}
    		int aefaef=0;
    		aefaef++;
        } // Generations loop end
        
        graph_generator.generate_graph();
        graph_generator.pack();
        graph_generator.setVisible(true);
        
	} // Main end
} // Class end
