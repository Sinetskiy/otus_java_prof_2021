package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.node.IntNode;
import ru.otus.model.Measurement;

import java.io.IOException;

/**
 * @author Andrei Sinetskii
 */
public class MeasurementDeserializer extends StdDeserializer<Measurement> {

    MeasurementDeserializer() {
        this(null);
    }

    private MeasurementDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Measurement deserialize(JsonParser jp, DeserializationContext ctx)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        var name = node.get("name").asText();
        var value = (double) node.get("value").numberValue();

        return new Measurement(name, value);
    }
}
