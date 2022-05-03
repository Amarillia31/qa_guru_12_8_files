package com.elena;

import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;
import com.google.gson.Gson;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;



public class FilesParsingZip {
    ClassLoader classLoader = getClass().getClassLoader();


    @Test
    @DisplayName(value = "Parse PDF")
    void pdfParsingTest() throws Exception {
        ZipFile zip = new ZipFile(new File("src/test/resources/sample.zip"));
        ZipInputStream is = new ZipInputStream(classLoader.getResourceAsStream("sample.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            try (InputStream inputStream = zip.getInputStream(entry)) {
                if (entry.getName().endsWith(".pdf")) {
                    PDF pdf = new PDF(inputStream);
                    Assertions.assertEquals(166, pdf.numberOfPages);
                }
            }
        }
    }

    @Test
    @DisplayName(value = "Parse Xls")
    void xlsParsingTest() throws Exception {
        ZipFile zip = new ZipFile(new File("src/test/resources/sample.zip"));
        ZipInputStream is = new ZipInputStream(classLoader.getResourceAsStream("sample.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            try (InputStream inputStream = zip.getInputStream(entry)) {
                if (entry.getName().endsWith(".xlsx")) {
                    XLS xls = new XLS(inputStream);
                    String stringCellValue = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    org.assertj.core.api.Assertions.assertThat(stringCellValue).isEqualTo("Cat");

                }
            }
        }
    }

    @Test
    @DisplayName(value = "Parse csv")
    void csvParsingTest() throws Exception {
        ZipFile zip = new ZipFile(new File("src/test/resources/sample.zip"));
        ZipInputStream is = new ZipInputStream(classLoader.getResourceAsStream("sample.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            try (InputStream inputStream = zip.getInputStream(entry)) {
                if (entry.getName().endsWith(".csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    List<String[]> content = reader.readAll();
                    org.assertj.core.api.Assertions.assertThat(content.get(1)[1]).isEqualTo("Cat");

                }
            }
        }
    }

    @Test
    @DisplayName(value = "Parse json")
    void jsonParsingTest() throws Exception {
        ZipFile zip = new ZipFile(new File("src/test/resources/sample.zip"));
        ZipInputStream is = new ZipInputStream(classLoader.getResourceAsStream("sample.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            try (InputStream inputStream = zip.getInputStream(entry)) {
                if (entry.getName().endsWith(".json")) {
                    Gson gson = new Gson();
                    String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
                    org.assertj.core.api.Assertions.assertThat(jsonObject.get("name").getAsString()).isEqualTo("Pushok");
                    org.assertj.core.api.Assertions.assertThat(jsonObject.get("animal").getAsString()).isEqualTo("Cat");
                }
            }
        }
    }

}
