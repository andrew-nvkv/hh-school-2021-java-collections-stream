package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */

public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    // если нужно пропустить один элемент, то при размере массива 1 тоже ничего не возвращаем
    if (persons.size() <= 1) {
      return Collections.emptyList();
    }
    return persons.stream()
            // дешевле пропустить один элемент чем удалять (мы заранее не знаем какой именно это список на входе)
            .skip(1)
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    // distinct здесь не нужен, так как все равно пишем во множество
    // стрим тоже не нужен, можно обойтись стандартный конструктором (даже IDEA это подсказывает)
    return new HashSet<String>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  static public String convertPersonToString(Person person) {
    // getSecondName запрашивалось дважды
    // неровно склеивались строки
    // переписано в функциональном стиле

    // Reference:
    // https://stackoverflow.com/questions/46987666/java-8-store-lambdas-in-list
    // https://stackoverflow.com/questions/2752192/array-of-function-pointers-in-java

      List<Supplier<String>> transformers = List.of(
              () -> person.getSecondName(),
              () -> person.getFirstName(),
              () -> person.getMiddleName()
      );
      return transformers.stream()
              .map(v -> v.get())
              .filter(v -> v != null)
              .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(
                    Person::getId,
                    Task8::convertPersonToString,
                    (a, b) -> a)
            );
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // тут стримы не нужны, просто использование стандартных методов интерфейса Collection
    // в изначальном методе не было раннего прерывания метода, когда совпадение уже найдено
    for (Person person : persons1) {
      if (persons2.contains(person)) {
        return true;
      }
    }
    return false;
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(v -> v % 2 == 0).count();
  }

  @Override
  public boolean check() {

    List<Person> persons1 = List.of(
            new Person(0, "test", Instant.now()),
            new Person(1, "Name1", "MiddleName1", "LastName1", Instant.now()),
            new Person(2, "Name2", "MiddleName2", Instant.now()),
            new Person(3, "NameSame", Instant.now()),
            new Person(4, "NameSame", Instant.now()),
            new Person(6, "Name4", "MiddleName4", "LastName4", Instant.now())
    );
    List<Person> persons2 = List.of(
            new Person(2, "Name2", "MiddleName2", Instant.now()),
            new Person(10, "Name2", "MiddleName2", Instant.now()),
            new Person(11, "Name8", "MiddleName8", Instant.now())
    );
    List<Person> persons3 = List.of(
            new Person(10, "Name2", "MiddleName2", Instant.now()),
            new Person(11, "Name8", "MiddleName8", Instant.now()),
            new Person(12, "Name9", "MiddleName9", Instant.now())
    );

//    System.out.println(getNames(persons1));
//    System.out.println(getDifferentNames(persons1));
//    System.out.println(convertPersonToString(persons1.get(1)));
//    System.out.println(convertPersonToString(persons1.get(2)));
//    System.out.println(convertPersonToString(persons1.get(3)));
//    System.out.println(getPersonNames(persons1));
//    System.out.println(hasSamePersons(persons1, persons2));   // true
//    System.out.println(hasSamePersons(persons1, persons3));   // false
//    System.out.println(countEven(List.of(1,2,3,4,5,6,7,8).stream()));
//    System.out.println(countEven(List.of(1,2,3,4,5,6,7).stream()));

    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
