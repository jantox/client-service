package au.com.vodafone.clientservice.service.impl;

import au.com.vodafone.clientservice.entity.ClientEntity;
import au.com.vodafone.clientservice.repository.CacheRepository;
import au.com.vodafone.clientservice.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientQueryServiceImplTest {

    @InjectMocks
    ClientQueryServiceImpl service;

    @Mock
    ClientRepository clientRepository;

    @Mock
    CacheRepository cacheRepository;

    @Test
    public void testGetClient_clientDoesNotExistsInCache() {
        ClientEntity client = new ClientEntity();
        client.setId(1L);
        client.setName("name");
        client.setEmail("name@email.com");

        when(clientRepository.findById(anyLong())).thenReturn(java.util.Optional.of(client));
        ClientEntity resultClient = service.getClient(1L);

        assertNotEquals(resultClient, null);
        assertEquals(client, resultClient);
        verify(clientRepository, times(1)).findById(anyLong());
        verify(cacheRepository, times(1)).getCache();
    }

    @Test
    public void testGetClient_clientExistsInCache() throws IllegalAccessException {
        ClientEntity client = new ClientEntity();
        client.setId(1L);
        client.setName("name");
        client.setEmail("name@email.com");

        Map<Long, ClientEntity> cache = new HashMap<>();
        cache.put(1L, client);

        when(cacheRepository.getCache()).thenReturn(cache);

        ClientEntity resultClient = service.getClient(1L);

        assertNotEquals(resultClient, null);
        assertEquals(client, resultClient);
        verify(clientRepository, never()).findById(anyLong());
        verify(cacheRepository, times(1)).getCache();
    }

    @Test
    public void testGetAllClients_cacheIsNotEmpty() throws IllegalAccessException {
        ClientEntity client = new ClientEntity();
        client.setId(1L);
        client.setName("name");
        client.setEmail("name@email.com");

        Map<Long, ClientEntity> cache = new HashMap<>();
        cache.put(1L, client);

        when(cacheRepository.getCache()).thenReturn(cache);

        List<ClientEntity> resultClients = service.getAllClients();

        assertNotEquals(resultClients, null);
        assertEquals(1, resultClients.size());
        assertEquals(client, resultClients.get(0));
        verify(clientRepository, never()).findAll();
        verify(cacheRepository, times(1)).getCache();
    }

    @Test
    public void testGetAllClients_cacheIsEmpty() throws IllegalAccessException {
        ClientEntity client = new ClientEntity();
        client.setId(1L);
        client.setName("name");
        client.setEmail("name@email.com");

        when(cacheRepository.getCache()).thenReturn(null);
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));
        List<ClientEntity> resultClients = service.getAllClients();

        assertNotEquals(resultClients, null);
        assertEquals(1, resultClients.size());
        assertEquals(client, resultClients.get(0));
        verify(clientRepository, times(1)).findAll();
        verify(cacheRepository, times(1)).getCache();
    }
}