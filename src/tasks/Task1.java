package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

  // !!! Редактируйте этот метод !!!
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    // метод считает что значения уникальны, просто потому что не очень понятно что делать в случае
    // с повторяющимися значениями (сервис работает как и положено? это особый случай?)
    // если нужно обрабатывать неуникальные id, в итоге нельзя будет использовать хеш-карту
    // и поиск станет медленнее
    Map<Integer, Person> persons = PersonService.findPersons(personIds).stream()
            .collect(Collectors.toMap(Person::getId, Function.identity()/*, (a,b) -> a */));
    return personIds.stream()
            .map(persons::get)
            .collect(Collectors.toList());
  }

  @Override
  public boolean check() {
    List<Integer> ids = List.of(1, 2, 3);

    return findOrderedPersons(ids).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(ids);
  }

}
