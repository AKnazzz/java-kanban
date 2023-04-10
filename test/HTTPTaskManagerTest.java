import org.junit.jupiter.api.Test;
import tracker.managers.HTTPTaskManager;
import tracker.tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager> {

    HTTPTaskManagerTest() {
        super(HTTPTaskManager.loadFromKVServer("http://localhost:8078"));
    }

    @Test
    void getTaskById() throws Exception {
        HTTPTaskManager httpTaskManager = HTTPTaskManager.loadFromKVServer("http://localhost:8078");
        Task task = httpTaskManager.getTask(1);
        assertNotNull(task);
        assertEquals("task1", task.getName());
        assertEquals("descriptionOfTask1", task.getDescription());
    }

    @Test
    void removeTaskById() throws Exception {
        HTTPTaskManager httpTaskManager = HTTPTaskManager.loadFromKVServer("http://localhost:8078");
        httpTaskManager.deleteTask(1);
        assertNull(httpTaskManager.getTask(1));
    }

}