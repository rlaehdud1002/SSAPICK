package com.ssapick.server.domain.user.event;

import com.ssapick.server.domain.user.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class S3RemoveEvent {
    Profile profile;
}
