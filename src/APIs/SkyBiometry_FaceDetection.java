package APIs;
import java.io.File;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class SkyBiometry_FaceDetection extends APIAbs{

	public void post() throws UnirestException{
		HttpResponse<JsonNode> response = Unirest.post("https://face.p.mashape.com/faces/detect?api_key=771b5991a5ff4ce1a7cfcef2e1d91ae5&api_secret=cfc895f2c40e475eb1032be25b63f969")
				.header("X-Mashape-Key", "kCKZDyhuAxmsh2l2E7GXVfOLFe9hp1w77PbjsnmUiFR69J94RG")
				.field("attributes", "all")
				.field("detector", "Aggressive")
				.field("files", new File("personas.png"))
				.asJson();
		
		try
		{
			JSONObject 	obj= response.getBody().getObject();
			System.out.println(obj);
			
			
			
		} catch(ParseException e) {
            e.printStackTrace(); }
	}

	@Override
	public void parse() throws JSONException {
		// TODO Auto-generated method stub
		
	}
}
