package com.example.analyzer_text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DictUtilsTest {

    private Dict medicalDict;
    private Dict programmingDict;
    private DictUtils dictUtils;

    @BeforeEach
    void setUp() {
        // Создаем фиктивные словари
        medicalDict = new Dict("Медицинский", Map.of(
                "пациент", 0,
                "болезнь", 0,
                "лекарство", 0
        ));

        programmingDict = new Dict("Программирование", Map.of(
                "код", 0,
                "переменная", 0,
                "функция", 0
        ));

        // Инициализируем DictUtils
        dictUtils = new DictUtils(List.of(medicalDict, programmingDict));
    }

    @Test
    void testCheckDict() {
        // Проверяем слово, связанное с медициной
        dictUtils.checkDict("пациент");
        assertEquals(1, medicalDict.getDict().get("пациент"));
        assertEquals(0, programmingDict.getDict().get("код"));

        // Проверяем слово, связанное с программированием
        dictUtils.checkDict("код");
        assertEquals(1, programmingDict.getDict().get("код"));
        assertEquals(0, medicalDict.getDict().get("лекарство"));
    }

    @Test
    void testGetTheme() {
        // Проверяем тему после обработки текста
        dictUtils.checkDict("пациент");
        dictUtils.checkDict("лекарство");
        dictUtils.checkDict("переменная");

        Dict theme = dictUtils.getTheme();
        assertNotNull(theme);
        assertEquals("Медицинский", theme.getName());
    }

    @Test
    void testClearDicts() {
        // Проверяем обнуление словарей
        dictUtils.checkDict("пациент");
        dictUtils.checkDict("код");
        dictUtils.clearDicts();

        for (Dict dict : List.of(medicalDict, programmingDict)) {
            dict.getDict().values().forEach(value -> assertEquals(0, value));
        }
    }
}
