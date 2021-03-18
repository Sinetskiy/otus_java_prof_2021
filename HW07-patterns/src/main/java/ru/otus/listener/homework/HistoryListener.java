package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private static long count = 0;
    private Map msgHistory = new HashMap<Long, Message>();
    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        var copyMsg = oldMsg.clone();
        msgHistory.put(++count, copyMsg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        var result = (Message) msgHistory.get(id);
        return  Optional.of(result) ;
    }

}
