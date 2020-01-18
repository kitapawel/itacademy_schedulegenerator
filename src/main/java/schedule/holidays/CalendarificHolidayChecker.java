package schedule.holidays;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import schedule.util.PropertiesReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalendarificHolidayChecker implements HolidayChecker {

    private PropertiesReader propertiesReader = PropertiesReader.getInstance();

    @Override
    public Collection<LocalDate> getHolidays(LocalDate startDate, LocalDate endDate) {
        String jsonPath = propertiesReader.readProperty("calendarific.jsonPath");

        Collection<LocalDate> holidays = new ArrayList<>();
        Collection<Integer> years = getYears(startDate, endDate);
        for (Integer year : years) {
            String response = performApiCall(year);
            DocumentContext jsonContext = JsonPath.parse(response);
            List<String> dateStrings = jsonContext.read(jsonPath);
            for (String dateString : dateStrings) {
                holidays.add(LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE));
            }
        }

        return holidays;
    }

    private String performApiCall(Integer year) {
        try {
            Thread.sleep(1100);
        } catch (InterruptedException ignore) {
            //ignored
        }

        String url = propertiesReader.readProperty("calendarific.url");
        String path = propertiesReader.readProperty("calendarific.path");
        String apiKey = propertiesReader.readProperty("calendarific.apiKey");
        String countryCode = propertiesReader.readProperty("calendarific.coutryCode");
        String holidaysType = propertiesReader.readProperty("calendarific.holidaysType");

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url)
                .path(path)
                .queryParam("api_key", apiKey)
                .queryParam("country", countryCode)
                .queryParam("type", holidaysType)
                .queryParam("year", year);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        return invocationBuilder.get(String.class);
    }

    private Collection<Integer> getYears(LocalDate startDate, LocalDate endDate) {
        int startDateYear = startDate.getYear();
        int endDateYear = endDate.getYear();
        Collection<Integer> years = new ArrayList<>();
        for (int i = startDateYear; i <= endDateYear; i++) {
            years.add(i);
        }
        return years;
    }
}
