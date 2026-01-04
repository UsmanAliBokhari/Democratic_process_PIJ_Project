/**
 * Represents an ally character that can join the player's campaign.
 * Allies provide influence boosts and have loyalty requirements.
 *
 * @author Usman
 * @version 1.0
 */
public class Ally {
    private String name;
    private double loyalty;
    private double influenceBoost;

    /**
     * Constructs a new Ally with specified attributes.
     *
     * @param name the ally's name
     * @param loyalty the loyalty threshold (affects negotiation difficulty)
     * @param influenceBoost the influence gained when allied
     */
    public Ally(String name, double loyalty, double influenceBoost) {
        this.name = name;
        this.loyalty = loyalty;
        this.influenceBoost = influenceBoost;
    }

    /**
     * Gets the ally's name.
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the loyalty value.
     * @return the loyalty value
     */
    public double getLoyalty() { return loyalty; }

    /**
     * Gets the influence boost provided by this ally.
     * @return the influence boost value
     */
    public double getInfluenceBoost() { return influenceBoost; }

    /**
     * Updates the ally's loyalty.
     *
     * @param amount the amount to change loyalty by
     */
    public void updateLoyalty(double amount) {
        loyalty += amount;
        if (loyalty > 100) loyalty = 100;
        if (loyalty < 0) loyalty = 0;
    }
}
