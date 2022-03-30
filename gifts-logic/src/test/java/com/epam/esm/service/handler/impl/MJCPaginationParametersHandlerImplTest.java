package com.epam.esm.service.handler.impl;

import com.epam.esm.service.exception.MJCInvalidPaginationDataException;
import com.epam.esm.service.exception.MJCMissingPageNumberException;
import com.epam.esm.service.handler.MJCPaginationParametersHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.service.constant.MJCPaginationConstant.*;

class MJCPaginationParametersHandlerImplTest {
    private MJCPaginationParametersHandler paginationParametersHandler;
    private Map<String, String> inputParameters;
    private Map<String, Integer> handledParameters;

    @BeforeEach
    void setUp() {
        paginationParametersHandler = new MJCPaginationParametersHandlerImpl();

        inputParameters = new HashMap<>();
        inputParameters.put("page", "1");
        inputParameters.put("pageSize", "2");

        handledParameters = new HashMap<>();
        handledParameters.put("page", 1);
        handledParameters.put("pageSize", 2);
    }

    @Test
    void handlePaginationParametersPositiveTest() {
        Assertions.assertEquals(handledParameters, paginationParametersHandler.handlePaginationParameters(inputParameters));
    }

    @Test
    void handlePaginationParametersWithoutPageSizeTest() {
        inputParameters.remove(PAGE_SIZE_PARAMETER);
        handledParameters.put(PAGE_SIZE_PARAMETER, 5);
        Assertions.assertEquals(handledParameters, paginationParametersHandler.handlePaginationParameters(inputParameters));
    }

    @Test
    void handlePaginationParametersWithInvalidPageTest() {
        inputParameters.put(PAGE_PARAMETER, "-1");
        Assertions.assertThrows(MJCInvalidPaginationDataException.class, () -> paginationParametersHandler.handlePaginationParameters(inputParameters));
    }

    @Test
    void handlePaginationParametersWithInvalidPageSizeTest() {
        inputParameters.put(PAGE_SIZE_PARAMETER, "-1");
        Assertions.assertThrows(MJCInvalidPaginationDataException.class, () -> paginationParametersHandler.handlePaginationParameters(inputParameters));
    }

    @Test
    void handlePaginationParametersWithoutPageTest() {
        inputParameters.remove(PAGE_PARAMETER);
        Assertions.assertThrows(MJCMissingPageNumberException.class, () -> paginationParametersHandler.handlePaginationParameters(inputParameters));
    }
}