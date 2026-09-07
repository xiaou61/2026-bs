package com.xiaou.recruitment;

import org.junit.jupiter.api.Test;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPatternParser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublicRoutePatternTest {

    private final PathPatternParser parser = new PathPatternParser();

    @Test
    void detailRoutesShouldOnlyMatchNumericIds() {
        assertTrue(parser.parse("/api/job/{id:\\d+}").matches(PathContainer.parsePath("/api/job/1")));
        assertFalse(parser.parse("/api/job/{id:\\d+}").matches(PathContainer.parsePath("/api/job/publish")));

        assertTrue(parser.parse("/api/company/{id:\\d+}").matches(PathContainer.parsePath("/api/company/2")));
        assertFalse(parser.parse("/api/company/{id:\\d+}").matches(PathContainer.parsePath("/api/company/update")));

        assertTrue(parser.parse("/api/experience/{id:\\d+}").matches(PathContainer.parsePath("/api/experience/3")));
        assertFalse(parser.parse("/api/experience/{id:\\d+}").matches(PathContainer.parsePath("/api/experience/create")));

        assertTrue(parser.parse("/api/referral/{id:\\d+}").matches(PathContainer.parsePath("/api/referral/4")));
        assertFalse(parser.parse("/api/referral/{id:\\d+}").matches(PathContainer.parsePath("/api/referral/publish")));
    }
}
