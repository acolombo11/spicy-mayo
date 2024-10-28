/**
 * Google Apps Script default entry point.
 *
 * Remember to fill-in the spicyApiKey const with a randomly generated value and copy the same value
 * to your project's /secrets.properties spicyApiKey variable. After uploading this script on Google
 * Apps Script and deploying it, make sure to also copy the Deployment ID to fill the spicyDeployId
 * value in the same file.
 *
 * @param {string} e - The request parameters. We are just interested in two url query parameters:
 * - key: The api-key to make sure we're not exposing the data to the public,
 * - date: The date the script is gonna request the events for (format: 2024-12-01).
 * @return {string} Json containing the events, or Json containing the error.
 */
function doGet(e) {
    const spicyApiKey = ""; // Replace this value and add it to /secrets.properties
    if (e.parameter.key == spicyApiKey) {
        return getEvents(e.parameter.date);
    } else {
        return result({
            status: 'Unauthorized',
            code: 401,
            message: 'Wrong Api Key Specified',
        });
    }
}

/**
 * Gets all the events on a specific date from the primary calendar of the Google Account connected
 * to the Google Apps Script.
 *
 * @param {string} date - The date we are requesting the events for (format: 2024-12-01).
 * @return {string} Json containing the events, or Json containing the error.
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
        console.log('Response: ', response);
        const events = response.items.map(event => ({
            start: event.start.dateTime,
            end: event.end.dateTime,
            summary: event.summary,
            attendees: event.attendees?.map((attendee) => extractNameFromCorporateEmail(attendee.email)),
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
 * Takes a corporate email of format "name.surname@company.xyz" or "name@company.xyz" and extracts
 * just the name part of the email, no surname or company name.
 *
 * @param {string} email - The attendee's corporate email.
 * @return {string} The name part of the email, no surname or company name.
 */
function extractNameFromCorporateEmail(email) {
    const string = email
        .substring(0, email.indexOf('@'))
        .substring(0, email.indexOf('.'));
    return string.charAt(0).toUpperCase() + string.slice(1);
}

// TODO Add docs
function result(data) {
    return ContentService
        .createTextOutput(JSON.stringify(data))
        .setMimeType(ContentService.MimeType.JSON);
}
