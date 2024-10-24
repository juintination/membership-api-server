package org.zerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.dto.ProfileImageDTO;
import org.zerock.apiserver.service.ProfileImageService;
import org.zerock.apiserver.util.CustomFileUtil;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/profiles/image")
public class ProfileImageController {

    private final ProfileImageService imageService;

    private final CustomFileUtil fileUtil;

    @GetMapping("/{pino}")
    public ProfileImageDTO get(@PathVariable("pino") Long pino) {
        return imageService.get(pino);
    }

    @GetMapping("/view/{pino}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("pino") Long pino) {
        String fileName = get(pino).getFileName();
        return fileUtil.getFile(fileName);
    }

    @PostMapping("/")
    public Map<String, Long> register(ProfileImageDTO profileImageDTO) throws IOException {
        MultipartFile file = profileImageDTO.getFile();
        String uploadFileName = fileUtil.saveFile(file);
        profileImageDTO.setFileName(uploadFileName);
        log.info("-------------------------------------");
        log.info(uploadFileName);
        Long ino = imageService.register(profileImageDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("ino", ino);
    }

    @DeleteMapping("/{pino}")
    public Map<String, String> remove(@PathVariable(name="pino") Long pino) {
        String oldFileName = imageService.get(pino).getFileName();
        imageService.remove(pino);
        fileUtil.deleteFile(oldFileName);
        return Map.of("RESULT", "SUCCESS");
    }

}
