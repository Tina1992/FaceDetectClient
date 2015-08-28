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

//ESTA API ACEPTA VARIAS IMAGENES

public class SkyBiometry_FaceDetection extends APICompuestaAbs {
	private HttpResponse<JsonNode> response;
	private Vector<File> files = new Vector<File>();
	long startTime=0;
	long endTime=0;
	

	public SkyBiometry_FaceDetection(File f) {
		//dataset = new VectorImageProc(f.getAbsolutePath()); // EN TODOS LOS HIJOS
		super();
		dataset.add(new ImageProc(f.getAbsolutePath()));
		files.add(f);
	}

	public void addImage(File f) {
		dataset.add(new ImageProc(f.getAbsolutePath()));
		files.add(f);
	}

	public HttpResponse<JsonNode> post() throws UnirestException {
		startTime = System.nanoTime();
		response = Unirest
				.post("https://face.p.mashape.com/faces/detect?api_key=771b5991a5ff4ce1a7cfcef2e1d91ae5&api_secret=cfc895f2c40e475eb1032be25b63f969")
				.header("X-Mashape-Key",
						"kCKZDyhuAxmsh2l2E7GXVfOLFe9hp1w77PbjsnmUiFR69J94RG")
				.field("attributes", "all").field("detector", "Aggressive")
				.field("files", files).asJson();
		endTime = System.nanoTime();
		return response;
	}

	public void parse() throws JSONException {

		JSONObject auxiliar = new JSONObject();

		try {
			JSONObject obj = response.getBody().getObject();
			int cantPhotos = ((JSONArray) obj.get("photos")).length();
			System.out.println(cantPhotos);
			for (int j = 0; j < files.size(); j++) {
				int imgHeight = (int) ((JSONArray) obj.get("photos"))
						.getJSONObject(j).get("height");
				int imgWidth = (int) ((JSONArray) obj.get("photos"))
						.getJSONObject(j).get("width");

				JSONObject faces = ((JSONArray) obj.get("photos"))
						.getJSONObject(j); // DATOS DE TODOS LOS ROSTROS: TAGS Y
											// RESTO.

				int cantfaces = faces.getJSONArray("tags").length();

				for (int i = 0; i < cantfaces; i++) {
					JSONObject face = faces.getJSONArray("tags").getJSONObject(
							i);
					JSONObject coordenadas = face.getJSONObject("center");
					double faceH = (double) face.get("height") / 100
							* imgHeight;
					double faceW = (double) face.get("width") / 100 * imgWidth;
					double valueX = (((double) coordenadas.get("x")) / 100 * imgWidth)
							- faceW / 2;
					double valueY = ((double) coordenadas.get("y")) / 100
							* imgHeight - faceH / 2;
					double valueHeight = faceH;
					double valueWidth = faceW;
					auxiliar.put("x", valueX);
					auxiliar.put("y", valueY);
					auxiliar.put("height", valueHeight);
					auxiliar.put("width", valueWidth);
					Rect r = new Rect(new Point(valueX, valueY), new Point(
							valueX + valueWidth, valueY + valueHeight));
					ImageProc imageproc=dataset.get(j);
					imageproc.drawRectangles(auxiliar);
				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public void display(){
		for (ImageProc i:dataset){
			i.displayImage();
		}
	}

	@Override
	public float getTimeRequest() {
		// TODO Auto-generated method stub
		return endTime-startTime;
	}
}
