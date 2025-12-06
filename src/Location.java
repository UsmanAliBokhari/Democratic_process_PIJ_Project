import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {
    private String name;
    private String description;
    private List<Item> items;
    private Map<String, Location> connections; // north, south, east, west
    private Map<String, String> npcs; // npc name -> type (opponent/ally)

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.connections = new HashMap<>();
        this.npcs = new HashMap<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void addNPC(String npcName, String type) {
        npcs.put(npcName, type);
    }

    public String getNPCType(String npcName) {
        return npcs.get(npcName);
    }

    public Map<String, String> getNPCs() {
        return new HashMap<>(npcs);
    }

    public void setConnection(String direction, Location location) {
        connections.put(direction.toLowerCase(), location);
    }

    public Location getConnection(String direction) {
        return connections.get(direction.toLowerCase());
    }

    public String getExits() {
        if (connections.isEmpty()) return "None";
        return String.join(", ", connections.keySet());
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Item> getItems() { return new ArrayList<>(items); }
}