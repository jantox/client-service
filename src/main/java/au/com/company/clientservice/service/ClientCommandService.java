package au.com.company.clientservice.service;

import au.com.company.clientservice.entity.ClientEntity;

/**
 * Service layer to implement data entry/update/deletion
 *
 */
public interface ClientCommandService {

    void addClient(ClientEntity client);

    void deleteClient(Long id);

    void updateClient(Long id, ClientEntity newClient);
}
