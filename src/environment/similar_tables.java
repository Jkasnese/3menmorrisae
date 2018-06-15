public int[][] rotate_right (int[][] table) {
	int [][] transformed_table;

	// Center
	transformed_table[1][1] = table[1][1];

	// Unrolled loops. Memory access order sucks, but clearer code.

	// Rotate line 0 to column 2
	transformed_table[0][2] = table[0][0];
	transformed_table[1][2] = table[0][1];
	transformed_table[2][2] = table[0][2];

	// Rotate column 2 to line 2
	transformed_table[2][0] = table[2][2];
	transformed_table[2][1] = table[1][2];
	transformed_table[2][2] = table[0][2];

	// Rotate line 2 to column 0
	transformed_table[0][0] = table[2][0];
	transformed_table[1][0] = table[2][1];
	transformed_table[2][0] = table[2][2];

	// Rotate column 0 to line 0
	transformed_table[0][2] = table[0][0];
	transformed_table[0][1] = table[1][0];
	transformed_table[0][0] = table[2][0];

	return transformed_table;
}

public int[][] reflect_right (int[][] table) {
	int [][] transformed_table;

	// Center
	transformed_table[1][1] = table[1][1];

	// Unrolled loops. Memory access order sucks, but clearer code.

	// Reflect line 0
	transformed_table[0][2] = table[0][0];
	transformed_table[0][1] = table[0][1];
	transformed_table[0][0] = table[0][2];

	// Reflect line 1
	transformed_table[1][2] = table[1][0];
	transformed_table[1][1] = table[1][1];
	transformed_table[1][0] = table[1][2];

	// Reflect line 2
	transformed_table[2][2] = table[2][0];
	transformed_table[2][1] = table[2][1];
	transformed_table[2][0] = table[2][2];	

	return transformed_table;
}


public int[][] create_hash(int[][] table) {
		

}