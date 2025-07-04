    package com.security.jwt.controller;

    import com.security.jwt.dto.request.UserRequestDto;
    import com.security.jwt.service.UserService;
    import jakarta.validation.Valid;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

        @RestController
        @RequestMapping("users")
        public class UserController {

            private final UserService userService;
            public UserController(UserService userService) {
                this.userService = userService;
            }

            @PostMapping("/register")
            public ResponseEntity<String> SaveUser(@Valid @RequestBody UserRequestDto userRequestDto){
              String required = userService.createUser(userRequestDto);
              return ResponseEntity.status(HttpStatus.CREATED).body(required);
            }
        }
