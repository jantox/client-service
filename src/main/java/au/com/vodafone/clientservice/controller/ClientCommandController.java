package au.com.vodafone.clientservice.controller;

import au.com.vodafone.clientservice.domain.ClientEntityCommandRequest;
import au.com.vodafone.clientservice.domain.ClientEntityQueryResponse;
import au.com.vodafone.clientservice.domain.StatusResponse;
import au.com.vodafone.clientservice.entity.ClientEntity;
import au.com.vodafone.clientservice.service.ClientCommandService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller class for command endpoints where data will be added/updated/deleted
 *
 */
@Controller
@RequestMapping(value = "/api/client")
public class ClientCommandController {

    final ClientCommandService service;
    final ModelMapper modelMapper;

    public ClientCommandController(ClientCommandService service,
                                   ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StatusResponse addClient(@RequestBody ClientEntityCommandRequest request){
        service.addClient(convertToEntity(request));
        return StatusResponse.builder().msg("Success").build();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public StatusResponse deleteClientById(@PathVariable Long id){
        service.deleteClient(id);
        return StatusResponse.builder().msg("Done").build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public StatusResponse updateClientById(@PathVariable Long id, @RequestBody ClientEntityCommandRequest request){
        service.updateClient(id, convertToEntity(request));
        return StatusResponse.builder().msg("OK").build();
    }

    private ClientEntity convertToEntity(ClientEntityCommandRequest request) {
        return modelMapper.map(request, ClientEntity.class);
    }
}
