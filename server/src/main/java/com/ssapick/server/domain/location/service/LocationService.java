package com.ssapick.server.domain.location.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ssapick.server.core.constants.PickConst.PICK_USER_LOCATION_COIN;
import static org.springframework.data.redis.domain.geo.Metrics.METERS;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    private final static String GEO_LOCATION_KEY = "geolocation:members";
    private final static String GEO_LOCATION_COIN_KEY = "geolocation:coins:";
    private final static String GEO_PROFILE_IMAGE_KEY = "geolocation:members:";
    private final static int LOCATION_LIMIT_TIME = 60 * 5;
    private final ProfileRepository profileRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ApplicationEventPublisher publisher;

    @Resource(name = "redisTemplate")
    private GeoOperations<String, Object> geoOperations;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> valueOperations;
    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    @Transactional
    public void saveUserLocation(String username, LocationData.Geo geo) {
        if (!StringUtils.hasText(username)) {
            throw new BaseException(ErrorCode.NOT_FOUND_USER);
        }
        String profileImage = profileRepository.findProfileImageByUsername(username)
                .orElse("");
        valueOperations.set(GEO_PROFILE_IMAGE_KEY + username, profileImage, LOCATION_LIMIT_TIME, TimeUnit.SECONDS);
        Point point = new Point(geo.getLongitude(), geo.getLatitude());
        geoOperations.add(GEO_LOCATION_KEY, point, username);
    }

    @Transactional
    public void pickUser(User user, String username) {
        String key = GEO_LOCATION_COIN_KEY + user.getId();
        boolean hasKey = Boolean.TRUE.equals(redisTemplate.hasKey(key));

        if (hasKey && setOperations.size(key) > 10) {
            throw new BaseException(ErrorCode.SERVER_ERROR);
        }

        setOperations.add(key, username);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = now.toLocalDate().atTime(23, 59, 59);
        long remain = ChronoUnit.SECONDS.between(now, endOfDay);
        redisTemplate.expire(key, remain, TimeUnit.SECONDS);

        publisher.publishEvent(new PickcoEvent(user, PickcoLogType.PICK_USER_LOCATION, PICK_USER_LOCATION_COIN));
    }

    public LocationData.Response findFriends(User user) {
        String key = GEO_LOCATION_COIN_KEY + user.getId();
        GeoReference<Object> reference = GeoReference.fromMember(user.getUsername());
        Distance distance = new Distance(500, METERS);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(10);
        try {
            GeoResults<RedisGeoCommands.GeoLocation<Object>> search = geoOperations.search(GEO_LOCATION_KEY, reference, distance, args);

            assert search != null;

            List<LocationData.GeoLocation> geoLocations = search.getContent().stream()
                    .filter(geo -> !geo.getContent().getName().equals(user.getUsername()))
                    .filter(geo -> valueOperations.get(GEO_PROFILE_IMAGE_KEY + geo.getContent().getName()) != null)
                    .filter(geo -> Boolean.FALSE.equals(setOperations.isMember(key, geo.getContent().getName())))
                    .map(geo -> {
                        LocationData.GeoLocation location = new LocationData.GeoLocation();
                        String username = geo.getContent().getName().toString();
                        Point point = geo.getContent().getPoint();
                        location.setUsername(username);
                        location.setProfileImage(Objects.requireNonNull(valueOperations.get(GEO_PROFILE_IMAGE_KEY + username)).toString());
                        location.setDistance(geo.getDistance().getValue());
                        location.setPosition(LocationData.Position.of(point.getX(), point.getY()));
                        return location;
                    }).toList();

            return new LocationData.Response(setOperations.size(key), geoLocations);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage(), e);
        }
        return new LocationData.Response(0L, List.of());
    }
}
