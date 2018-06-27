package environment;

public abstract class Player {
	protected int[] pieces;
	protected String[] plays;
	protected Game game;
	
	
	/***
	 * This function receives a table and evaluate it.
	 * The higher the score, the better the evaluation is for this player.
	 * @param table
	 * @return
	 */
	public abstract int evaluate_table ();
	
	public abstract int put_pieces ();
	public abstract void getNextPlay(char[] board);
	
}

