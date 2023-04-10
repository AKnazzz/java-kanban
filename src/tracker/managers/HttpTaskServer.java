package tracker.managers;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static HttpServer httpServer;

    public HttpTaskServer(TaskManager taskManager) {

        try {
            httpServer = HttpServer.create();
            // конфигурирование и запуск сервера
            httpServer.bind(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/tasks", new TasksHandler(taskManager));
            httpServer.start();
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        } catch (IOException e) {
            System.out.println("Не удалось запустить HTTP-сервер на " + PORT + " порту!");
        }
    }

    public void start() {
        httpServer.start();
    }
    public void stop() {
        httpServer.stop(0);
    }
}