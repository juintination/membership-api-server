package org.zerock.apiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.apiserver.domain.ProfileImage;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProfileImageRepositoryTests {

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(profileImageRepository, "ProfileImageRepository should not be null");
        log.info(profileImageRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsertProfileImage() {
        ProfileImage profileImage = profileImageRepository.save(ProfileImage.builder()
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .build());
        log.info(profileImage);
    }

    @Test
    public void testRead() {

        Long pino = 1L;
        Optional<ProfileImage> result = profileImageRepository.findById(pino);
        ProfileImage profileImage = result.orElseThrow();

        Assertions.assertNotNull(profileImage);
        log.info(profileImage);
    }

    @Test
    public void testDelete() {
        Long pino = 1L;
        profileImageRepository.deleteById(pino);
        Optional<ProfileImage> result = profileImageRepository.findById(pino);
        Assertions.assertEquals(result, Optional.empty());
        log.info(result);
    }

}
