public class Ally {
    private String name;
    private double loyalty;
    private double influenceBoost;

    public Ally(String name, double loyalty, double influenceBoost) {
        this.name = name;
        this.loyalty = loyalty;
        this.influenceBoost = influenceBoost;
    }

    public String getName() { return name; }
    public double getLoyalty() { return loyalty; }
    public double getInfluenceBoost() { return influenceBoost; }

    public void updateLoyalty(double amount) {
        loyalty += amount;
        if (loyalty > 100) loyalty = 100;
        if (loyalty < 0) loyalty = 0;
    }
}