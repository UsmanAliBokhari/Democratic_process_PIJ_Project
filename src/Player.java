import java.util.ArrayList;
import java.util.List;

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

    public Player(String name, CharacterType type) {
        this.name = name;
        this.type = type;
        this.money = 100.0;
        this.popularity = 50.0;
        this.influence = 20.0;
        this.scandalRisk = 0.0;

        this.alliances = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    public void bribe(Opponent target) {
        System.out.println(name + " is bribing " + target.getName());
        if (money >= 20) {
            money -= 20;
            influence += 5;
            target.loseSupport();
        } else {
            System.out.println("Not enough money to bribe!");
        }
    }

    public void negotiateAlliance(Ally target) {
        System.out.println(name + " is negotiating alliance with " + target.getName());
        if (!alliances.contains(target)) {
            alliances.add(target);
            influence += target.getInfluenceBoost();
            System.out.println("Alliance formed! Influence increased by " + target.getInfluenceBoost());
        } else {
            System.out.println("Already allied with " + target.getName());
        }
    }

    public void giveSpeech() {
        System.out.println(name + " gives a powerful speech.");
        popularity += 10;
        System.out.println("Popularity increased by 10!");
    }

    public void sabotage(Opponent opponent) {
        System.out.println(name + " sabotages " + opponent.getName());
        opponent.reactToSabotage();
        scandalRisk += 15;
        if (scandalRisk > 100) scandalRisk = 100;
        System.out.println("Scandal risk increased to " + scandalRisk);
    }

    public void manageMedia() {
        System.out.println(name + " is managing the media...");
        if (money >= 10) {
            money -= 10;
            popularity += 5;
            scandalRisk -= 3;
            if (scandalRisk < 0) scandalRisk = 0;
            System.out.println("Media managed successfully! Popularity +5, Scandal Risk -3");
        } else {
            System.out.println("Not enough money to manage media!");
        }
    }

    public void takeItem(Item item) {
        inventory.add(item);
        System.out.println(name + " picked up " + item.getName());
    }

    public void dropItem(Item item) {
        inventory.remove(item);
        System.out.println(name + " dropped " + item.getName());
    }

    public Item findInventoryItem(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return null;
    }

    public void useItem(String itemName) {
        Item item = findInventoryItem(itemName);
        if (item != null) {
            item.use(this);
        } else {
            System.out.println("Item not found: " + itemName);
        }
    }

    public void updatePopularity(double amount) {
        this.popularity += amount;
        if (popularity > 100) popularity = 100;
        if (popularity < 0) popularity = 0;
        System.out.println(name + " popularity updated: " + popularity);
    }

    public void addMoney(double amount) {
        this.money += amount;
    }

    public void updateStats() {
        System.out.println("\n========== STATS ==========");
        System.out.println("Name: " + name);
        System.out.println("Character Type: " + type);
        System.out.println("Money: $" + String.format("%.2f", money));
        System.out.println("Popularity: " + String.format("%.1f", popularity) + "%");
        System.out.println("Influence: " + String.format("%.1f", influence));
        System.out.println("Scandal Risk: " + String.format("%.1f", scandalRisk));
        System.out.println("Allies: " + alliances.size());
        System.out.println("===========================\n");
    }

    // Getters
    public String getName() { return name; }
    public CharacterType getType() { return type; }
    public double getMoney() { return money; }
    public double getPopularity() { return popularity; }
    public double getInfluence() { return influence; }
    public double getScandalRisk() { return scandalRisk; }
    public List<Ally> getAlliances() { return new ArrayList<>(alliances); }
    public List<Item> getInventory() { return new ArrayList<>(inventory); }
    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location location) { this.currentLocation = location; }
}