package tasks;
import java.util.Objects;

public class SubTask extends Task { // класс для подзадачи, которые используются как составные части в Epic
    protected Integer epicID; // параметр, который показывает к какой задаче типа Epic относится SubTask
    public SubTask(String name, String description, Integer id, Integer epicID) {
        super(name, description, id);
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
        if (!(o instanceof SubTask)) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(getEpicID(), subTask.getEpicID());
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicID());
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicID=" + epicID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

}