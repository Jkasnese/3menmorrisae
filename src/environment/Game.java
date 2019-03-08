package environment;

import java.util.Arrays;

public class Game {
	private Representation representation;
	
	private Player player_one;
	private Player player_two;
	
	private char[] board;

	// Number of plays for each player
	private static int NUMBER_OF_PLAYS = 50;

	public Game(Player player_x, Player player_y, Representation representation){
		this.representation = representation;
		this.player_one = player_x;
		this.player_two = player_y;
		this.board = new char[9];
		Arrays.fill(board, '0');
	}
	
	/***
		Checks if game is finished :-)
	***/	
	public boolean is_game_finished(){
		int i = 0;
		for(i=0; i<3; i++) {
			// Checa vertical
			if(this.board[i] == this.board[i+3] && this.board[i+3] == this.board[i+6] && this.board[i] != '0')
				return true;
		}
		
		for(i=0; i<7; i+= 3) {
				if(this.board[i] == this.board[i+1] && this.board[i+1] == this.board[i+2] && this.board[i] != '0')
					return true;
		}
		
		return false;
	}

	/***
		This function is used to swap board, so that players always see their stones as 1 and adversary stones as 2.
		This saves representation positions on the 
	***/
	public void swap_board(){
		for (int i=0; i<9; i++){
			if (this.board[i] == '1')
				this.board[i] = '2';
			else if (this.board[i] == '2')
				this.board[i] = '1';				
		}
	}
	
	
	/***
		This function plays the game.
		Max number of plays is defined in the class.
		Player One always start, thus the sortition of who starts must be done beforehand.
		After playing, we check if game is over.
		If game isnt over, swap the board and give it to the other player.
	***/
	public int play(){
		//System.out.println("Jogo entre jogador " + this.player_one.getId() + " e " + this.player_two.getId());
		for (int i=0; i<Game.NUMBER_OF_PLAYS; i++){
			System.out.println("Jogada jogador 1");
			String newBoard = this.player_one.getNextPlay(this.representation, this.board);
			
			// If he has no available plays, he lost.
			if (newBoard.equals(new String("LOST")))
				return this.player_two.getId();
			
			// Else, update board.
			this.board = newBoard.toCharArray();
			
			// Checks if he won
			if (this.is_game_finished()) 
				return this.player_one.getId();
			
			System.out.println(new String(this.board));
			
			swap_board();

			System.out.println("Jogada jogador 2");
			newBoard = this.player_two.getNextPlay(this.representation, this.board);
			
			// If he has no available plays, he lost.
			if (newBoard.equals(new String("LOST")))
				return this.player_one.getId();
			
			// Else, update board.
			this.board = newBoard.toCharArray();
			
			// Checks if he won
			if (this.is_game_finished()) 
				return this.player_two.getId();

			System.out.println(new String(this.board));
			
			swap_board();
		}
		return -1;
	} 
	
	// Sortear quem come�a
	// Retornar quem foi o ganhador.  Fun��o recebe o id de cada jogador e retorna -1 se nenhum deles ganhou OK
	// Jogo j� acabou (vetor de char board) boolean OK
	// Passar a vez pro coleguinha, limite de jogadas = 100, OK
	// Swap (onde tem 2 bota 1 onde 1 bota 2) 
}
