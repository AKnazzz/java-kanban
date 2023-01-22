package managers;

import tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager { // класс менеджер для описания логики работы с обычными задачами Task
    protected HashMap<Integer, Task> tasks = new HashMap<>(); // мапа для хранения задач [ID] [Task]
    protected Integer taskID = 0;


    public Task makeNewTask(Task task) {                     //  метод для внесения новой задачи
        final Task newTask = new Task(task.getName(), task.getDescription(), ++taskID); // используем конструктор со статусом NEW по умолчанию
        if (!tasks.containsKey(newTask.getId()))
            tasks.put(newTask.getId(), newTask);
        else {
            System.out.println("Задача с таким номером (ID) уже есть в списке.");
            return null;
        }
        return newTask;
    }

    public Task updateTaskById(Task task) {             // метод для изменений имени,описания и статуса задачи по номеру
        final Task originalTask = tasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким номером (ID) не обнаружена в списке.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        return originalTask;
    }


    public ArrayList<Task> getAllTasks() {                  // метод для получения списка всех задач
        return new ArrayList<>(tasks.values());
    }

    
    public void deleteAllTasks() {                           // метод для удаления всех задач из списка задач
        tasks.clear();
    }


    public Task findTaskById(Integer id) {                  // метод для получения task по ID
        return tasks.get(id);
    }

    public Task deleteTaskById(Integer id) {        // метод удаления задачи по номеру ID.
        final Task deletedTask = tasks.get(id);
        tasks.remove(id);
        return deletedTask;
    }
}