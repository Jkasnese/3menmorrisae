package environment;

import java.util.Random;

public class Mutation {

	private Random random = new Random();

	public ArrayList<String> generatePlays(Representation representation) {
		ArrayList<String> plays = new ArrayList<String>;
		String futurePlay;

		/*** Gets the originary board position
			 Generate a play to it and append to the arraylist that contain the players's playbook.
		 ***/		

		int i = 0;

		for (i=0; i<335; i++){
			futurePlay = representation.positions_decode.get(i);
			futurePlay = putPiece(futurePlay);
			plays.add(futurePlay);
		}
		for (i=336; i<564; i++){
			futurePlay = representation.positions_decode.get(i);
			futurePlay = movePiece(futurePlay);
			plays.add(futurePlay);
		}

	}

	/***
		Puts a piece in an empty square of the board
	***/
	public String putPiece(String position){
		char[] board = position.toCharArray();
		int square;

		do {
			square = random.nextInt(9);
		}  while (board[square] != '0');

		board[square] = '1';

		return (new String(board));
	}

	public String movePiece(String position, int[] pieces){
		char[] board = position.toCharArray();
		
		// Gets which piece will move
		int piece = random.nextInt(3) + 1;
		int pieces_found = 0;

		for (int i=0; i<9; i++){
			if (board[i] == '1'){
				pieces_found++;
				// If found correct piece to change:
				if (pieces_found == piece){
					// Get move. -3 up, -1 left, +1 right, +3 down
					int move;
					do{
						move = random.nextInt(3) + 1;
					} while (move > -1 && move < 9);
				}
			}
		}


		board[square] = '1';

		return (new String(board));
	}

}

