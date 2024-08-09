package com.ssapick.server.domain.location.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.data.redis.domain.geo.Metrics.METERS;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    private final static String GEO_LOCATION_KEY = "geolocation:members";
    private final static String GEO_PROFILE_IMAGE_KEY = "geolocation:members:profile";

    @Resource(name = "redisTemplate")
    private GeoOperations<String, String> geoOperations;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOperations;

    @Transactional
    public void saveUserLocation(String username, LocationData.Request request) {
        if (!StringUtils.hasText(username)) {
            throw new BaseException(ErrorCode.NOT_FOUND_USER);
        }
        hashOperations.put(GEO_PROFILE_IMAGE_KEY, username, request.getProfileImage());
        Point point = new Point(request.getGeo().getLongitude(), request.getGeo().getLatitude());
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

        return search.getContent().stream().map(geo -> {
            LocationData.Response response = new LocationData.Response();
            String username = geo.getContent().getName();
            Point point = geo.getContent().getPoint();
            response.setUsername(username);
            response.setProfileImage(hashOperations.get(GEO_PROFILE_IMAGE_KEY, username));
            response.setDistance(geo.getDistance().getValue());
            response.setPosition(LocationData.Position.of(point.getX(), point.getY()));
            return response;
        }).toList();
    }
}
