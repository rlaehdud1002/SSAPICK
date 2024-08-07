package com.ssapick.server.domain.location.service;

import com.ssapick.server.domain.location.dto.LocationData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.springframework.data.redis.domain.geo.Metrics.METERS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    private final static String GEO_LOCATION_KEY = "geolocation:members";

    @Resource(name = "redisTemplate")
    private GeoOperations<String, String> geoOperations;

    @Transactional
    public void saveUserLocation(Long userId, LocationData.Geo geo) {
        Point point = new Point(geo.getLongitude(), geo.getLatitude());
        geoOperations.add(GEO_LOCATION_KEY, point, userId.toString());
    }

    public void findFriends(Long userId) {
//        geoOperations.search(GEO_LOCATION_KEY, );
        GeoReference<String> reference = GeoReference.fromMember(userId.toString());
        Distance distance = new Distance(500, METERS);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(10);

        GeoResults<RedisGeoCommands.GeoLocation<String>> search = geoOperations.search(GEO_LOCATION_KEY, reference, distance, args);

    }
}
