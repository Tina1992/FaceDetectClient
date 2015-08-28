package APIs;

import java.util.Vector;

import org.json.JSONException;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public abstract class APISimpleAbs extends APIAbs {

	public ImageProc imageproc; //Inicializada en todos los hijos con el path de la imagen
	
	@Override
	public abstract HttpResponse<JsonNode> post() throws UnirestException, JSONException;

	@Override
	public abstract void parse() throws JSONException ;

	@Override
	public void display() {
		// TODO Auto-generated method stub
		imageproc.displayImage();
	}

}
