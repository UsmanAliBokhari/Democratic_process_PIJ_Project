public abstract class Character {
    protected String name;
    protected double popularity;

    public Character(String name, double popularity) {
        this.name = name;
        this.popularity = popularity;
    }

    public void updatePopularity(double amount) {
        this.popularity += amount;
        System.out.println(name + " popularity updated: " + popularity);
    }

    public String getName() {
        return name;
    }
}
