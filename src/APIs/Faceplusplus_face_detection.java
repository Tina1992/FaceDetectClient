package APIs;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

//IMPORTANTE: ESTA API SOLO DETECTA DE A UNA IMAGEN

public class Faceplusplus_face_detection extends APISimpleAbs{
	private HttpResponse<JsonNode> response;
	long startTime=0;
	long endTime=0;
	
	public HttpResponse<JsonNode> post() throws UnirestException{
		startTime = System.nanoTime();
		response = Unirest.get("https://faceplusplus-faceplusplus.p.mashape.com/detection/detect?attribute=glass%2Cpose%2Cgender%2Cage%2Crace%2Csmiling&url=http%3A%2F%2Fmecatronicaavanzada.com.co%2Fwp-content%2Fuploads%2F2013%2F07%2Fslider2_persona.png")
				.header("X-Mashape-Key", "7rS5YDw5YHmshtdgMHP2ZYBLAljfp1OxKNzjsn1GJxNBgad6C9")
				.header("Accept", "application/json")
				.asJson();
		
		endTime = System.nanoTime();

		return response;
	}

	
	public void parse() throws JSONException {
		JSONObject auxiliar=new JSONObject();

		try
		{
			JSONObject 	obj= response.getBody().getObject();
			JSONArray faces= obj.getJSONArray("face"); 
			
			int cantfaces=faces.length();
			
			for(int i=0;i<cantfaces;i++)
			{
				JSONObject face=faces.getJSONObject(i).getJSONObject("position");
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


	@Override
	public float getPrecision() throws JSONException {
		return imageproc.getPrecision();
	}


	@Override
	public float getTimeRequest() {

		return endTime - startTime;
	}
}
