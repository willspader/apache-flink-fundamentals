import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FilterStrings {

    public static void main(String[] args) throws Exception {
        // get the correct execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // transformation
        DataStream<String> dataStream = env.socketTextStream("localhost", 9999)
                .filter(new Filter());

        // sink
        dataStream.print();

        env.execute("filter-strings");
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
