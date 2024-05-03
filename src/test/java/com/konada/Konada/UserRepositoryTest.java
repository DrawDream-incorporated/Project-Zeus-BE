package com.konada.Konada;

import com.konada.Konada.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.UUID;


@DataJpaTest
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testNewUserPersistence() {
        // Create a new User
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("securePassword");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPreferredName("Tester");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Persist the user
        User savedUser = entityManager.persistFlushFind(user);

        // Assert the user was saved correctly
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat("testUser").isEqualTo("testUser");
    }
}
