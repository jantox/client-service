package au.com.company.clientservice.service.impl;

import au.com.company.clientservice.repository.CacheRepository;
import au.com.company.clientservice.repository.ClientRepository;
import au.com.company.clientservice.entity.ClientEntity;
import au.com.company.clientservice.service.ClientCommandService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClientCommandServiceImpl implements ClientCommandService {
    private final AtomicLong idCount = new AtomicLong();

    final ClientRepository clientRepository;
    final CacheRepository cacheRepository;

    public ClientCommandServiceImpl(ClientRepository clientRepository,
                                  CacheRepository cacheRepository) {
        this.clientRepository = clientRepository;
        this.cacheRepository = cacheRepository;
    }

    @Override
    public void addClient(ClientEntity client) {
        client.setId(idCount.incrementAndGet());
        clientRepository.save(client);
        cacheRepository.getCache().put(client.getId(),client);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
        cacheRepository.getCache().remove(id);
    }

    @Override
    public void updateClient(Long id, ClientEntity newClient) {
        newClient.setId(id);
        clientRepository.findById(id)
                .map(entity -> {
                    entity.setEmail(newClient.getEmail());
                    entity.setName(newClient.getName());
                    return clientRepository.save(newClient);
                })
                .orElseGet(() -> clientRepository.save(newClient));

        cacheRepository.getCache().put(newClient.getId(), newClient);
    }
}
