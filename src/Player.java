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
        money -= 20;
        influence += 5;
        target.loseSupport();
    }

    public void negotiateAlliance(Ally target) {
        System.out.println(name + " is negotiating alliance with " + target.getName());
        alliances.add(target);
        influence += target.getInfluenceBoost();
    }

    public void giveSpeech() {
        System.out.println(name + " gives a powerful speech.");
        popularity += 10;
    }

    public void sabotage(Opponent opponent) {
        System.out.println(name + " sabotages " + opponent.getName());
        opponent.reactToSabotage();
        scandalRisk += 15;
    }

    public void manageMedia() {
        System.out.println(name + " is managing the media...");
        popularity += 5;
        scandalRisk -= 3;
    }


    public void takeItem(Item item) {
        inventory.add(item);
        System.out.println(name + " picked up " + item.getName());
    }

    public void dropItem(Item item) {
        inventory.remove(item);
        System.out.println(name + " dropped " + item.getName());
    }

    public void useItem(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                i.use(this);
                return;
            }
        }
        System.out.println("Item not found: " + itemName);
    }

    public void updateStats() {
        System.out.println("Updating stats...");
        System.out.println("Money: " + money);
        System.out.println("Popularity: " + popularity);
        System.out.println("Influence: " + influence);
        System.out.println("Scandal Risk: " + scandalRisk);
    }

    public String getName() {
        return name;
    }
}
