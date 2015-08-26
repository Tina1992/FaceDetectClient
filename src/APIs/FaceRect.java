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

public class FaceRect extends APIAbs{
	
	private File img;
	public ImageProc imageproc;
	private HttpResponse<JsonNode> response;
	
	public FaceRect(String path){
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
	
	public void parse() throws JSONException{
		try {
			JSONObject obj = response.getBody().getObject();
			//System.out.println(obj);
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
			//@SuppressWarnings("unused")
			//boolean i = imageproc.matToBufferedImage();
			
			//imageproc.getFacesPosition();
			// TIRA:{"orientation":"frontal","height":45,"width":45,"y":22,"x":396}

			// imageproc.drawRectangles(faces1);

			// JSONObject faces2 = obj.getJSONArray("faces").getJSONObject(1);
			// TIRA:{"orientation":"frontal","height":49,"width":49,"y":31,"x":78}
			// imageproc.drawRectangles(faces2);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	//Anda mal para m�s de una cara
	//Qu� precisi�n hay si una detecta m�s caras que la otra?
	
	public float getPrecision() throws JSONException{
		Vector<Rect> faces=imageproc.getRealFacesPosition();
		System.out.println("FACES OpenCV:"+faces);
		Vector<Rect> APIfaces=imageproc.getAPIFacesPosition();
		System.out.println("FACES Api:"+APIfaces);
		float res=0;
		for (Rect r: faces){
			float f=0;
			for (Rect Ar:APIfaces){
				float f1=getPrecisionUnitaria(r, Ar);
				System.out.println(f1);
				if (f1>f)
					f=f1;
			}
			res+=f;
		}
		return res/faces.size();
	}

	private float getPrecisionUnitaria(Rect r, Rect ar) {
		double total=r.area();
		double inter=0;
		if (r.contains(new Point(ar.x, ar.y))){
			if (r.contains(new Point(ar.x+ar.width, ar.y+ar.height)))
				inter=ar.area();
			else
			{
				Rect NNr=new Rect(new Point(ar.x,ar.y), new Point(r.x, r.y));
				inter=NNr.area();
			}
				
		}
		else
		{
			if (r.contains(new Point(ar.x+ar.width, ar.y+ar.height)))
			{
				Rect Nr=new Rect(new Point(r.x, r.y), new Point(ar.x+ar.width, ar.y+ar.height));
				inter=Nr.area();
			}
		}
		return (float) (inter/total);
	}

}