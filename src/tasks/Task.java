package tasks;
import java.util.Objects;
import static tasks.StatusMarker.NEW;

public class Task { // класс стандартной задачи
    protected Integer id; // номер задачи
    protected String name; // имя задачи
    protected String description;  // описание к задаче
    protected StatusMarker status; // статус задачи


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
        return id + "," + TypeOfTasks.TASK.name() + "," + name + "," + status + "," + description + ",";

    }

    public void setId(Integer id) {
        this.id = id;
    }
}