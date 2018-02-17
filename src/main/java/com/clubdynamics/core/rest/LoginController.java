package com.clubdynamics.core.rest;

import com.clubdynamics.dto.login.LoginDto;
import com.clubdynamics.dto.login.LoginResultDto;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping
public class LoginController {

  @Autowired
  
  @PostMapping(ApiPaths.LOGIN)
  public LoginResultDto login(@PathParam("clubId") @NotNull Long clubId, @RequestBody @NotNull LoginDto login) {
    return null;
  }
}
