import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final int ELECTION_DAY = 10; // Elections happen after 10 turns
    private static final double WIN_POPULARITY = 70.0;
    private static final double LOSE_POPULARITY = 30.0;

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

        // Add NPCs to locations
        townSquare.addNPC("Local Business Owner", "ally");
        parliament.addNPC("Prime Minister", "opponent");
        parliament.addNPC("Union Leader", "ally");
        mediaCentre.addNPC("Media Magnate", "ally");
        slums.addNPC("Opposition Leader", "opponent");
        mansion.addNPC("Industry Baron", "opponent");

        // Add items to locations
        townSquare.addItem(new Item("Flyer", "Campaign flyers to distribute", "political", 0));
        parliament.addItem(new Item("Political Document", "Classified government memo", "evidence", 10));
        mediaCentre.addItem(new Item("Recording Device", "Record evidence of corruption", "tool", 15));
        mansion.addItem(new Item("Briefcase", "Contains campaign funds", "valuable", 50));
        slums.addItem(new Item("Petition", "Support from residents", "document", 5));

        // Set up directional connections
        townSquare.setConnection("north", parliament);
        townSquare.setConnection("south", slums);
        townSquare.setConnection("east", mediaCentre);
        townSquare.setConnection("west", mansion);

        parliament.setConnection("south", townSquare);
        slums.setConnection("north", townSquare);
        mediaCentre.setConnection("west", townSquare);
        mansion.setConnection("east", townSquare);

        worldMap.put("Town Square", townSquare);
        worldMap.put("Parliament Building", parliament);
        worldMap.put("Media Centre", mediaCentre);
        worldMap.put("Downtown Slums", slums);
        worldMap.put("Political Mansion", mansion);

        currentLocation = townSquare;
    }

    private void initializeOpponents() {
        opponents.add(new Opponent("Prime Minister"));
        opponents.add(new Opponent("Opposition Leader"));
        opponents.add(new Opponent("Industry Baron"));
    }

    private void initializeAllies() {
        availableAllies.add(new Ally("Local Business Owner", 65, 15));
        availableAllies.add(new Ally("Union Leader", 70, 20));
        availableAllies.add(new Ally("Media Magnate", 55, 25));
        availableAllies.add(new Ally("Community Organizer", 80, 10));
    }

    public void start() {
        System.out.println("\n========== DEMOCRATIC PROCESS ==========");
        System.out.println("Character: " + player.getName());
        System.out.println("Starting Popularity: 20%");
        System.out.println("Starting Money: $100");
        System.out.println("Elections in: " + maxTurns + " turns");
        System.out.println("Win Condition: 70% popularity + 1 major ally");
        System.out.println("========================================\n");

        commandProcessor.start();
    }

    public void movePlayer(String direction) {
        Location nextLocation = currentLocation.getConnection(direction);

        if (nextLocation == null) {
            System.out.println("You cannot go " + direction + " from here.");
            return;
        }

        currentLocation = nextLocation;
        System.out.println("You moved " + direction + ".");
        displayCurrentLocation();
    }

    public void takeItem(String itemName) {
        Item item = currentLocation.findItem(itemName);

        if (item == null) {
            System.out.println("Item '" + itemName + "' not found here.");
            return;
        }

        player.takeItem(item);
        currentLocation.removeItem(item);
    }

    public void dropItem(String itemName) {
        Item item = player.findInventoryItem(itemName);

        if (item == null) {
            System.out.println("Item '" + itemName + "' not in inventory.");
            return;
        }

        player.dropItem(item);
        currentLocation.addItem(item);
    }

    public void useItem(String itemName) {
        Item item = player.findInventoryItem(itemName);

        if (item == null) {
            System.out.println("Item '" + itemName + "' not in inventory.");
            return;
        }

        // Items have different effects based on type
        String type = item.getType();
        double itemValue = item.getValue();

        if (type.equals("political")) {
            System.out.println("Using " + item.getName() + "...");
            player.updatePopularity(itemValue / 5);
            System.out.println("Popularity increased!");
            player.dropItem(item);
        } else if (type.equals("evidence")) {
            System.out.println("Using " + item.getName() + "...");
            System.out.println("You found incriminating evidence!");
            player.updatePopularity(10);
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
        }
    }

    public void playerGiveSpeech() {
        player.giveSpeech();
        Event event = eventSystem.triggerRandomEvent();
        if (event != null) event.trigger(player);
    }

    public void playerBribe(String opponentName) {
        Opponent opponent = findOpponent(opponentName);
        if (opponent == null) {
            System.out.println("Opponent '" + opponentName + "' not found.");
            return;
        }
        player.bribe(opponent);
    }

    public void playerNegotiateAlliance(String allyName) {
        Ally ally = findAlly(allyName);
        if (ally == null) {
            System.out.println("Ally '" + allyName + "' not found.");
            return;
        }
        player.negotiateAlliance(ally);
        availableAllies.remove(ally);
    }

    public void playerSabotage(String opponentName) {
        Opponent opponent = findOpponent(opponentName);
        if (opponent == null) {
            System.out.println("Opponent '" + opponentName + "' not found.");
            return;
        }
        player.sabotage(opponent);
    }

    public void playerManageMedia() {
        player.manageMedia();
    }

    public void endTurn() {
        currentTurn++;
        System.out.println("\n--- Turn " + currentTurn + " / " + maxTurns + " ---");

        // Check win/lose conditions
        checkGameStatus();

        // Trigger random event occasionally
        if (Math.random() < 0.3) {
            Event event = eventSystem.triggerRandomEvent();
            if (event != null) event.trigger(player);
        }
    }

    private void checkGameStatus() {
        double popularity = player.getPopularity();
        double money = player.getMoney();
        int alliesCount = player.getAlliances().size();

        if (currentTurn >= maxTurns) {
            if (popularity >= WIN_POPULARITY && alliesCount >= 1) {
                System.out.println("\n========== YOU WON THE ELECTION! ==========");
                System.out.println("Final Popularity: " + popularity + "%");
                System.out.println("Allies: " + alliesCount);
                gameRunning = false;
            } else {
                System.out.println("\n========== YOU LOST THE ELECTION! ==========");
                System.out.println("Final Popularity: " + popularity + "%");
                System.out.println("Required: 70% popularity + 1 ally");
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
    }

    public void displayPlayerStats() {
        player.updateStats();
    }

    public void displayInventory() {
        System.out.println("\n--- INVENTORY ---");
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + " (" + item.getType() + ")");
            }
        }
        System.out.println("-----------------\n");
    }

    public void displayCurrentLocation() {
        System.out.println("\n--- " + currentLocation.getName() + " ---");
        System.out.println(currentLocation.getDescription());

        // Show NPCs
        Map<String, String> npcs = currentLocation.getNPCs();
        if (!npcs.isEmpty()) {
            System.out.println("\nPeople here:");
            for (String npcName : npcs.keySet()) {
                String type = npcs.get(npcName);
                System.out.println("- " + npcName + " (" + type + ")");
            }
        }

        // Show items
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
    public void displayHelp() {
        commandProcessor.displayHelp();
    }

    private Opponent findOpponent(String name) {
        for (Opponent o : opponents) {
            if (o.getName().equalsIgnoreCase(name)) {
                return o;
            }
        }
        return null;
    }

    private Ally findAlly(String name) {
        for (Ally a : availableAllies) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public List<Ally> getAvailableAllies() {
        return availableAllies;
    }
}