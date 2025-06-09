/*********************************** IMPORTANT: API KEY REQUIRED **********************************\
  Replace the value of `spicyApiKey` with a secure, randomly generated API key before deployment.
 Ensure the same key is set in your project's `secrets.properties` file under the `spicyApiKey` key.
\**************************************************************************************************/
const spicyApiKey = "";

/**
 * Entry point for HTTP GET requests to this Google Apps Script Web App.
 *
 * Validates the API key and, if correct, retrieves events for the specified date.
 *
 * @param {Object} e - The event parameter object containing query parameters.
 *                     Required query parameters:
 *                     - key: API key for authentication.
 *                     - date: Target date for fetching events (format: YYYY-MM-DD).
 * @returns {TextOutput} JSON response containing the events or an error message.
 */
function doGet(e) {
    if (e.parameter.key == spicyApiKey) {
        return getEvents(e.parameter.date);
    } else {
        return result({
            status: 'Unauthorized',
            code: 401,
            message: 'Invalid API Key',
        });
    }
}

/**
 * Retrieves all calendar events on a given date from the primary calendar.
 *
 * @param {string} date - The date for which to retrieve events (format: YYYY-MM-DD).
 * @returns {TextOutput} JSON response with event details or an error message.
 */
function getEvents(date) {
    const today = new Date(date);
    const tomorrow = new Date(date);
    tomorrow.setDate(today.getDate() + 1);

    try {
        const response = Calendar.Events.list('primary', {
            timeMin: today.toISOString(),
            timeMax: tomorrow.toISOString(),
            showDeleted: false,
            singleEvents: true,
            orderBy: 'startTime',
        });

        const events = response.items.map(event => ({
            start: event.start.dateTime,
            end: event.end.dateTime,
            summary: event.summary,
            attendees: event.attendees?.map(attendee => extractNameFromCorporateEmail(attendee.email)),
            type: event.eventType,
        }));

        return result(events);
    } catch (err) {
        return result({
            status: 'Internal Server Error',
            code: 500,
            message: err.message,
        });
    }
}

/**
 * Extracts the first name from a corporate-style email address.
 *
 * @param {string} email - Email address in the format "firstname.lastname@company.com" or "firstname@company.com".
 * @returns {string} First name with the first letter capitalized.
 */
function extractNameFromCorporateEmail(email) {
    const namePart = email.substring(0, email.indexOf('@')).split('.')[0];
    return namePart.charAt(0).toUpperCase() + namePart.slice(1);
}

/**
 * Formats a JavaScript object as a JSON text output.
 *
 * @param {Object} data - The data to convert to JSON format.
 * @returns {TextOutput} Text output containing the JSON string with the appropriate MIME type.
 */
function result(data) {
    return ContentService
        .createTextOutput(JSON.stringify(data))
        .setMimeType(ContentService.MimeType.JSON);
}
