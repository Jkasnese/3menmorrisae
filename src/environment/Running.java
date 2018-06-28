package environment;

public class Running {

    private static int POPULATION_SIZE = 100;

	public static void main(String[] args) {

        // Generate the board/position representations
		Representation representation = new Representation();
		representation.create_all_possibilities();

        // Players arraylist
        ArrayList <Player> players = new ArrayList<Player>()
    
        // Generate first generation        
        for (int i=0; i<Running.POPULATION_SIZE; i++){
            players.add(Player());
        }

	}

}
