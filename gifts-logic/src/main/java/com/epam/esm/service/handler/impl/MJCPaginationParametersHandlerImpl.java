package com.epam.esm.service.handler.impl;

import com.epam.esm.service.exception.MJCInvalidPaginationDataException;
import com.epam.esm.service.exception.MJCMissingPageNumberException;
import com.epam.esm.service.handler.MJCPaginationParametersHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.service.constant.MJCPaginationConstant.PAGE_PARAMETER;
import static com.epam.esm.service.constant.MJCPaginationConstant.PAGE_SIZE_PARAMETER;

@Component
public class MJCPaginationParametersHandlerImpl implements MJCPaginationParametersHandler {
    private static final String MISSING_PAGE_NUMBER_MESSAGE = "missing.page.number";
    private static final String INVALID_PAGE_OR_PAGE_SIZE_MESSAGE = "invalid.page.or.page.size";
    private static final int DEFAULT_PAE_SIZE = 5;

    @Override
    public Map<String, Integer> handlePaginationParameters(Map<String, String> paginationParameters) {
        if (paginationParameters == null || paginationParameters.isEmpty() || !paginationParameters.containsKey(PAGE_PARAMETER)) {
            throw new MJCMissingPageNumberException(MISSING_PAGE_NUMBER_MESSAGE);
        }

        int page = Integer.parseInt(paginationParameters.remove(PAGE_PARAMETER));
        int pageSize = paginationParameters.containsKey(PAGE_SIZE_PARAMETER)
                ? Integer.parseInt(paginationParameters.remove(PAGE_SIZE_PARAMETER))
                : DEFAULT_PAE_SIZE;

        if (page <= 0 || pageSize <= 0) {
            throw new MJCInvalidPaginationDataException(INVALID_PAGE_OR_PAGE_SIZE_MESSAGE);
        }

        HashMap<String, Integer> handledPaginationParameters = new HashMap<>();
        handledPaginationParameters.put(PAGE_PARAMETER, page);
        handledPaginationParameters.put(PAGE_SIZE_PARAMETER, pageSize);
        return handledPaginationParameters;
    }
}
