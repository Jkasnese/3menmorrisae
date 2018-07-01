package environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Representation {
	
    // Create hash map to contain all possibilities
    private Map<String,Integer> all_possibilities = new HashMap<String,Integer>(4100, (float)1.0);

    // Array that maps each index to a board position
    private ArrayList<String> positions_decode = new ArrayList<String>();
    
    
	/**
    Creates all board possibilities and adds them with their respective definitive representation to a hash map
    Then maps each key (position) to a position index.
        This position index on the player chromossome represents the play that he will make.
        This position index on the representations arraylist represents the boards current position.    
    Writes the hash objects that map all board possibilities to an array without repetition
	 **/
    public Representation() {		// How many board configurations were added to hashmap
	    int position = 0;
	  
	    // Board representations
	    char[] empty_board = new char[9];
	    Arrays.fill(empty_board, '0');
	    char[] board = new char[9];
	
	    // Add the single position with the empty board.
	    position = add_board_to_map(empty_board, position);

    	// Add all possibilities for putting stones phase.
	    for (int i=0; i<9; i++){
		    Arrays.fill(board, '0');
		    board[i] = '2';
		    position = add_board_to_map(board, position);
		    for(int j=0; j<9; j++) {
		    	if (i == j) {continue;}
		    	else {
		    		board[j] = '1';
		    		position = add_board_to_map(board, position);
		    		for(int k=0; k<9;k++) {
		    			if (k == i || k==j) {continue;}
		    			else {
		    				board[k] = '2';
		    				position = add_board_to_map(board, position);
		    				for(int l=0; l<9;l++) {
		    					if (l==k || l==j || l==i) {continue;}
		    					else {
			    					board[l] = '1';
			    					position = add_board_to_map(board, position);
			    					for(int m=0; m<9; m++) {
			    						if (m == l || m==k || m==j || m==i) {continue;}
			    						else {
			    							board[m] = '2';
			    							position = add_board_to_map(board, position);
			    							board[m] = '0';
			    						}
			    					}
			    					board[l] = '0';
		    					}
		    				}
		    				board[k] = '0';
		    			}
		    		}
		    		board[j] = '0';
		    	}
		    }
	    }
	    System.out.println("Number of possible plays before 6 pieces on the board: " + position);

	    // Add all possibilities for 6 stones
	    for (int i=0; i<9; i++){
		    Arrays.fill(board, '0');
		    board[i] = '2';
		    position = add_board_to_map(board, position);
		    for(int j=0; j<9; j++) {
		    	if (i == j) {continue;}
		    	else {
		    		board[j] = '1';
		    		position = add_board_to_map(board, position);
		    		for(int k=0; k<9;k++) {
		    			if (k == i || k==j) {continue;}
		    			else {
		    				board[k] = '2';
		    				position = add_board_to_map(board, position);
		    				for(int l=0; l<9;l++) {
		    					if (l==k || l==j || l==i) {continue;}
		    					else {
			    					board[l] = '1';
			    					position = add_board_to_map(board, position);
			    					for(int m=0; m<9; m++) {
			    						if (m == l || m==k || m==j || m==i) {continue;}
			    						else {
			    							board[m] = '2';
			    							position = add_board_to_map(board, position);
			    							for (int n=0; n<9; n++) {
			    								if (n == m || n == l || n == k || n == j || n == i) {continue;}
			    								else {
			    									board[n] = '1';
			    									position = add_board_to_map(board, position);
			    									board[n] = '0';
			    								}
			    							}
			    							board[m] = '0';
			    						}
			    					}
			    					board[l] = '0';
		    					}
		    				}
		    				board[k] = '0';
		    			}
		    		}
		    		board[j] = '0';
		    	}
		    }
	    }
	    System.out.println("Total number of board repeated board combinations: " + this.all_possibilities.size());
	    System.out.println("Total number of plays: " + position);
	}
    
    
    /***
     * Method to access hashmap and receive a index corresponding to the players playbook
     * TODO: overcharge method to acept both string or char array
     * @param String board
     * @return int index
     */
    public int getIndex(String board) {
    	return this.all_possibilities.get(board).intValue();
    }
    
    /***
     * Method to access arraylists with board original positions and receive a index corresponding to that position
     * @param String board
     * @return int index
     */
    public String getPosition(int i) {
    	return this.positions_decode.get(i);
    }
    
	public static char[] rotate_right (char[] table) {
		char[] transformed_table = new char[9];
		
		transformed_table[0] = table[6];
		transformed_table[1] = table[3];
		transformed_table[2] = table[0];
		transformed_table[3] = table[7];
		transformed_table[4] = table[4];		
		transformed_table[5] = table[1];
		transformed_table[6] = table[8];
		transformed_table[7] = table[5];
		transformed_table[8] = table[2];
		
		return transformed_table;
	}
	
	public static char[] reflect_right (char[] table) {
		char[] transformed_table = new char[9];
	
		transformed_table[0] = table[2];
		transformed_table[1] = table[1];
		transformed_table[2] = table[0];
		transformed_table[3] = table[5];
		transformed_table[4] = table[4];
		transformed_table[5] = table[3];
		transformed_table[6] = table[8];
		transformed_table[7] = table[7];
		transformed_table[8] = table[6];
	
		return transformed_table;
	}
	
	public void addToHashmap (String hash, char[] board, int position) {
	    // Add current board position to hash.
	    this.all_possibilities.put(hash, position);
	
	    // Rotate/reflect and find all representations of the same board position and associate with the value in hash.
	    char[] reflected_board = reflect_right(board);
	    String same_hash = new String(reflected_board);
	    this.all_possibilities.put(same_hash, position);
	
	    for (int j=0; j<3; j++){
	        board = rotate_right(board);
	        same_hash = new String(board);
	        this.all_possibilities.put(same_hash, position);
	
	        reflected_board = reflect_right(board);
	        same_hash = new String(reflected_board);
	        this.all_possibilities.put(same_hash, position);
	    }
	}
	 
	public int add_board_to_map (char[] board, Integer position){
	    String hash = new String(board);
	
	    if (this.all_possibilities.containsKey(hash) ){         // This position already has a similar board configuration added
	        return position;            
	    } else {
	        this.positions_decode.add(hash);
	        addToHashmap(hash, board, position);
	        position++;
	        return position;
	        }
	    }
}

/**
for (int k=0; k<9; k++) {
System.out.print(board[k]);
}
System.out.print("--");
System.out.println(j);**/