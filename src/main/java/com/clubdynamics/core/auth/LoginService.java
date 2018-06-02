package com.clubdynamics.core.auth;

import com.clubdynamics.core.domain.user.User;
import com.clubdynamics.core.domain.user.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
  
	@Autowired
  private UserRepository userRepository;
  
	@Autowired
  private JwtTokenService jwtTokenService;
  

  /**
   * Tries to login user. 
   * 
   * @param clubId clubId
   * @param username username
   * @param password password (plaintext)
   * @return optional JWT token in case of success, empty otherwise (username not found or wrong password)
   */
  public Optional<String> login(long clubId, String username, String password) {
    Optional<User> optionalUser = userRepository.getUserByClubIdAndUsername(clubId, username);
    
    if(optionalUser.isPresent()) {
      User user = optionalUser.get();
      final String passwordHash = CryptoUtil.encryptPassword(password, user.getSalt());
      
      if(passwordHash.equals(user.getPasswordHash())) {
        return Optional.of(jwtTokenService.generateJwtToken(user));
      }
    }
    
    return Optional.empty();
  }
}
