package ru.pegasagro.gps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gps")
public class GpsLogController {

    private final GpsLogAnalyzer gpsLogAnalyzer;

    public GpsLogController(GpsLogAnalyzer gpsLogAnalyzer) {
        this.gpsLogAnalyzer = gpsLogAnalyzer;
    }

    @GetMapping("/analyzeGpsLog")
    public Double analyzeGpsLog() {
        return gpsLogAnalyzer.analyzeGpsLog();
    }
}
