package pl.sda.finalapp.app.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.sda.finalapp.app.users.User;
import pl.sda.finalapp.app.users.UserContextService;
import pl.sda.finalapp.app.users.UserService;

@Service
public class WeatherService {

    private final String apiUrl = "http://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "ea900b66f547fd7b23625544873a4200";
    @Autowired
    private RestTemplate weatherRestTemplate;
    @Autowired
    private UserContextService userContextService;
    @Autowired
    private UserService userService;

    public String downloadWeather() {
        final String userEMail = userContextService.userName();
        final User user = userService.findUserByEMail(userEMail).orElse(null);
        String city = user == null ? "Lublin" : user.getCity();
        String country = user == null ? "PL" : user.getCountry();
        String cityAndCountry = (city==null || city.isBlank() || country==null || country.isBlank())?"Lublin,PL":city + "," + country;
        String url = apiUrl
                + "?q=" + "Lublin,pl"
                + "&appId=" + apiKey
                + "&units=" + "metric"
                + "&lang=" + "pl";
        final ResponseEntity<String> response = weatherRestTemplate.getForEntity(url, String.class);
        System.out.println();
        return response.getBody();
    }
}