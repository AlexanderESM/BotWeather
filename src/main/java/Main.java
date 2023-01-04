import botWeather.BotWeather;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        try {
            new BotWeather();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
