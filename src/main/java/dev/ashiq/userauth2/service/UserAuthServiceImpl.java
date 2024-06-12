package dev.ashiq.userauth2.service;

import dev.ashiq.userauth2.DTO.UserDto;
import dev.ashiq.userauth2.exception.PasswordDoesNotMatch;
import dev.ashiq.userauth2.exception.UserAlreadyExists;
import dev.ashiq.userauth2.exception.UserDoesNotExistException;
import dev.ashiq.userauth2.model.SessionStatus;
import dev.ashiq.userauth2.model.User;
import dev.ashiq.userauth2.model.UserSession;
import dev.ashiq.userauth2.repository.RoleRepository;
import dev.ashiq.userauth2.repository.UserRepository;
import dev.ashiq.userauth2.repository.UserSessionRepository;
import dev.ashiq.userauth2.util.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.*;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserSessionRepository sessionRepository;
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JWTToken jwtToken;

    private AuthenticationManager authenticationManager;


    private RoleRepository roleRepository;



@Autowired
    public UserAuthServiceImpl(UserRepository userRepository, UserSessionRepository sessionRepository,
                               PasswordEncoder passwordEncoder, JWTToken jwtToken,
                               AuthenticationManager authenticationManager,RoleRepository roleRepository
            ){
        this.userRepository=userRepository;
        this.sessionRepository=sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtToken=jwtToken;
        this.authenticationManager=authenticationManager;
        this.roleRepository=roleRepository;


    }


    @Override
    public UserDto signUp(String email, String pwd, String fullName,ArrayList<String> role) throws UserAlreadyExists {
        System.out.println(role);
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if(!optionalUser.isEmpty()) {
            throw new UserAlreadyExists("user exists with email:" + email);
        }
         User user = new User();

        user.setEmail(email);
        user.setPwd(passwordEncoder.encode(pwd));
        user.setFullName(fullName);
        System.out.print("fullName: ");
        System.out.println(fullName);
        String roles=role.isEmpty()?null: role.get(0);
        System.out.print("roles : ");
        System.out.println(roles);
//        String roles= "Admin";
        System.out.println(roleRepository.findByRole(roles));

        user.setRoles(Arrays.asList(roleRepository.findByRole(roles)) );

//       Set<RoleDef> setRole = new Set<RoleDef>( Set.of(roleRepository.findByName(role)));
//        user.setRoles(Collections.singletonList(roleRepository.findByRole(roles)));
        System.out.println(role);
        User savedUser=userRepository.save(user);

        return UserDto.from(savedUser);
    }
//    Set<RoleDef> convertStringSetToRoleSetWithStreams(final Set<String> rolesInString) {
//        return rolesInString.stream().map(roleInString -> {
//            final RoleDef role = new RoleDef();
//            role.setRole(roleInString);
//            return role;
//        }).collect(Collectors.toSet());
//    }
    @Override
    public ResponseEntity<UserDto>  login(String email, String pwd) throws UserDoesNotExistException, PasswordDoesNotMatch {

//        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
//        if(optionalUser.isEmpty()){
//            throw new UserDoesNotExistException("user with email:"+email + "doesn't exist");
//        }
//
//        User user =optionalUser.get();
//
//        if(!passwordEncoder.matches(pwd,user.getPwd() )){
//            throw new PasswordDoesNotMatch("password invalid! Check your password.");
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, pwd));
         if(!authenticate.isAuthenticated()){
             throw new UserDoesNotExistException("user with email:"+email +"doesn't exist");
         }
         User user = userRepository.findByEmail(email);
        String token = jwtToken.generateToken(email);

        MultiValueMapAdapter<String, String > headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN",token);


       UserSession userSession = new UserSession(jwtToken.expireAt);
        userSession.setSessionStatus(SessionStatus.ACTIVE);
        userSession.setExpireAt(userSession.getExpireAt());
        System.out.println("expiration :"+userSession.getExpireAt());
        userSession.setUser(user);
        userSession.setToken(token);
//        userSession.setExpireAt(token.);
        sessionRepository.save(userSession);


        UserDto userDto = UserDto.from(user);
        ResponseEntity<UserDto> response = new ResponseEntity<>(
                userDto,
                headers,
                HttpStatus.OK
        );
//       System.out.println(response);
        return response;
    }

    public void validateToken(String token) {
        jwtToken.validateToken(token);
    }

    @Override
    public Optional<UserDto> validate(String token, Long userId) {
        Optional<UserSession> optionalUserSession=sessionRepository.findByTokenAndUserId(token,userId);
        if(optionalUserSession.isEmpty()){
            return Optional.empty();
        }
        UserSession session=optionalUserSession.get();
        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return Optional.empty();
        }
        User user=userRepository.findById(userId).get();
        UserDto userDto= UserDto.from(user);
        return Optional.of(userDto);
    }

    @Override
    public ResponseEntity<Void> logOut(String token, Long userId) {
        Optional<UserSession> optionalUserSession=sessionRepository.findByTokenAndUserId(token, userId);
        if(optionalUserSession.isEmpty()){
            return null;
        }
        UserSession userSession=optionalUserSession.get();
        userSession.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(userSession);
        return ResponseEntity.ok().build();
    }


}
