package au.com.vodafone.clientservice.controller;

import au.com.vodafone.clientservice.domain.ClientEntityQueryResponse;
import au.com.vodafone.clientservice.entity.ClientEntity;
import au.com.vodafone.clientservice.service.ClientQueryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for query endpoints where data will be fetched
 *
 */
@Controller
@RequestMapping(value = "/api/client")
public class ClientQueryController {

    final ClientQueryService service;
    final ModelMapper modelMapper;

    public ClientQueryController(ClientQueryService service,
                                 ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ClientEntityQueryResponse> getAllClients(){
        List<ClientEntity> clientEntities = service.getAllClients();
        return clientEntities
                .stream()
                .map(clientEntity -> convertEntityToResponse(clientEntity))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ClientEntityQueryResponse getClientById(@PathVariable Long id){
        ClientEntity clientEntity = service.getClient(id);
        return convertEntityToResponse(clientEntity);
    }

    private ClientEntityQueryResponse convertEntityToResponse(ClientEntity entity) {
        return modelMapper.map(entity, ClientEntityQueryResponse.class);
    }
}
