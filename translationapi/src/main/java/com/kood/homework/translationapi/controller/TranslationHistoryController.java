package com.kood.homework.translationapi.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kood.homework.translationapi.entity.TranslationEntity;
import com.kood.homework.translationapi.model.ApiResponse;
import com.kood.homework.translationapi.model.ErrorResponse;
import com.kood.homework.translationapi.model.SuccessResponse;
import com.kood.homework.translationapi.model.TranslationHistoryParameter;
import com.kood.homework.translationapi.service.TranslationHistoryService;
import com.kood.homework.translationapi.util.IpRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Controller handling requests related to translation history.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p> 
 */
@RestController
@RequestMapping("/api/translate/history")
public class TranslationHistoryController {

    @Autowired
    private IpRateLimiter ipRateLimiter;

    @Autowired
    private TranslationHistoryService translationHistoryService;

    @Value("${translation.api.version}")
    private String apiVersion;

    @Value("${translation.api.history.ratelimit}")
    private int translationHistoryRateLimit;


    /**
     * Endpoint to get the translation history.
     *
     * @param request  The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @return ResponseEntity containing the API response.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getTranslationHistory(HttpServletRequest request, HttpServletResponse response) {

        String ipAddress = request.getRemoteAddr();

        try {
            if (!ipRateLimiter.tryAcquire("/api/translate", ipAddress, translationHistoryRateLimit)) {
                return new ResponseEntity<ApiResponse>(new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS,
                        "Rate limit exceeded. Current rate limit: "
                                + String.valueOf(translationHistoryRateLimit),
                        getUriWithParameters(request), apiVersion).toJson(),
                        HttpStatus.TOO_MANY_REQUESTS);
            }
            

            List<TranslationEntity> translationHistory = translationHistoryService
                    .getTranslationHistory(new TranslationHistoryParameter(request.getParameter("order")));

            SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK,
                    "Successfully recieved translation history",
                    getUriWithParameters(request), apiVersion);

            successResponse.addData("translationHistory", translationHistory);

            return new ResponseEntity<ApiResponse>(successResponse.toJson(), HttpStatus.OK);


        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(
                    new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(),
                            getUriWithParameters(request),
                            apiVersion).toJson(),
                    HttpStatus.BAD_REQUEST);

                    
        } catch (Exception e) {
            System.out.print(e);
            return new ResponseEntity<ApiResponse>(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                            getUriWithParameters(request),
                            apiVersion).toJson(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getUriWithParameters(HttpServletRequest request) {
        return request.getRequestURI() + "?" + request.getQueryString();
    }
}