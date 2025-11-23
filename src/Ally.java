public class Ally extends Character {
    private double loyalty;
    private double influenceBoost;

    public Ally(String name, double popularity, double loyalty, double influenceBoost) {
        super(name, popularity);
        this.loyalty = loyalty;
        this.influenceBoost = influenceBoost;
    }

    public void supportPlayer(Player player) {
        System.out.println(name + " supports " + player.getName());
        player.increasePopularity(influenceBoost);
    }

    public void breakAlliance() {
        System.out.println(name + " has broken the alliance!");
    }
}