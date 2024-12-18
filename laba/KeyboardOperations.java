// Класс для выполнения операций с клавишами
class KeyboardOperations {
    private KeyboardState state;  // Ссылка на объект KeyboardState

    // Конструктор принимает объект KeyboardState
    public KeyboardOperations(KeyboardState state) {
        this.state = state;  // Инициализация объекта state
    }
    // 3. Метод для проверки возможности напечатать символ
    public boolean canTypeChar(char key) {
        // Проверяем, можно ли напечатать заглавную латинскую букву
        if (key >= 'A' && key <= 'Z') {
            if (!state.isKeyWorking('@')) {  // Если клавиша SHIFT сломана
                return false;  // Заглавные буквы не напечатаются
            }
            char lowerKey = (char) (key - 'A' + 'a');  // Преобразуем заглавную букву в строчную
            return state.isKeyWorking(lowerKey);  // Проверяем состояние строчной буквы
        }
        // Проверяем, можно ли напечатать заглавную русскую букву
        else if (key >= 'А' && key <= 'Я') {
            if (!state.isKeyWorking('@')) {  // Если клавиша SHIFT сломана
                return false;  // Заглавные русские буквы не напечатаются
            }
            char lowerKey = (char) (key - 'А' + 'а');  // Преобразуем заглавную русскую букву в строчную
            return state.isKeyWorking(lowerKey);  // Проверяем состояние строчной буквы
        }
        return state.isKeyWorking(key);  // Для остальных символов
    }

    // 5. Метод для проверки возможности напечатать слово
    public boolean canTypeWord(String word) {
        boolean n = true;  // Предполагаем, что слово можно напечатать
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);  // Извлекаем символ из слова
            if (!canTypeChar(c)) {  // Если хотя бы один символ нельзя напечатать
                n = false;  // Устанавливаем n в false
                break;  // Прерываем цикл, так как дальнейшая проверка не имеет смысла
            }
        }
        return n;  // Возвращаем значение переменной n
    }

    // 6. Метод для проверки сломанных букв
    public boolean areAnyLettersBroken() {
        boolean n = false;  // Все буквы в рабочем состоянии
        // Проверка всех латинских букв
        for (char c = 'a'; c <= 'z'; c++) {
            if (!state.isKeyWorking(c)) {
                n = true;  // Если хотя бы одна клавиша сломана, устанавливаем n в true
            }
        }
        // Проверка всех русских букв
        for (char c = 'а'; c <= 'я'; c++) {
            if (!state.isKeyWorking(c)) {
                n = true;  // Если хотя бы одна клавиша сломана, устанавливаем n в true
            }
        }
        return n;  // Возвращаем значение переменной n
    }

    // 7. Метод для проверки, сломаны ли все цифры
    public boolean areAllDigitsBroken() {
        boolean n = true;  // Предполагаем, что все цифры сломаны
        for (char c = '0'; c <= '9'; c++) {
            if (state.isKeyWorking(c)) {
                n = false;  // Если хотя бы одна цифра исправна, устанавливаем n в false
            }
        }
        return n;  // Возвращаем значение переменной n
    }

    // 8. Метод для определения необходимых клавиш для слова
    public String necessaryKeysForWord(String word) {
        // Переменная для подсчета нужных клавиш
        int neededCount = 0;
        // Перебираем каждый символ в слове
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // Если символ нельзя напечатать, увеличиваем счетчик
            if (!canTypeChar(c)) {
                neededCount++;
            }
        }
        // Если нет символов, которые нельзя напечатать, возвращаем null
        if (neededCount == 0) {
            return null;
        }
        // Строка для хранения символов, которые нельзя напечатать
        String result = " ";
        int index = 0;
        // Заполняем строку символами, которые нельзя напечатать
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!canTypeChar(c)) {
                result += c + " ";  // Добавляем символ в строку результата
                index++;
            }
        }
        return result;  // Возвращаем строку с символами, которые нельзя напечатать
    }
    // 9. Метод для подсчета печатаемых слов в строке
    public int countPrintableWords(String text) {
        int wordCount = 0;  // Счётчик печатаемых слов
        int i = 0;

        while (i < text.length()) {
            while (i < text.length() && text.charAt(i) == ' ') {
                i++;  // Пропускаем пробелы
            }
            if (i < text.length()) {
                int start = i;
                while (i < text.length() && text.charAt(i) != ' ') {
                    i++;  // Читаем символы слова
                }
                String word = text.substring(start, i);  // Извлекаем слово
                if (canTypeWord(word)) {
                    wordCount++;  // Если слово можно напечатать, увеличиваем счётчик
                }
            }
        }
        return wordCount;  // Возвращаем количество печатаемых слов
    }

    // 10. Метод для анализа напечатанного слова
    public void analyzeTypedWord(String word, String typedVersion) {
        int wordIndex = 0;
        int typedIndex = 0;

        // Проходим по символам и сравниваем оригинальное слово с напечатанным
        while (wordIndex < word.length() && typedIndex < typedVersion.length()) {
            char originalChar = word.charAt(wordIndex);
            char typedChar = typedVersion.charAt(typedIndex);
            if (originalChar == typedChar) {
                state.restoreKey(originalChar);  // Если символ совпадает, восстанавливаем клавишу
                wordIndex++;
                typedIndex++;
            } else {
                state.breakKey(originalChar);  // Если символ не совпадает, ломаем клавишу
                wordIndex++;
            }
        }

        // Обработка оставшихся символов в слове
        while (wordIndex < word.length()) {
            char originalChar = word.charAt(wordIndex);
            state.breakKey(originalChar);  // Ломаем клавишу
            wordIndex++;
        }
    }

    // 11. Метод для переключения состояния клавиш строки
    public void toggleKeysForString(String keys) {
        for (int i = 0; i < keys.length(); i++) {
            char key = keys.charAt(i);
            if (canTypeChar(key)) {
                state.breakKey(key);  // Если можно напечатать символ, ломаем клавишу
            } else {
                state.restoreKey(key);  // Иначе восстанавливаем клавишу
            }
        }
    }
}
