package ru.pegasagro.gps;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GpsLogProcessor {

    public List<GpsData> processGpsLog(List<String> lines) {
        List<GpsData> gpsDataList = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("$GPGGA")) {
                GpsData gpsData = parseGpggaLine(line);
                if (gpsData != null) {
                    gpsDataList.add(gpsData);
                }
            }
        }

        return gpsDataList;
    }

    private GpsData parseGpggaLine(String line) {
        String[] tokens = line.split(",");
        if (tokens.length >= 7) {
            String latitude = tokens[2];
            String longitude = tokens[4];
            String altitudeString = tokens[9];
            double altitude = altitudeString.isEmpty() ? 0.0 : Double.parseDouble(altitudeString);

            if (StringUtils.isNoneBlank(latitude, longitude)) {
                return new GpsData(Double.parseDouble(latitude), Double.parseDouble(longitude), altitude);
            }
        }
        return null;
    }
}
