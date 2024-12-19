package com.example.analyzer_text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdditionalDictUtilsTest {

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
    void testClearDictsResetsAllCounts() {
        // Увеличиваем счетчики в словарях
        dictUtils.checkDict("пациент");
        dictUtils.checkDict("код");
        dictUtils.checkDict("код");

        // Проверяем, что счетчики увеличились
        assertEquals(1, medicalDict.getDict().get("пациент"));
        assertEquals(2, programmingDict.getDict().get("код"));

        // Очищаем словари
        dictUtils.clearDicts();

        // Проверяем, что все счетчики сброшены
        assertEquals(0, medicalDict.getDict().get("пациент"));
        assertEquals(0, medicalDict.getDict().get("болезнь"));
        assertEquals(0, programmingDict.getDict().get("код"));
        assertEquals(0, programmingDict.getDict().get("переменная"));
    }

    @Test
    void testCheckDictHandlesUnknownWords() {
        // Проверяем слово, которого нет в словарях
        dictUtils.checkDict("неизвестное");

        // Убедимся, что ничего не сломалось и счетчики остались на месте
        assertEquals(0, medicalDict.getDict().get("пациент"));
        assertEquals(0, programmingDict.getDict().get("код"));
    }

    @Test
    void testGetThemeWithEqualCounts() {
        // Добавляем одинаковое количество совпадений в оба словаря
        dictUtils.checkDict("пациент");
        dictUtils.checkDict("код");

        // Получаем тему
        Dict theme = dictUtils.getTheme();

        // Убедимся, что тема возвращается и она одна из двух
        assertNotNull(theme);
        assertEquals(1, theme.getDict().values().stream().mapToInt(Integer::intValue).sum());
    }
}
