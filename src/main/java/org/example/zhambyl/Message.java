package org.example.zhambyl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/***
 * Message represents a message between two Players
 */
public class Message {

    private final String from;
    private final String to;
    private final String message;

    @JsonCreator
    public Message(@JsonProperty("from") String from, @JsonProperty("to") String to, @JsonProperty("message") String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public Message append(Long counter) {
        return new Message(this.from, this.to, this.message + counter);
    }

    public String getMessage() {
        return message;
    }

    public String getTo() { return to;}

    public String getFrom() { return from;}
}
