package com.paulmount.paulfoliodisplay.converters;

import com.paulmount.paulfoliodisplay.command.SearchCommand;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * *  Created by paulm on 2/14/2024
 */

@Component
public class SearchCommandToString implements Converter<SearchCommand, String> {

    @Synchronized
    @Nullable
    @Override
    public String convert(SearchCommand source) {
        if (source == null)
            return null;
        else
            return source.getSearchString();
    }
}
