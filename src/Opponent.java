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

    public void reactToSabotage() {
        System.out.println(name + " is reacting to sabotage!");
        popularity -= 5;
    }

    public void loseSupport() {
        System.out.println(name + " loses some support.");
        popularity -= 3;
    }
}
