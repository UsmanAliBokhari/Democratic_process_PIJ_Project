import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GameManager class manages the overall game state, including player actions,
 * world navigation, opponents, allies, and game progression.
 *
 * @author Usman
 * @version 1.0
 */
public class GameManager {
    private Player player;
    private List<Opponent> opponents;
    private List<Ally> availableAllies;
    private Map<String, Location> worldMap;
    private Location currentLocation;
    private EventSystem eventSystem;
    private CommandProcessor commandProcessor;

    private int currentTurn;
    private int maxTurns;
    private boolean gameRunning;

    private static final int ELECTION_DAY = 15; // Elections happen after 15 turns (increased difficulty)
    private static final double WIN_POPULARITY = 75.0; // Increased from 70
    private static final double LOSE_POPULARITY = 25.0; // Stricter loss condition
    private static final int MIN_ALLIES_TO_WIN = 2; // Need more allies now

    /**
     * Constructs a new GameManager with the specified player.
     * Initializes the game world, opponents, allies, and event system.
     *
     * @param player the player character for this game session
     */
    public GameManager(Player player) {
        this.player = player;
        this.opponents = new ArrayList<>();
        this.availableAllies = new ArrayList<>();
        this.worldMap = new HashMap<>();
        this.eventSystem = new EventSystem();
        this.commandProcessor = new CommandProcessor(this);

        this.currentTurn = 1;
        this.maxTurns = ELECTION_DAY;
        this.gameRunning = true;

        initializeWorld();
        initializeOpponents();
        initializeAllies();
    }

    /**
     * Initializes the game world with locations, NPCs, items, and connections.
     * Creates a more complex world map with 8 locations.
     */
    private void initializeWorld() {
        // Create locations with directional connections
        Location townSquare = new Location("Town Square",
                "The heart of the city. Citizens gather here for important announcements.");
        Location parliament = new Location("Parliament Building",
                "The center of political power. Opponents and allies gather here.");
        Location mediaCentre = new Location("Media Centre",
                "Where journalists and media influence opinions.");
        Location slums = new Location("Downtown Slums",
                "Where the working class lives. Building grassroots support here is valuable.");
        Location mansion = new Location("Political Mansion",
                "Your campaign headquarters. Plan strategy and manage finances here.");
        Location university = new Location("University Campus",
                "Young voters and intellectuals gather here. A key demographic.");
        Location industryPark = new Location("Industry Park",
                "Corporate headquarters and business leaders. Deep pockets but tough negotiations.");
        Location suburbs = new Location("Suburban District",
                "Middle-class families live here. Swing voters who could go either way.");

        // Add NPCs to locations
        townSquare.addNPC("Local Business Owner", "ally");
        townSquare.addNPC("Concerned Citizen", "neutral");
        parliament.addNPC("Prime Minister", "opponent");
        parliament.addNPC("Union Leader", "ally");
        parliament.addNPC("Conservative Senator", "opponent");
        mediaCentre.addNPC("Media Magnate", "ally");
        mediaCentre.addNPC("Investigative Journalist", "neutral");
        slums.addNPC("Opposition Leader", "opponent");
        slums.addNPC("Community Activist", "ally");
        mansion.addNPC("Industry Baron", "opponent");
        mansion.addNPC("Campaign Manager", "ally");
        university.addNPC("Student Leader", "ally");
        university.addNPC("Professor", "neutral");
        industryPark.addNPC("Corporate CEO", "opponent");
        industryPark.addNPC("Tech Entrepreneur", "ally");
        suburbs.addNPC("Local Mayor", "neutral");
        suburbs.addNPC("Soccer Mom", "neutral");

        // Add items to locations
        townSquare.addItem(new Item("Flyer", "Campaign flyers to distribute", "political", 5));
        townSquare.addItem(new Item("Coffee", "Energy boost", "consumable", 3));
        parliament.addItem(new Item("Political Document", "Classified government memo", "evidence", 15));
        parliament.addItem(new Item("Voting Records", "Opponent voting history", "evidence", 12));
        mediaCentre.addItem(new Item("Recording Device", "Record evidence of corruption", "tool", 20));
        mediaCentre.addItem(new Item("Press Pass", "Access to exclusive events", "tool", 15));
        mansion.addItem(new Item("Briefcase", "Contains campaign funds", "valuable", 50));
        mansion.addItem(new Item("Strategy Guide", "Campaign tips", "document", 8));
        slums.addItem(new Item("Petition", "Support from residents", "document", 10));
        university.addItem(new Item("Research Paper", "Policy insights", "document", 7));
        industryPark.addItem(new Item("Business Card", "Corporate connections", "tool", 10));
        suburbs.addItem(new Item("Survey Results", "Voter preferences", "document", 12));

        // Set up directional connections (more complex map)
        townSquare.setConnection("north", parliament);
        townSquare.setConnection("south", slums);
        townSquare.setConnection("east", mediaCentre);
        townSquare.setConnection("west", mansion);

        parliament.setConnection("south", townSquare);
        parliament.setConnection("east", university);

        slums.setConnection("north", townSquare);
        slums.setConnection("west", suburbs);

        mediaCentre.setConnection("west", townSquare);
        mediaCentre.setConnection("north", university);

        mansion.setConnection("east", townSquare);
        mansion.setConnection("south", suburbs);

        university.setConnection("south", mediaCentre);
        university.setConnection("west", parliament);
        university.setConnection("east", industryPark);

        industryPark.setConnection("west", university);
        industryPark.setConnection("south", suburbs);

        suburbs.setConnection("north", mansion);
        suburbs.setConnection("east", slums);
        suburbs.setConnection("north", industryPark);

        worldMap.put("Town Square", townSquare);
        worldMap.put("Parliament Building", parliament);
        worldMap.put("Media Centre", mediaCentre);
        worldMap.put("Downtown Slums", slums);
        worldMap.put("Political Mansion", mansion);
        worldMap.put("University Campus", university);
        worldMap.put("Industry Park", industryPark);
        worldMap.put("Suburban District", suburbs);

        currentLocation = townSquare;
    }

    /**
     * Initializes the game's opponents with varying difficulty levels.
     */
    private void initializeOpponents() {
        opponents.add(new Opponent("Prime Minister"));
        opponents.add(new Opponent("Opposition Leader"));
        opponents.add(new Opponent("Industry Baron"));
        opponents.add(new Opponent("Conservative Senator"));
        opponents.add(new Opponent("Corporate CEO"));
    }

    /**
     * Initializes available allies with different loyalty and influence values.
     */
    private void initializeAllies() {
        availableAllies.add(new Ally("Local Business Owner", 60, 12));
        availableAllies.add(new Ally("Union Leader", 65, 18));
        availableAllies.add(new Ally("Media Magnate", 50, 22));
        availableAllies.add(new Ally("Community Organizer", 75, 8));
        availableAllies.add(new Ally("Student Leader", 70, 10));
        availableAllies.add(new Ally("Tech Entrepreneur", 55, 15));
        availableAllies.add(new Ally("Campaign Manager", 80, 20));
        availableAllies.add(new Ally("Community Activist", 68, 14));
    }

    /**
     * Starts the game and displays initial information.
     */
    public void start() {
        System.out.println("\n========== DEMOCRATIC PROCESS ==========");
        System.out.println("Character: " + player.getName());
        System.out.println("Starting Popularity: " + player.getPopularity() + "%");
        System.out.println("Starting Money: $" + player.getMoney());
        System.out.println("Elections in: " + maxTurns + " turns");
        System.out.println("Win Condition: " + WIN_POPULARITY + "% popularity + " + MIN_ALLIES_TO_WIN + " major allies");
        System.out.println("========================================\n");

        commandProcessor.start();
    }

    /**
     * Moves the player to a connected location.
     *
     * @param direction the direction to move (north, south, east, west)
     */
    public void movePlayer(String direction) {
        Location nextLocation = currentLocation.getConnection(direction);

        if (nextLocation == null) {
            System.out.println("You cannot go " + direction + " from here.");
            return;
        }

        currentLocation = nextLocation;
        System.out.println("You moved " + direction + ".");
        displayCurrentLocation();

        // Random encounter chance
        if (Math.random() < 0.25) {
            System.out.println("\n[!] You encounter someone as you arrive...");
            Event event = eventSystem.triggerRandomEvent();
            if (event != null) event.trigger(player);
        }
    }

    /**
     * Allows the player to pick up an item from the current location.
     *
     * @param itemName the name of the item to take
     */
    public void takeItem(String itemName) {
        Item item = currentLocation.findItem(itemName);

        if (item == null) {
            System.out.println("Item '" + itemName + "' not found here.");
            return;
        }

        player.takeItem(item);
        currentLocation.removeItem(item);
    }

    /**
     * Allows the player to drop an item at the current location.
     *
     * @param itemName the name of the item to drop
     */
    public void dropItem(String itemName) {
        Item item = player.findInventoryItem(itemName);

        if (item == null) {
            System.out.println("Item '" + itemName + "' not in inventory.");
            return;
        }

        player.dropItem(item);
        currentLocation.addItem(item);
    }

    /**
     * Uses an item from the player's inventory with appropriate effects.
     *
     * @param itemName the name of the item to use
     */
    public void useItem(String itemName) {
        Item item = player.findInventoryItem(itemName);

        if (item == null) {
            System.out.println("Item '" + itemName + "' not in inventory.");
            return;
        }

        String type = item.getType();
        double itemValue = item.getValue();

        if (type.equals("political")) {
            System.out.println("Using " + item.getName() + "...");
            player.updatePopularity(itemValue);
            System.out.println("Popularity increased!");
            player.dropItem(item);
        } else if (type.equals("evidence")) {
            System.out.println("Using " + item.getName() + "...");
            System.out.println("You found incriminating evidence against an opponent!");
            player.updatePopularity(itemValue);
            // Damage a random opponent
            if (!opponents.isEmpty()) {
                Opponent target = opponents.get((int)(Math.random() * opponents.size()));
                target.updatePopularity(-10);
                System.out.println(target.getName() + " loses popularity!");
            }
            player.dropItem(item);
        } else if (type.equals("valuable")) {
            System.out.println("Using " + item.getName() + "...");
            player.addMoney(itemValue);
            System.out.println("Money gained: $" + itemValue);
            player.dropItem(item);
        } else if (type.equals("document")) {
            System.out.println("Using " + item.getName() + "...");
            player.updatePopularity(itemValue);
            System.out.println("Support increased!");
            player.dropItem(item);
        } else if (type.equals("tool")) {
            System.out.println("This tool can be used to gather evidence or blackmail opponents.");
            System.out.println("Tools remain in inventory for multiple uses.");
        } else if (type.equals("consumable")) {
            System.out.println("Using " + item.getName() + "...");
            player.updatePopularity(itemValue);
            player.dropItem(item);
        }
    }

    /**
     * Triggers the player's speech action and potentially a random event.
     */
    public void playerGiveSpeech() {
        player.giveSpeech();
        // Higher chance of event after speech
        if (Math.random() < 0.4) {
            Event event = eventSystem.triggerRandomEvent();
            if (event != null) event.trigger(player);
        }
    }

    /**
     * Attempts to bribe an opponent.
     *
     * @param opponentName the name of the opponent to bribe
     */
    public void playerBribe(String opponentName) {
        Opponent opponent = findOpponent(opponentName);
        if (opponent == null) {
            System.out.println("Opponent '" + opponentName + "' not found.");
            return;
        }
        player.bribe(opponent);
    }

    /**
     * Negotiates an alliance with an ally.
     *
     * @param allyName the name of the ally to negotiate with
     */
    public void playerNegotiateAlliance(String allyName) {
        Ally ally = findAlly(allyName);
        if (ally == null) {
            System.out.println("Ally '" + allyName + "' not found or already allied.");
            return;
        }

        // Alliances now require minimum influence
        if (player.getInfluence() < 25) {
            System.out.println("You need at least 25 influence to negotiate alliances!");
            System.out.println("Current influence: " + player.getInfluence());
            return;
        }

        player.negotiateAlliance(ally);
        availableAllies.remove(ally);
    }

    /**
     * Attempts to sabotage an opponent's campaign.
     *
     * @param opponentName the name of the opponent to sabotage
     */
    public void playerSabotage(String opponentName) {
        Opponent opponent = findOpponent(opponentName);
        if (opponent == null) {
            System.out.println("Opponent '" + opponentName + "' not found.");
            return;
        }
        player.sabotage(opponent);
    }

    /**
     * Manages media narrative at a cost.
     */
    public void playerManageMedia() {
        player.manageMedia();
    }

    /**
     * Ends the current turn and progresses the game.
     * Checks for win/lose conditions and triggers events.
     */
    public void endTurn() {
        currentTurn++;
        System.out.println("\n--- Turn " + currentTurn + " / " + maxTurns + " ---");

        // Opponents gain popularity each turn (increased difficulty)
        for (Opponent opp : opponents) {
            opp.updatePopularity(2 + Math.random() * 3);
        }

        // Maintenance costs
        if (player.getAlliances().size() > 0) {
            double maintenanceCost = player.getAlliances().size() * 5;
            player.addMoney(-maintenanceCost);
            System.out.println("Alliance maintenance cost: $" + maintenanceCost);
        }

        checkGameStatus();

        // Higher chance of events
        if (Math.random() < 0.5) {
            Event event = eventSystem.triggerRandomEvent();
            if (event != null) event.trigger(player);
        }
    }

    /**
     * Checks the current game status and determines win/lose conditions.
     */
    private void checkGameStatus() {
        double popularity = player.getPopularity();
        double money = player.getMoney();
        int alliesCount = player.getAlliances().size();

        if (currentTurn >= maxTurns) {
            if (popularity >= WIN_POPULARITY && alliesCount >= MIN_ALLIES_TO_WIN) {
                System.out.println("\n========== YOU WON THE ELECTION! ==========");
                System.out.println("Final Popularity: " + String.format("%.1f", popularity) + "%");
                System.out.println("Allies: " + alliesCount);
                System.out.println("Remaining Funds: $" + String.format("%.2f", money));
                gameRunning = false;
            } else {
                System.out.println("\n========== YOU LOST THE ELECTION! ==========");
                System.out.println("Final Popularity: " + String.format("%.1f", popularity) + "%");
                System.out.println("Allies: " + alliesCount);
                System.out.println("Required: " + WIN_POPULARITY + "% popularity + " + MIN_ALLIES_TO_WIN + " allies");
                gameRunning = false;
            }
        }

        if (money <= 0) {
            System.out.println("\n========== GAME OVER! ==========");
            System.out.println("You ran out of money!");
            gameRunning = false;
        }

        if (popularity <= LOSE_POPULARITY) {
            System.out.println("\n========== GAME OVER! ==========");
            System.out.println("Your popularity dropped too low!");
            gameRunning = false;
        }

        if (player.getScandalRisk() >= 100) {
            System.out.println("\n========== GAME OVER! ==========");
            System.out.println("A major scandal destroyed your campaign!");
            gameRunning = false;
        }
    }

    /**
     * Displays the player's current statistics.
     */
    public void displayPlayerStats() {
        player.updateStats();
    }

    /**
     * Displays the player's inventory contents.
     */
    public void displayInventory() {
        System.out.println("\n--- INVENTORY ---");
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + " (" + item.getType() + ") - " + item.getDescription());
            }
        }
        System.out.println("-----------------\n");
    }

    /**
     * Displays information about the current location including NPCs and items.
     */
    public void displayCurrentLocation() {
        System.out.println("\n--- " + currentLocation.getName() + " ---");
        System.out.println(currentLocation.getDescription());

        Map<String, String> npcs = currentLocation.getNPCs();
        if (!npcs.isEmpty()) {
            System.out.println("\nPeople here:");
            for (String npcName : npcs.keySet()) {
                String type = npcs.get(npcName);
                System.out.println("- " + npcName + " (" + type + ")");
            }
        }

        List<Item> items = currentLocation.getItems();
        if (!items.isEmpty()) {
            System.out.println("\nItems here:");
            for (Item item : items) {
                System.out.println("- " + item.getName());
            }
        }

        System.out.println("\nExits: " + currentLocation.getExits());
        System.out.println();
    }

    /**
     * Interacts with a neutral NPC for information or small benefits.
     *
     * @param npcName the name of the NPC to interact with
     */
    public void playerInteractNPC(String npcName) {
        Map<String, String> npcs = currentLocation.getNPCs();

        if (!npcs.containsKey(npcName)) {
            System.out.println("NPC '" + npcName + "' not found here.");
            return;
        }

        String npcType = npcs.get(npcName);

        if (!npcType.equals("neutral")) {
            System.out.println("Use specific commands for allies and opponents (ally/bribe/sabotage).");
            return;
        }

        System.out.println("\nYou approach " + npcName + "...");

        // Different neutrals provide different benefits
        switch (npcName) {
            case "Concerned Citizen":
                System.out.println("The citizen shares local concerns with you.");
                player.updatePopularity(3);
                System.out.println("You gain insight into public opinion! +3 popularity");
                break;

            case "Investigative Journalist":
                if (player.getMoney() >= 25) {
                    System.out.println("The journalist offers to investigate your opponents for $25.");
                    System.out.print("Accept? (yes/no): ");
                    // For now, auto-accept in this version
                    player.addMoney(-25);
                    System.out.println("The journalist digs up dirt on a random opponent!");
                    if (!opponents.isEmpty()) {
                        Opponent target = opponents.get((int)(Math.random() * opponents.size()));
                        target.updatePopularity(-8);
                        System.out.println(target.getName() + " loses 8 popularity!");
                    }
                } else {
                    System.out.println("The journalist would help, but you need $25.");
                }
                break;

            case "Professor":
                System.out.println("The professor shares research on political strategy.");
                double influenceGain = 5 + Math.random() * 5;
                System.out.println("You gain valuable insights! +" + String.format("%.1f", influenceGain) + " influence");
                // Note: Player class would need influence getter/setter exposed
                player.updatePopularity(2);
                break;

            case "Local Mayor":
                System.out.println("The mayor is willing to endorse you... for a price.");
                if (player.getMoney() >= 30) {
                    player.addMoney(-30);
                    player.updatePopularity(12);
                    System.out.println("Mayor endorsement secured! +12 popularity (Cost: $30)");
                } else {
                    System.out.println("The mayor wants $30 for an endorsement.");
                }
                break;

            case "Soccer Mom":
                System.out.println("She represents suburban voters' concerns.");
                if (Math.random() < 0.6) {
                    player.updatePopularity(4);
                    System.out.println("She's impressed with your policies! +4 popularity");
                } else {
                    System.out.println("She's skeptical of politicians. No effect.");
                }
                break;

            default:
                System.out.println(npcName + " chats with you briefly but offers no specific help.");
        }

        System.out.println();
    }

    /**
     * Displays the help menu.
     */
    public void displayHelp() {
        commandProcessor.displayHelp();
    }

    /**
     * Finds an opponent by name.
     *
     * @param name the name of the opponent
     * @return the Opponent object or null if not found
     */
    private Opponent findOpponent(String name) {
        for (Opponent o : opponents) {
            if (o.getName().equalsIgnoreCase(name)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Finds an available ally by name.
     *
     * @param name the name of the ally
     * @return the Ally object or null if not found
     */
    private Ally findAlly(String name) {
        for (Ally a : availableAllies) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Returns whether the game is currently running.
     *
     * @return true if game is running, false otherwise
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Gets the player object.
     *
     * @return the Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the list of opponents.
     *
     * @return list of Opponent objects
     */
    public List<Opponent> getOpponents() {
        return opponents;
    }

    /**
     * Gets the list of available allies.
     *
     * @return list of available Ally objects
     */
    public List<Ally> getAvailableAllies() {
        return availableAllies;
    }
}