package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.ProfileImage;
import org.zerock.apiserver.dto.ProfileImageDTO;
import org.zerock.apiserver.repository.ProfileImageRepository;
import org.zerock.apiserver.util.CustomServiceException;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProfileImageServiceImpl implements ProfileImageService {

    private final ProfileImageRepository imageRepository;

    @Override
    public ProfileImageDTO get(Long ino) {
        Optional<ProfileImage> result = imageRepository.findById(ino);
        ProfileImage image = result.orElseThrow(() -> new CustomServiceException("NOT_EXIST_IMAGE"));
        return entityToDTO(image);
    }

    @Override
    public Long register(ProfileImageDTO imageDTO) {
        ProfileImage image = dtoToEntity(imageDTO);
        ProfileImage result = imageRepository.save(image);
        return result.getPino();
    }

    @Override
    public void remove(Long ino) {
        if (!imageRepository.existsById(ino)) {
            throw new CustomServiceException("NOT_EXIST_IMAGE");
        }
        imageRepository.deleteById(ino);
    }

    @Override
    public ProfileImage dtoToEntity(ProfileImageDTO profileImageDTO) {

        if (profileImageDTO.getFileName() == null) {
            throw new NullPointerException();
        }

        return ProfileImage.builder()
                .pino(profileImageDTO.getPino())
                .fileName(profileImageDTO.getFileName())
                .build();
    }

}
