package com.kood.homework.translationapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kood.homework.translationapi.model.ApiResponse;
import com.kood.homework.translationapi.model.ErrorResponse;
import com.kood.homework.translationapi.model.SuccessResponse;
import com.kood.homework.translationapi.model.TranslationParameter;
import com.kood.homework.translationapi.service.DeeplTranslatorService;
import com.kood.homework.translationapi.service.TranslationHistoryService;
import com.kood.homework.translationapi.util.IpRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Controller handling requests related to translation process.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
@RestController
@RequestMapping("/api/translate")
public class TranslationController {

        @Autowired
        private IpRateLimiter ipRateLimiter;

        @Autowired
        private DeeplTranslatorService deepLTranslatorService;

        @Autowired
        private TranslationHistoryService translationHistoryService;

        @Value("${translation.api.version}")
        private String apiVersion;

        @Value("${translation.api.translate.ratelimit}")
        private int translateTextRateLimit;


        /**
         * Endpoint to translate input text.
         *
         * @param request  The HTTP servlet request.
         * @param response The HTTP servlet response.
         * @return ResponseEntity containing the API response.
         */
        @PostMapping
        public ResponseEntity<ApiResponse> translate(HttpServletRequest request, HttpServletResponse response) {

                String ipAddress = request.getRemoteAddr();

                try {
                        if (!ipRateLimiter.tryAcquire("/api/translate", ipAddress, translateTextRateLimit)) {
                                return new ResponseEntity<ApiResponse>(new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS,
                                                "Rate limit exceeded. Current rate limit: "
                                                                + String.valueOf(translateTextRateLimit),
                                                getUriWithParameters(request), apiVersion).toJson(),
                                                HttpStatus.TOO_MANY_REQUESTS);
                        }


                        String input = request.getParameter("input");
                        String source_lang = request.getParameter("source_lang");
                        String target_lang = request.getParameter("target_lang");

                        
                        String translatedText = deepLTranslatorService
                                        .translateText(new TranslationParameter(input,
                                                        source_lang,
                                                        target_lang));

                        SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK,
                                        "Successfully translated text",
                                        getUriWithParameters(request), apiVersion);

                        successResponse.addData("translation", translatedText);

                        translationHistoryService.saveTranslation(input, translatedText, source_lang,
                                        target_lang);

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