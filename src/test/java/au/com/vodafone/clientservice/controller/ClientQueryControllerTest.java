package au.com.vodafone.clientservice.controller;

import au.com.vodafone.clientservice.domain.ClientEntityQueryResponse;
import au.com.vodafone.clientservice.entity.ClientEntity;
import au.com.vodafone.clientservice.service.impl.ClientQueryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientQueryControllerTest {

    @InjectMocks
    ClientQueryController controller;

    @Mock
    ClientQueryServiceImpl service;

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
    void getClientById() throws Exception {
        ClientEntity client = new ClientEntity();
        client.setId(1L);
        client.setName("name");
        client.setEmail("name@email.com");

        when(service.getClient(anyLong())).thenReturn(client);
        when(modelMapper.map(any(), any())).thenReturn(ClientEntityQueryResponse
                .builder()
                .id(1L)
                .name("name")
                .email("name@email.com")
                .build());

        final MvcResult mvcResult = mvc.perform(get("/api/client/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        ObjectNode objectNode = (ObjectNode) mapper.readTree(actualResponse);
        assertEquals(1L, objectNode.get("id").asLong());
        assertEquals("name", objectNode.get("name").asText());
        assertEquals("name@email.com", objectNode.get("email").asText());
    }
}