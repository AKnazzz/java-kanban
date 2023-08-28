# Трекер-задач Kanban.

## Стек технологий: Java SE, Git, HTTP, Apache Tomcat, CSS.

### В данном коде реализовано:

- Создание задачи, подзачи, эпика.
- Получение списка всех задач.
- Обновление задачи, подзачи, эпика.
- Удаление всех задач.
- Получение по идентификатору.
- Удаление по идентификатору.
- Получение списка всех подзадач определённого эпика.
- Обновление статуса выполнения.
- Хранение истории просмотра пользователем задач.

Программа, отвечающая за формирование модели данных для этой страницы:
![image](https://user-images.githubusercontent.com/118910569/230314134-f7e8c694-c3aa-426d-bfa9-4b72030ca335.png)

#### У задачи (task) есть следующие свойства:

- Название, кратко описывающее суть задачи (например, «Переезд»).
- Описание, в котором раскрываются детали.
- Уникальный идентификационный номер задачи, по которому её можно будет найти.
- Статус, отображающий её прогресс. Выделяются следующие этапы жизни задачи:
    NEW — задача только создана, но к её выполнению ещё не приступили.
    IN_PROGRESS — над задачей ведётся работа.
    DONE — задача выполнена.
  
Функций реализованы в классе InMemoryTaskManager, имплементирующий интерфейс TaskManager. 
Класс Task - родитель. Subtask и Epic - наследники. Такая структура дает возможность не только изменять свойство сразу у всех видов задач, но и отдельно у каждой.

Функционалы добавления просмотра и хранения истории просмотра задач пользователем реализованы в классе InMemoryHistoryManager, имплементирующий интерфейс HistoryManager.

Утилитарный class Managers отвечает за создание менеджера задач, истории просмотра задач, что дает возможность не зависить от реализации интерфейсов.

Задачи всех типов хранятся в HashMap tasks. Это удобно для поиска элемента. История просмотра пользователем задач хранится в List history.

Тесты кода реализованы в классе Main.

Status хранит в себе статусы для задач.
