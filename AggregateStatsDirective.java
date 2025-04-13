package io.cdap.wrangler.directives.column;

import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.parser.*;
import io.cdap.wrangler.api.directive.*;
import io.cdap.wrangler.api.row.Row;

import java.util.*;

public class AggregateStatsDirective implements Directive, Aggregate {
    private String byteCol, timeCol, outByteCol, outTimeCol;
    private long totalBytes = 0, totalMillis = 0;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
            .addRequiredArg("byte_col", TokenType.COLUMN_NAME)
            .addRequiredArg("time_col", TokenType.COLUMN_NAME)
            .addRequiredArg("out_byte_col", TokenType.COLUMN_NAME)
            .addRequiredArg("out_time_col", TokenType.COLUMN_NAME)
            .build();
    }

    @Override
    public void initialize(Arguments arguments) {
        byteCol = ((ColumnName) arguments.value("byte_col")).value();
        timeCol = ((ColumnName) arguments.value("time_col")).value();
        outByteCol = ((ColumnName) arguments.value("out_byte_col")).value();
        outTimeCol = ((ColumnName) arguments.value("out_time_col")).value();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext ctx) {
        for (Row row : rows) {
            String byteVal = row.getValue(byteCol).toString();
            String timeVal = row.getValue(timeCol).toString();
            totalBytes += new ByteSize(byteVal).getBytes();
            totalMillis += new TimeDuration(timeVal).getMilliseconds();
        }

        Row result = new Row();
        result.add(outByteCol, totalBytes / (1024 * 1024.0)); // MB
        result.add(outTimeCol, totalMillis / 1000.0); // Seconds

        return Collections.singletonList(result);
    }
}
