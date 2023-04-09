import tracker.managers.HTTPTaskManager;

public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager> {

    HTTPTaskManagerTest() {
        super(HTTPTaskManager.loadFromKVServer("http://localhost:8078"));
    }
}