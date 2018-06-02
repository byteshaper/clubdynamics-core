package com.clubdynamics.core.domain.person;

import com.clubdynamics.core.domain.AbstractRestIntegrationTest;
import com.clubdynamics.core.testutil.AssertAllFieldsEqual;
import com.clubdynamics.dto.entity.login.LoginDto;
import com.clubdynamics.dto.entity.person.PersonReadDto;
import com.clubdynamics.dto.entity.person.PersonWriteDto;
import org.junit.Test;

public class PersonRestIntegrationTest extends AbstractRestIntegrationTest {

  @Test
  public void createPersonSuccessDefaultUser() {
    createPersonSuccess(DEFAULT_USERNAME, DEFAULT_USER_PASSWORD);
  }

  private void createPersonSuccess(String username, String password) {
    PersonWriteDto personCreate = new PersonWriteDto("Michael", "Phelps", "", "1985-06-30");
    LoginDto login = new LoginDto(username, password);
    PersonReadDto personRead = loginAndPostToClubSuccess("persons", personCreate, login, PersonReadDto.class);
    AssertAllFieldsEqual.assertEqual(personCreate, personRead);
  }
}
