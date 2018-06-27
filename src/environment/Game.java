package environment;

public class Game {
	private Player player_one;
	private Player player_two;
	
	private char[] table;
	
	/***
	 * Validates a given play (legal or ilegal)
	 */
	public int validate_play (String play){
		return 0;
	}
	
	public boolean is_game_finished(){

		int i = 0;
		
		for(i=0; i<3; i++) {
			// Checa vertical
			if(this.table[i] == this.table[i+3] && this.table[i+3] == this.table[i+6])
				return true;
		}
		
		for(i=0; i<7; i++) {
			
			if(i == 0 || i == 3 || i == 6) {
				if(this.table[i] == this.table[i+1] && this.table[i+1] == this.table[i+2])
					return true;
			}
		}
		
		return false;
		
	}
	
	// Sortear quem começa
	// Retornar quem foi o ganhador.  Função recebe o id de cada jogador e retorna -1 se nenhum deles ganhou
	// Jogo já acabou (vetor de char table) boolean
	// Passar a vez pro coleguinha, limite de jogadas = 100, 
	// Swap (onde tem 2 bota 1 onde 1 bota 2) 
	
	
}
