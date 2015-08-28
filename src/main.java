import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Core;

import APIs.FaceRect;
import APIs.SkyBiometry_FaceDetection;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class main {
	

	public static void main(String[] args) throws UnirestException,
			JSONException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		File img1 = new File("Aaron_Eckhart_0001.jpg");
		File img2 = new File("Aaron_Guiel_0001.jpg");
		File img3 = new File("Aaron_Patterson_0001.jpg");
		File img4 = new File("Aaron_Peirsol_0001.jpg");
		File img5 = new File("Aaron_Peirsol_0002.jpg");
		File img6 = new File("Aaron_Peirsol_0003.jpg");
		File img7 = new File("Aaron_Peirsol_0004.jpg");
		File img8 = new File("Aaron_Pena_0001.jpg");
		File img9 = new File("Aaron_Sorkin_0001.jpg");
		File img10 = new File("Aaron_Sorkin_0002.jpg");

		// FACERECT:
		/*HttpResponse<JsonNode> response1 = Unirest
				.post("https://apicloud-facerect.p.mashape.com/process-file.json")
				.header("X-Mashape-Key",
						"kCKZDyhuAxmsh2l2E7GXVfOLFe9hp1w77PbjsnmUiFR69J94RG")
				.field("image", img1).asJson();
		
		Parser parser=new Parser("Aaron_Eckhart_0001.jpg");
		parser.parse(response1);*/
		SkyBiometry_FaceDetection f=new SkyBiometry_FaceDetection(img5);
		f.addImage(img1);
		f.post();
		f.parse();
		System.out.println("Precisión:" +f.getPrecision());
		System.out.println("Duración:"+(f.getTimeRequest()/1000000000));
		f.display();

		// INFORMACION (USANDO LAS JPG QUE ESTAN EN EL PROYECTO)
		// 80 REQUEST: TARDA 1MIN, 11SEG, 78MILESIMAS.

		/*
		 * try { JSONObject obj= response.getBody().getObject();
		 * //System.out.println(obj);
		 * 
		 * 
		 * //PARA PARSEAR //"FACES" ES UTILIZADO POR FACERECT JSONArray
		 * faces=obj.getJSONArray("faces"); int cantfaces=faces.length();
		 * //System.out.println(faces); //System.out.println(cantfaces);
		 * 
		 * //EN CADA CLASE DE APIS, PODRIA AGREGAR UN METODO ESPECIFICO DE
		 * OBTENER LOS DATOS... //EN TODOS, LA IDEA ES OBTENER EL X, Y, WIDTH,
		 * HEIGHT PARA DIBUJAR EL CUADRADO.
		 * 
		 * 
		 * 
		 * } catch(ParseException e) { e.printStackTrace(); }
		 * 
		 * 
		 * PRUEBA DE CARGAR VARIAS IMAGENES DE UN DIRECTORIO: File dir = new
		 * File("C:/Users/marmota/Desktop/SIL/PRUEBA"); File[] directoryListing
		 * = dir.listFiles(); if (directoryListing != null) { for (File child :
		 * directoryListing) { System.out.println(child.getName()); }
		 * 
		 * //FUNCIONAAAAAAAAA: DEVUELVE TODOS LOS NOMBRES CORRECRTAMENTEEEE!!!!
		 */
	}

}
