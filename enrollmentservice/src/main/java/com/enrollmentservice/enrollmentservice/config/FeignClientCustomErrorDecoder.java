package com.enrollmentservice.enrollmentservice.config;

import com.enrollmentservice.enrollmentservice.exception.NotFoundException;
import com.enrollmentservice.enrollmentservice.exception.ServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignClientCustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();

        log.error("Feign error → Method: {}, Status: {}", methodKey, status);

        if (methodKey.contains("getStudentById")) {

            if (status == 404)
                return new NotFoundException("Student Not Found By Given ID");

            if (status == 400)
                return new RuntimeException("Bad Request has been sent to Student Service");

            if ((status >= 500))
                return new ServiceUnavailableException("Student Service is unavailable");

        }

        if (methodKey.contains("getCourseById")) {

            if (status == 404)
                return new NotFoundException("Course Not Found by given ID");

            if (status == 400)
                return new RuntimeException("Bad Request has been sent to Course Service");

            if (status >= 500)
                return new ServiceUnavailableException("Course Service is unavailable");
        }

        // Fallback exception handling
        if (status == 404)
            return new NotFoundException("Resource Not Found");

        if (status == 400)
            return new RuntimeException("Bad Request has been sent to Service");

        if (status >= 500)
            return new ServiceUnavailableException("Service is unavailable");

        return new RuntimeException("Unknown Feign Error");
    }
}
