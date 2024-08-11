package com.ssapick.server.domain.location.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.redis.domain.geo.Metrics.METERS;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    private final static String GEO_LOCATION_KEY = "geolocation:members";
    private final static String GEO_PROFILE_IMAGE_KEY = "geolocation:members:";
    private final static int LOCATION_LIMIT_TIME = 60 * 5;
    private final ProfileRepository profileRepository;


    @Resource(name = "redisTemplate")
    private GeoOperations<String, String> geoOperations;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

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

    public List<LocationData.Response> findFriends(User user) {
        GeoReference<String> reference = GeoReference.fromMember(user.getUsername());
        Distance distance = new Distance(500, METERS);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(10);

        GeoResults<RedisGeoCommands.GeoLocation<String>> search = geoOperations.search(GEO_LOCATION_KEY, reference, distance, args);

        assert search != null;

        return search.getContent().stream()
                .filter(geo -> !geo.getContent().getName().equals(user.getUsername()))
                .filter(geo -> valueOperations.get(GEO_PROFILE_IMAGE_KEY + geo.getContent().getName()) != null)
                .map(geo -> {
                    LocationData.Response response = new LocationData.Response();
                    String username = geo.getContent().getName();
                    Point point = geo.getContent().getPoint();
                    response.setUsername(username);
                    response.setProfileImage(GEO_PROFILE_IMAGE_KEY + valueOperations.get(username));
                    response.setDistance(geo.getDistance().getValue());
                    response.setPosition(LocationData.Position.of(point.getX(), point.getY()));
                    return response;
                }).toList();
    }
}
