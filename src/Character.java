/**
 * Abstract base class for all character entities.
 *
 * @author Abdur Rahim
 * @version 1.0
 */
public abstract class Character {
    protected String name;
    protected double popularity;

    /**
     * Constructs a Character with name and initial popularity.
     *
     * @param name the character's name
     * @param popularity the initial popularity value
     */
    public Character(String name, double popularity) {
        this.name = name;
        this.popularity = popularity;
    }

    /**
     * Updates the character's popularity.
     *
     * @param amount the amount to change popularity by
     */
    public void updatePopularity(double amount) {
        this.popularity += amount;
        System.out.println(name + " popularity updated: " + popularity);
    }

    /**
     * Gets the character's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}