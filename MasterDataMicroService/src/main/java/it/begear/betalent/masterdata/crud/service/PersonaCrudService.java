package it.begear.betalent.masterdata.crud.service;

import it.begear.betalent.masterdata.commons.CheckExistenceBR;
import it.begear.betalent.masterdata.commons.CheckSameIdBR;
import it.begear.betalent.masterdata.commons.DecoratePagedDTOBR;
import it.begear.betalent.masterdata.commons.exceptions.BusinessException;
import it.begear.betalent.masterdata.crud.converter.PersonaModelConverter;
import it.begear.betalent.masterdata.crud.repository.PersonaRepository;
import it.begear.betalent.masterdata.dto.PersonaDto;
import it.begear.betalent.masterdata.dto.searchDto.PersonaSearchInformation;
import it.begear.betalent.masterdata.dto.searchDto.commons.GenericListDTO;
import it.begear.betalent.masterdata.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonaCrudService /*extends AbstractCrudService<Anagrafica, Integer>*/{

    @Autowired
    private PersonaRepository repository;
    @Autowired
    private CheckExistenceBR checkExistenceBR;
    @Autowired
    private CheckSameIdBR checkSameIdBR;
    @Autowired
    private DecoratePagedDTOBR decoratePagedDTOBR;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public GenericListDTO<PersonaDto> getAllServiziSvuotamento(PersonaSearchInformation searchInformation) throws BusinessException{
        try {
            Long totalCounts= repository.countElements(searchInformation);

            GenericListDTO<PersonaDto> list=new GenericListDTO<>();

            List<PersonaDto> collect=repository.readAllElements(searchInformation).stream().map(PersonaModelConverter.istance::modelToDto).collect(Collectors.toList());

            list.setList(collect);
            decoratePagedDTOBR.decorate(list, totalCounts, searchInformation);
            return list;
        } catch (Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public PersonaDto get(Integer id) throws BusinessException{
        checkExistenceBR.checkExistence(repository, id, Persona.Fields.id);
        Persona model = repository.findById(id).orElse(null);
        PersonaDto dto=PersonaModelConverter.istance.modelToDto(model);
        return dto;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public PersonaDto create(PersonaDto dto) throws BusinessException{
        Persona example=new Persona();
        if(dto.getNome()!=null && dto.getCognome()!=null &&
                dto.getDdn()!=null && dto.getEta()!=null){
            example=PersonaModelConverter.istance.dtoToModel(dto);
            checkExistenceBR.checkNotExistenceByExample(repository, Example.of(example), String.valueOf(dto.getId()));
        }
        Persona model=PersonaModelConverter.istance.dtoToModel(dto);
        model=repository.save(model);
        dto=PersonaModelConverter.istance.modelToDto(model);
        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public void delete(Integer id) throws BusinessException {
        checkExistenceBR.checkExistence(repository, id, Persona.Fields.id);
        repository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public PersonaDto update(Integer id, PersonaDto dto) throws BusinessException {
        checkExistenceBR.checkExistence(repository, id, Persona.Fields.id);
        checkSameIdBR.checkSameId(id, dto.getId());
        Persona model = repository.findById(id).orElse(null);
        PersonaModelConverter.istance.updateToModel(dto,model);
        model = repository.save(model);
        dto = PersonaModelConverter.istance.modelToDto(model);
        return dto;
    }


}
