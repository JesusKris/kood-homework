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
import com.kood.homework.translationapi.model.SuccessResponse;
import com.kood.homework.translationapi.model.TranslationLanguageParameter;
import com.kood.homework.translationapi.service.DeepLTranslatorService;
import com.kood.homework.translationapi.util.IpRateLimiter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/translate/languages")
public class TranslationLanguageController {

    @Autowired
    private IpRateLimiter ipRateLimiter;

    @Autowired
    private DeepLTranslatorService deepLTranslatorService;

    @Value("${api.version}")
    private String apiVersion;

    private final int availableLanguagesRateLimit = 25;

    @GetMapping
    public ResponseEntity<ApiResponse> getSupportedLanguages(HttpServletRequest request,
            HttpServletResponse response) {

        String ipAddress = request.getRemoteAddr();
        try {

            if (!ipRateLimiter.tryAcquire("/api/language", ipAddress, availableLanguagesRateLimit)) {
                return new ResponseEntity<ApiResponse>(new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS,
                        "Rate limit exceeded. Current rate limit: "
                                + String.valueOf(availableLanguagesRateLimit),
                        getUriWithParameters(request), apiVersion).toJson(), null);
            }

            TranslationLanguageParameter languageRequest = new TranslationLanguageParameter(request.getParameter("type"));

            Object availableLanguages = deepLTranslatorService.getAvailaableLanguages(languageRequest);

            SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK,
                    "Successfully returned available languages for type: "
                            + languageRequest.getType(),
                    getUriWithParameters(request), apiVersion);

            successResponse.addData("languages", availableLanguages);

            return new ResponseEntity<ApiResponse>(successResponse.toJson(), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(
                    new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(),
                            getUriWithParameters(request),
                            apiVersion).toJson(),
                    HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
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
