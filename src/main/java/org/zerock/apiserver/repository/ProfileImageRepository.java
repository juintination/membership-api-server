package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.domain.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
