package managers;

import tasks.Epic;

import java.util.ArrayList;
import java.util.HashMap;


public class EpicManager {          // класс менеджер для описания логики работы с составными задачами Epic
    protected HashMap<Integer, Epic> epics = new HashMap<>(); // мапа для хранения задач [ID] [Epic]
    protected Integer counterIDEpics = 0;

    public Epic makeNewEpic(Epic task) {             //    метод для создания новой сложной задачи (EPIC)
        final Epic newTask = new Epic(task.getName(), task.getDescription(), ++counterIDEpics);
        if (!epics.containsKey(newTask.getId())) {
            epics.put(newTask.getId(), newTask);
        } else {
            System.out.println("Составная задача с этим номером ID уже есть в списке.");
            return null;
        }
        return newTask;
    }

    public Epic updateEpic(Epic epic) {    // метод для изменений имени и описания сложной задачи (EPIC) по номеру
        final Epic originalTask = epics.get(epic.getId());
        if (originalTask == null) {
            System.out.println("Составной задачи с таким номером ID не существует.");
            return null;
        }
        originalTask.setDescription(epic.getDescription());
        originalTask.setName(epic.getName());
        return originalTask;
    }


    public ArrayList<Epic> getAllEpics() {              // метод для получения списка всех сложных задач (EPIC)
        return new ArrayList<>(epics.values());
    }

    public void deleteAllEpics() {               // метод для удаления всех значений из списка сложных задач (EPIC)
        epics.clear();
    }


    public Epic findEpicById(Integer id) {      // метод для получения EPIC по ID
        return epics.get(id);
    }

    public void deleteEpicById(Integer id) {                 // метод для удаления сложной задачи (EPIC) по номеру.
        epics.remove(id);
    }

}