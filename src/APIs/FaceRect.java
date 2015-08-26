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

//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

public class FaceRect extends APIAbs {

	private File img;
	public ImageProc imageproc;
	private HttpResponse<JsonNode> response;

	public FaceRect(String path) {
		img = new File(path);
		imageproc = new ImageProc(path);
	}

	public void post() throws UnirestException, JSONException {
		response = Unirest
				.post("https://apicloud-facerect.p.mashape.com/process-file.json")
				.header("X-Mashape-Key",
						"kCKZDyhuAxmsh2l2E7GXVfOLFe9hp1w77PbjsnmUiFR69J94RG")
				.field("image", img).asJson();
	}

	public void parse() throws JSONException {
		try {
			JSONObject obj = response.getBody().getObject();
			// System.out.println(obj);
			JSONArray faces = obj.getJSONArray("faces");
			int cantfaces = faces.length(); // TIRA BIEN LA CANTIDAD DE ROSTROS
											// ENCONTRADOS. CON ESTO ITERAR.
			// System.out.println(faces);
			// System.out.println(cantfaces);

			// FOR CADA JSONObject QUE HAYA...
			for (int i = 0; i < cantfaces; i++) {
				JSONObject faces1 = obj.getJSONArray("faces").getJSONObject(i);
				imageproc.drawRectangles(faces1);
			}
			// @SuppressWarnings("unused")
			// boolean i = imageproc.matToBufferedImage();

			// imageproc.getFacesPosition();
			// TIRA:{"orientation":"frontal","height":45,"width":45,"y":22,"x":396}

			// imageproc.drawRectangles(faces1);

			// JSONObject faces2 = obj.getJSONArray("faces").getJSONObject(1);
			// TIRA:{"orientation":"frontal","height":49,"width":49,"y":31,"x":78}
			// imageproc.drawRectangles(faces2);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	// Anda mal para más de una cara
	// Qué precisión hay si una detecta más caras que la otra? Agregamos datos?

	public float getPrecision() throws JSONException {
		Vector<Rect> faces = imageproc.getRealFacesPosition();
		Vector<Rect> APIfaces = imageproc.getAPIFacesPosition();
		float res = 0;
		for (Rect r : faces) {
			float f = 0;
			for (Rect Ar : APIfaces) {
				float f1 = getPrecisionUnitaria(r, Ar);
				if (f1 > f)
					f = f1;
			}
			res += f;
			System.out.println(res);
		}
		return (res / faces.size());
	}

	private float getPrecisionUnitaria(Rect r, Rect ar) {
		double total = r.area();
		double inter = 0;
		int x1= maximo(r.x, ar.x);
		int y1= maximo(r.y, ar.y);
		int x2= minimo(r.x+r.width, ar.x+ar.width);
		int y2= minimo(r.y+r.height, ar.y+ar.height);
		Rect Nr= new Rect(new Point(x1, y1), new Point(x2,y2));
		if ((r.contains(new Point(x1, y1))) && (r.contains(new Point(x2, y2))))
			inter=Nr.area();
		System.out.println("Rectangulo total:"+r);
		System.out.println("Segundo rectangulo:"+ar);
		System.out.println("Rectangulo interseccion:"+Nr);
		return (float) (inter / total);
	}

	private int minimo(int i, int j) {
		if (i<j)
			return i;
		return j;
	}

	private int maximo(int x, int x2) {
		if (x>x2)
			return x;
		return x2;
	}

}
