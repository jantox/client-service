package au.com.vodafone.clientservice.service.impl;

import au.com.vodafone.clientservice.entity.ClientEntity;
import au.com.vodafone.clientservice.repository.CacheRepository;
import au.com.vodafone.clientservice.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ClientCommandServiceImplTest {

    @InjectMocks
    ClientCommandServiceImpl service;

    @Mock
    ClientRepository clientRepository;

    @Mock
    CacheRepository cacheRepository;

    @Test
    public void testAddClient() {
        ClientEntity client = new ClientEntity();
        client.setName("name");
        client.setEmail("name@email.com");

        service.addClient(client);

        ArgumentCaptor<ClientEntity> captor = ArgumentCaptor.forClass(ClientEntity.class);
        verify(clientRepository).save(captor.capture());
        assertEquals(1L, captor.getValue().getId());
        assertEquals(client.getName(), captor.getValue().getName());
        assertEquals(client.getEmail(), captor.getValue().getEmail());

        verify(clientRepository, times(1)).save(client);
        verify(cacheRepository, times(1)).getCache();
    }

    @Test
    public void testDeleteClient() {
        service.deleteClient(1L);

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(clientRepository).deleteById(captor.capture());
        assertEquals(1L, captor.getValue());

        verify(clientRepository, times(1)).deleteById(anyLong());
        verify(cacheRepository, times(1)).getCache();
    }

    @Test
    public void testUpdateClient() throws IllegalAccessException {
        ClientEntity oldClient = new ClientEntity();
        oldClient.setId(1L);
        oldClient.setName("name");
        oldClient.setEmail("name@email.com");

        ClientEntity newClient = new ClientEntity();
        newClient.setName("name1");
        newClient.setEmail("name1@email.com");

        service.updateClient(1L, newClient);

        ArgumentCaptor<ClientEntity> captor = ArgumentCaptor.forClass(ClientEntity.class);
        verify(clientRepository).save(captor.capture());
        assertEquals(oldClient.getId(), captor.getValue().getId());
        assertEquals(newClient.getName(), captor.getValue().getName());
        assertEquals(newClient.getEmail(), captor.getValue().getEmail());

        verify(clientRepository, times(1)).save(newClient);
        verify(cacheRepository, times(1)).getCache();
    }
}