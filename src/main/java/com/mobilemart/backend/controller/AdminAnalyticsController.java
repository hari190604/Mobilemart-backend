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

    @GetMapping
    public ResponseEntity<ApiResponse> getAnalyticsByRange(@RequestParam(defaultValue = "30d") String range) {
        Object data;
        String title;
        if (range.equalsIgnoreCase("7d")) {
            data = adminAnalyticsService.getAnalyticsForLastNDays(7);
            title = "7 Days Analytics";
        } else if (range.equalsIgnoreCase("90d")) {
            data = adminAnalyticsService.getAnalyticsForLastNDays(90);
            title = "90 Days Analytics";
        } else if (range.equalsIgnoreCase("1y")) {
            data = adminAnalyticsService.get1YearAnalytics();
            title = "1 Year Analytics";
        } else if (range.equalsIgnoreCase("lifetime")) {
            data = adminAnalyticsService.getLifetimeAnalytics();
            title = "Lifetime Analytics";
        } else {
            // Default to 30d
            data = adminAnalyticsService.getAnalyticsForLastNDays(30);
            title = "30 Days Analytics";
        }
        return ResponseEntity.ok(new ApiResponse(true, title, data));
    }

    // Keep overall for fallback mapping
    @GetMapping("/overall")
    public ResponseEntity<ApiResponse> getOverallAnalytics() {
        return ResponseEntity.ok(new ApiResponse(true, "Overall Analytics", adminAnalyticsService.getOverallAnalytics()));
    }
}
