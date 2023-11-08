package com.lastminute.recruitment.reader;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class PathHelper {
    static public URL fixPath(String originalPath) throws MalformedURLException {
        String fixedPath;
        if (originalPath.startsWith("/")) {
            fixedPath = "file:" + originalPath;
        } else if (originalPath.startsWith("file:") && originalPath.toLowerCase().contains(".jar!/")) {
            fixedPath = "jar:" + originalPath;
        } else {
            fixedPath = originalPath;
        }

        return URI.create(fixedPath).toURL();
    }
}
