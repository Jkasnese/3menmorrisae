package environment;

public abstract class Player {
	private static int id;
	protected String[] plays;
	protected int playerId;
	
	
	/***
	 * This function receives a table and evaluate it.
	 * The higher the score, the better the evaluation is for this player.
	 * @param table
	 * @return
	 */
	public abstract int evaluate_table ();
	
	public abstract int put_pieces ();
	public abstract void getNextPlay(char[] board);

	public int getId() {
		return this.playerId;
	}
	
}

