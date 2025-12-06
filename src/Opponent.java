public class Opponent {
    private String name;
    private double popularity;
    private double defenseLevel;

    public Opponent(String name) {
        this.name = name;
        this.popularity = 40;
        this.defenseLevel = 10;
    }

    public String getName() { return name; }
    public double getPopularity() { return popularity; }
    public double getDefenseLevel() { return defenseLevel; }

    public void reactToSabotage() {
        System.out.println(name + " is reacting to sabotage!");
        popularity -= 5;
        if (popularity < 0) popularity = 0;
    }

    public void loseSupport() {
        System.out.println(name + " loses some support.");
        popularity -= 3;
        if (popularity < 0) popularity = 0;
    }

    public void updatePopularity(double amount) {
        popularity += amount;
        if (popularity > 100) popularity = 100;
        if (popularity < 0) popularity = 0;
    }
}