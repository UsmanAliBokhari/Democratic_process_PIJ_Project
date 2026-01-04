/**
 * Represents an opponent in the political campaign.
 * Opponents compete for popularity and have defense against player actions.
 *
 * @author Abdur Rahim
 * @version 1.0
 */
public class Opponent {
    private String name;
    private double popularity;
    private double defenseLevel;

    /**
     * Constructs a new Opponent with default stats.
     *
     * @param name the opponent's name
     */
    public Opponent(String name) {
        this.name = name;
        this.popularity = 45;
        this.defenseLevel = 15;
    }

    /**
     * Gets the opponent's name.
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the opponent's popularity.
     * @return the popularity value
     */
    public double getPopularity() { return popularity; }

    /**
     * Gets the opponent's defense level.
     * @return the defense level
     */
    public double getDefenseLevel() { return defenseLevel; }

    /**
     * Makes the opponent react to sabotage attempts.
     */
    public void reactToSabotage() {
        System.out.println(name + " is reacting to sabotage!");
        popularity -= 10;
        if (popularity < 0) popularity = 0;
    }

    /**
     * Makes the opponent lose support (from bribes or other actions).
     */
    public void loseSupport() {
        System.out.println(name + " loses support.");
        popularity -= 5;
        if (popularity < 0) popularity = 0;
    }

    /**
     * Updates the opponent's popularity.
     *
     * @param amount the amount to change popularity by
     */
    public void updatePopularity(double amount) {
        popularity += amount;
        if (popularity > 100) popularity = 100;
        if (popularity < 0) popularity = 0;
    }
}