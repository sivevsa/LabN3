class KeyboardState {
    private int aL = 26;  // Латинский алфавит (a-z), 26 букв
    private int aR = 33;  // Русский алфавит (а-я), 33 буквы
    private int digit = 10;  // Цифры (0-9), 10 цифр
    private int indShift = aL + aR + digit; // Индекс для клавиши SHIFT
    private boolean[] mass = new boolean[aL + aR + digit + 1];  // Массив состояния клавиш (каждый элемент true, если клавиша исправна)

    // Конструктор, где все клавиши исправны
    public KeyboardState() {
        for (int i = 0; i < mass.length; i++) {
            mass[i] = true;  // Инициализация всех клавиш как исправных
        }
    }

    // 1. Метод для вывода сломанных клавиш
    public String getBrokenKeys() {
        String result = "";  // Строка для хранения сломанных клавиш
        // Проверяем все латинские буквы
        for (char c = 'a'; c <= 'z'; c++) {
            int index = c - 'a';  // Индекс латинской буквы
            if (!mass[index]) {  // Если клавиша сломана
                result += c + ", ";  // Добавляем букву в строку результата
            }
        }
        // Проверяем все русские буквы
        for (char c = 'а'; c <= 'я'; c++) {
            int index = aL + (c - 'а');  // Индекс русской буквы
            if (!mass[index]) {  // Если клавиша сломана
                result += c + ", ";  // Добавляем букву в строку результата
            }
        }
        // Проверяем все цифры
        for (char c = '0'; c <= '9'; c++) {
            int index = aL + aR + (c - '0');  // Индекс цифры
            if (!mass[index]) {  // Если клавиша сломана
                result += c + ", ";  // Добавляем цифру в строку результата
            }
        }
        // Проверка клавиши SHIFT
        if (!mass[indShift]) {
            result += "SHIFT, ";  // Добавляем "SHIFT" в строку результата, если клавиша сломана
        }
        // Убираем последнюю запятую и пробел, если они есть
        if (result.length() > 0) {
            result = result.substring(0, result.length() - 2);  // Удаляем последнюю запятую и пробел
        } else {
            result = "Нет сломанных клавиш";  // Если нет сломанных клавиш
        }
        return result;  // Возвращаем строку с результатом
    }

    // Метод для получения индекса клавиши
    public int getKeyIndex(char key) {
        int n = -1;  // Изначально символ неверный (не найден)
        if (key >= 'a' && key <= 'z') {
            n = key - 'a';  // Индекс латинской буквы (от 0 до 25)
        } else if (key >= 'а' && key <= 'я') {
            n = aL + (key - 'а');  // Индекс русской буквы (от 26 до 58)
        } else if (key >= '0' && key <= '9') {
            n = aL + aR + (key - '0');  // Индекс цифры (от 59 до 68)
        } else if (key == '@') {
            n = indShift;  // Индекс для клавиши SHIFT
        }
        return n;  // Возвращаем индекс клавиши
    }

    // 2. Метод для поломки клавиши
    public void breakKey(char key) {
        int index = getKeyIndex(key);  // Получаем индекс клавиши
        if (index != -1) {  // Если клавиша существует
            mass[index] = false;  // Меняем состояние клавиши на "сломана"
        }
    }

    // 4. Метод для восстановления клавиши
    public void restoreKey(char key) {
        int index = getKeyIndex(key);  // Получаем индекс клавиши
        if (index != -1) {  // Если клавиша существует
            mass[index] = true;  // Меняем состояние клавиши на "исправна"
        }
    }

    // Метод для получения состояния клавиши (работает или нет)
    public boolean isKeyWorking(char key) {
        int index = getKeyIndex(key);  // Получаем индекс клавиши
        return index != -1 && mass[index];  // Возвращаем состояние клавиши: true, если исправна
    }
}
