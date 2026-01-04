/**
 * Main entry point for the Democratic Process game.
 * Creates the player and initializes the game.
 *
 * @author Refat
 * @version 1.0
 */
public class Main {
    /**
     * Main method that starts the game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("     DEMOCRATIC PROCESS - THE GAME");
        System.out.println("========================================");
        System.out.println("A political simulation game where you");
        System.out.println("compete to win an election through");
        System.out.println("speeches, alliances, and strategy.");
        System.out.println("========================================\n");

        // Create player with chosen character type
        // You can change the character type here:
        // BUSINESS_TYCOON, CAREER_POLITICIAN, OUTSIDER, MAFIA_LEADER
        Player player = new Player("Refat", CharacterType.OUTSIDER);

        // Create game manager
        GameManager gameManager = new GameManager(player);

        // Start the game
        gameManager.start();
    }
}