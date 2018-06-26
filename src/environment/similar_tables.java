012     210
345     543
678     876

1 2 
3 4
5 6
7 8

public char[] rotate_right (char[] table) {
	char[] transformed_table = table;

	transformed_table[0] = table[6];
	transformed_table[1] = table[3];
	transformed_table[2] = table[0];
	transformed_table[3] = table[7];
	transformed_table[5] = table[1];
	transformed_table[6] = table[8];
	transformed_table[7] = table[5];
	transformed_table[8] = table[2];

	return transformed_table;
}

public char[] reflect_right (char[] table) {
	char transformed_table = table;

	transformed_table[0] = table[2];
	transformed_table[2] = table[0];
	transformed_table[3] = table[5];
	transformed_table[5] = table[3];
	transformed_table[6] = table[8];
	transformed_table[8] = table[6];

	return transformed_table;
}

public void add_similar_boards (Map all_possibilities, String hash, char[] board, int position) {
    // Add current board position to hash.
    all_possibilities.put(hash, position);

    // Rotate/reflect and find all representations of the same board position and associate with the value in hash.
    reflected_board = reflect_right(board);
    String same_hash = new String(reflected_board);
    all_possibilities.put(same_hash, position);

    for (j=0; j<3; j++){
        board = rotate_right(board);
        String same_hash = new String(board);
        all_possibilities.put(same_hash, position);

        reflected_board = reflect_right(board);
        String same_hash = new String(reflected_board);
        all_possibilities.put(same_hash, position);
    }
}

public void add_board_to_map (char[] board, Map all_possibilities, String[] positions_decode, int position){
    String hash = new String(board);



    if (all_possibilities.containsKey(board) ){         // This position already has a similar board configuration added
        return;            
    } else {
        positions_decode[position] = hash;
        this.add_similar_boards(all_possibilities, hash, board, position);
        position++;
        }
    }
}

/**
    Creates all board possibilities and adds them with their respective definitive representation to a hash map
    Then maps each key to a position index.
        This position index on the player chromossome represents the play that he will make.
        This position index on the representations array represents the boards current position.    
    Writes the hash objects that map all board possibilities to an array without repetition
**/
public int[] create_all_possibilities(int[][] table) {
    // Positions on the positions array
    int position = 0;

    // Array that maps each index to a board position
    String positions_decode[];
    
    // Board representations
    char[] empty_board = "000000000";
    char[] board;

    // Create hash map to contain all possibilities
    Map<String,int> all_possibilities = new HashMap<String,int>(19684, 1.0);
	

    // All possibilities for 1 stone
    for (i=0; i<9, i++){
        board[] = empty_board[];
        board[i] = "1";
        add_board_to_map(board, all_possibilities, positions_decode, position);
 
        board[i] = "2";
        add_board_to_map(board, all_possibilities, positions_decode, position);        

        System.out.println("%d", position);
    }        


	// All possibilities for 2 stones

	// All possibilities for 3 stones

	// All possibilities for 4 stones

	// All possibilities for 5 stones

	// All possibilities for 6 stones
}
