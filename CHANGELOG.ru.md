# Список изменений SportiduinoApp

## v1.6

* Поддержка аутентификации NTAG (BS FW >= v3.11.0)
* Небольшие улучшения

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.5.1...v1.6)

## v1.5.1

* Исправлены ошибки и сделаны небольшие улучшения

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.5...v1.5.1)

## v1.5

* Добавлен ночной режим
* Теперь пользователь может установить таймер обратного отсчета от 0 до 10 секунд для Мастер чипа времени
* Исправлена ошибка при попытке записать пароль с пустыми полями ([GH-3](https://github.com/sportiduino/sportiduinoapp/issues/3))
* Установка минимального года ([GH-22](https://github.com/sportiduino/sportiduinoapp/issues/22))
* Улучшено поведение при изменении ориентации экрана

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.4.1...v1.5)

## v1.4.1

* Добавлена возможность скопировать данные, прочитанные с чипа

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.4...v1.4.1)

## v1.4

* Временная метка чипа состояния станции: жёлтая, если время на станции отличается более чем на 5 секунд,
красная, если время отличается более чем на 20 секунд

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.3.2...v1.4)

## v1.3.2

* Исправлено чтение чипов Mifare Classic
* Исправлены опечатки в строках интерфейса

## v1.3.1

* Исправление проблемы с записью чипов Mifare Classic

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.3...v1.3.1)

## v1.3

* Отображение уровня заряда литий-полимерной батареи
* Выделение цветом значений (например, красный для низкого заряда батареи)

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.2.1...v1.3)

## v1.2.1

* Исправлено падение на Android 12

## v1.2

* Поддержка обновлённых настроек для станций vX.10.0
* Опция "Только очистка" для записи чипа
* Новый режим быстрой отметки (прошивка vX.10.0)
* Прокрутка для вкладки настроек станции
* Подсветка некорректного времени или низкого заряда батареи станции
* Исправлена проблема с отображением времени в UTC при записи чипа сна

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.1...v1.2)

## v1.1

* Добавлен мастер-чип пароля (прошивка >= vX.10.0)
* Новый формат мастер-чипа лога (прошивка >= vX.10.0)
* Исправлена ошибка чтения меток MIFARE Classic

[Все изменения](https://github.com/sportiduino/sportiduinoapp/compare/v1.0...v1.1)

## v1.0

Первый выпуск приложения.

Совместим со станциями Sportiduino с прошивкой vX.7 или выше.

Поддерживаются чипы NTAG213/215/216, MIFARE Classic 1K/4K.

