public class Main {
    public static void main(String[] args) {

        Player p = new Player("Refat", CharacterType.OUTSIDER);
        Opponent o = new Opponent("PM ");
        Ally a = new Ally("Local Leader", 60, 10);

        p.giveSpeech();
        p.bribe(o);
        p.negotiateAlliance(a);
        p.updateStats();
    }
}
