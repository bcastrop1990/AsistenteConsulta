package com.senasa.bpm.ng.masajes.service.impl;

import com.senasa.bpm.ng.masajes.service.CitasService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CitasServiceImpl implements CitasService {

/*
    @Override
    public List<Event> listarEventos(
            String accessToken, DateTime startTime, DateTime endTime) throws IOException, GeneralSecurityException {

        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        Calendar service = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Medic Perú")
                .build();

        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(startTime)
                .setTimeMax(endTime)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        return events.getItems();
    }


    private java.util.Calendar parseIso8601String(String isoDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC")); // Ajusta esto según la zona horaria de tus usuarios
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(format.parse(isoDate));
        return calendar;
    }

    @Override
    public Event crearCita(String accessToken, String summary, String location, String description,
                           String startTime, String endTime, String[] attendeeEmails)
            throws IOException, GeneralSecurityException, ParseException {

        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        Calendar service = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Medic Perú")
                .build();

        java.util.Calendar startCalendar = parseIso8601String(startTime);
        java.util.Calendar endCalendar = parseIso8601String(endTime);

        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description);

        EventDateTime start = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(startCalendar.getTime()))
                .setTimeZone("America/Lima"); // Ajusta la zona horaria según sea necesario
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(endCalendar.getTime()))
                .setTimeZone("America/Lima"); // Ajusta la zona horaria según sea necesario
        event.setEnd(end);

        if (attendeeEmails != null) {
            EventAttendee[] attendees = Arrays.stream(attendeeEmails)
                    .map(email -> new EventAttendee().setEmail(email))
                    .toArray(EventAttendee[]::new);
            event.setAttendees(Arrays.asList(attendees));
        }

        return service.events().insert("primary", event).execute();
    }
*/

}