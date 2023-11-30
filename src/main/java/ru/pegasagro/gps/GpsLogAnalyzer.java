package ru.pegasagro.gps;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class GpsLogAnalyzer {

    public Double analyzeGpsLog() {
        try {
            Path filePath = Path.of("nmea.log");
            List<String> lines = Files.readAllLines(filePath);

            GpsLogProcessor gpsLogProcessor = new GpsLogProcessor();
            List<GpsData> gpsDataList = gpsLogProcessor.processGpsLog(lines);

            DistanceCalculator distanceCalculator = new DistanceCalculator();
            double totalDistance = distanceCalculator.calculateTotalDistance(gpsDataList);

            return totalDistance;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
