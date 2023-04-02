import managers.InMemoryTaskManager;
import managers.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest extends TaskManagerTest <InMemoryTaskManager> {

    // в АБСТРАКТНОМ КЛАССЕ тестируются все ПУБЛИЧНЫЕ методы
    @BeforeEach
    public void init (){
        taskManager = new InMemoryTaskManager();
    }

    @DisplayName("При создании InMemoryTaskManager он возвращает пустые списки задач, эпиков и подзадач")
    @Test
    public void shouldReturnEmptyListsOfEpicsTasksAndSubs(){
        taskManager = Managers.getDefault();
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ожидался пустой список TASKS");
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой список SUBTASKS");
        Assertions.assertTrue(taskManager.getAllEpics().isEmpty(), "Ожидался пустой список EPICS");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой список задач");
        Assertions.assertTrue(taskManager.history().isEmpty(), "Ожидался пустой список истории");
    }

}
