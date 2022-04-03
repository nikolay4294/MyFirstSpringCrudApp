package springcourse.dao;

import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PERSON_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PERSON_COUNT, "Bob"));
        people.add(new Person(++PERSON_COUNT, "Nik"));
        people.add(new Person(++PERSON_COUNT, "Egor"));
        people.add(new Person(++PERSON_COUNT, "Mylo"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person->person.getId()==id).findAny().orElse(null);
    }
}
