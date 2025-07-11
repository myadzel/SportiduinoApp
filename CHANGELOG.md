# SportiduinoApp Changelog

## v1.6

* Ntag authentication support (BS FW >= v3.11.0)
* Small improvements

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.5.1...v1.6)

## v1.5.1

* Bug fixes and small improvements

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.5...v1.5.1)

## v1.5

* Added night mode
* Now user can set countdown timer from 0 to 10 seconds for Time master card
* Fixed crash when trying to write password with empty fields ([GH-3](https://github.com/sportiduino/sportiduinoapp/issues/3))
* Setting a minimum year for operation ([GH-22](https://github.com/sportiduino/sportiduinoapp/issues/22))
* Improved the application behavior in landscape mode

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.4.1...v1.5)

## v1.4.1

* Add the ability to copy the data read from a card

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.4...v1.4.1)

## v1.4

* State Master card timestamp: yellow if the time of the station differs by more than 5 seconds,
red if the time differs by more than 20 seconds

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.3.2...v1.4)

## v1.3.2

* Fix Mifare Classic cards reading
* Fix strings

## v1.3.1

* Fix problem with Mifare Classic cards writing

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.3...v1.3.1)

## v1.3

* Display Li-pol battery level
* Colored station state (e.g. red for low battery)

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.2.1...v1.3)

## v1.2.1

* Fix crash on Android 12

## v1.2

* Updated config for BS vX.10.0
* Cleaning only option for card prepare
* New fast punch mode (BS vX.10.0)
* Scrollable station settings tab
* Highlight text for incorrect time or low battery charge level
* Fix problem with UTC time for Master Sleep card

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.1...v1.2)

## v1.1

* Added Password Master card (BS FW >= vX.10.0)
* New format of Backup Master card (BS FW >= vX.10.0)
* Fix MIFARE Classic tag reading bug

[All changes](https://github.com/sportiduino/sportiduinoapp/compare/v1.0...v1.1)

## v1.0

Initial release of the application.

Compatible with Sportiduino vX.7 and greater.

Supports NTAG213/215/216, Mifare Classic 1K/4K tags.

