package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        //читает файл, парсит и возвращает результат
        var fis = new FileInputStream(fileName);
        var reader = Json.createReader(fis);
        var personObject = (JsonArray) reader.read();
        reader.close();
        var measurementList = new ArrayList<Measurement>();
        for (var jsonElement : personObject) {
            var measurement = new Measurement(jsonElement.asJsonObject().getString("name")
                    , jsonElement.asJsonObject().getInt("value"));
            measurementList.add(measurement);
        }

        return measurementList;
    }


}
