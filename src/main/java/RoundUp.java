import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class RoundUp {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Long> dataStream = env.socketTextStream("localhost", 9999)
                .filter(new Filter())
                .map(new Round());

        dataStream.print();

        env.execute("filter-map-strings");
    }

    public static class Round implements MapFunction<String, Long> {

        @Override
        public Long map(String value) throws Exception {
            double v = Double.parseDouble(value.trim());

            return Math.round(v);
        }
    }

    public static class Filter implements FilterFunction<String> {

        @Override
        public boolean filter(String value) throws Exception {
            try {
                Double.parseDouble(value.trim());

                return true;
            } catch(Exception e) {

            }

            return false;
        }
    }

}
