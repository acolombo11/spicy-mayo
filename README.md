# spicy-mayo

**spicy-mayo** is a simple iOS, Android, and Desktop app to view events from a Google Calendar—without needing to add your Google account to your device.

It’s ideal for quickly checking your work calendar for **Today**, **Tomorrow**, or a **specific date**, while keeping your work account off your phone.

The app fetches calendar data through a lightweight [Google Apps Script](https://script.google.com/home) backend, which must be set up on the calendar owner's Google account. Since the backend and API key are specific to each setup, the app isn't available on app stores and must be built manually (see below).

## 📸 Screenshots

#### Android

| Events                                      | Error                                        | Empty                                       |
|---------------------------------------------|----------------------------------------------|---------------------------------------------|
| ![Android-1](docs/screenshot-android-1.png) | ![Android-2](docs/screenshot-android-2.png)  | ![Android-3](docs/screenshot-android-3.png) |

#### iOS

| Today                                       | Tomorrow                                     | Specific Date                               |
|---------------------------------------------|----------------------------------------------|---------------------------------------------|
| ![iOS-1](docs/screenshot-ios-1.png)         | ![iOS-2](docs/screenshot-ios-2.png)          | ![iOS-3](docs/screenshot-ios-3.png)         |

> Screenshots showcase:
> - Light and dark themes, following system preference (iOS, Android)
> - Material You color theming (Android)
> - Daily view types (today, tomorrow, specific date)
> - UI states (events present, empty, error)

## ⚙️ Setup

> You’ll need access to the Google account that owns the calendar.

1. Go to [Google Apps Script](https://script.google.com/home) and create a new project.
2. In the **Services** tab, add **Google Calendar API** to the project.
3. Replace the default script with the content from [`spicy-mayo.js`](spicy-mayo.js).
4. In the script, set a secure value for the `spicyApiKey` constant (e.g., a password or random string).
5. Click **Deploy > Manage deployments** and deploy as a **Web App**:
   - **Execute as**: Me <sub><sup>(your email)</sup></sub>
   - **Who has access**: Anyone
6. Copy the **Web App URL** and the `spicyApiKey` you set earlier.
7. In the app project, copy [`secrets.template.properties`](secrets.template.properties) to a new file named `secrets.properties`.
8. Paste the Web App URL and API key into `secrets.properties`.
9. Build and run the app.

> 🛡️ `secrets.properties` is ignored by Git to keep your data private.

## 🤝 Contributing

This project was built for a very specific use-case and kept intentionally minimal.  
However, contributions are welcome!

- Check [issues](/../../issues) for ideas or beginner-friendly tasks.
- Feel free to fork the repo and adapt it to your needs.
- Submit a pull request if you have improvements to share.
