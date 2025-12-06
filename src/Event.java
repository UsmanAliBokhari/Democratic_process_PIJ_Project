public class Event {
    private String name;
    private String description;
    private double popularityImpact;
    private double scandalRiskImpact;

    public Event(String name, String description,
                 double popularityImpact, double scandalRiskImpact) {
        this.name = name;
        this.description = description;
        this.popularityImpact = popularityImpact;
        this.scandalRiskImpact = scandalRiskImpact;
    }

    public void trigger(Player player) {
        System.out.println("\n=== EVENT TRIGGERED ===");
        System.out.println(name + ": " + description);
        player.updatePopularity(popularityImpact);
        if (scandalRiskImpact != 0) {
            // Assume Player has method to update scandal risk
            System.out.println("Scandal risk changed by: " + scandalRiskImpact);
        }
        System.out.println("=======================\n");
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPopularityImpact() { return popularityImpact; }
    public double getScandalRiskImpact() { return scandalRiskImpact; }
}