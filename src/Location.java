import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private String description;
    List<Item> items = new ArrayList<>();

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
