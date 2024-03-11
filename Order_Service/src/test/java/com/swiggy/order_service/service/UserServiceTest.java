package com.swiggy.order_service.service;

import com.swiggy.order_service.entity.Location;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testAdd() {
        User user = mock(User.class);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("pass");

        User result = userService.add("testUser", "pass", mock(Location.class));

        assertEquals(result,user);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
