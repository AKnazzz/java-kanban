import managers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest <InMemoryTaskManager> {

    // в АБСТРАКТНОМ КЛАССЕ тестируются все ПУБЛИЧНЫЕ методы
    @BeforeEach
    public void init (){
        taskManager = new InMemoryTaskManager();
    }
}
