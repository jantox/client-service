package au.com.company.clientservice.controller;

import au.com.company.clientservice.domain.ClientEntityCommandRequest;
import au.com.company.clientservice.entity.ClientEntity;
import au.com.company.clientservice.service.impl.ClientCommandServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientCommandControllerTest {

    @InjectMocks
    ClientCommandController controller;

    @Mock
    ClientCommandServiceImpl service;

    @Mock
    ModelMapper modelMapper;

    private MockMvc mvc;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
    }

    @Test
    void createClient() throws Exception {
        ClientEntity client = new ClientEntity();
        client.setName("name");
        client.setEmail("name@email.com");

        doNothing().when(service).addClient(any(ClientEntity.class));
        when(modelMapper.map(any(), any())).thenReturn(client);

        mvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ClientEntityCommandRequest
                        .builder()
                        .name("name")
                        .email("name@email.com")
                        .build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("msg").value("Success"));
    }

    @Test
    void deleteClientById() throws Exception {
        doNothing().when(service).deleteClient(anyLong());

        mvc.perform(delete("/api/client/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("msg").value("Done"));
    }

    @Test
    void updateClientById() throws Exception {
        ClientEntity newClient = new ClientEntity();
        newClient.setId(1L);
        newClient.setName("name1");
        newClient.setEmail("name1@email.com");

        doNothing().when(service).updateClient(anyLong(), any(ClientEntity.class));
        when(modelMapper.map(any(), any())).thenReturn(newClient);

        mvc.perform(put("/api/client/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ClientEntityCommandRequest
                        .builder()
                        .id(1L)
                        .name("name1")
                        .email("name1@email.com")
                        .build())))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("msg").value("OK"));
    }
}