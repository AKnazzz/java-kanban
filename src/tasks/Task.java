package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static tasks.StatusMarker.NEW;

public class Task { // класс стандартной задачи
    protected Integer id; // номер задачи
    protected String name; // имя задачи
    protected String description;  // описание к задаче
    protected StatusMarker status; // статус задачи
    protected Duration duration;   // продолжительность задачи
    protected LocalDateTime startTime; // время начала задачи


    public Task(Integer id, String name, String description) { // конструктор класса для новой задачи
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = NEW;  //  статус -  новая NEW
    }

    public Task(Integer id, String name, String description, StatusMarker status) { // конструктор с указанием статуса
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, StatusMarker status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, StatusMarker status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(Integer id, String name, String description, StatusMarker status, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public StatusMarker getStatus() {
        return status;
    }

    public void setStatus(StatusMarker status) {
        this.status = status;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(getName(), task.getName())
                && Objects.equals(getDescription(), task.getDescription())
                && Objects.equals(getId(), task.getId())
                && Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getId(), getStatus());
    }


    @Override
    public String toString() {
        return id + "," + TypeOfTasks.TASK.name() + "," + name + "," + status + "," + description + "," + "null" + "," + startTime + "," + duration + "," + startTime.plus(duration);

    }

    public void setId(Integer id) {
        this.id = id;
    }


}