package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tasks.StatusMarker.*;


public class InMemoryTaskManager implements TaskManager { // класс менеджер для описания логики работы с обычными задачами Task

    protected Map<Integer, Task> tasks = new HashMap<>(); // мапа для хранения задач [ID] [Task]
    protected Map<Integer, Epic> epics = new HashMap<>(); // мапа для хранения задач [ID] [Epic]
    protected Map<Integer, Subtask> subtasks = new HashMap<>(); // мапа для хранения задач [ID] [subTask]
    protected Integer taskID = 0;

    protected HistoryManager historyManager = Managers.getDefaultHistory();


    //  методы для TASK = 6 шт

    @Override
    public List<Task> getAllTasks() {                  // 1. метод для получения списка всех TASK
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {                          // 2. метод для удаления всех задач из списка задач TASK
        tasks.clear();
    }

    @Override
    public Task getTaskById(Integer id) {                  // 3. метод для получения TASK по идентификатору ID
        historyManager.add(tasks.get(id));                      // добавляем в список с историей просмотров
        return tasks.get(id);
    }

    @Override
    public int createNewTask(Task task) {                    //  4. метод для создания новой задачи типа (TASK)
        task.setId(++taskID);
        tasks.put(task.getId(), task);
        return task.getId();
    }


    @Override
    public Task updateTaskById(Task task) {            // 5. метод для обновления имени, описания и статуса (TASK) по (ID)
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

    @Override
    public Task deleteTaskById(Integer id) {        // 6. метод удаления TASK по номеру идентификатору ID.

        final Task deletedTask = tasks.get(id);
        historyManager.remove(id);
        tasks.remove(id);
        return deletedTask;

    }


    //  методы для EPIC = 7 шт

    @Override
    public List<Epic> getAllEpics() {              // 1. метод для получения списка всех сложных задач EPIC
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() {            // 2. метод для удаления всех значений из списка сложных задач EPIC
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpicById(Integer id) {      // 3. метод для получения EPIC по ID
        historyManager.add(epics.get(id));          // добавляем в список с историей просмотров
        return epics.get(id);
    }

    @Override
    public int createNewEpic(Epic epic) {             //    4.метод для создания новой сложной задачи (EPIC)
        epic.setId(++taskID);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public Epic updateEpic(Epic epic) {    // 5. метод для изменений имени и описания сложной задачи (EPIC) по номеру
        final Epic originalTask = epics.get(epic.getId());
        if (originalTask == null) {
            System.out.println("Составной задачи с таким номером ID не существует.");
            return null;
        }
        originalTask.setDescription(epic.getDescription());
        originalTask.setName(epic.getName());
        return originalTask;
    }

    @Override
    public void deleteEpicById(Integer id) {                 // 6. метод для удаления сложной задачи EPIC по номеру.

        for (Subtask subtask : findAllOfEpic(getEpicById(id))) {
            if (subtask.getEpicID() == id) {
                subtasks.remove(subtask.getId());
                historyManager.remove(subtask.getId());
            }
        }

        historyManager.remove(id);
        epics.get(id).getSubTasks().clear();
        epics.remove(id);
    }

    @Override
    public List<Subtask> findAllOfEpic(Epic epic) {     // 7. метод получения списка всех SUBTASK определённого Epic.
        return epics.get(epic.getId()).getSubTasks();
    }


    //  методы для SUBTASK = 6 шт

    @Override
    public List<Subtask> getAllSubtasks() {          // 1. метод для получения списка всех SUBTASK
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllSubTasks() {                   // 2. метод для удаления всех SUBTASK
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epics.get(epic.getId()).getSubTasks().clear(); // очищаем список субтасков в каждом эпике
            epics.get(epic.getId()).setStatus(NEW);        // обновляем статус до NEW
        }
    }

    @Override
    public Subtask getSubtaskById(Integer id) {        // 3. метод получения SUBTASK по идентификатору ID
        historyManager.add(subtasks.get(id));               // добавляем в список с историей просмотров
        return subtasks.get(id);
    }

    @Override
    public int createNewSubTask(Subtask subTask) {         // 4. метод для создания подзадачи SUBTASK
        subTask.setId(++taskID);
        subtasks.put(subTask.getId(), subTask);
        epics.get(subTask.getEpicID()).getSubTasks().add(subTask); // ?????
        return subTask.getId();
    }

    @Override
    public Subtask updateSubTaskById(Subtask task) {            // 5-1. метод для обновления Subtask по номеру ID
        final Subtask oldSubtask = subtasks.get(task.getId());
        if (oldSubtask == null) {
            System.out.println("Подзадачи с таким номером ID нет в списке.");
            return null;
        }
        oldSubtask.setDescription(task.getDescription());
        oldSubtask.setName(task.getName());
        oldSubtask.setStatus(task.getStatus());
        epics.get(task.getEpicID()).getSubTasks().remove(oldSubtask); // для обновления удаляем старую
        epics.get(task.getEpicID()).getSubTasks().add(task);    // для обновления добавляем новую
        setNewStatus(task);                                             // обновляем статус
        return oldSubtask;
    }

    /*

    Согласно ТЗ:
    Когда меняется статус любой подзадачи в эпике, вам необходимо проверить, что статус эпика изменится
    соответствующим образом. При этом изменение статуса эпика может и не произойти, если в нём, к примеру,
    всё ещё есть незакрытые задачи.

    Вывод: делаем дополнительный метод по обновлению статуса EPIC в зависимости от статусов SUBTASKS

     */

    @Override
    public void setNewStatus(Subtask task) {      // 5-2. метод обновления статуса Epic в зависимости от статуса подзадач
        final ArrayList<Subtask> subTasksOfEpic = epics.get(task.getEpicID()).getSubTasks();
        int subStatusIsNew = 0;
        int subStatusIsDone = 0;
        for (Subtask subTask : subTasksOfEpic) {
            if (subTask.getStatus().equals(NEW)) {
                subStatusIsNew++;
            } else if (subTask.getStatus().equals(DONE)) {
                subStatusIsDone++;
            }
        }
        if (subStatusIsNew == subTasksOfEpic.size()) {     //проверяем условия и в зависимости от совпадений - присваиваем статус
            epics.get(task.getEpicID()).setStatus(NEW);
        } else if (subStatusIsDone == subTasksOfEpic.size()) {
            epics.get(task.getEpicID()).setStatus(DONE);
        } else {
            epics.get(task.getEpicID()).setStatus(IN_PROGRESS);
        }
    }

    @Override
    public Subtask deleteSubTaskById(Integer id) {     // 6. метод для удаления SUBTASK по идентификатору ID.
        final Subtask deletedTask = subtasks.get(id);
        historyManager.remove(id);
        epics.get(deletedTask.getEpicID()).getSubTasks().remove(deletedTask);
        subtasks.remove(id);
        return deletedTask;
    }


    // Новый метод по возвращает ArrayList истории обращений
    @Override
    public List<Task> history() {
        return historyManager.getHistory();
    }

}