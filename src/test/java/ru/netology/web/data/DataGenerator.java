package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.time.LocalDate;
import java.util.Locale;
import static java.time.format.DateTimeFormatter.ofPattern;

public class DataGenerator {

    public static class FillingForm {
        private FillingForm() {
        }

        public static CustomerData generateCustomerData(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new CustomerData(
                    generateCityFromDropList(),
                    faker.name().lastName() + " " + faker.name().firstName(),
                    faker.numerify("+7##########") // банковская форма не принимает сгенерированный Факером
                            //код номера в скобках +7(916)#######
            );
        }

        public static String generateMeetingDay(int days) {
            return LocalDate.now().plusDays(days).format(ofPattern("dd.MM.yyyy"));

        }

        private static String generateCityFromDropList() {
            String[] cities = new String[]{
                    "Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Белгород",
                    "Биробиджан", "Благовещенск", "Брянск", "Великий Новгород", "Владивосток",
                    "Владикавказ", "Владимир", "Волгоград", "Вологда", "Воронеж", "Горно-Алтайск",
                    "Грозный", "Екатеринбург", "Иваново", "Ижевск", "Иркутск", "Йошкар-Ола",
                    "Казань", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Краснодар",
                    "Красноярск", "Курган", "Курск", "Кызыл", "Липецк", "Магадан", "Магас", "Майкоп",
                    "Махачкала", "Москва", "Мурманск", "Нальчик", "Нарьян-Мар", "Нижний Новгород",
                    "Новосибирск", "Омск", "Орёл", "Оренбург", "Пенза", "Пермь", "Петрозаводск",
                    "Петропавловск-Камчатский", "Псков", "Ростов-на-Дону", "Рязань", "Салехард",
                    "Самара", "Санкт-Петербург", "Саранск", "Саратов", "Севастополь", "Симферополь",
                    "Смоленск", "Ставрополь", "Сыктывкар", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень",
                    "Улан-Удэ", "Ульяновск", "Уфа", "Хабаровск", "Ханты-Мансийск", "Чебоксары", "Челябинск",
                    "Черкесск", "Чита", "Элиста", "Южно-Сахалинск", "Якутск", "Ярославль"
            };
            return cities[generateRandomNumber(0, cities.length)];
        }

        public static int generateRandomNumber(int min, int max) { //генерация дат в определенных диапозонах дней
            max -= min;
            return (int) (Math.random() * ++max) + min;
        }

        @Value
        public static class CustomerData {
            private String city;
            private String name;
            private String phone;
        }
    }
}
