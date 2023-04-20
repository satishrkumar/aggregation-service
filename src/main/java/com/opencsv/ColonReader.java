package com.opencsv;

import com.opencsv.processor.RowProcessor;
import com.opencsv.validators.LineValidatorAggregator;
import com.opencsv.validators.RowValidatorAggregator;

import java.io.Reader;
import java.util.Locale;

public class ColonReader extends CSVReader {
    public ColonReader(Reader reader, int line) {
        super(reader, line, new CSVParser(':', '"', '\\', false, true, false, ICSVParser.DEFAULT_NULL_FIELD_INDICATOR, Locale.getDefault()), false, true, 0, Locale.getDefault(), new LineValidatorAggregator(), new RowValidatorAggregator(), (RowProcessor) null);

    }

}
