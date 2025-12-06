import java.util.Scanner;

public class CommandProcessor {
    private GameManager gameManager;
    private Scanner scanner;
    private boolean running;

    public CommandProcessor(GameManager gameManager) {
        this.gameManager = gameManager;
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    public void start() {
        System.out.println("Welcome to Democratic Process!");
        System.out.println("Type 'help' for a list of commands.\n");

        while (running && gameManager.isGameRunning()) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String args = parts.length > 1 ? parts[1] : "";

            processCommand(command, args);
        }

        scanner.close();
    }

    private void processCommand(String command, String args) {
        switch (command) {
            case "move":
                if (args.isEmpty()) {
                    System.out.println("Specify a direction: north, south, east, west");
                } else {
                    gameManager.movePlayer(args.toLowerCase());
                }
                break;

            case "take":
                if (args.isEmpty()) {
                    System.out.println("Specify which item to take");
                } else {
                    gameManager.takeItem(args);
                }
                break;

            case "drop":
                if (args.isEmpty()) {
                    System.out.println("Specify which item to drop");
                } else {
                    gameManager.dropItem(args);
                }
                break;

            case "use":
                if (args.isEmpty()) {
                    System.out.println("Specify which item to use");
                } else {
                    gameManager.useItem(args);
                }
                break;

            case "speech":
                gameManager.playerGiveSpeech();
                break;

            case "bribe":
                if (args.isEmpty()) {
                    System.out.println("Specify which opponent to bribe");
                } else {
                    gameManager.playerBribe(args);
                }
                break;

            case "ally":
                if (args.isEmpty()) {
                    System.out.println("Specify which ally to negotiate with");
                } else {
                    gameManager.playerNegotiateAlliance(args);
                }
                break;

            case "sabotage":
                if (args.isEmpty()) {
                    System.out.println("Specify which opponent to sabotage");
                } else {
                    gameManager.playerSabotage(args);
                }
                break;

            case "media":
                gameManager.playerManageMedia();
                break;

            case "stats":
                gameManager.displayPlayerStats();
                break;

            case "inventory":
                gameManager.displayInventory();
                break;

            case "location":
                gameManager.displayCurrentLocation();
                break;

            case "help":
                displayHelp();
                break;

            case "end":
                gameManager.endTurn();
                break;

            case "quit":
            case "exit":
                running = false;
                System.out.println("Thanks for playing!");
                break;

            default:
                System.out.println("Unknown command: '" + command + "'. Type 'help' for available commands.");
        }
    }

    public void displayHelp() {
        System.out.println("\n=== AVAILABLE COMMANDS ===");
        System.out.println("move <direction>    - Move north, south, east, west");
        System.out.println("take <item>         - Pick up an item");
        System.out.println("drop <item>         - Drop an item from inventory");
        System.out.println("use <item>          - Use an item from your inventory");
        System.out.println("\n--- INTERACT WITH NPCs ---");
        System.out.println("speech              - Give a campaign speech");
        System.out.println("bribe <name>        - Bribe an opponent (costs $20)");
        System.out.println("ally <name>         - Negotiate alliance with ally");
        System.out.println("sabotage <name>     - Sabotage opponent's campaign (risky!)");
        System.out.println("media               - Manage media narrative (costs $10)");
        System.out.println("\n--- VIEW INFO ---");
        System.out.println("stats               - View your current stats");
        System.out.println("inventory           - View your inventory");
        System.out.println("location            - View current location & NPCs");
        System.out.println("help                - Show this help menu");
        System.out.println("\n--- GAME ---");
        System.out.println("end                 - End current turn");
        System.out.println("quit/exit           - Exit the game");
        System.out.println("==========================\n");
    }
}