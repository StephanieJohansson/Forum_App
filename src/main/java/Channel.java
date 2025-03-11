import java.util.ArrayList;
import java.util.List;

public class Channel {
    private Long id;
    private String name;
    private List<String> messages = new ArrayList<>();

    //constructor, getters och setters
    public Channel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
