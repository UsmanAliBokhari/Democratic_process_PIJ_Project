import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player character in the game.
 * Manages player statistics, inventory, alliances, and actions.
 *
 * @author Usman
 * @version 12
 */
public class Player {
    private String name;
    private CharacterType type;
    private double money;
    private double popularity;
    private double influence;
    private double scandalRisk;

    private List<Ally> alliances;
    private List<Item> inventory;
    private Location currentLocation;

    /**
     * Constructs a new Player with specified name and character type.
     * Initializes starting stats based on character type.
     *
     * @param name the player's name
     * @param type the character type affecting initial stats
     */
    public Player(String name, CharacterType type) {
        this.name = name;
        this.type = type;
        this.alliances = new ArrayList<>();
        this.inventory = new ArrayList<>();

        // Different starting stats based on character type (increased difficulty)
        switch (type) {
            case BUSINESS_TYCOON:
                this.money = 150.0;
                this.popularity = 35.0;
                this.influence = 25.0;
                this.scandalRisk = 10.0;
                break;
            case CAREER_POLITICIAN:
                this.money = 100.0;
                this.popularity = 45.0;
                this.influence = 30.0;
                this.scandalRisk = 20.0;
                break;
            case OUTSIDER:
                this.money = 80.0;
                this.popularity = 40.0;
                this.influence = 15.0;
                this.scandalRisk = 5.0;
                break;
            case MAFIA_LEADER:
                this.money = 200.0;
                this.popularity = 25.0;
                this.influence = 35.0;
                this.scandalRisk = 40.0;
                break;
            default:
                this.money = 100.0;
                this.popularity = 40.0;
                this.influence = 20.0;
                this.scandalRisk = 10.0;
        }
    }

    /**
     * Attempts to bribe an opponent to reduce their support.
     * Costs money and increases scandal risk.
     *
     * @param target the opponent to bribe
     */
    public void bribe(Opponent target) {
        System.out.println(name + " is attempting to bribe " + target.getName());
        double bribeCost = 25 + (Math.random() * 15); // 25-40 dollars

        if (money >= bribeCost) {
            money -= bribeCost;

            // Success chance based on opponent's defense and your influence
            double successChance = 0.6 + (influence / 200.0) - (target.getDefenseLevel() / 100.0);

            if (Math.random() < successChance) {
                influence += 3;
                target.loseSupport();
                scandalRisk += 10;
                System.out.println("Bribe successful! Cost: $" + String.format("%.2f", bribeCost));
                System.out.println("Warning: Scandal risk increased!");
            } else {
                System.out.println("Bribe failed! " + target.getName() + " rejected your offer.");
                System.out.println("Cost: $" + String.format("%.2f", bribeCost));
                scandalRisk += 20;
                popularity -= 30;
                System.out.println("Your reputation took a hit! Scandal risk significantly increased!");
            }
        } else {
            System.out.println("Not enough money to bribe! Need: $" + String.format("%.2f", bribeCost));
        }
    }

    /**
     * Negotiates an alliance with an ally character.
     * Requires sufficient influence and increases overall influence.
     *
     * @param target the ally to negotiate with
     */
    public void negotiateAlliance(Ally target) {
        System.out.println(name + " is negotiating alliance with " + target.getName());

        if (alliances.contains(target)) {
            System.out.println("Already allied with " + target.getName());
            return;
        }

        // Alliance success based on influence vs loyalty requirement
        double successChance = influence / (target.getLoyalty() + 10);

        if (Math.random() < successChance) {
            alliances.add(target);
            influence += target.getInfluenceBoost();
            System.out.println("Alliance formed! Influence increased by " + target.getInfluenceBoost());
        } else {
            System.out.println("Alliance negotiation failed! " + target.getName() + " needs more convincing.");
            System.out.println("Hint: Increase your influence before trying again.");
        }
    }

    /**
     * Gives a campaign speech to increase popularity.
     * Costs money and has variable effectiveness.
     * WARNING: Speeches can backfire and hurt your popularity!
     */
    public void giveSpeech() {
        System.out.println(name + " gives a campaign speech.");

        double speechCost = 15.0;
        if (money < speechCost) {
            System.out.println("Not enough money to organize a speech event! Need: $" + speechCost);
            return;
        }

        money -= speechCost;

        // 20% chance speech backfires
        if (Math.random() < 0.20) {
            double damage = 5 + (Math.random() * 15); // 5-20 popularity loss
            popularity -= damage;
            if (popularity < 0) popularity = 0;

            String[] backfireReasons = {
                    "You stumbled over your words and appeared unprepared!",
                    "A heckler disrupted your speech and you lost your composure!",
                    "Your speech contradicted previous statements - the media noticed!",
                    "Technical difficulties made you look incompetent!",
                    "You accidentally insulted a key demographic!",
                    "Your speech was boring and people walked out!"
            };

            System.out.println("\n*** SPEECH BACKFIRED! ***");
            System.out.println(backfireReasons[(int)(Math.random() * backfireReasons.length)]);
            System.out.println("Popularity decreased by " + String.format("%.1f", damage) + "!");
            scandalRisk += 5;
            System.out.println("Scandal risk increased by 5");
        } else {
            // Speech effectiveness varies
            double effectiveness = 10 + (Math.random() * 15); // 10-25 popularity
            popularity += effectiveness;

            System.out.println("The speech was well received!");
            System.out.println("Popularity increased by " + String.format("%.1f", effectiveness) + "!");
        }

        System.out.println("Speech cost: $" + speechCost);
    }

    /**
     * Sabotages an opponent's campaign.
     * Risky action that increases scandal risk significantly.
     *
     * @param opponent the opponent to sabotage
     */
    public void sabotage(Opponent opponent) {
        System.out.println(name + " attempts to sabotage " + opponent.getName());

        double sabotageCost = 30.0;
        if (money < sabotageCost) {
            System.out.println("Not enough money to execute sabotage! Need: $" + sabotageCost);
            return;
        }

        money -= sabotageCost;

        // Sabotage success chance
        double successChance = 0.5 + (influence / 150.0);

        if (Math.random() < successChance) {
            opponent.reactToSabotage();
            scandalRisk += 25;
            popularity += 5;
            System.out.println("Sabotage successful! Cost: $" + sabotageCost);
        } else {
            scandalRisk += 40;
            popularity -= 20;
            System.out.println("Sabotage backfired! You've been exposed!");
            System.out.println("Cost: $" + sabotageCost);
        }

        if (scandalRisk > 100) scandalRisk = 100;
        System.out.println("Scandal risk: " + String.format("%.1f", scandalRisk));
    }

    /**
     * Manages media narrative to improve image and reduce scandal risk.
     * Costs money but helps maintain reputation.
     */
    public void manageMedia() {
        System.out.println(name + " is managing the media...");

        double mediaCost = 15.0;
        if (money >= mediaCost) {
            money -= mediaCost;
            popularity += 4;
            scandalRisk -= 8;
            if (scandalRisk < 0) scandalRisk = 0;
            System.out.println("Media managed successfully!");
            System.out.println("Cost: $" + mediaCost + " | Popularity +4 | Scandal Risk -8");
        } else {
            System.out.println("Not enough money to manage media! Need: $" + mediaCost);
        }
    }

    /**
     * Picks up an item and adds it to inventory.
     *
     * @param item the item to take
     */
    public void takeItem(Item item) {
        if (inventory.size() >= 10) {
            System.out.println("Inventory full! Drop something first.");
            return;
        }
        inventory.add(item);
        System.out.println(name + " picked up " + item.getName());
    }

    /**
     * Drops an item from inventory.
     *
     * @param item the item to drop
     */
    public void dropItem(Item item) {
        inventory.remove(item);
        System.out.println(name + " dropped " + item.getName());
    }

    /**
     * Finds an item in the player's inventory by name.
     *
     * @param itemName the name of the item to find
     * @return the Item object or null if not found
     */
    public Item findInventoryItem(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Uses an item from inventory.
     *
     * @param itemName the name of the item to use
     */
    public void useItem(String itemName) {
        Item item = findInventoryItem(itemName);
        if (item != null) {
            item.use(this);
        } else {
            System.out.println("Item not found: " + itemName);
        }
    }

    /**
     * Updates the player's popularity stat.
     *
     * @param amount the amount to change popularity by
     */
    public void updatePopularity(double amount) {
        this.popularity += amount;
        if (popularity > 100) popularity = 100;
        if (popularity < 0) popularity = 0;
        System.out.println(name + " popularity: " + String.format("%.1f", popularity) + "%");
    }

    /**
     * Adds or removes money from the player's funds.
     *
     * @param amount the amount to add (positive) or subtract (negative)
     */
    public void addMoney(double amount) {
        this.money += amount;
        if (money < 0) money = 0;
    }

    /**
     * Displays the player's current statistics.
     */
    public void updateStats() {
        System.out.println("\n========== STATS ==========");
        System.out.println("Name: " + name);
        System.out.println("Character Type: " + type);
        System.out.println("Money: $" + String.format("%.2f", money));
        System.out.println("Popularity: " + String.format("%.1f", popularity) + "%");
        System.out.println("Influence: " + String.format("%.1f", influence));
        System.out.println("Scandal Risk: " + String.format("%.1f", scandalRisk) + "%");
        System.out.println("Allies: " + alliances.size());

        if (!alliances.isEmpty()) {
            System.out.println("\nCurrent Allies:");
            for (Ally ally : alliances) {
                System.out.println("  - " + ally.getName() + " (Influence: +" + ally.getInfluenceBoost() + ")");
            }
        }
        System.out.println("===========================\n");
    }

    // Getters
    /**
     * Gets the player's name.
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the player's character type.
     * @return the CharacterType
     */
    public CharacterType getType() { return type; }

    /**
     * Gets the player's current money.
     * @return the money amount
     */
    public double getMoney() { return money; }

    /**
     * Gets the player's current popularity.
     * @return the popularity percentage
     */
    public double getPopularity() { return popularity; }

    /**
     * Gets the player's current influence.
     * @return the influence value
     */
    public double getInfluence() { return influence; }

    /**
     * Gets the player's current scandal risk.
     * @return the scandal risk percentage
     */
    public double getScandalRisk() { return scandalRisk; }

    /**
     * Gets the list of allied characters.
     * @return a new ArrayList containing all allies
     */
    public List<Ally> getAlliances() { return new ArrayList<>(alliances); }

    /**
     * Gets the player's inventory.
     * @return a new ArrayList containing all items
     */
    public List<Item> getInventory() { return new ArrayList<>(inventory); }

    /**
     * Gets the player's current location.
     * @return the current Location
     */
    public Location getCurrentLocation() { return currentLocation; }

    /**
     * Sets the player's current location.
     * @param location the new location
     */
    public void setCurrentLocation(Location location) { this.currentLocation = location; }
}