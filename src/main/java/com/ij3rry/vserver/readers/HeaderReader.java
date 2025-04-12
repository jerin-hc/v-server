package com.ij3rry.vserver.readers;

import com.ij3rry.vserver.exceptions.InvalidHeaderException;
import com.ij3rry.vserver.http.data.HttpRequest;

import java.io.BufferedReader;

public interface HeaderReader {
    void read(HttpRequest request) throws InvalidHeaderException;
}
