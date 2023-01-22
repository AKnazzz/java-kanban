package tasks;
import java.util.Objects;
import static tasks.StatusMarker.NEW;

public class Task { // класс стандартной задачи
    protected String name; // имя задачи
    protected String description;  // описание к задаче
    protected Integer id; // номер задачи
    protected String status; // статус задачи

    public Task(String name, String description, Integer id) { // конструктор класса для новой задачи
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = NEW; //  статус -  новая NEW
    }

    public Task(String name, String description, Integer id, String status) { // конструктор с указанием статуса
        this.name = name;
        this.description = description;
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

}