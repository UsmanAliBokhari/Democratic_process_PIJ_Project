public class Main {
    public static void main(String[] args) {
        // Create player with chosen character type
        Player player = new Player("Refat", CharacterType.OUTSIDER);

        // Create game manager
        GameManager gameManager = new GameManager(player);

        // Start the game
        gameManager.start();
    }
}