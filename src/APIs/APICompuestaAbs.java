package APIs;

import java.util.Vector;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public abstract class APICompuestaAbs extends APIAbs {

	protected Vector<ImageProc> dataset = new Vector<ImageProc>();
	@Override
	public abstract HttpResponse<JsonNode> post() throws UnirestException, JSONException;

	@Override
	public abstract void parse() throws JSONException;

	@Override
	public void display() {
		// TODO Auto-generated method stub
		for (ImageProc a: dataset){
			a.displayImage();
		}
	}
	
	public float getPrecision() throws JSONException{
		float f=0;
		for (ImageProc i: dataset){
			f+=i.getPrecision();
		}
		return f/dataset.size();
	}

}
