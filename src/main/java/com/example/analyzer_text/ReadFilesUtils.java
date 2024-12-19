package com.example.analyzer_text;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.List;

public class ReadFilesUtils {

    private static final Logger log = LogManager.getLogger(ReadFilesUtils.class);

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Произошла ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void readTxtFile(String path, DictUtils dictUtils) {
        log.debug("Reading txt file " + path);
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while (reader.ready()) {
                String[] line = reader.readLine().replaceAll("[.,!?(){}\\[\\]\\-\\\\|/\"'_=$@<>:;*&%]", "")
                        .split("\\s+");
                for (String word : line) {
                    dictUtils.checkDict(word);
                }
            }
        } catch (IOException e) {
            log.error("Error reading txt file " + path, e);
            showError("Ошибка при чтении txt файла: " + e.getMessage());
        }
    }

    public static void readDocxFile(String path, DictUtils dictUtils) {
        log.debug("Reading docx file " + path);
        try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path))) {
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph para : paragraphs) {
                String[] line = para.getParagraphText().replaceAll("[.,!?(){}\\[\\]\\-\\\\|/\"'_=$@<>:;*&%]", "")
                        .split("\\s+");
                for (String word : line) {
                    dictUtils.checkDict(word);
                }
            }
        } catch (Exception e) {
            log.error("Error reading docx file " + path, e);
            showError("Ошибка при чтении docx файла: " + e.getMessage());
        }
    }

    public static void readDocFile(String path, DictUtils dictUtils) {
        log.debug("Reading doc file " + path);
        try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path))) {
            HWPFDocument document = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(document);
            String[] paragraphs = extractor.getParagraphText();

            for (String paragraph : paragraphs) {
                String[] words = paragraph.replaceAll("[.,!?(){}\\[\\]\\-\\\\|/\"'_=$@<>:;*&%]", "")
                        .split("\\s+");
                for (String word : words) {
                    dictUtils.checkDict(word);
                }
            }
        } catch (Exception e) {
            log.error("Error reading doc file " + path, e);
            showError("Ошибка при чтении doc файла: " + e.getMessage());
        }
    }
}
