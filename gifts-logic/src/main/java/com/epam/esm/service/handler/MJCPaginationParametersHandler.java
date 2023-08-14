package com.epam.esm.service.handler;

import java.util.Map;

public interface MJCPaginationParametersHandler {
    Map<String, Integer> handlePaginationParameters(Map<String, String> paginationParameters);
}
