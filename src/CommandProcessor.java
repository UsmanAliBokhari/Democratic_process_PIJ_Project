import java.util.Scanner;

/**
 * Processes player commands and manages the game loop.
 * Handles all user input and routes commands to appropriate game actions.
 *
 * @author Usman
 * @version 1.0
 */
public class CommandProcessor {
    private GameManager gameManager;
    private Scanner scanner;
    private boolean running;

    /**
     * Constructs a new CommandProcessor for the given game.
     *
     * @param gameManager the GameManager instance to control
     */
    public CommandProcessor(GameManager gameManager) {
        this.gameManager = gameManager;
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    /**
     * Starts the main game loop, processing commands until game ends.
     */
    public void start() {
        System.out.println("Welcome to Democratic Process!");
        System.out.println("Type 'help' for a list of commands.\n");

        gameManager.displayCurrentLocation();

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

    /**
     * Processes a single command with its arguments.
     *
     * @param command the command to execute
     * @param args the command arguments
     */
    private void processCommand(String command, String args) {
        switch (command) {
            case "move":
            case "go":
                if (args.isEmpty()) {
                    System.out.println("Specify a direction: north, south, east, west");
                } else {
                    gameManager.movePlayer(args.toLowerCase());
                }
                break;

            case "take":
            case "get":
            case "pickup":
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
                    System.out.println("Available opponents:");
                    for (var opp : gameManager.getOpponents()) {
                        System.out.println("  - " + opp.getName());
                    }
                } else {
                    gameManager.playerBribe(args);
                }
                break;

            case "ally":
            case "alliance":
            case "negotiate":
                if (args.isEmpty()) {
                    System.out.println("Specify which ally to negotiate with");
                    System.out.println("Available allies:");
                    for (var ally : gameManager.getAvailableAllies()) {
                        System.out.println("  - " + ally.getName() +
                                " (Loyalty: " + ally.getLoyalty() +
                                ", Influence: +" + ally.getInfluenceBoost() + ")");
                    }
                } else {
                    gameManager.playerNegotiateAlliance(args);
                }
                break;

            case "sabotage":
            case "attack":
                if (args.isEmpty()) {
                    System.out.println("Specify which opponent to sabotage");
                    System.out.println("Available opponents:");
                    for (var opp : gameManager.getOpponents()) {
                        System.out.println("  - " + opp.getName() +
                                " (Popularity: " + String.format("%.1f", opp.getPopularity()) + "%)");
                    }
                } else {
                    gameManager.playerSabotage(args);
                }
                break;

            case "media":
            case "pr":
                gameManager.playerManageMedia();
                break;

            case "talk":
            case "interact":
            case "speak":
                if (args.isEmpty()) {
                    System.out.println("Specify which NPC to talk to");
                } else {
                    gameManager.playerInteractNPC(args);
                }
                break;

            case "stats":
            case "status":
                gameManager.displayPlayerStats();
                break;

            case "inventory":
            case "inv":
            case "i":
                gameManager.displayInventory();
                break;

            case "location":
            case "look":
            case "l":
                gameManager.displayCurrentLocation();
                break;

            case "opponents":
                displayOpponents();
                break;

            case "allies":
                displayAllies();
                break;

            case "help":
            case "h":
            case "?":
                displayHelp();
                break;

            case "end":
            case "endturn":
                gameManager.endTurn();
                break;

            case "quit":
            case "exit":
            case "q":
                running = false;
                System.out.println("Thanks for playing!");
                break;

            default:
                System.out.println("Unknown command: '" + command + "'. Type 'help' for available commands.");
        }
    }

    /**
     * Displays all current opponents and their stats.
     */
    private void displayOpponents() {
        System.out.println("\n--- OPPONENTS ---");
        for (var opp : gameManager.getOpponents()) {
            System.out.println(opp.getName() +
                    " - Popularity: " + String.format("%.1f", opp.getPopularity()) + "%" +
                    " | Defense: " + opp.getDefenseLevel());
        }
        System.out.println("-----------------\n");
    }

    /**
     * Displays all available allies and player's current allies.
     */
    private void displayAllies() {
        System.out.println("\n--- YOUR ALLIES ---");
        if (gameManager.getPlayer().getAlliances().isEmpty()) {
            System.out.println("No allies yet.");
        } else {
            for (var ally : gameManager.getPlayer().getAlliances()) {
                System.out.println(ally.getName() + " (Influence: +" + ally.getInfluenceBoost() + ")");
            }
        }

        System.out.println("\n--- AVAILABLE ALLIES ---");
        if (gameManager.getAvailableAllies().isEmpty()) {
            System.out.println("No more allies available.");
        } else {
            for (var ally : gameManager.getAvailableAllies()) {
                System.out.println(ally.getName() +
                        " - Loyalty: " + ally.getLoyalty() +
                        " | Influence: +" + ally.getInfluenceBoost());
            }
        }
        System.out.println("------------------------\n");
    }

    /**
     * Displays the help menu with all available commands.
     */
    public void displayHelp() {
        System.out.println("\n=== AVAILABLE COMMANDS ===");
        System.out.println("MOVEMENT:");
        System.out.println("  move/go <direction>  - Move north, south, east, west");
        System.out.println("\nITEMS:");
        System.out.println("  take/get <item>      - Pick up an item");
        System.out.println("  drop <item>          - Drop an item from inventory");
        System.out.println("  use <item>           - Use an item from your inventory");
        System.out.println("\nCAMPAIGN ACTIONS:");
        System.out.println("  speech               - Give a campaign speech (costs $15, 20% backfire risk!)");
        System.out.println("  bribe <name>         - Bribe an opponent (costs $25-40, risky)");
        System.out.println("  ally <name>          - Negotiate alliance with ally (needs 25+ influence)");
        System.out.println("  sabotage <name>      - Sabotage opponent's campaign (costs $30, very risky!)");
        System.out.println("  media                - Manage media narrative (costs $15)");
        System.out.println("  talk/interact <name> - Talk to neutral NPCs for bonuses");
        System.out.println("\nINFORMATION:");
        System.out.println("  stats/status         - View your current stats");
        System.out.println("  inventory/inv/i      - View your inventory");
        System.out.println("  location/look/l      - View current location & NPCs");
        System.out.println("  opponents            - View all opponents and their stats");
        System.out.println("  allies               - View allied and available allies");
        System.out.println("  help/h/?             - Show this help menu");
        System.out.println("\nGAME CONTROL:");
        System.out.println("  end/endturn          - End current turn");
        System.out.println("  quit/exit/q          - Exit the game");
        System.out.println("\nTIPS:");
        System.out.println("  - Maintain a balance between popularity and money");
        System.out.println("  - Build alliances early to meet the win requirement");
        System.out.println("  - Watch your scandal risk - if it reaches 100%, you lose!");
        System.out.println("  - Explore all locations to find valuable items");
        System.out.println("  - Different character types have different starting advantages");
        System.out.println("  - Talk to neutral NPCs for information and small bonuses");
        System.out.println("  - Speeches have a 20% chance to backfire - use carefully!");
        System.out.println("==========================\n");
    }
}