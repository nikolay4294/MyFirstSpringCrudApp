package springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired //спринг будет внедрять созданный бин в Конфиге сюда.
    public PersonDAO(JdbcTemplate jdbcTemplate, JdbcTemplate jdbcTemplate1) {
        this.jdbcTemplate = jdbcTemplate1;
    }

    public List<Person> index() {

        //Используем JDBC TAMPLATE
        //return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper()); //используем свой ро маппер
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        //return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},new PersonMapper()).
        //       stream().findAny().orElse(null); //используем свой ро маппер
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES(1,?,?,?)", person.getName(),
                person.getSurname(), person.getEmail());
    }

    public void update(Person updatedPerson, int id) {
        jdbcTemplate.update("UPDATE Person SET name=?,surname=?,email=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getSurname(), updatedPerson.getEmail(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
