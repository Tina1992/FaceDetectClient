package APIs;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;


public class Faceplusplus_face_detection extends APIAbs{
	
	public void post() throws UnirestException{
		HttpResponse<JsonNode> response = Unirest.get("https://faceplusplus-faceplusplus.p.mashape.com/detection/detect?attribute=glass%2Cpose%2Cgender%2Cage%2Crace%2Csmiling&url=http%3A%2F%2Fmecatronicaavanzada.com.co%2Fwp-content%2Fuploads%2F2013%2F07%2Fslider2_persona.png")
				.header("X-Mashape-Key", "7rS5YDw5YHmshtdgMHP2ZYBLAljfp1OxKNzjsn1GJxNBgad6C9")
				.header("Accept", "application/json")
				.routeParam("attribute", "glass,pose,gender,age,race,smiling")
				.routeParam("url","http://mecatronicaavanzada.com.co/wp-content/uploads/2013/07/slider2_persona.png")
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
