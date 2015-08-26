package APIs;

import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;

public abstract class APIAbs {

	public abstract void post() throws UnirestException, JSONException;

	public abstract void parse() throws JSONException;

}
