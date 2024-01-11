package com.kood.homework.translationapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kood.homework.translationapi.model.ApiResponse;
import com.kood.homework.translationapi.model.ErrorResponse;
import com.kood.homework.translationapi.model.ProjectLogger;
import com.kood.homework.translationapi.model.SuccessResponse;
import com.kood.homework.translationapi.model.TranslationLanguageParameter;
import com.kood.homework.translationapi.service.DeeplTranslatorService;
import com.kood.homework.translationapi.util.IpRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.System.Logger.Level;


/**
 * Controller handling requests related to supported translation languages.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p> 
 */
@RestController
@RequestMapping("/api/translate/languages")
public class TranslationLanguageController {

    @Autowired
    private IpRateLimiter ipRateLimiter;

    @Autowired
    private DeeplTranslatorService deepLTranslatorService;

    @Autowired
    private ProjectLogger logger;

    @Value("${translation.api.version}")
    private String apiVersion;

    @Value("${translation.api.language.ratelimit}")
    private int availableLanguagesRateLimit;

    private static final String API_LANGUAGE_PATH = "/api/translate/languages";


    /**
     * Endpoint to get the supported translation languages.
     *
     * @param request  The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @return ResponseEntity containing the API response.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getSupportedLanguages(HttpServletRequest request, HttpServletResponse response) {

        String ipAddress = request.getRemoteAddr();

        logger.log(Level.INFO, "Request recieved: " + request.getMethod() + " " + getUriWithParameters(request) + " " + request.getProtocol());

        try {
            if (!ipRateLimiter.tryAcquire(API_LANGUAGE_PATH, ipAddress, availableLanguagesRateLimit)) {
                logger.log(Level.WARNING, ipAddress + " is exceeding rate limit at " +  API_LANGUAGE_PATH);

                return new ResponseEntity<ApiResponse>(new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS,
                        "Rate limit exceeded. Current rate limit: "
                                + String.valueOf(availableLanguagesRateLimit),
                        getUriWithParameters(request), apiVersion).toJson(), null);
            }
            

            TranslationLanguageParameter languageRequest = new TranslationLanguageParameter(request.getParameter("type"));

            Object availableLanguages = deepLTranslatorService.getAvailableLanguages(languageRequest);


            SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK,
                    "Successfully returned available languages for type: "
                            + languageRequest.getType(),
                    getUriWithParameters(request), apiVersion);

            successResponse.addData("languages", availableLanguages);

            return new ResponseEntity<ApiResponse>(successResponse.toJson(), HttpStatus.OK);


        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        
            return new ResponseEntity<ApiResponse>(
                    new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(),
                            getUriWithParameters(request),
                            apiVersion).toJson(),
                    HttpStatus.BAD_REQUEST);


        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage(), e);

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