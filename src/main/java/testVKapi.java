import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

public class VkApiExample {
    public static void main(String[] args) {
        String groupId = "your_group_id"; // Замените на ID вашей группы
        String accessToken = "your_access_token"; // Замените на ваш access token

        try {
            String apiUrl = String.format("https://api.vk.com/method/groups.getMembers?group_id=%s&access_token=%s&v=5.131", groupId, accessToken);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());

            if (jsonResponse.has("response")) {
                JSONArray items = jsonResponse.getJSONObject("response").getJSONArray("items");
                System.out.println(items);
            } else {
                System.out.println("Ошибка при запросе данных");
                System.out.println(jsonResponse);
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}