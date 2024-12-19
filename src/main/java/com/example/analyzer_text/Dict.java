package com.example.analyzer_text;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dict {
    private final Logger log = LogManager.getLogger(Dict.class);
    private final String name;
    private final Map<String, Integer> dict = new HashMap<>();

    /**
     * Конструктор для создания словаря из файла
     *
     * @param name имя словаря
     * @param path путь к файлу
     */
    public Dict(String name, String path) {
        log.debug("Creating dictionary " + name);
        this.name = name;
        fillDict(dict, path);
    }

    /**
     * Конструктор для создания словаря из заранее заданной карты (для тестов)
     *
     * @param name          имя словаря
     * @param predefinedDict заранее заданная карта слов
     */
    public Dict(String name, Map<String, Integer> predefinedDict) {
        log.debug("Creating dictionary " + name + " with predefined data");
        this.name = name;
        this.dict.putAll(predefinedDict);
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getDict() {
        return dict;
    }

    private void fillDict(Map<String, Integer> map, String path) {
        log.debug("Filling dictionary " + name + " from file " + path);
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while (reader.ready()) {
                String[] line = reader.readLine().split(" ");
                for (String word : line) {
                    map.put(word, 0);
                }
            }
        } catch (IOException e) {
            log.error("Exception while reading file " + path, e);
        }
    }
}
