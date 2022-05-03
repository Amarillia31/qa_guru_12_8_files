package com.elena;

import com.codeborne.pdftest.PDF;
import com.codeborne.pdftest.matchers.ContainsExactText;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.MatcherAssert.assertThat;

public class SelenideDownloadTest {

    @Test
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File textFile = $("#raw-url").download();
        System.out.println();
    }

    @Test
    void pdfParsingTest() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("junit-user-guide-5.8.2.pdf");
        PDF pdf = new PDF(stream);
        Assertions.assertEquals(166,pdf.numberOfPages);
        assertThat(pdf, new ContainsExactText("123"));
    }
}
