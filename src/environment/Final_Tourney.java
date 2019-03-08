package environment;

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

public class Final_Tourney {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		// Generate the board/position representations
		Representation representation = new Representation();

		ArrayList<Player> opponents = new ArrayList<Player>();
		ArrayList<Player> coevos = new ArrayList<Player>();

		// Reading opponents evolved from normal AE
        System.out.println("Reading opponents");
        
        // Load evolved opponents:
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
    
    	// Load coevolved players:
    	System.out.println("Reading coevolved opponents");

    	FileInputStream playersIn = new FileInputStream("P100A0.3G2001M0.1OP100__coevo_best.ser");
    	ObjectInputStream pIn = new ObjectInputStream(playersIn);
    	int playersRead = 0;

    	try {
            ArrayPlayer e;
			while( (e= (ArrayPlayer)pIn.readObject()) != null) {
            	coevos.add(e);
            	playersRead++;
            }
        } catch (IOException ioex){
        	ioex.printStackTrace();
        }
        System.out.println("Players read: " + playersRead);



        // - - - - - - BEGINNING OF TOURNEY - - - - - - -

        int playersWins = 0, playersDraws = 0, playersLoss = 0;
        
        System.out.println("Players wins: " + playersWins + " playersDraws: " + playersDraws + " playersLoss: " + playersLoss);

        for (int j=0; j<playersRead; j++) {
    		Player tourneyPlayer = coevos.get(j);

    		for (int k=0; k< opponentsRead; k++) {

    			Game game = new Game(tourneyPlayer, opponents.get(k), representation);
                int winner = game.play();

    			if (winner == tourneyPlayer.getId()) {
                    playersWins++;
    			} else if (winner == -1) {
    				playersDraws++;
    			} else {
    				playersLoss++;
    			}

    			game = new Game(opponents.get(k), tourneyPlayer, representation);
    			winner = game.play();

    			if (winner == tourneyPlayer.getId()) {
                    playersWins++;
    			} else if (winner == -1) {
    				playersDraws++;
    			} else {
    				playersLoss++;
    			}
    		}
    	}

    	System.out.println("Players wins: " + playersWins + " playersDraws: " + playersDraws + " playersLoss: " + playersLoss);

    } // Main end
} // Class end
