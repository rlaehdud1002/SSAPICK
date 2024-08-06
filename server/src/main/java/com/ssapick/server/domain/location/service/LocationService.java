package com.ssapick.server.domain.location.service;

import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    private final static String GEO_LOCATION_KEY = "geolocation:members";
    @Resource(name = "redisTemplate")
    private GeoOperations<String, String> geoOperations;

    @Transactional
    public void saveUserLocation(User user, LocationData.Geo geo) {
        Point point = new Point(geo.getLongitude(), geo.getLatitude());
        geoOperations.add(GEO_LOCATION_KEY, point, user.getUsername());
    }
}
