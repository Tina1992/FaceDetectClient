package APIs;

import java.util.Vector;

import org.json.JSONException;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public abstract class APIAbs {

	public abstract HttpResponse<JsonNode> post() throws UnirestException, JSONException;

	public abstract void parse() throws JSONException;

	public abstract void display();
	
	public abstract float getPrecision() throws JSONException;
	
	public abstract float getTimeRequest();
}
