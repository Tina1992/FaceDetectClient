package APIs;

import java.io.File;
import java.util.Vector;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class FaceRect extends APISimpleAbs {
	private File img;
	private HttpResponse<JsonNode> response;
	private float startTime=0;
	private float endTime=0;

	public FaceRect(File f) { // LES VA A LLEGAR DIRECTAMENTE EL FILE (IMG)
		img = f;
		imageproc = new ImageProc(f.getAbsolutePath());
	}

	public HttpResponse<JsonNode> post() throws UnirestException, JSONException {
		File img = new File(
				"C:/Users/marmota/workspace/HttpComponents - Prueba/personas.png");
		startTime = System.nanoTime();
		response = Unirest
				.post("https://apicloud-facerect.p.mashape.com/process-file.json")
				.header("X-Mashape-Key",
						"kCKZDyhuAxmsh2l2E7GXVfOLFe9hp1w77PbjsnmUiFR69J94RG")
				.field("image", img).asJson();
		endTime = System.nanoTime();
		return response;
	}

	public void parse() throws JSONException {
		try {
			JSONObject obj = response.getBody().getObject();
			JSONArray faces = (JSONArray) obj.get("faces");

			int cantfaces = faces.length();
			for (int i = 0; i < cantfaces; i++)
				imageproc.drawRectangles(faces.getJSONObject(i));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public float getPrecision() throws JSONException {
		// TODO Auto-generated method stub
		return imageproc.getPrecision();
	}

	@Override
	public float getTimeRequest() {
		// TODO Auto-generated method stub
		return endTime-startTime;
	}

	// Anda mal para más de una cara
	// Qué precisión hay si una detecta más caras que la otra? Agregamos datos?

}
