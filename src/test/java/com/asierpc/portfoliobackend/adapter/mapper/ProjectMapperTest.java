package com.asierpc.portfoliobackend.adapter.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperTest {

    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);


    @Test
    void testCsvToArray() {
        String csv = "java,spring,backend";
        String[] result = ProjectMapper.csvToArray(csv);
        assertArrayEquals(new String[]{"java", "spring", "backend"}, result);
    }

    @Test
    void testCsvToArray_NullInput() {
        assertNull(ProjectMapper.csvToArray(null));
    }

    @Test
    void testArrayToCsv() {
        String[] tags = {"java", "spring", "backend"};
        String result = ProjectMapper.arrayToCsv(tags);
        assertEquals("java,spring,backend", result);
    }

    @Test
    void testArrayToCsv_NullInput() {
        assertNull(ProjectMapper.arrayToCsv(null));
    }

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