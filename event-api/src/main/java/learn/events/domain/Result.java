package learn.events.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result<T> {

    private T payload;
    private final ArrayList<String> messages = new ArrayList<>();

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public void addErrorMessage(String message) {
        messages.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(payload, result.payload) && Objects.equals(messages, result.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, messages);
    }
}

