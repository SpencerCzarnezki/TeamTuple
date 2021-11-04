package learn.events.controllers;

import learn.events.models.User;
import learn.events.security.JwtConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
=======
import javax.servlet.http.Cookie;
>>>>>>> origin/main
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final PasswordEncoder encoder;

<<<<<<< HEAD
    public AuthController(AuthenticationManager authenticationManager,
                          JwtConverter converter,
                          PasswordEncoder encoder) {
=======
    public AuthController(AuthenticationManager authenticationManager, JwtConverter converter, PasswordEncoder encoder) {
>>>>>>> origin/main
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.encoder = encoder;
    }

<<<<<<< HEAD
=======

>>>>>>> origin/main
    @PostMapping("/authenticate")
    public ResponseEntity<Object> login(@RequestBody HashMap<String, String> credentials,
                                        HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.get("username"),
                        credentials.get("password"));

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();
<<<<<<< HEAD
                String jwtToken = converter.getTokenFromUser(user);

                HashMap<String, Object> map = new HashMap<>();
                map.put("jwt", jwtToken);

                return new ResponseEntity<>(map, HttpStatus.OK);
=======
                return getObjectResponseEntity(user, response);
>>>>>>> origin/main
            }

        } catch (AuthenticationException ex) {
            System.out.println(ex);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

<<<<<<< HEAD
    @PostMapping("/refresh_token")
    public ResponseEntity<Object> refresh(@AuthenticationPrincipal User user,
                                          HttpServletResponse response) {
        String jwtToken = converter.getTokenFromUser(user);

        HashMap<String, Object> map = new HashMap<>();
        map.put("jwt", jwtToken);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
=======
    @PostMapping("/expire_token")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Object> refresh(@AuthenticationPrincipal User user,
                                          HttpServletResponse response) {
        return getObjectResponseEntity(user, response);
    }



    @PostMapping("/encode")
    public ResponseEntity encode(@RequestBody HashMap<String, String> map) {
        System.out.println();
        System.out.println("ENCODED: " + encoder.encode(map.get("value")));
        System.out.println();
        return new ResponseEntity(HttpStatus.OK);
    }


    private ResponseEntity<Object> getObjectResponseEntity(@AuthenticationPrincipal User user, HttpServletResponse response) {
        String jwtToken = converter.getTokenFromUser(user);

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("authorities", user.getAuthorityNames());

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
>>>>>>> origin/main
