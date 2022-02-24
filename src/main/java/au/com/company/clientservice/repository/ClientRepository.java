package au.com.company.clientservice.repository;

import au.com.company.clientservice.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class that manages the CRUD operation
 *
 */
@Repository
public interface ClientRepository extends CrudRepository<ClientEntity,Long> {
}
