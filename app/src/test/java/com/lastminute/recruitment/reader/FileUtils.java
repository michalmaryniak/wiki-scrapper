package com.lastminute.recruitment.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtils {
    static public String readResource(final Class<?> clazz, final String location) throws IOException, URISyntaxException {
        final URL resource = clazz.getResource(location);
        Objects.requireNonNull(resource, String.format("Cannot find resource: %s", location));
        return Files.readString(Paths.get(resource.toURI()));
    }
}