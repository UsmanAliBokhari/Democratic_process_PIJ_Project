import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages random events that can occur during the game.
 * Events affect the player's stats and add unpredictability.
 *
 * @author Usman
 * @version 1.0
 */
public class EventSystem {
    private List<Event> events;
    private Random random;

    /**
     * Constructs a new EventSystem and initializes all possible events.
     */
    public EventSystem() {
        this.events = new ArrayList<>();
        this.random = new Random();
        initializeEvents();
    }

    /**
     * Initializes all game events with their effects.
     */
    private void initializeEvents() {
        events.add(new Event("Journalist Discovery",
                "A journalist discovers evidence of questionable dealings", -15, -10));
        events.add(new Event("Public Support",
                "Your policies resonate with the public", 12, 0));
        events.add(new Event("Scandal Exposed",
                "A scandal from your past is exposed", -25, 20));
        events.add(new Event("Ally Betrayal",
                "One of your allies withdraws support", -8, -15));
        events.add(new Event("Media Boost",
                "Positive media coverage increases your popularity", 18, -3));
        events.add(new Event("Funding Received",
                "Anonymous donor provides funds", 40, 5));
        events.add(new Event("Opponent Scandal",
                "Your opponent is caught in a scandal", 15, 0));
        events.add(new Event("Economic Crisis",
                "Economic downturn affects campaign funding", -30, 0));
        events.add(new Event("Viral Speech",
                "Your speech goes viral on social media", 20, 0));
        events.add(new Event("Debate Win",
                "You dominate in the televised debate", 16, -5));
        events.add(new Event("Policy Backlash",
                "One of your policies receives harsh criticism", -12, 8));
        events.add(new Event("Grassroots Support",
                "Grassroots movement rallies behind you", 10, -3));
    }

    /**
     * Triggers a random event from the available events.
     *
     * @return a randomly selected Event or null if no events exist
     */
    public Event triggerRandomEvent() {
        if (events.isEmpty()) return null;
        return events.get(random.nextInt(events.size()));
    }

    /**
     * Gets a specific event by name.
     *
     * @param name the name of the event
     * @return the Event object or null if not found
     */
    public Event getEventByName(String name) {
        for (Event e : events) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Gets all available events.
     *
     * @return a new list containing all events
     */
    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }
}