/**
 * Represents an item that can be collected and used by the player.
 * Items have different types and effects.
 *
 * @author Refat
 * @version 1.0
 */
public class Item {
    private String name;
    private String description;
    private String type;
    private double value;

    /**
     * Constructs a new Item with specified properties.
     *
     * @param name the item's name
     * @param description what the item is
     * @param type the item type (political, evidence, tool, valuable, document, consumable)
     * @param value the numerical value/effect of the item
     */
    public Item(String name, String description, String type, double value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    /**
     * Uses the item (basic implementation).
     * Actual use logic is in GameManager.
     *
     * @param player the player using the item
     */
    public void use(Player player) {
        System.out.println(player.getName() + " used item: " + name);
    }

    /**
     * Gets the item's name.
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the item's description.
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Gets the item's type.
     * @return the type
     */
    public String getType() { return type; }

    /**
     * Gets the item's value.
     * @return the value
     */
    public double getValue() { return value; }
}