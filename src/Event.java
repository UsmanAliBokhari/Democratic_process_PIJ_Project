/**
 * Represents a random game event with effects on player stats.
 *
 * @author Refat
 * @version 1.0
 */
public class Event {
    private String name;
    private String description;
    private double popularityImpact;
    private double scandalRiskImpact;

    /**
     * Constructs a new Event with specified effects.
     *
     * @param name the name of the event
     * @param description a description of what happens
     * @param popularityImpact the change to player popularity
     * @param scandalRiskImpact the change to scandal risk
     */
    public Event(String name, String description,
                 double popularityImpact, double scandalRiskImpact) {
        this.name = name;
        this.description = description;
        this.popularityImpact = popularityImpact;
        this.scandalRiskImpact = scandalRiskImpact;
    }

    /**
     * Triggers this event and applies its effects to the player.
     *
     * @param player the player to affect
     */
    public void trigger(Player player) {
        System.out.println("\n=== EVENT TRIGGERED ===");
        System.out.println(name + ": " + description);

        if (popularityImpact != 0) {
            player.updatePopularity(popularityImpact);
        }

        if (scandalRiskImpact > 0) {
            System.out.println("Scandal risk increased by: " + scandalRiskImpact);
        } else if (scandalRiskImpact < 0) {
            System.out.println("Scandal risk decreased by: " + Math.abs(scandalRiskImpact));
        }

        System.out.println("=======================\n");
    }

    /**
     * Gets the event name.
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the event description.
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Gets the popularity impact.
     * @return the popularity impact value
     */
    public double getPopularityImpact() { return popularityImpact; }

    /**
     * Gets the scandal risk impact.
     * @return the scandal risk impact value
     */
    public double getScandalRiskImpact() { return scandalRiskImpact; }
}