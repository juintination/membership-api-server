package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.ProfileImage;
import org.zerock.apiserver.dto.ProfileImageDTO;
import org.zerock.apiserver.repository.ProfileImageRepository;
import org.zerock.apiserver.util.CustomServiceException;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProfileImageServiceImpl implements ProfileImageService {

    private final ProfileImageRepository profileImageRepository;

    @Override
    public ProfileImageDTO get(Long pino) {
        Object result = profileImageRepository.getProfileImageByPino(pino);
        if (result == null) {
            throw new CustomServiceException("NOT_EXIST_IMAGE");
        }

        Object[] arr = (Object[]) result;
        return entityToDTO((ProfileImage) arr[0], (Member) arr[1]);
    }

    @Override
    public Long register(ProfileImageDTO profileImageDTO) {
        ProfileImage profileImage = dtoToEntity(profileImageDTO);
        ProfileImage result = profileImageRepository.save(profileImage);
        return result.getPino();
    }

    @Override
    public void remove(Long pino) {
        ProfileImage profileImage = profileImageRepository.findById(pino)
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_IMAGE"));
        profileImage.getMember().removeMemberAssociation();
        profileImageRepository.delete(profileImage);
    }

    @Override
    public ProfileImage dtoToEntity(ProfileImageDTO profileImageDTO) {

        if (profileImageDTO.getFileName() == null) {
            throw new NullPointerException();
        }

        Member member = Member.builder()
                .mno(profileImageDTO.getMno())
                .build();

        return ProfileImage.builder()
                .pino(profileImageDTO.getPino())
                .fileName(profileImageDTO.getFileName())
                .member(member)
                .build();
    }

}
