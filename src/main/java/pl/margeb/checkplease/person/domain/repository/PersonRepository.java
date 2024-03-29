package pl.margeb.checkplease.person.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.margeb.checkplease.person.domain.model.Person;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    List<Person> findByGroupId(UUID groupId);
}
