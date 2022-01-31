package monster.hello.test.dao;

import monster.hello.test.domain.Person;

import java.util.List;

public interface PersonDao {
    Integer createTable();
    Integer dropTable();
    List<Person> selectAll();
    Person selectById(Integer id);
    Integer deleteById(Integer id);
    Integer update(Person person);
    Integer insert(Person person);
}
