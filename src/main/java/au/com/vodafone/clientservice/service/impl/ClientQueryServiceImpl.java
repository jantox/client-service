package au.com.vodafone.clientservice.service.impl;

import au.com.vodafone.clientservice.entity.ClientEntity;
import au.com.vodafone.clientservice.repository.CacheRepository;
import au.com.vodafone.clientservice.repository.ClientRepository;
import au.com.vodafone.clientservice.service.ClientQueryService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClientQueryServiceImpl implements ClientQueryService {

    final ClientRepository clientRepository;
    final CacheRepository cacheRepository;

    public ClientQueryServiceImpl(ClientRepository clientRepository,
                                  CacheRepository cacheRepository) {
        this.clientRepository = clientRepository;
        this.cacheRepository = cacheRepository;
    }

    @Override
    public List<ClientEntity> getAllClients() {
        Map<Long, ClientEntity> cache = cacheRepository.getCache();
        if (CollectionUtils.isEmpty(cache)) {
            return (List<ClientEntity>) clientRepository.findAll();
        }

        return cache.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public ClientEntity getClient(Long id) {
        Map<Long, ClientEntity> cache = cacheRepository.getCache();
        if (cache.containsKey(id)){
            return cache.get(id);
        }
        return clientRepository.findById(id).get();
    }
}
