package environment;

/***
	Player that has all possible board configurations and the play to it.
***/
public class ArrayPlayer extends Player {

	private char[][] plays;

	public ArrayPlayer (){
		this.plays = new char[564][9];
	}
	
	// Picks a random winning position and tries to win with it no matter what.
	private int goal;

	@Override
	public int evaluate_table() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int put_pieces() {
		// TODO Auto-generated method stub
		return 0;
	}


	/***
		This function gets the next play.
		In a given board configuration, first find what is the "correct" representation in the hashmap.
		Pass the hash to the hashmap and obtain the array index.
		Use the given index to find the play in the player chromossome
	***/
	@Override
	public void getNextPlay(char[] board) {
		// TODO Auto-generated method stub
		return null;
	}

}
