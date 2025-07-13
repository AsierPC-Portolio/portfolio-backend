package com.asierpc.portfoliobackend.adapter.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperTest {

    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    @Test
    void testStringToUri() {
        String url = "https://example.com";
        URI uri = mapper.stringToUri(url);
        assertNotNull(uri);
        assertEquals(url, uri.toString());
    }

    @Test
    void testStringToUri_NullInput() {
        assertNull(mapper.stringToUri(null));
    }

    @Test
    void testUriToString() {
        URI uri = URI.create("https://example.com");
        String result = mapper.uriToString(uri);
        assertEquals("https://example.com", result);
    }

    @Test
    void testUriToString_NullInput() {
        assertNull(mapper.uriToString(null));
    }
}