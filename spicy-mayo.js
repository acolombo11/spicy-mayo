function doGet(e) {
    const privateKey = "ryXExF2T$UNomSk%eCCUFYaN@ymU3KGjWLbC^yQcKDFjc54DHySVAW6kF%7k";
    if (e.parameter.key == privateKey) {
        const today = new Date(e.parameter.date);
        const tomorrow = new Date(e.parameter.date);
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
                attendees: event.attendees?.map((attendee) => extractNameFromFrescoEmail(attendee.email)),
            }));
            return ContentService.createTextOutput(JSON.stringify(events)).setMimeType(ContentService.MimeType.JSON);
        } catch (err) {
            console.log('Failed with error %s', err.message);
        }
    }
}

function extractNameFromFrescoEmail(email) {
    const string = email.substring(0, email.indexOf('@')).substring(0, email.indexOf('.'));
    return string.charAt(0).toUpperCase() + string.slice(1);
}
