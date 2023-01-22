package managers;

import tasks.Epic;
import tasks.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

import static tasks.StatusMarker.*;


public class SubtaskManager {           // класс менеджер для логики работы с подзадачами Subtask
    protected Integer counterIDSubTasks = 0;
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>(); // мапа для хранения задач [ID] [subTask]
    protected EpicManager epicManager;

    public SubtaskManager(EpicManager epicManager) {
        this.epicManager = epicManager;
    }

    public SubTask makeNewSubTask(SubTask subTask, Epic epic) {         // метод для создания подзадачи SubTask
        final SubTask newSubTask = new SubTask(subTask.getName(), subTask.getDescription(), ++counterIDSubTasks, epic.getId());
        if (!subTasks.containsKey(newSubTask.getId())) {
            subTasks.put(newSubTask.getId(), newSubTask);
            epicManager.epics.get(epic.getId()).getSubTasks().add(newSubTask);
        } else {
            System.out.println("Подзадача с таким номером ID уже есть в списке");
            return null;
        }
        return newSubTask;
    }

    public SubTask updateSubTaskById(SubTask task) {            // метод для изменения Subtask по номеру ID
        final SubTask oldSubTask = subTasks.get(task.getId());
        if (oldSubTask == null) {
            System.out.println("Подзадачи с таким номером ID нет в списке.");
            return null;
        }
        oldSubTask.setDescription(task.getDescription());
        oldSubTask.setName(task.getName());
        oldSubTask.setStatus(task.getStatus());
        epicManager.epics.get(task.getEpicID()).getSubTasks().remove(oldSubTask); // для обновления удаляем старую
        epicManager.epics.get(task.getEpicID()).getSubTasks().add(task);    // для обновления добавляем новую
        setNewStatus(task);                                             // обновляем статус
        return oldSubTask;
    }



    public void setNewStatus(SubTask task) {       // метод обновления статуса Epic в зависимости от статуса подзадач
        ArrayList<SubTask> subTasksOfEpic = epicManager.epics.get(task.getEpicID()).getSubTasks();
        int subStatusIsNew = 0;
        int subStatusIsDone = 0;
        for (SubTask subTask : subTasksOfEpic) {
            if (subTask.getStatus().equals(NEW)) {
                subStatusIsNew++;
            } else if (subTask.getStatus().equals(DONE)) {
                subStatusIsDone++;
            }
        }
        if (subStatusIsNew == subTasksOfEpic.size()) {          // проверяем условия и в зависимости от совпадений - присваиваем статус
            epicManager.epics.get(task.getEpicID()).setStatus(NEW);
        } else if (subStatusIsDone == subTasksOfEpic.size()) {
            epicManager.epics.get(task.getEpicID()).setStatus(DONE);
        } else {
            epicManager.epics.get(task.getEpicID()).setStatus(IN_PROGRESS);
        }
    }


    public ArrayList<SubTask> findAllOfEpic(Epic epic) {     // метод получения списка всех подзадач конкретного Epic.
        return epicManager.epics.get(epic.getId()).getSubTasks();
    }


    public void deleteAllSubTasks() {               // метод для удаления всех подзадач.
        subTasks.clear();
    }

    public SubTask findSubTAskById(Integer id) { // метод получения подзадачи по номеру ID
        return subTasks.get(id);
    }


    public SubTask deleteSubTaskById(Integer id) {     // метод для удаления подзадачи по номеру ID.
        final SubTask deletedTask = subTasks.get(id);
        epicManager.epics.get(deletedTask.getEpicID()).getSubTasks().remove(deletedTask);
        subTasks.remove(id);
        return deletedTask;
    }
}