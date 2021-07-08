package au.com.vodafone.clientservice.repository;

import au.com.vodafone.clientservice.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class that manages the CRUD operation
 *
 */
@Repository
public interface ClientRepository extends CrudRepository<ClientEntity,Long> {
}
