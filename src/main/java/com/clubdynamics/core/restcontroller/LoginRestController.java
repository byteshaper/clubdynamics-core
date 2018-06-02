package com.clubdynamics.core.restcontroller;

import com.clubdynamics.core.auth.LoginService;
import com.clubdynamics.core.exception.InvalidCredentialsException;
import com.clubdynamics.dto.entity.login.LoginDto;
import com.clubdynamics.dto.entity.login.LoginResultDto;
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
public class LoginRestController {

	@Autowired
	private LoginService loginService;
  
  @PostMapping(ApiPaths.LOGIN)
  public LoginResultDto login(@PathVariable("clubId") @NotNull Long clubId, @RequestBody @NotNull @Valid LoginDto login) {
  	
  	String jwtToken = loginService
  	    .login(clubId, login.username, login.password).orElseThrow(() -> new InvalidCredentialsException(login));
  	return new LoginResultDto(jwtToken);
  }
}