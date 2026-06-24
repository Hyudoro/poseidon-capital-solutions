package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    // --- findAll ---

    @Test
    void given_usersExist_when_findAll_then_returnAllUsers() {
        User user = buildUser("john", "Admin1!");
        given(userRepository.findAll()).willReturn(List.of(user));

        List<User> result = userService.findAll();

        assertThat(result).hasSize(1).containsExactly(user);
        then(userRepository).should().findAll();
    }

    @Test
    void given_noUsers_when_findAll_then_returnEmptyList() {
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        assertThat(userService.findAll()).isEmpty();
    }

    // --- findById ---

    @Test
    void given_validId_when_findById_then_returnUser() {
        User user = buildUser("john", "Admin1!");
        given(userRepository.findById(1)).willReturn(Optional.of(user));

        User result = userService.findById(1);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void given_invalidId_when_findById_then_throwIllegalArgumentException() {
        given(userRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    // --- save ---

    @Test
    void given_validPassword_when_save_then_encodePasswordAndSave() {
        User user = buildUser("john", "Passw0rd!");
        given(passwordEncoder.encode("Passw0rd!")).willReturn("encoded");
        given(userRepository.save(user)).willReturn(user);

        User result = userService.save(user);

        assertThat(result.getPassword()).isEqualTo("encoded");
        then(passwordEncoder).should().encode("Passw0rd!");
        then(userRepository).should().save(user);
    }

    @Test
    void given_nullPassword_when_save_then_throwIllegalArgumentException() {
        User user = buildUser("john", null);

        assertThatThrownBy(() -> userService.save(user))
                .isInstanceOf(IllegalArgumentException.class);

        then(userRepository).should(never()).save(any());
    }

    @Test
    void given_blankPassword_when_save_then_throwIllegalArgumentException() {
        User user = buildUser("john", "   ");

        assertThatThrownBy(() -> userService.save(user))
                .isInstanceOf(IllegalArgumentException.class);

        then(userRepository).should(never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "short1!",       // too short (< 8 chars)
            "alllowercase1!", // no uppercase
            "ALLUPPERCASE1!", // no lowercase
            "NoDigitHere!",  // no digit
            "NoSpecial123"   // no special character
    })
    void given_weakPassword_when_save_then_throwIllegalArgumentException(String password) {
        User user = buildUser("john", password);

        assertThatThrownBy(() -> userService.save(user))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password must be");

        then(userRepository).should(never()).save(any());
    }

    // --- update ---

    @Test
    void given_invalidId_when_update_then_throwBeforePasswordCheck() {
        given(userRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(99, buildUser("john", "Admin1!")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(passwordEncoder).should(never()).encode(any());
        then(userRepository).should(never()).save(any());
    }

    @Test
    void given_existingIdAndInvalidPassword_when_update_then_throwIllegalArgumentException() {
        User existing = buildUser("john", "Admin1!");
        given(userRepository.findById(1)).willReturn(Optional.of(existing));

        assertThatThrownBy(() -> userService.update(1, buildUser("john", "weak")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password must be");

        then(userRepository).should(never()).save(any());
    }

    @Test
    void given_existingIdAndValidPassword_when_update_then_encodePasswordSetIdAndSave() {
        User existing = buildUser("john", "Admin1!");
        User updated = buildUser("john_updated", "NewPass1!");
        given(userRepository.findById(1)).willReturn(Optional.of(existing));
        given(passwordEncoder.encode("NewPass1!")).willReturn("encodedNew");
        given(userRepository.save(updated)).willReturn(updated);

        User result = userService.update(1, updated);

        assertThat(result.getPassword()).isEqualTo("encodedNew");
        assertThat(updated.getId()).isEqualTo(1);
        then(passwordEncoder).should().encode("NewPass1!");
        then(userRepository).should().save(updated);
    }

    // --- delete ---

    @Test
    void given_existingId_when_delete_then_deleteFromRepository() {
        User user = buildUser("john", "Admin1!");
        given(userRepository.findById(1)).willReturn(Optional.of(user));

        userService.delete(1);

        then(userRepository).should().delete(user);
    }

    @Test
    void given_invalidId_when_delete_then_throwIllegalArgumentException() {
        given(userRepository.findById(99)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.delete(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");

        then(userRepository).should(never()).delete(any(User.class));
    }

    private User buildUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullname("Full Name");
        user.setRole("USER");
        return user;
    }
}
