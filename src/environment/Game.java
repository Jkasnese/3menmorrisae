package environment;

import java.util.Arrays;

public class Game {
	private Player player_one;
	private Player player_two;
	
	private char[] board;

	// Number of plays for each player
	private static int NUMBER_OF_PLAYS = 50;

	public Game(Player player_x, Player player_y){
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
			if(this.board[i] == this.board[i+3] && this.board[i+3] == this.board[i+6]) {return true;}
		}
		
		for(i=0; i<7; i+= 3) {
				if(this.board[i] == this.board[i+1] && this.board[i+1] == this.board[i+2]){return true;}
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
		for (int i=0; i<NUMBER_OF_PLAYS; i++){
			this.player_one.getNextPlay(this.board);
			if (this.is_game_finished()) {return this.player_one.getId();}
			swap_board();

			this.player_two.getNextPlay(this.board);
			if (this.is_game_finished()) {return this.player_two.getId();}
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
