package tasks;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task { // класс для комплексной задач типа Epic
    protected ArrayList<Subtask> Subtasks = new ArrayList<>(); // список для подзадач типа Subtask

    public Epic(Integer id , String name, String description) {
        super(id, name, description);
    }

    public ArrayList<Subtask> getSubTasks() {
        return Subtasks;
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
        return "Epic{" +
                "Subtasks=" + Subtasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}