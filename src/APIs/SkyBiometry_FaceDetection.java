package APIs;
import java.io.File;

import org.eclipse.rap.json.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class SkyBiometry_FaceDetection extends APIAbs{
	private HttpResponse<JsonNode> response;

	public HttpResponse<JsonNode> post() throws UnirestException{
		response = Unirest.post("https://face.p.mashape.com/faces/detect?api_key=771b5991a5ff4ce1a7cfcef2e1d91ae5&api_secret=cfc895f2c40e475eb1032be25b63f969")
				.header("X-Mashape-Key", "kCKZDyhuAxmsh2l2E7GXVfOLFe9hp1w77PbjsnmUiFR69J94RG")
				.field("attributes", "all")
				.field("detector", "Aggressive")
				.field("files", new File("personas.png"))
				.asJson();
		
		return response;
	}

	
	public void parse() throws JSONException{
		
		JSONObject auxiliar=new JSONObject();

		try
		{
			JSONObject 	obj= response.getBody().getObject();
			int prueba=((JSONArray) obj.get("photos")).length();
			System.out.println(prueba);
			JSONObject faces= ((JSONArray) obj.get("photos")).getJSONObject(0); //DATOS DE TODOS LOS ROSTROS: TAGS Y RESTO.
			
			int cantfaces=faces.getJSONArray("tags").length();
			for(int i=0;i<cantfaces;i++)
			{
				JSONObject face=faces.getJSONArray("tags").getJSONObject(0);
				JSONObject coordenadas=face.getJSONObject("center");
				double valueX=((double) coordenadas.get("x"))-(double)face.get("width");
				double valueY=((double) coordenadas.get("y"))-(double)face.get("height");
				double valueHeight=(double)face.get("height")*2;
				double valueWidth=(double)face.get("width")*2;
				auxiliar.put("x", valueX);
				auxiliar.put("y", valueY);
				auxiliar.put("height",valueHeight);
				auxiliar.put("width", valueWidth);
				
				imageproc.drawRectangles(auxiliar);
			}
			
		} catch(ParseException e) {
            e.printStackTrace(); }
		
	}
}

