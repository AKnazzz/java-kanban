package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static tasks.StatusMarker.*;


public class InMemoryTaskManager implements TaskManager { // класс менеджер для описания логики работы с обычными задачами Task

    protected Map<Integer, Task> tasks = new HashMap<>(); // мапа для хранения задач [ID] [Task]
    protected Map<Integer, Epic> epics = new HashMap<>(); // мапа для хранения задач [ID] [Epic]
    protected Map<Integer, Subtask> subtasks = new HashMap<>(); // мапа для хранения задач [ID] [subTask]
    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));


    protected Integer taskID = 0;
    protected HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }


    //  методы для TASK = 6 шт

    @Override
    public List<Task> getAllTasks() {                  // 1. метод для получения списка всех TASK
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {                          // 2. метод для удаления всех задач из списка задач TASK
        prioritizedTasks.removeAll(tasks.values());
        tasks.clear();
    }

    @Override
    public Task getTaskById(Integer id) {                  // 3. метод для получения TASK по идентификатору ID
        historyManager.add(tasks.get(id));                      // добавляем в список с историей просмотров
        return tasks.get(id);
    }

    @Override
    public int createNewTask(Task task) {                    //  4. метод для создания новой задачи типа (TASK)
        if (task == null) {
            System.out.println("Невозможно создать задачу некорректна!");
            return -1;
        }

        if (!timeNotBusy(task)) {
            System.out.println("Невозможно создать задачу " + task.getName() + " => пересечение по времени !!!");
            return -1;
        }

        task.setId(++taskID);
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
        return task.getId();
    }

    @Override
    public Task updateTaskById(Task task) {            // 5. метод для обновления имени, описания и статуса (TASK) по (ID)

        final Task originalTask = tasks.get(task.getId());

        if (originalTask == null) {
            System.out.println("Задачи с таким номером (ID) не обнаружена в списке.");
            return null;
        }

        if (!timeNotBusy(task)) {
            System.out.println("Невозможно обновить задачу " + originalTask.getName() + "=> пересечение по времени !!!");
            return originalTask;
        }

        tasks.put(task.getId(), task);
        prioritizedTasks.remove(task);
        prioritizedTasks.add(task);

        return task;
    }

    @Override
    public Task deleteTaskById(Integer id) {        // 6. метод удаления TASK по номеру идентификатору ID.

        final Task deletedTask = tasks.get(id);
        historyManager.remove(id);
        tasks.remove(id);
        prioritizedTasks.remove(deletedTask);
        return deletedTask;

    }

    //  методы для EPIC = 7 шт

    @Override
    public List<Epic> getAllEpics() {              // 1. метод для получения списка всех сложных задач EPIC
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() {            // 2. метод для удаления всех значений из списка сложных задач EPIC
        prioritizedTasks.removeAll(epics.values());
        prioritizedTasks.removeAll(subtasks.values());
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

        if (epic == null) {
            System.out.println("Невозможно создать задачу некорректна!");
            return -1;
        }


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

        if (!timeNotBusy(epic)) {
            System.out.println("Невозможно обновить задачу " + originalTask.getName() + " => пересечение по времени !!!");
            return originalTask;
        }

        epics.put(epic.getId(), epic);

        return epic;
    }

    @Override
    public void deleteEpicById(Integer id) {                 // 6. метод для удаления сложной задачи EPIC по номеру.

        for (Subtask subtask : getEpicSubtasks(getEpicById(id))) {
            if (Objects.equals(subtask.getEpicID(), id)) {
                subtasks.remove(subtask.getId());
                historyManager.remove(subtask.getId());
                prioritizedTasks.remove(subtask);
            }
        }

        historyManager.remove(id);
        epics.get(id).getSubTasks().clear();
        epics.remove(id);
    }

    @Override
    public List<Subtask> getEpicSubtasks(Epic epic) {     // 7. метод получения списка всех SUBTASK определённого Epic.
        if (epic == null || !epics.containsKey(epic.getId())) {
            return new ArrayList<>();
        }
        return epics.get(epic.getId()).getSubTasks();
    }


    //  методы для SUBTASK = 6 шт

    @Override
    public List<Subtask> getAllSubtasks() {          // 1. метод для получения списка всех SUBTASK
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllSubTasks() {                   // 2. метод для удаления всех SUBTASK
        prioritizedTasks.removeAll(subtasks.values());
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epics.get(epic.getId()).getSubTasks().clear(); // очищаем список субтасков в каждом эпике
            epics.get(epic.getId()).setStatus(NEW);        // обновляем статус до NEW
            updateEpicDurationAndEndTime(epic);
        }
    }

    @Override
    public Subtask getSubtaskById(Integer id) {        // 3. метод получения SUBTASK по идентификатору ID
        historyManager.add(subtasks.get(id));               // добавляем в список с историей просмотров
        return subtasks.get(id);
    }

    @Override
    public int createNewSubTask(Subtask subTask) {         // 4. метод для создания подзадачи SUBTASK
        if (!timeNotBusy(subTask)) {
            System.out.println("Невозможно создать SUBTASK задачу " + subTask.getName() + " у " + epics.get(subTask.getEpicID()).getName() + " => пересечение по времени !!!");
            return -1;
        }

        if (subTask == null) {
            System.out.println("Невозможно создать задачу некорректна!");
            return -1;
        }

        subTask.setId(++taskID);
        subtasks.put(subTask.getId(), subTask);
        epics.get(subTask.getEpicID()).getSubTasks().add(subTask);
        prioritizedTasks.add(subTask);
        updateEpicDurationAndEndTime(epics.get(subTask.getEpicID()));
        return subTask.getId();
    }

    @Override
    public Subtask updateSubTaskById(Subtask task) {            // 5-1. метод для обновления Subtask по номеру ID

        final Subtask oldSubtask = subtasks.get(task.getId());
        if (oldSubtask == null) {
            System.out.println("Подзадачи с таким номером ID нет в списке.");
            return null;
        }

        if (!timeNotBusy(task)) {
            System.out.println("Невозможно обновить задачу " + oldSubtask.getName() + " => пересечение по времени !!!");
            return oldSubtask;
        }

        if (task.getEpicID() == null) {
            System.out.println("Подзадачи с таким номером ID нет в списке.");
            return null;
        }

        subtasks.put(task.getId(), task);
        epics.get(task.getEpicID()).getSubTasks().remove(oldSubtask);
        epics.get(task.getEpicID()).getSubTasks().add(task);
        updateEpicStatus(task.getEpicID());
        updateEpicDurationAndEndTime(epics.get(task.getEpicID()));
        return task;
    }


    @Override
    public void updateEpicStatus(int epicID) {      // 5-2. метод обновления статуса Epic в зависимости от статуса подзадач

        Epic epic = epics.get(epicID);

        if (epic.getSubTasks().isEmpty()) {
            epic.setStatus(NEW);
            return;
        }

        boolean isNew = true;
        boolean isDone = true;

        for (Subtask subTask : epic.getSubTasks()) {
            if (subTask.getStatus() == NEW) {
                isDone = false;
            }
            if (subTask.getStatus() == DONE) {
                isNew = false;
            }
            if (subTask.getStatus() == IN_PROGRESS) {
                isDone = false;
                isNew = false;
            }
        }

        if (isNew) {
            epic.setStatus(NEW);
        } else if (isDone) {
            epic.setStatus(DONE);
        } else {
            epic.setStatus(IN_PROGRESS);
        }
    }

    @Override
    public Subtask deleteSubTaskById(Integer id) {     // 6. метод для удаления SUBTASK по идентификатору ID.
        final Subtask deletedTask = subtasks.get(id);
        historyManager.remove(id);
        epics.get(deletedTask.getEpicID()).getSubTasks().remove(deletedTask);
        subtasks.remove(id);
        //      updateEpicDurationAndEndTime(deletedTask.getEpicID());
        prioritizedTasks.remove(deletedTask);
        updateEpicDurationAndEndTime(epics.get(deletedTask.getEpicID()));
        return deletedTask;
    }


    // Новый метод по возвращает ArrayList истории обращений
    @Override
    public List<Task> history() {
        return historyManager.getHistory();
    }

    public void updateEpicDurationAndEndTime(Epic epic) {

        List<Subtask> epicSubtasks = getEpicSubtasks(epic);

        if (epicSubtasks.isEmpty()) {
            epic.setStartTime(null);
            epic.setDuration(Duration.ofMinutes(0));
            epic.setEndTime(null);
            return;
        }

        LocalDateTime startTime = epicSubtasks.stream().min(Comparator.comparing((x) -> x.getStartTime())).get().getStartTime();
        epic.setStartTime(startTime);

        LocalDateTime endTime = epicSubtasks.stream().max(Comparator.comparing((x) -> x.getEndTime())).get().getEndTime();
        epic.setEndTime(endTime);

        Duration result = epicSubtasks.stream().map(Task::getDuration).reduce(Duration.ZERO, Duration::plus);
        epic.setDuration(result);
    }

    public void clearAll() {
        deleteAllSubTasks();
        deleteAllEpics();
        deleteAllTasks();
        prioritizedTasks.clear();
        taskID = 0;
    }

    public boolean timeNotBusy(Task task) {

        return prioritizedTasks.stream()
                .filter(t -> t.getStartTime() != null && t.getDuration() != null)
                .noneMatch(t -> (t.getStartTime().isBefore(task.getStartTime()) && t.getEndTime().isAfter(task.getStartTime()) ||
                        t.getStartTime().isBefore(task.getEndTime()) && t.getEndTime().isAfter(task.getEndTime())) ||
                        task.getStartTime().isBefore(t.getStartTime()) && task.getEndTime().isAfter(t.getEndTime()) ||
                        task.getStartTime().isEqual(t.getStartTime()) || task.getEndTime().isEqual(t.getEndTime()));

    }

/*    public boolean timeNotBusy(Task task) {
        for (Task taskInManager : prioritizedTasks) {
            if (!(taskInManager.getStartTime() == null && taskInManager.getDuration() == null)) {
                if (taskInManager.getStartTime().isBefore(task.getStartTime()) &&
                        taskInManager.getEndTime().isAfter(task.getStartTime())) {
                    return false;
                }
                if (taskInManager.getStartTime().isBefore(task.getEndTime()) &&
                        taskInManager.getEndTime().isAfter(task.getEndTime())) {
                    return false;
                }
                if (task.getStartTime().isBefore(taskInManager.getStartTime()) &&
                        task.getEndTime().isAfter(taskInManager.getEndTime())) {
                    return false;
                }
                if (task.getStartTime().isEqual(taskInManager.getStartTime()) ||
                        task.getEndTime().isEqual(taskInManager.getEndTime())) {
                    return false;
                }
            }
        }
        return true;
    }*/

}