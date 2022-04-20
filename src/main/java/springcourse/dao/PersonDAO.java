package springcourse.dao;

import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PersonDAO {
    private static int PERSON_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = Objects.requireNonNull(getConnection()).createStatement(); //создаем объект стейтмент на базе конекшена, он выполняет запрос к БД
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL); //метод выполняет СКЛ запрос в базу данных и результат запроса помещаем в резаулт сет
            //не изменяет БД, только запрос к ней

            //вручную проходим по всему резаулсету и ищем все наши поля(переменные)
            while (resultSet.next()) { //next() сдвигаем на одну строкуц вперед и смотрим что там,в результатае получаем одну строку с  данными человека
                Person person = new Person();
                person.setId(resultSet.getInt("id")); //из резаулт сета получаем значение по ключу и записываем в человека
                person.setName(resultSet.getString("name"));
                person.setSurname(resultSet.getString("surname"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        //используя лямбду ищем в списке человека с нужным id
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);

        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    Objects.requireNonNull(getConnection()).prepareStatement("SELECT * FROM Person WHERE id = ?");
            preparedStatement.setInt(1, id);//вставляем в запрос скл наш параметр ид
            ResultSet resultSet = preparedStatement.executeQuery(); //делаем запрос к бд и получаем результат, результат записываем в резаулт сет
            resultSet.next();

            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setSurname(resultSet.getString("surname"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public void save(Person person) {
        try {
            PreparedStatement preparedStatement =
                    Objects.requireNonNull(getConnection()).prepareStatement("INSERT INTO Person VALUES(1,?,?,?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.executeUpdate(); //обновляем данные в БД.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Person updatedPerson, int id) {
//        Person personToBeUpdated = show(id);
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setSurname(updatedPerson.getSurname());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());

        try {
            PreparedStatement preparedStatement =
                    Objects.requireNonNull(getConnection()).prepareStatement("UPDATE Person SET name=?,surname=?,email=? WHERE id=?");
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setString(2, updatedPerson.getSurname());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id) {
//        people.removeIf(p -> p.getId() == id); //поиск по предикату, если в скобках тру - удаляем.
        try {
            PreparedStatement preparedStatement =
                    Objects.requireNonNull(getConnection()).prepareStatement("DELETE FROM Person WHERE id=?");
            preparedStatement.setInt(1, id); //запрос создан, теперь его надо выполнить
            preparedStatement.executeUpdate();//запрос выполнен(отправил в бд)
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
