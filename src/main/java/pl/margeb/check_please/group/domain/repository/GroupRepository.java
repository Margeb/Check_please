package pl.margeb.check_please.group.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.margeb.check_please.group.domain.model.Group;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Page<Group> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
