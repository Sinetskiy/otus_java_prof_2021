package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        var resultMap = new TreeMap<String, Double>();
        for (var measurement : data) {
            var aggregate = resultMap.getOrDefault(measurement.getName(), 0d);
            resultMap.put(measurement.getName(), aggregate + measurement.getValue());
        }
        return resultMap;
    }
}
