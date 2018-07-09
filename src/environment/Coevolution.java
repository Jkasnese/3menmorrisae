package environment;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Coevolution {
	
	// Environment variables
    private static final int POPULATION_SIZE = 100;
    private static final double ALLOWED_BREED = 0.3; // Parents percentage
    private static final int NUMBER_OF_GENERATIONS = 200;
    private static final double PROGRESS_STEP = 0; // Show progress in NUMBER_OF_GENERATIONS / PROGRESS STEP. Also save opponents in same step
    private static final int SAVED_OPPONENTS_NUMBER = 5;
    private static final boolean USE_RANDOM = true; // Use random opponents?
    private static final double MUTATION_RATE = 0.1; // Mutation percentage
    private static final int NUMBER_OF_SAMPLES = 5;
    private static Random random = new Random();
    
    // Tourney definitions
    private static final int OPPONENTS_AMOUNT = 100; // Plays an opponent twice. Putting first piece/not putting first piece. Used only to benchmark.
    private static final int COEVOLUTION_OPPONENTS_AMOUNT = 5; // Number of players from the population that a player will play against to determine fitness.
    private static final int WIN_POINTS = 3;
    private static final int DRAW_POINTS = 1;
    private static final int LOSE_POINTS = 0;

    // Persistance variables 
    private static String file_name = new String("");
    private static GraphGenerator coevoFitnessGraph;
    private static GraphGenerator coevoStatsGraph;
    private static GraphGenerator fitnessGraph;
    private static GraphGenerator statsGraph;
    
	public static void main(String[] args) throws IOException, ClassNotFoundException {

        // OBJECTS
        // Generate the board/position representations
		Representation representation = new Representation();
		
		// Save configurations to name the files
        String configs = new String("P" + Coevolution.POPULATION_SIZE + "A" + Coevolution.ALLOWED_BREED + "G" + Coevolution.NUMBER_OF_GENERATIONS + "M" + Coevolution.MUTATION_RATE + "OP" + Coevolution.OPPONENTS_AMOUNT);
		
		// Save best players from each run to compare them. See how much they differ:
        FileOutputStream bestPlayersFile = new FileOutputStream(configs + "_best.ser");
        ObjectOutputStream bestOut = new ObjectOutputStream(bestPlayersFile);
        
        // If this is a run to generate an opponent pool, uncomment the following lines between /* -- */: CTRL + F to find other lines to uncomment
        // File to output opponents.
        /*
        FileOutputStream opponentsFile = new FileOutputStream("opponents.ser");
        ObjectOutputStream out = new ObjectOutputStream(opponentsFile);
        int opponentsWritten = 0;
        */
        
        // GENERATING BENCHMARK PLAYERS. CAN BE EITHER RANDOM OR EVOLVED OPPONENTS.
        System.out.println("Generating benchmark players");
        ArrayList<Player> opponents = new ArrayList<Player>();
        
        if (Coevolution.USE_RANDOM) {
	        // Generating random opponents:
	        for (int i=0; i<Coevolution.OPPONENTS_AMOUNT; i++){
	            opponents.add(new ArrayPlayer(representation));
	        }
        } else {
	        // Uncomment for evolved opponents:
	        FileInputStream opponentsIn = new FileInputStream("opponents.ser");
	        ObjectInputStream in = new ObjectInputStream(opponentsIn);
	        int opponentsRead = 0;

	        try {
	            ArrayPlayer e;
				while( (e= (ArrayPlayer)in.readObject()) != null) {
	            	opponents.add(e);
	            	opponentsRead++;
	            }
	        } catch (IOException ioex){
	        	ioex.printStackTrace();
	        }
	        System.out.println("Opponents read: " + opponentsRead);
        }
        
	    // EVOLUTION VARIABLES
        // Players arraylist
        ArrayList <Player> newPlayers = new ArrayList<Player>();        

        // Generate first generation        
        System.out.println("Generating first generation!");
        for (int i=0; i<Coevolution.POPULATION_SIZE; i++){
            newPlayers.add(new ArrayPlayer(representation));
        }
        
	   for(int h=0; h < Coevolution.NUMBER_OF_SAMPLES; h++) {

		   	// GRAPHS VARIABLES
		   	file_name = "Sample" + (h+1);
			
		   	// Coevo players graph
	      	coevoFitnessGraph = new GraphGenerator("Fitness x Generations");
	      	coevoStatsGraph = new GraphGenerator("Statistics x Generations");
      	 
		   // Create XYSeries for graphs:
	        coevoFitnessGraph.createSeries("Max Fitness");
	        coevoFitnessGraph.createSeries("Average Fitness");
	        coevoStatsGraph.createSeries("Average Wins");
	        coevoStatsGraph.createSeries("Average Draws");
	        coevoStatsGraph.createSeries("Average Losses");
	        coevoStatsGraph.createSeries("Best Player's Wins");
	        coevoStatsGraph.createSeries("Best Player's Draws");
	        coevoStatsGraph.createSeries("Best Player's Losses");
	        coevoStatsGraph.createSeries("Max Number of Wins");		   
	        
	        // Benchmark players graph
	        fitnessGraph = new GraphGenerator("Fitness x Generations");
	      	statsGraph = new GraphGenerator("Statistics x Generations");
      	 
		   // Create XYSeries for graphs:
	        fitnessGraph.createSeries("Max Fitness");
	        fitnessGraph.createSeries("Average Fitness");
	        statsGraph.createSeries("Average Wins");
	        statsGraph.createSeries("Average Draws");
	        statsGraph.createSeries("Average Losses");
	        statsGraph.createSeries("Best Player's Wins");
	        statsGraph.createSeries("Best Player's Draws");
	        statsGraph.createSeries("Best Player's Losses");
	        statsGraph.createSeries("Max Number of Wins");	
	        
	        
	        // - - - - - - -
	        
		    // EVOLUTION VARIABLES
	        // Players arraylist
	        ArrayList <Player> players = new ArrayList<Player>(newPlayers);        
		   
	        // - - - - - BEGINING OF GENERATIONS - - - - -
		   
	        for (int i=0; i<Coevolution.NUMBER_OF_GENERATIONS; i++) {
	        	
	        	// EVALUATION METRICS
	    		int MAX_FITNESS = 0;
	    		double AVG_FITNESS = 0;
	            int maxWins = 0;
	            int numWinsBestPlayer = 0;
	            int numDrawsBestPlayer = 0;
	            int numLossesBestPlayer = 0;
	            double avgWins = 0;
	            double avgDraws = 0;
	            double avgLosses = 0;
	            
	            // BENCHMARK EVALUATION METRICS
	    		int COEVO_MAX_FITNESS = 0;
	    		double COEVO_AVG_FITNESS = 0;
	            int coevoMaxWins = 0;
	            int coevoNumWinsBestPlayer = 0;
	            int coevoNumDrawsBestPlayer = 0;
	            int coevoNumLossesBestPlayer = 0;
	            double coevoAvgWins = 0;
	            double coevoAvgDraws = 0;
	            double coevoAvgLosses = 0;
	
	        	// Each player plays in a tourney to determine fitness
	        	for (int j=0; j<Coevolution.POPULATION_SIZE; j++) {
	        		Player tourneyPlayer = players.get(j);
	
	                // Zero previous status
	        		tourneyPlayer.setFitness(0); // Reset fitness from previous tourneys
	                tourneyPlayer.wins = 0;
	                tourneyPlayer.draws = 0;
	                tourneyPlayer.losses = 0;
	                tourneyPlayer.coevoFitness = 0;
	                tourneyPlayer.coevoWins = 0;
	                tourneyPlayer.coevoDraws = 0;
	                tourneyPlayer.coevoLosses = 0;
	                
	        		// Select opponents and play games to evaluate fitness;
	                // Benchmark status
	        		int newFitness = 0;
	                int newWins = 0;
	                int newDraws = 0;
	                int newLosses = 0;
	                
	                // Coevolution status
	        		int coevoNewFitness = 0;
	                int coevoNewWins = 0;
	                int coevoNewDraws = 0;
	                int coevoNewLosses = 0;
	
	                // - - - - - TOURNEYS - - - - - -

        			// Benchmark games
	        		for (int k=0; k<Coevolution.OPPONENTS_AMOUNT; k++) {
	        			Game game = new Game(tourneyPlayer, opponents.get(k), representation);
	                    int winner = game.play();
	        			if (winner == tourneyPlayer.getId()) {
	        				newFitness += Coevolution.WIN_POINTS;
	                        newWins++;
	        			} else if (winner == -1) {
	        				newFitness += Coevolution.DRAW_POINTS;
	                        newDraws++;
	        			} else {
	        				newFitness += Coevolution.LOSE_POINTS;
	                        newLosses++;
	        			}
	        			game = new Game(opponents.get(k), tourneyPlayer, representation);
	        			winner = game.play();
	        			if (winner == tourneyPlayer.getId()) {
	        				newFitness += Coevolution.WIN_POINTS;
	                        newWins++;
	        			} else if (winner == -1) {
	        				newFitness += Coevolution.DRAW_POINTS;
	                        newDraws++;
	        			} else {
	        				newFitness += Coevolution.LOSE_POINTS;
	                        newLosses++;
	        			}	        			
	        		}

        			// Coevolutive games
	        		for (int k=0; k<Coevolution.COEVOLUTION_OPPONENTS_AMOUNT; k++) {
	        			Player opponent = players.get(random.nextInt(Coevolution.POPULATION_SIZE));
	        			Game game = new Game(tourneyPlayer, opponent, representation);
	                    int winner = game.play();
	        			if (winner == tourneyPlayer.getId()) {
	        				coevoNewFitness += Coevolution.WIN_POINTS;
	                        coevoNewWins++;
	        			} else if (winner == -1) {
	        				coevoNewFitness += Coevolution.DRAW_POINTS;
	                        coevoNewDraws++;
	        			} else {
	        				coevoNewFitness += Coevolution.LOSE_POINTS;
	                        coevoNewLosses++;
	        			}
	        			game = new Game(opponent, tourneyPlayer, representation);
	        			winner = game.play();
	        			if (winner == tourneyPlayer.getId()) {
	        				coevoNewFitness += Coevolution.WIN_POINTS;
	                        coevoNewWins++;
	        			} else if (winner == -1) {
	        				coevoNewFitness += Coevolution.DRAW_POINTS;
	                        coevoNewDraws++;
	        			} else {
	        				coevoNewFitness += Coevolution.LOSE_POINTS;
	                        coevoNewLosses++;
	        			}	        			
	        		}
	        		
	        		
	        		
	                // Give benchmark's newFitness
	        		tourneyPlayer.setFitness(newFitness);
	                
	                // Give coevolution's newFitness 
	                tourneyPlayer.coevoFitness = coevoNewFitness;
	        		

	                // EVALUATION METRICS
	
	                // Benchmark evaluation
	                // Best player metrics
	        		if (coevoNewFitness > COEVO_MAX_FITNESS) {
	        			COEVO_MAX_FITNESS = coevoNewFitness;
	                    numWinsBestPlayer = newWins;
	                    numDrawsBestPlayer = newDraws;
	                    numLossesBestPlayer = newLosses;
	                    coevoNumWinsBestPlayer = coevoNewWins;
	                    coevoNumDrawsBestPlayer = coevoNewDraws;
	                    coevoNumLossesBestPlayer = coevoNewLosses;
	        		}
	
	                    // Max benchmark fitness
	                if (newFitness > MAX_FITNESS){
	                    MAX_FITNESS = newFitness;
	                }
	                
	             // Max wins coevo
	                if (newWins > maxWins){
	                    maxWins = newWins;
	                }
	                
	                // Max wins coevo
	                if (coevoNewWins > coevoMaxWins){
	                    coevoMaxWins = coevoNewWins;
	                }
	
	                    // Bench Avg status
	        		AVG_FITNESS += newFitness;
	                avgWins += newWins;
	                avgDraws += newDraws;
	                avgLosses += newLosses;
	                
	                // Coevo avg status
	                COEVO_AVG_FITNESS += coevoNewFitness;
	                coevoAvgWins += coevoNewWins;
	                coevoAvgDraws += coevoNewDraws;
	                coevoAvgLosses += coevoNewLosses;
	                
	                
	        	}
	
	        	AVG_FITNESS /= Coevolution.POPULATION_SIZE;
	            avgWins /= Coevolution.POPULATION_SIZE;
	            avgDraws /= Coevolution.POPULATION_SIZE;
	            avgLosses /= Coevolution.POPULATION_SIZE;
	            
	            COEVO_AVG_FITNESS /= Coevolution.POPULATION_SIZE;
                coevoAvgWins /= Coevolution.POPULATION_SIZE;
                coevoAvgDraws /= Coevolution.POPULATION_SIZE;
                coevoAvgLosses /= Coevolution.POPULATION_SIZE;
	        	
	            
	    		// After everyone played their games, sort based on fitness and breed the best.
	    		players.sort(null);
	    		
	        	if (i%(Coevolution.NUMBER_OF_GENERATIONS/Coevolution.PROGRESS_STEP) == 0) {
	        		System.out.println("Geracao: " + i);
	                // If this is a run to generate an opponent pool, uncomment the following lines between /* -- */: CTRL + F to find other lines to uncomment
	        		/*
	        		// ops determine the number of opponents of a generation to be saved
	                for (int ops = 0; ops < Coevolution.SAVED_OPPONENTS_NUMBER; ops++){
	                    try{
	                        out.writeObject(players.get(ops));
	                        opponentsWritten++;
	                    } catch (IOException ioex){
	                        ioex.printStackTrace();
	                    }
	                }*/
	        	}
	    		
	    		
	    		double[] fit_array = {MAX_FITNESS, AVG_FITNESS}; 
	    		fitnessGraph.addValue((double) i, fit_array);
	    	
	    		double[] stats_array = {avgWins, avgDraws, avgLosses, numWinsBestPlayer, numDrawsBestPlayer, numLossesBestPlayer, maxWins};
	    		statsGraph.addValue((double) i, stats_array);
	    		
	    		double[] coevoFit_array = {COEVO_MAX_FITNESS, COEVO_AVG_FITNESS}; 
	    		coevoFitnessGraph.addValue((double) i, coevoFit_array);
	    	
	    		double[] coevoStats_array = {coevoAvgWins, coevoAvgDraws, coevoAvgLosses, coevoNumWinsBestPlayer, coevoNumDrawsBestPlayer, coevoNumLossesBestPlayer, coevoMaxWins};
	    		coevoStatsGraph.addValue((double) i, stats_array);
	    		
	    		// Select %ALLOWED_BREED parents to breed and fill the remainder of the population
	    		int parent = 0;
	    		int allowedToBreed = (int) (Coevolution.POPULATION_SIZE * Coevolution.ALLOWED_BREED);
	    		for (int b = allowedToBreed; b < Coevolution.POPULATION_SIZE; b++) {
	    			// Parent one is determined. Parent two is random from the allowed to breed ones.
	    			// TODO: Decrease changes from parent two according to arraylist index.
	    			Player newPlayer = new ArrayPlayer(representation, (ArrayPlayer) players.get(parent), (ArrayPlayer) players.get(random.nextInt(allowedToBreed)), Coevolution.MUTATION_RATE, random);
	    			players.set(b, newPlayer);
	    			parent++;
	    			if (parent > allowedToBreed -1)
	    				parent = 0;
	    		}
	    		
	    		// If this is a run to generate an opponent pool, uncomment the following lines between /* -- */: CTRL + F to find other lines to uncomment
	            /*
	            System.out.println("Opponents written: " + opponentsWritten);
	            out.close();
	            opponentsFile.close();
	            */
	    		
	        } // Generations loop end
	        
	        // Save the best chromossome from each run to compare them. See how much they differ.
            try{
                bestOut.writeObject( ((ArrayPlayer) players.get(0)).getPlaybook() );
            } catch (IOException ioex){
                ioex.printStackTrace();
            }
            
	        // Generate graphs
	        coevoFitnessGraph.generateGraph("Fitness");
	        coevoFitnessGraph.pack();
	        coevoFitnessGraph.setVisible(true);
	        
	        // Putting the thread to sleep for 2 seconds to avoid ConcurrentModificationException when trying to save graph
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        
	        coevoFitnessGraph.savePNGFile(configs + file_name + "fit");
	        
	        coevoStatsGraph.generateGraph("Statistics");
	        coevoStatsGraph.pack();
	        coevoStatsGraph.setVisible(true);
	        
	         try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        coevoStatsGraph.savePNGFile(configs + file_name + "stats");
	        
	        // Generate graphs
	        fitnessGraph.generateGraph("Fitness");
	        fitnessGraph.pack();
	        fitnessGraph.setVisible(true);
	        
	        // Putting the thread to sleep for 2 seconds to avoid ConcurrentModificationException when trying to save graph
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        
	        fitnessGraph.savePNGFile(configs + file_name + "fit");
	        
	        statsGraph.generateGraph("Statistics");
	        statsGraph.pack();
	        statsGraph.setVisible(true);
	        
	         try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        statsGraph.savePNGFile(configs + file_name + "stats");
	   
       } // Samples loop end
	} // Main end
} // Class end
