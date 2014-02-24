/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package mysoft.sonar.plugins.css;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * Created by liux09 on 14-2-24.
 */
public class FileEncodingConveter {

    private static final Logger LOG = LoggerFactory
            .getLogger(FileEncodingConveter.class);

    private final static String TempFileSuffix = "temp.css";

    public static void Convert(String fileName) {
        File file = new File(fileName);
        File tempFile = new File(file + TempFileSuffix);

        try {
            UnicodeReader reader = new UnicodeReader(new FileInputStream(file), Charset.defaultCharset().name());
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile), Charset.defaultCharset().name());
            int len;
            while ((len = reader.read()) != -1) {
                writer.write(len);
                writer.flush();
            }
            reader.close();
            writer.close();

            file.delete();
            tempFile.renameTo(file);

        } catch (Exception e) {
            LOG.error(file + " encoding convert failed.", e);
        }
    }
}
