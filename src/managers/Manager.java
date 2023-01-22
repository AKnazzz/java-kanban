package managers;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;

public class Manager { // класс для описания логики взаимодействия между классами менеджеров TaskManager, EpicManager, SubtaskManager

    TaskManager taskManager = new TaskManager();
    EpicManager epicManager = new EpicManager();
    SubtaskManager subTaskManager = new SubtaskManager(epicManager);



    public ArrayList<Task> getAllTasks() {         //    метод для получения списка всех задач TASK
        return taskManager.getAllTasks();
    }

    public ArrayList<Epic> getAllEpics() {         //    метод для получения списка всех составных задач EPIC
        return epicManager.getAllEpics();
    }

    public ArrayList<SubTask> findAllSubTasksOfEpic(Epic epic) {    // метод для получения списка всех SUBTASK конкретного EPIC
        return subTaskManager.findAllOfEpic(epic);
    }

    public void deleteAllTask() {                   // метод для удаления всех задач TASK
        taskManager.deleteAllTasks();
    }

    public void deleteAllSubTasks() {       // метод для удаления всех подзадач SUBTASK
        subTaskManager.deleteAllSubTasks();
    }

    public void deleteAllEpics() {      // метод для удаления всех составных задач EPIC
        epicManager.deleteAllEpics();
    }


    public SubTask findSubTaskById(Integer id) { // метод для получения SUBTASK по номеру ID
        return subTaskManager.findSubTAskById(id);
    }

    public Task findTaskById(Integer id) {      // метод для получения TASK по номеру ID
        return taskManager.findTaskById(id);
    }

    public Epic findEpicById(Integer id) {      // метод для получения EPIC по номеру ID
        return epicManager.findEpicById(id);
    }

    public Task makeNewTask(Task task) {         // метод для добавления TASK
        return taskManager.makeNewTask(task);
    }

    public SubTask makeNewSubTask(SubTask subTask, Epic epic) {  // метод для добавления SUBTASK в Epic
        return subTaskManager.makeNewSubTask(subTask, epic);
    }

    public Epic makeNewEpic(Epic epic) {             // метод для добавления EPIC
        return epicManager.makeNewEpic(epic);
    }

    public Task updateTask(Task task) {         // метод для изменения TASK
        return taskManager.updateTaskById(task);
    }

    public SubTask updateSubTask(SubTask subTask) { // метод для изменения SUBTASK
        return subTaskManager.updateSubTaskById(subTask);
    }

    public Task updateEpicByID(Epic epic) {             // метод для обновления EPIC
        return epicManager.updateEpic(epic);
    }

    public void deleteSubTaskById(Integer id) {         // метод для удаления SUBTASK по ID
        subTaskManager.deleteSubTaskById(id);
    }

    public void deleteEpicById(Integer id) {            // метод для удаления EPIC по ID
        epicManager.deleteEpicById(id);
    }

    public Task deleteTaskById(Integer id) {            // метод для удаления TASK по ID
        return taskManager.deleteTaskById(id);
    }
}