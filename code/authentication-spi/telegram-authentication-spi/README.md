rh-sso-custom-authentication-spi
---

Expected variable as host:[port] default value: localhost:8081 => USER_STORAGE_CUSTOM_SPI_TARGET_HOST

Must be setted (no default value):
Telegram bot token variable: TELEGRAM_TOKEN
Telegram bot name: TELEGRAM_BOT_USERNAME

The Authenticator has been developed with a rolling action for obtaining the telegram ID instead of the @username for simplification; this avoids the use of the telegram api_id, phone_registered and api_hash. If desired, the authenticator can be modified for doing a lookup of the Telegram ID from the @username calling https://core.telegram.org/method/users.getFullUser
