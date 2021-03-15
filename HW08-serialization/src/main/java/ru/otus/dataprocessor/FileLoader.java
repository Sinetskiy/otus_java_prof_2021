package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import ru.otus.model.Measurement;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileLoader implements Loader {

    private final File file;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileLoader(String fileName) {
        file = new File(fileName);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public List<Measurement> load() throws IOException {
        //читает файл, парсит и возвращает результат
        CollectionType measurementType = mapper.getTypeFactory()
                .constructCollectionType(List.class, Measurement.class);
        return mapper.readValue(file, measurementType);
    }


}
