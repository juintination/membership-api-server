package org.zerock.apiserver.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.apiserver.dto.ProfileImageDTO;

import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProfileImageServiceTests {

    @Autowired
    private ProfileImageService profileImageService;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(profileImageService, "ProfileImageService should not be null");
        log.info(profileImageService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {

        ProfileImageDTO profileImageDTO = ProfileImageDTO.builder()
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .build();
        Long pino = profileImageService.register(profileImageDTO);
        log.info(pino);

    }

    @Test
    public void testGet() {
        Long pino = 1L;
        ProfileImageDTO profileImageDTO = profileImageService.get(pino);
        Assertions.assertNotNull(profileImageDTO);
        log.info(profileImageDTO);
    }

}
