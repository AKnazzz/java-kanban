package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {

    ArrayList<Task> getAllTasks();          // 1. метод для получения списка всех TASK

    void deleteAllTasks();                  // 2. метод для удаления всех задач из списка задач TASK

    Task getTaskById(Integer id);          // 3. метод для получения TASK по идентификатору ID

    Task createNewTask(Task task);            //  4. метод для создания новой задачи типа (TASK)

    Task updateTaskById(Task task);         // 5. метод для обновления имени, описания и статуса (TASK) по (ID)

    Task deleteTaskById(Integer id);        // 6. метод удаления TASK по номеру идентификатору ID.



    //  методы для EPIC = 7 шт

    ArrayList<Epic> getAllEpics();          // 1. метод для получения списка всех сложных задач EPIC

    void deleteAllEpics();                  // 2. метод для удаления всех значений из списка сложных задач EPIC

    Epic getEpicById(Integer id);          // 3. метод для получения EPIC по ID

    Epic createNewEpic(Epic task);            // 4.метод для создания новой сложной задачи (EPIC)

    Epic updateEpic(Epic epic);             // 5. метод для изменений имени и описания сложной задачи (EPIC) по номеру

     void deleteEpicById (Integer id);      // 6. метод для удаления сложной задачи EPIC по номеру.

    ArrayList<Subtask> findAllOfEpic(Epic epic);   // 7. метод получения списка всех SUBTASK определённого Epic.



    //  методы для SUBTASK = 6 шт

    ArrayList<Subtask> getAllSubtasks();    // 1. метод для получения списка всех SUBTASK

    void deleteAllSubTasks();               // 2. метод для удаления всех SUBTASK

    Subtask getSubtaskById(Integer id);     // 3. метод получения SUBTASK по идентификатору ID

    Subtask crateNewSubTask(Subtask subTask, Epic epic);     // 4. метод для создания подзадачи SUBTASK

    Subtask updateSubTaskById(Subtask task);                // 5-1. метод для обновления Subtask по номеру ID

    void setNewStatus(Subtask task);        // 5-2. метод обновления статуса Epic в зависимости от статуса подзадач

    Subtask deleteSubTaskById(Integer id);  // 6. метод для удаления SUBTASK по идентификатору ID.


}
