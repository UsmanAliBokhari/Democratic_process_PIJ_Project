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
    public double getInfluenceBoost() { return influenceBoost; }
}
