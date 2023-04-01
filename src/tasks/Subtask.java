package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task { // класс для подзадачи, которые используются как составные части в Epic
    protected Integer epicID; // параметр, который показывает к какой задаче типа Epic относится Subtask

    public Subtask(Integer id, String name, String description, Integer epicID) {
        super(id, name, description);
        this.epicID = epicID;
    }

    public Subtask(Integer id, String name, String description, StatusMarker status, Integer epicID) {
        super(id, name, description, status);
        this.epicID = epicID;
    }

    public Subtask(String name, String description, StatusMarker status, Integer epicID) {
        super(name, description, status);
        this.epicID = epicID;
    }

    public Subtask(String name, String description, StatusMarker status, Duration duration, LocalDateTime startTime, Integer epicID) {
        super(name, description, status, duration, startTime);
        this.epicID = epicID;
    }

    public Subtask(Integer id, String name, String description, StatusMarker status, Duration duration, LocalDateTime startTime, Integer epicID) {
        super(id, name, description, status, duration, startTime);
        this.epicID = epicID;
    }

    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(Integer epicID) { // метод для изменения ID Epic к которому привязана Subtask
        this.epicID = epicID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        if (!super.equals(o)) return false;
        Subtask subTask = (Subtask) o;
        return Objects.equals(getEpicID(), subTask.getEpicID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicID());
    }

    @Override
    public String toString() {
        return id + "," + TypeOfTasks.SUBTASK.name() + "," + name + "," + status + "," + description + "," + epicID + "," + startTime + "," + duration + "," + startTime.plus(duration);

    }

}