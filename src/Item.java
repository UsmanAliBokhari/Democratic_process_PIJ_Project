public class Item {
    private String name;
    private String description;
    private String type; // political, evidence, tool, valuable, document
    private double value;

    public Item(String name, String description, String type, double value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public void use(Player player) {
        System.out.println(player.getName() + " used item: " + name);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public double getValue() { return value; }
}
