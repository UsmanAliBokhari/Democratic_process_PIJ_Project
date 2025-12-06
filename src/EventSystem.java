import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventSystem {
    private List<Event> events;
    private Random random;

    public EventSystem() {
        this.events = new ArrayList<>();
        this.random = new Random();
        initializeEvents();
    }

    private void initializeEvents() {
        events.add(new Event("Journalist Discovery",
                "A journalist discovers evidence of bribery", -20, -15));
        events.add(new Event("Public Support",
                "Your policies resonate with the public", 15, 0));
        events.add(new Event("Scandal Exposed",
                "A scandal from your past is exposed", -30, 25));
        events.add(new Event("Ally Betrayal",
                "One of your allies withdraws support", -10, -20));
        events.add(new Event("Media Boost",
                "Positive media coverage increases your popularity", 20, -5));
        events.add(new Event("Funding Received",
                "Anonymous donor provides funds", 50, 0));
    }

    public Event triggerRandomEvent() {
        if (events.isEmpty()) return null;
        return events.get(random.nextInt(events.size()));
    }

    public Event getEventByName(String name) {
        for (Event e : events) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }
}
