package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task { // класс для комплексной задач типа Epic
    protected final ArrayList<Subtask> Subtasks = new ArrayList<>(); // список для подзадач типа Subtask

    protected LocalDateTime endTime;

    public Epic(Integer id, String name, String description) {
        super(id, name, description);
    }

    public Epic(Integer id, String name, String description, StatusMarker status) {
        super(id, name, description, status);
    }

    public Epic(String name, String description, StatusMarker status) {
        super(name, description, status);
    }

    public Epic(String name, String description, StatusMarker status, Duration duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(name, description, status, duration, startTime);
        this.endTime = endTime;
    }

    public Epic(Integer id, String name, String description, StatusMarker status, Duration duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(id, name, description, status, duration, startTime);
        this.endTime = endTime;
    }

    public Epic(String name, String description, StatusMarker status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
    }

    public ArrayList<Subtask> getSubTasks() {
        return Subtasks;
    }


    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(Subtasks, epic.Subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Subtasks);
    }

    @Override
    public String toString() {
        return id + "," + TypeOfTasks.EPIC.name() + "," + name + "," + status + "," + description + "," + "null" + "," + startTime + "," + duration + "," + endTime + ",";

    }

}