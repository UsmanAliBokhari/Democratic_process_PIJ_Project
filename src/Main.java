import java.util.Scanner;

/**
 * Main entry point for the Democratic Process game.
 * Creates the player and initializes the game.
 *
 * @author Usman
 * @version 1.0
 */
public class Main {
    /**
     * Main method that starts the game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n========================================");
        System.out.println("     DEMOCRATIC PROCESS - THE GAME");
        System.out.println("========================================");
        System.out.println("A political simulation game where you");
        System.out.println("compete to win an election through");
        System.out.println("speeches, alliances, and strategy.");
        System.out.println("========================================\n");

        // Get player name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();

        if (playerName.isEmpty()) {
            playerName = "Politician";
            System.out.println("No name entered. Using default: " + playerName);
        }

        // Choose character type
        System.out.println("\nChoose your character type:");
        System.out.println("1. BUSINESS_TYCOON   - High money ($150), moderate popularity (35%)");
        System.out.println("2. CAREER_POLITICIAN - High popularity (45%), experienced (30 influence)");
        System.out.println("3. OUTSIDER          - Balanced stats, low scandal risk (5%)");
        System.out.println("4. MAFIA_LEADER      - Very high money ($200), high scandal risk (40%)");
        System.out.print("\nEnter choice (1-4): ");

        CharacterType chosenType = CharacterType.OUTSIDER; // Default
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                chosenType = CharacterType.BUSINESS_TYCOON;
                break;
            case "2":
                chosenType = CharacterType.CAREER_POLITICIAN;
                break;
            case "3":
                chosenType = CharacterType.OUTSIDER;
                break;
            case "4":
                chosenType = CharacterType.MAFIA_LEADER;
                break;
            default:
                System.out.println("Invalid choice. Using OUTSIDER as default.");
        }

        System.out.println("\nYou chose: " + chosenType);

        // Create player with chosen name and character type
        Player player = new Player(playerName, chosenType);

        // Create game manager
        GameManager gameManager = new GameManager(player);

        // Start the game
        gameManager.start();
    }
}