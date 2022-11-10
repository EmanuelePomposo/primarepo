package it.begear.betalent.masterdata.crud.controller;


import it.begear.betalent.masterdata.commons.exceptions.BusinessException;
import it.begear.betalent.masterdata.crud.controller.constants.CrudControllerConstants;
import it.begear.betalent.masterdata.crud.service.PersonaCrudService;
import it.begear.betalent.masterdata.dto.PersonaDto;
import it.begear.betalent.masterdata.dto.searchDto.PersonaSearchInformation;
import it.begear.betalent.masterdata.dto.searchDto.commons.GenericListDTO;
import it.begear.betalent.masterdata.dto.searchDto.commons.GenericMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static it.begear.betalent.masterdata.crud.controller.constants.CrudControllerConstants.*;

@RestController
@RequestMapping(path = "/persona")
public class PersonaCrudController /*extends AbstractCrudController<Persona, Integer, PersonaDto>*/{
    
    @Autowired
    private PersonaCrudService service;

    /*@Override
    public Class<PersonaDto > getDtoClass(){return null;}*/

    @GetMapping(value = CrudControllerConstants.READ_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericListDTO<PersonaDto>> getAllAnagrafica(PersonaSearchInformation searchInformation) throws
            BusinessException {
        return ResponseEntity.ok(service.getAllServiziSvuotamento(searchInformation));
    }

    @GetMapping(value = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonaDto> get(@PathVariable(name = "id") Integer id) throws BusinessException {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping(value = UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonaDto> update(@PathVariable(name = "id") Integer id, @RequestBody PersonaDto dto) throws BusinessException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping(value = CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonaDto> create(@RequestBody PersonaDto dto) throws BusinessException {
        return ResponseEntity.ok(service.create(dto));
    }

    @DeleteMapping(value = DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericMessageDTO> delete(@PathVariable(name = "id") Integer id) throws BusinessException {
        service.delete(id);
        return ResponseEntity.ok(new GenericMessageDTO("OK"));
    }
}
