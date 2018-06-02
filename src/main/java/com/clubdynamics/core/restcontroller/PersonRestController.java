package com.clubdynamics.core.restcontroller;

import com.clubdynamics.core.domain.person.Person;
import com.clubdynamics.core.domain.person.PersonService;
import com.clubdynamics.dto.DateConverter;
import com.clubdynamics.dto.entity.person.PersonReadDto;
import com.clubdynamics.dto.entity.person.PersonWriteDto;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping
public class PersonRestController {
  
  @Autowired
  private PersonService personService;

  @PostMapping(path = ApiPaths.PERSON_CREATE)
  public PersonReadDto createPerson(@PathVariable("clubId") @NotNull Long clubId, @RequestBody @NotNull @Valid PersonWriteDto personWriteDto) {
    Person person = personService.createPerson(personWriteDto, clubId);
    return new PersonReadDto(
        person.getId(), 
        person.getFirstName(), 
        person.getLastName(), 
        person.getMiddleNames(), 
        DateConverter.toDayString(person.getBirthday()));
  }
}
