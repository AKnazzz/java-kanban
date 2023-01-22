package tasks;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task { // класс для комплексной задач типа Epic
    protected ArrayList<SubTask> subTasks = new ArrayList<>(); // список для подзадач типа SubTask

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasks=" + subTasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}