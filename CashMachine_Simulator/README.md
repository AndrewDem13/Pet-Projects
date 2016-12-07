# Симулятор банкомата
(Сентябрь 2016)

Взаимодействие происходит через консоль. В зависимости от контекста, символы [0-9] имитируют цифровую клавиатуру банкомата для ввода данных, [1-4] – кнопки взаимодействия с интерфейсом банкомата, клавиша [0] – отмена и экстренный выход в любое время.

### Основные функции:
-	Внесение денег на счет
-	Проверка баланса
-	Снятие наличных

### Дополнительные функции:
- Поддержка мультиязычности
- Аутентификация через проверку пары номера карты и ПИН кода
- Тестовый режим работы без аутентификации
- Поддержка мультивалютности
- Проверка вносимых на счет купюр на валидность по каждой валюте (например, купюра с номиналом 500 валидна для EUR, но некоректна для USD).
- Выдача наличных только теми купюрами, которые были внесены ранее. Соответственно, проверка на возможность собрать необходимую сумму для выдачи из имеющихся в банкомате купюр.
- Алгоритм выдачи наличных собирает необходимую сумму из минимально возможного количества купюр (жадный алгоритм).

### Технические особенности:
-	Основной функционал реализован через паттерн Command
-	Аутентификация реализована через ResourceBundle
-	Поддержка мультиязычности через ResourceBundle + Locale
-	Выдача наличных происходит с помощью жадного алгоритма с рекурсией. Этот алгоритм написан мною самостоятельно. Возможно, не совсем оптимальный, но абсолютно рабочий.

=================================================================================

# ATM Simulator
(September 2016)

The interaction occurs through the console. Depending on the context, the characters [0-9] simulate digital ATM keyboard for data input, [1-4] - the interaction with the ATM interface buttons, key [0] - cancel the emergency exit at any time.

### Main functions:
- Depositing
- Balance Info
- Cash withdrawal

### Additional functions:
- Multilanguage support
- Authentication by checking card number and PIN code pair
- Test mode without authentication
- Multicurrency support
- When depositing ATM checks bills on the validity for each currency (for example, a bill with nominal value of 500 is valid for the EUR, but invalid for the USD).
- When withdrawing ATM operates only with those bills that have been deposited earlier. Accordingly, program tests the opportunity to collect the necessary sum for the withdrawing from the banknotes currently available in the ATM.

### Technical features:
- The main functionality is implemented via Command pattern
- Authentication is implemented via ResourceBundle
- Multilanguage support via ResourceBundle + Locale
- Cash withdrawal is done by the greedy algorithm with recursion. This algorithm I wrote on my own. Perhaps it's not quite optimal but it working absolutely right.
