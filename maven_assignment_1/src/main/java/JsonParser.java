import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class JsonParser {
    public static void parseJSOn(String url) throws ParseException {
        Client client = Client.create();
        WebResource webResource = client.resource(url);

        ClientResponse clientResponse =
                webResource.accept("application/json").get(ClientResponse.class);
        if(clientResponse.getStatus() !=200) {
            throw new RuntimeException("Failed" + clientResponse.toString());
        }

        JSONArray jsonArray =
                (JSONArray) new JSONParser().parse(clientResponse.getEntity(String.class));

        Iterator<Object> it = jsonArray.iterator();
        List<Student> students = new ArrayList<Student>();

        while(it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
            Student student = new Student();
            String id = String.valueOf(jsonObject.get("id"));
            String firstName = String.valueOf(jsonObject.get("first_name"));
            String gpa = String.valueOf(jsonObject.get("gpa"));
            String email = String.valueOf(jsonObject.get("email"));
            String gender = String.valueOf(jsonObject.get("gender"));
            student.setId(id);
            student.setFirstName(firstName);
            student.setGpa(gpa);
            student.setEmail(email);
            student.setGender(gender);
            students.add(student);
        }

        int code = 0;
        String name;
        String gender;

        while(code != 3) {
            Scanner input = new Scanner(System.in);
            Scanner search = new Scanner(System.in);
            System.out.println("Enter 1 To Search By Name");
            System.out.println("Enter 2 To Search By Gender");
            System.out.println("Enter 3 To Exit");
            code = input.nextInt();
            System.out.println("\n");
            if(code == 1) {
                System.out.println("Enter a name:");
                name = search.nextLine();
                for (Student student : students) {
                    if (Objects.equals(student.getFirstName(), name)) {
                        System.out.println(student);
                    }
                }
            }
            if (code == 2) {
                System.out.println("Enter a gender (Male, Female):");
                gender = search.nextLine();
                for (Student student : students) {
                    if (Objects.equals(student.getGender(), gender)) {
                        System.out.println(student);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        parseJSOn("https://hccs-advancejava.s3.amazonaws.com/student.json");
    }
}
