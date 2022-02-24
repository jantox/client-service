package au.com.company.clientservice.service;

import au.com.company.clientservice.entity.ClientEntity;

import java.util.List;

/**
 * Service layer to implement data retrieval
 *
 */
public interface ClientQueryService {

    List<ClientEntity> getAllClients();

    ClientEntity getClient(Long id);
}
