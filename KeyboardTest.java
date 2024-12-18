// Класс для тестирования функционала клавиатуры
class KeyboardTest {
    public static void main(String[] args) {
        KeyboardState keyboard = new KeyboardState();  // Создаём объект для состояния клавиш
        KeyboardOperations analyzer = new KeyboardOperations(keyboard);  // Создаём объект для операций с клавишами
        // Пример тестирования
        keyboard.breakKey('б');
        keyboard.breakKey('4');
        keyboard.breakKey('d');
        System.out.println("Сломанные клавиши: " + keyboard.getBrokenKeys());  // Проверка сломанных клавиш
        keyboard.restoreKey('4');
        keyboard.restoreKey('б');
        System.out.println("Сломанные клавиши после восстановления 4 и б: " + keyboard.getBrokenKeys());  // Проверка после восстановления
        // Пример проверки возможности напечатать символ
        System.out.println("Можно напечатать 'a'?: " + analyzer.canTypeChar('a'));
        // Пример проверки возможности напечатать слово
        System.out.println("Можно напечатать слово 'hello': " + analyzer.canTypeWord("hello"));
        // Пример анализа напечатанного слова
        analyzer.analyzeTypedWord("hello", "hll");
        System.out.println("Сломанные клавиши после анализа: " + keyboard.getBrokenKeys());
        // Пример подсчета печатаемых слов в строке
        System.out.println("Количество печатаемых слов: " + analyzer.countPrintableWords("do мама привет"));
        System.out.println("Необходимые буквы для слова 'do': " + analyzer.necessaryKeysForWord("do"));
        System.out.println("Есть сломанные буквы?: " + analyzer.areAnyLettersBroken());
        System.out.println("Все цифры сломаны?: " + analyzer.areAllDigitsBroken());
        analyzer.toggleKeysForString("do123");
        System.out.println("Сломанные клавиши после переключения: " + keyboard.getBrokenKeys());
    }
}
