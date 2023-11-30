package ru.pegasagro.gps;

import java.util.List;

public class DistanceCalculator {

    public double calculateTotalDistance(List<GpsData> gpsDataList) {
        double totalDistance = 0;

        for (int i = 1; i < gpsDataList.size(); i++) {
            GpsData prevData = gpsDataList.get(i - 1);
            GpsData currentData = gpsDataList.get(i);

            if (prevData.getAltitude() != 0 && currentData.getAltitude() != 0) {
                double distance = calculateDistance(prevData.getLatitude(), prevData.getLongitude(),
                        currentData.getLatitude(), currentData.getLongitude());
                totalDistance += distance;
            }
        }

        return totalDistance;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int earthRadiusKm = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadiusKm * c;
    }
}
