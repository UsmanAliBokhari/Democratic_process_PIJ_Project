import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a location in the game world.
 * Locations contain items, NPCs, and connections to other locations.
 *
 * @author Abdur Rahim
 * @version 2.0
 */
public class Location {
    private String name;
    private String description;
    private List<Item> items;
    private Map<String, Location> connections;
    private Map<String, String> npcs;

    /**
     * Constructs a new Location with a name and description.
     *
     * @param name the location's name
     * @param description a description of the location
     */
    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.connections = new HashMap<>();
        this.npcs = new HashMap<>();
    }

    /**
     * Adds an item to this location.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from this location.
     *
     * @param item the item to remove
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Finds an item by name at this location.
     *
     * @param itemName the name of the item to find
     * @return the Item object or null if not found
     */
    public Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Adds an NPC to this location.
     *
     * @param npcName the NPC's name
     * @param type the NPC type (ally, opponent, neutral)
     */
    public void addNPC(String npcName, String type) {
        npcs.put(npcName, type);
    }

    /**
     * Gets an NPC's type.
     *
     * @param npcName the NPC's name
     * @return the type or null if not found
     */
    public String getNPCType(String npcName) {
        return npcs.get(npcName);
    }

    /**
     * Gets all NPCs at this location.
     *
     * @return a new map of all NPCs
     */
    public Map<String, String> getNPCs() {
        return new HashMap<>(npcs);
    }

    /**
     * Sets a directional connection to another location.
     *
     * @param direction the direction (north, south, east, west)
     * @param location the location to connect to
     */
    public void setConnection(String direction, Location location) {
        connections.put(direction.toLowerCase(), location);
    }

    /**
     * Gets the location connected in a specific direction.
     *
     * @param direction the direction to check
     * @return the connected Location or null if none exists
     */
    public Location getConnection(String direction) {
        return connections.get(direction.toLowerCase());
    }

    /**
     * Gets a string listing all available exits.
     *
     * @return comma-separated list of directions or "None"
     */
    public String getExits() {
        if (connections.isEmpty()) return "None";
        return String.join(", ", connections.keySet());
    }

    /**
     * Gets the location's name.
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the location's description.
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Gets all items at this location.
     * @return a new list of all items
     */
    public List<Item> getItems() { return new ArrayList<>(items); }
}