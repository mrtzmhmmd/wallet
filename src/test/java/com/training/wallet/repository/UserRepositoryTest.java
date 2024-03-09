package com.training.wallet.repository;

import com.training.wallet.domain.enums.Role;
import com.training.wallet.domain.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreateReadDelete() {
        User user = new User(1L,
                "Morteza",
                "Mohammadi",
                "morteza@mail.com",
                "12345",
                Role.USER);
        userRepository.save(user);
        Iterable<User> employees = userRepository.findAll();
        Assertions.assertThat(employees).extracting(User::getFirstName).containsOnly("Morteza");
        userRepository.deleteAll();
        Assertions.assertThat(userRepository.findAll()).isEmpty();
    }
}