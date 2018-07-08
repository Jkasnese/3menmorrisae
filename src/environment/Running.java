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

public class Running {

	// Environment variables
    private static int POPULATION_SIZE = 100;
    private static double ALLOWED_BREED = 0.30; // Parents percentage
    private static int NUMBER_OF_GENERATIONS = 2000;
    private static double MUTATION_RATE = 0.1; // Mutation percentage
    private static Random random = new Random();
    


    // Tourney definitions
    private static int OPPONENTS_AMOUNT = 100; // Plays an opponent twice. Putting first piece/not putting first piece.
    private static int WIN_POINTS = 3;
    private static int DRAW_POINTS = 1;
    private static int LOSE_POINTS = 0;

    
    // Persistance variables 
    private static String file_name = new String("");
    private static GraphGenerator fitnessGraph = new GraphGenerator("Fitness x Generations");
    private static GraphGenerator statsGraph = new GraphGenerator("Statistics x Generations");
    private static boolean fitGraphClosed = false;
	private static boolean statsGraphClosed = false;
    
	public static void main(String[] args) throws IOException, ClassNotFoundException {

        // OBJECTS
        // Generate the board/position representations
		Representation representation = new Representation();

        // Players arraylist
        ArrayList <Player> players = new ArrayList<Player>();

        // Generate benchmark players
        System.out.println("Generating benchmark players");
        ArrayList<Player> opponents = new ArrayList<Player>();
        
        // Uncomment for random opponents:
        for (int i=0; i<Running.OPPONENTS_AMOUNT; i++){
            opponents.add(new ArrayPlayer(representation));
        }

        // If this is a run to generate an opponent pool, uncomment the following lines between /* -- */: CTRL + F to find other lines to uncomment
        // File to output oponents.
        /*
        FileOutputStream opponentsFile = new FileOutputStream("opponents.ser");
        ObjectOutputStream out = new ObjectOutputStream(opponentsFile);
        int opponentsWritten = 0;
        */
        
        // Uncomment for evolved opponents:
        FileInputStream opponentsIn = new FileInputStream("opponents.ser");
        ObjectInputStream in = new ObjectInputStream(opponentsIn);

        try {
            ArrayPlayer e;
			while( (e= (ArrayPlayer)in.readObject()) != null) {
            	opponents.add(e);
            }
        } catch (IOException ioex){
        	ioex.printStackTrace();
        }

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

        // Generate first generation        
        System.out.println("Generating first generation!");
        for (int i=0; i<Running.POPULATION_SIZE; i++){
            players.add(new ArrayPlayer(representation));
        }



        // - - - - - BEGINING OF GENERATIONS - - - - -


        for (int i=0; i<Running.NUMBER_OF_GENERATIONS; i++) {
        	
        	
        	// EVALUATION METRICS
    		int MAX_FITNESS = 0;
    		double AVG_FITNESS = 0;
            int maxWins = 0;
            int numWinsBestPlayer = 0;
            int numDrawsBestPlayer = 0;
            int numLossesBestPlayer = 0;
            int avgWins = 0;
            int avgDraws = 0;
            int avgLosses = 0;

        	// Each player plays in a tourney to determine fitness
        	for (int j=0; j<Running.POPULATION_SIZE; j++) {
        		Player tourneyPlayer = players.get(j);

                // Zero previous status
        		tourneyPlayer.setFitness(0); // Reset fitness from previous tourneys
                tourneyPlayer.wins = 0;
                tourneyPlayer.draws = 0;
                tourneyPlayer.losses = 0;
        		
        		// Select opponents and play games to evaluate fitness;
        		int newFitness = 0;
                int newWins = 0;
                int newDraws = 0;
                int newLosses = 0;

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
                        newWins++;
        			} else if (winner == -1) {
        				newFitness += Running.DRAW_POINTS;
                        newDraws++;
        			} else {
        				newFitness += Running.LOSE_POINTS;
                        newLosses++;
        			}
        			game = new Game(opponents.get(k), tourneyPlayer, representation);
        			winner = game.play();
        			//System.out.println("Ganhador do jogo entre " + opponent.getId() + " e " + tourneyPlayer.getId() + " foi: " + winner);
        			if (winner == tourneyPlayer.getId()) {
        				newFitness += Running.WIN_POINTS;
                        newWins++;
        			} else if (winner == -1) {
        				newFitness += Running.DRAW_POINTS;
                        newDraws++;
        			} else {
        				newFitness += Running.LOSE_POINTS;
                        newLosses++;
        			}
        		}
        		
                // Give newFitness and status
        		tourneyPlayer.setFitness(newFitness);
                tourneyPlayer.wins = newWins;
                tourneyPlayer.wins = newDraws;
                tourneyPlayer.wins = newLosses;
        		
        		// EVALUATION METRICS

                    // Best player metrics
        		if (newFitness > MAX_FITNESS) {
        			MAX_FITNESS = newFitness;
                    numWinsBestPlayer = newWins;
                    numDrawsBestPlayer = newDraws;
                    numLossesBestPlayer = newLosses;
        		}

                    // Max wins
                if (newWins > maxWins){
                    maxWins = newWins;
                }

                    // Avg status
        		AVG_FITNESS += newFitness;
                avgWins += newWins;
                avgDraws += newDraws;
                avgLosses += newLosses;
        	}

        	AVG_FITNESS /= Running.POPULATION_SIZE;
            avgWins /= Running.POPULATION_SIZE;
            avgDraws /= Running.POPULATION_SIZE;
            avgLosses /= Running.POPULATION_SIZE;
        	
            
    		// After everyone played their games, sort based on fitness and breed the best.
    		players.sort(null);
    		
        	if (i%(Running.NUMBER_OF_GENERATIONS/20) == 0) {
        		System.out.println("Geracao: " + i);
                // If this is a run to generate an opponent pool, uncomment the following lines between /* -- */: CTRL + F to find other lines to uncomment
        		/*
        		// ops determine the number of opponents of a generation to be saved
                for (int ops = 0; ops < 5; ops++){
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
    		
    		// Select %ALLOWED_BREED parents to breed and fill the remainder of the population
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
        } // Generations loop end
        
        // If this is a run to generate an opponent pool, uncomment the following lines between /* -- */: CTRL + F to find other lines to uncomment
        /*
        System.out.println("Opponents written: " + opponentsWritten);
        out.close();
        opponentsFile.close();
        */
        
        fitnessGraph.generateGraph("Fitness");
        fitnessGraph.pack();
        fitnessGraph.addWindowListener(new WindowAdapter() {
        	@Override
			public void windowClosing(WindowEvent e) {
        		fitGraphClosed = true;
        	}
        });
        fitnessGraph.setVisible(true);
        
        // Putting the thread to sleep for 2 seconds to avoid ConcurrentModificationException when trying to save graph
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        fitnessGraph.savePNGFile(file_name + "P" + Running.POPULATION_SIZE + "A" + Running.ALLOWED_BREED + "G" + Running.NUMBER_OF_GENERATIONS + "M" + Running.MUTATION_RATE + "OP" + Running.OPPONENTS_AMOUNT);
        
        statsGraph.generateGraph("Statistics");
        statsGraph.pack();
        statsGraph.addWindowListener(new WindowAdapter() {
        	@Override
			public void windowClosing(WindowEvent e) {
        		statsGraphClosed = true;
        	}
        });
        statsGraph.setVisible(true);
        
         try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        statsGraph.savePNGFile(file_name + "Stats_" + "P" + Running.POPULATION_SIZE + "A" + Running.ALLOWED_BREED + "G" + Running.NUMBER_OF_GENERATIONS + "M" + Running.MUTATION_RATE + "OP" + Running.OPPONENTS_AMOUNT);
        while(true) {
    	   if(statsGraphClosed && fitGraphClosed) {
    		   System.out.println("Ate mais e obrigado pelos peixes");
    		   System.exit(0);
    	   }
       }
	} // Main end
} // Class end
