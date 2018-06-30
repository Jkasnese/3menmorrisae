package environment;

import java.util.ArrayList;
import java.util.Random;

public class Mutation {

	private static Random random = new Random();

	public static ArrayList<String> generatePlays(Representation representation) {
		ArrayList<String> plays = new ArrayList<String>();
		String futurePlay;

		/*** Gets the originary board position
			 Generate a play to it and append to the arraylist that contain the players's playbook.
		 ***/		
		int i = 0;

		for (i=0; i<335; i++){
			futurePlay = representation.getPosition(i);
			futurePlay = putPiece(futurePlay);
			plays.add(futurePlay);
		}
		
		for (i=335; i<564; i++){
			futurePlay = representation.getPosition(i);
			futurePlay = movePiece(futurePlay);
			plays.add(futurePlay);
		}
		return plays;
	}
	
	public static String generatePlay(Representation representation, int index) {
		if (index < 335) {
			return putPiece(representation.getPosition(index));
		} else {
			return movePiece(representation.getPosition(index));
		}
		
	}

	/***
		Puts a piece in an empty square of the board
	***/
	public static String putPiece(String position){
		char[] board = position.toCharArray();
		int square;

		do {
			square = random.nextInt(9);
		}  while (board[square] != '0');

		board[square] = '1';

		return (new String(board));
	}
	
	private static int raffleMove (int position, char[] board) {
		int[] possibleMoves = new int[4];
		int numMoves = 0;
		int move;
		switch (position) { 
			case 0:
				if (board[1] == '0') {
					possibleMoves[numMoves] = 1;
					numMoves++;
				} else if (board[3] == '0') {
					possibleMoves[numMoves] = 3;
					numMoves++;
				}
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
			case 1:
				if (board[0] == '0') {
					possibleMoves[numMoves] = 0;
					numMoves++;
				} else if (board[2] == '0') {
					possibleMoves[numMoves] = 2;
					numMoves++;
				} else if (board[4] == '0'){
					possibleMoves[numMoves] = 4;
					numMoves++;
				}
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
			case 2:
				if (board[1] == '0') {
					possibleMoves[numMoves] = 1;
					numMoves++;
				} else if (board[5] == '0') {
					possibleMoves[numMoves] = 5;
					numMoves++;
				}
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
			case 3:
				if (board[0] == '0') {
					possibleMoves[numMoves] = 0;
					numMoves++;
				} else if (board[4] == '0') {
					possibleMoves[numMoves] = 4;
					numMoves++;
				} else if (board[6] == '0') {
					possibleMoves[numMoves] = 6;
					numMoves++;
				}
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
			case 4:
				if (board[1] == '0') {
					possibleMoves[numMoves] = 1;
					numMoves++;
				} else if (board[3] == '0') {
					possibleMoves[numMoves] = 3;
					numMoves++;
				} else if (board[5] == '0') {
					possibleMoves[numMoves] = 5;
					numMoves++;
				} else if (board[7] == '0') {
					possibleMoves[numMoves] = 7;
					numMoves++;
				}
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
			case 5:
				if (board[2] == '0') {
					possibleMoves[numMoves] = 2;
					numMoves++;
				} else if (board[4] == '0') {
					possibleMoves[numMoves] = 4;
					numMoves++;
				} else if (board[8] == '0') {
					possibleMoves[numMoves] = 8;
					numMoves++;
				}
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
			case 6:
				if (board[3] == '0') {
					possibleMoves[numMoves] = 3;
					numMoves++;
				} else if (board[7] == '0') {
					possibleMoves[numMoves] = 7;
					numMoves++;
				}
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
			case 7:
				if (board[4] == '0') {
					possibleMoves[numMoves] = 4;
					numMoves++;
				} else if (board[6] == '0') {
					possibleMoves[numMoves] = 6;
					numMoves++;
				} else if (board[8] == '0') {
					possibleMoves[numMoves] = 8;
					numMoves++;
				}
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
			case 8:
				if (board[5] == '0') {
					possibleMoves[numMoves] = 5;
					numMoves++;
				} else if (board[7] == '0') {
					possibleMoves[numMoves] = 7;
					numMoves++;
				if (numMoves == 0) break;
				return possibleMoves[random.nextInt(numMoves)];
				}
		}
		return -1;
	}
	
	
	
	public static String movePiece(String position){
		char[] board = position.toCharArray();
		ArrayList<Integer> pieces = new ArrayList<Integer>();
		
		// Search for the player`s pieces, add them to array
		for (int i=0; i<9; i++){ 
			if (board[i] == '1'){
				pieces.add(new Integer(i));
			}
		}

		int move;
		int pieceLocation;
		int freePieces = 3;
		// Random select which piece to move. If piece is stuck, remove it from available pieces and raffle again
		do {
			// Get the location of the piece to move
	        pieceLocation = pieces.get(random.nextInt(freePieces)).intValue();
	        pieces.remove(freePieces);
	        freePieces--;
	        
	        // Try to move piece
		    move = raffleMove(pieceLocation, board);
    	} while (move == -1); 
		
		// Plays the move
		board[pieceLocation] = '0';
		board[move] = '1';

		// Returns the new board with the move
		return (new String(board));
	}
	
}

