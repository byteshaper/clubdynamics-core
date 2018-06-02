package com.clubdynamics.core.domain.person;

import com.clubdynamics.dto.DateConverter;
import com.clubdynamics.dto.entity.person.PersonWriteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;
  
  public Person createPerson(PersonWriteDto personWriteDto, long clubId) {
    Person person = new Person();
    person.setClubId(clubId);
    person.setFirstName(personWriteDto.firstName);
    person.setLastName(personWriteDto.lastName);
    person.setMiddleNames(personWriteDto.middleNames);
    person.setBirthday(DateConverter.fromDayString(personWriteDto.birthday));
    return personRepository.save(person);
  }
}
