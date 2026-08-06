package com.mobilemart.backend.controller;

import com.mobilemart.backend.dto.ApiResponse;
import com.mobilemart.backend.service.AdminAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/revenue")
public class AdminAnalyticsController {

    @Autowired
    private AdminAnalyticsService adminAnalyticsService;

    @GetMapping("/daily")
    public ResponseEntity<ApiResponse> getDailyAnalytics() {
        return ResponseEntity.ok(new ApiResponse(true, "Daily Analytics", adminAnalyticsService.getDailyAnalytics()));
    }

    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse> getMonthlyAnalytics() {
        return ResponseEntity.ok(new ApiResponse(true, "Monthly Analytics", adminAnalyticsService.getMonthlyAnalytics()));
    }

    @GetMapping("/yearly")
    public ResponseEntity<ApiResponse> getYearlyAnalytics() {
        return ResponseEntity.ok(new ApiResponse(true, "Yearly Analytics", adminAnalyticsService.getYearlyAnalytics()));
    }

    @GetMapping("/overall")
    public ResponseEntity<ApiResponse> getOverallAnalytics() {
        return ResponseEntity.ok(new ApiResponse(true, "Overall Analytics", adminAnalyticsService.getOverallAnalytics()));
    }
}
