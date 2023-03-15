# Telegram Currency Bot

## Overview

The Telegram bot for currency rates monitoring. It shows the currencies(USD/EUR) exchange rate from the most populat ukrainian banks(NBU/MonoBank/PrivatBank) with **the ability to set real-time** notification by schedule.

## Installation
1. Create `config/bot_credentials.txt` file based on `config/bot_credentials.txt.dist` 
2. In `config/bot_credentials.txt` set own BOT_TOKEN and BOT_NAME. For details see official instruction [How to Obtain Your Bot Token](https://core.telegram.org/bots/tutorial#obtain-your-bot-token)

## Usage

There is a list of static commands:

- **`/help`** - shows the information about the bot.

  ![help_1](src/main/resources/help_1.png)

- **`/start`** - shows the Main Menu with buttons "Get Information" and "Settings" as commands.

  ![bot_1](src/main/resources/bot_1.png)

- **`Get information`** - shows the actual currency exchange rate according to the user settings.

  ![bot_2](src/main/resources/bot_2.png)

- **`Settings`** - shows the user settings menu.

  ![bot_3](src/main/resources/bot_3.png)

- **`Price pression`** - shows the menu with rounding precision options. Allowed options are: 2, 3, 4. Marked with green check marks applied for showing rates.

  ![bot_4](src/main/resources/bot_4.png)

- **`Bank`** - shows the menu with supported ukrainian banks. Marked with green check marks applied for showing.

  ![bot_5](src/main/resources/bot_5.png)

- **`Currencies`** - shows the menu with supported currencies. Multiselect is supported for currencies. Marked with green check marks applied for showing.

  ![bot_6](src/main/resources/bot_6.png)

- **`Time notification`** - shows the menu with working hours which can be selected and notification message will be send by schedule with currency exchange rates according to the user settings.

  ![bot_7](src/main/resources/bot_7.png)

## License

This project is licensed under the MIT License
