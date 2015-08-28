package APIs;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public abstract class APIAbs {
	public ImageProc imageproc; //NO ESTA INICIALIZADA!!

	public abstract HttpResponse<JsonNode> post() throws UnirestException, JSONException;

	public abstract void parse() throws JSONException;

}
