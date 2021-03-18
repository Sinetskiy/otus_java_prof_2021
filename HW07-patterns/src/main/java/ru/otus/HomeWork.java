package ru.otus;

import org.w3c.dom.ls.LSOutput;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinter;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.ProcessorChangeFieldsValue;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorEvenSecondException;
import ru.otus.processor.ProcessorUpperField10;

import java.util.List;


public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) throws InterruptedException {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        var processors = List.of(new ProcessorConcatFields(),
                new LoggerProcessor(new ProcessorUpperField10()), new ProcessorChangeFieldsValue(), new ProcessorEvenSecondException());

        var complexProcessor = new ComplexProcessor(processors, (ex) -> System.out.println(ex.getMessage()));
        var listenerPrinter = new ListenerPrinter();
        var historyListener = new HistoryListener();
        complexProcessor.addListener(listenerPrinter);
        complexProcessor.addListener(historyListener);

        var field13 = new ObjectForMessage();
        field13.setData(List.of("field13.1", "field13.2"));

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        for (var i = 0; i <= 1000; i++) {
            Thread.sleep(1000);
            var result = complexProcessor.handle(message);
            System.out.println("result:" + result);
        }

        complexProcessor.removeListener(listenerPrinter);
    }
}
