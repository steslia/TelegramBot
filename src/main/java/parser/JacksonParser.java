package parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JacksonParser {
    public static String parseJackson(String url) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        //Получаем массив элементов CurrencyRate, и передаем ссылку какой JSON считывать и откуда(в данном случ из сайта)
        CurrencyRate[] currencyRate = objectMapper.readValue(new URL(url), CurrencyRate[].class);

        //Использую стримы для вывода
        List<CurrencyRate> btc = Arrays.stream(currencyRate).filter(s -> !s.getFrom().equals("BTC")).collect(Collectors.toList());
        StringBuilder string = new StringBuilder();
        for (CurrencyRate rate : btc) {
            string.append(rate);
        }

        return String.valueOf(string);
    }
}
