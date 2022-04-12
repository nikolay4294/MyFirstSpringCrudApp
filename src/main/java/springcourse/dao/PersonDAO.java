package springcourse.dao;

import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PERSON_COUNT;
    private  List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PERSON_COUNT, "Bob", "Bobov", "boss1811@yandex.ru"));
        people.add(new Person(++PERSON_COUNT, "Nik", "Xarin", "ddddd@ya.ru"));
        people.add(new Person(++PERSON_COUNT, "Egor", "Vjr", "djdhhd@jdjd.com"));
        people.add(new Person(++PERSON_COUNT, "Mylo", "djdjd", "djjd@cskk.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        //используя лямбду ищем в списке человека с нужным id
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PERSON_COUNT); //динамически увеличивается счетчик людей
        people.add(person); //добавляем человека в БД
    }

    public void update(Person updatedPerson, int id){
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setSurname(updatedPerson.getSurname());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id); //поиск по предикату, если в скобках тру - удаляем.
    }
}
