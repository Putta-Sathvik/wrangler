package io.cdap.wrangler;

import io.cdap.wrangler.api.row.Row;
import io.cdap.wrangler.utils.TestingRig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsDirectiveTest {
    @Test
    public void testAggregateStats() throws Exception {
        List<Row> rows = Arrays.asList(
            new Row("data_transfer_size", "10KB").add("response_time", "500ms"),
            new Row("data_transfer_size", "1MB").add("response_time", "1.5s")
        );

        String[] recipe = new String[] {
            "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        List<Row> result = TestingRig.execute(recipe, rows);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1.009, result.get(0).getValue("total_size_mb"), 0.01);
        Assert.assertEquals(2.0, result.get(0).getValue("total_time_sec"), 0.01);
    }
}
