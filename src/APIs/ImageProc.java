package APIs;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ImageProc {
	private Mat image;
	private Mat imageOpen;
	private BufferedImage imProc;
	private BufferedImage imOpen;
	private Vector<JSONObject> caras;

	public ImageProc(String path_image) {
		image = Imgcodecs.imread(path_image);
		imageOpen = Imgcodecs.imread(path_image);
		caras = new Vector<JSONObject>();
	}

	// ESTE METODO ANDA BIEN PARA EL PARSER DE FACERECT.
	public void drawRectangles(JSONObject obj) throws JSONException {
		caras.add(obj);
		int x = (int) obj.get("x");
		int y = (int) obj.get("y");
		int height = (int) obj.get("height");
		int width = (int) obj.get("width");
		Imgproc.rectangle(image, new Point(x, y), new Point(x + width, y
				+ height), new Scalar(0, 255, 0), 2); // EL 2 FINAL ES EL GROSOR
														// DEL RECTANGULO

	}

	public void savedImage(Mat image) {
		String filename = "faceDetection.png";
		Imgcodecs.imwrite(filename, image);
	}

	public boolean matToBufferedImage() {
		MatOfByte mb = new MatOfByte();
		MatOfByte mb1 = new MatOfByte();
		Imgcodecs.imencode(".jpg", image, mb);
		Imgcodecs.imencode(".jpg", imageOpen, mb1);
		try {
			this.imProc = ImageIO.read(new ByteArrayInputStream(mb.toArray()));
			this.imOpen = ImageIO.read(new ByteArrayInputStream(mb1.toArray()));
		} catch (IOException e) {
			e.printStackTrace();
			return false; // Error
		}
		return true; // Successful
	}

	public void displayImage() {
		// MOSTRAR LA IMAGEN
		if (matToBufferedImage()) {
			JFrame frame = new JFrame("API");
			JLabel CuadroImagen = new JLabel();
			CuadroImagen.setBounds(29, 11, 436, 330);
			ImageIcon img = new ImageIcon(imProc);

			Icon icono = new ImageIcon(img.getImage().getScaledInstance(
					CuadroImagen.getWidth(), CuadroImagen.getHeight(),
					Image.SCALE_DEFAULT));
			CuadroImagen.setIcon(icono);

			frame.getContentPane().add(CuadroImagen, BorderLayout.CENTER);
			frame.setSize(515, 440);
			frame.setVisible(true);

			JFrame frame1 = new JFrame("Open");
			JLabel CuadroImagen1 = new JLabel();
			CuadroImagen1.setBounds(29, 11, 436, 330);
			ImageIcon img1 = new ImageIcon(imOpen);

			Icon icono1 = new ImageIcon(img1.getImage().getScaledInstance(
					CuadroImagen1.getWidth(), CuadroImagen1.getHeight(),
					Image.SCALE_DEFAULT));
			CuadroImagen1.setIcon(icono1);

			frame1.getContentPane().add(CuadroImagen1, BorderLayout.CENTER);
			frame1.setSize(515, 440);
			frame1.setVisible(true);
		}
	}

	public Vector<JSONObject> getFaces() {
		return caras;
	}

	public Vector<Rect> getRealFacesPosition() {
		CascadeClassifier faceDetector = new CascadeClassifier(
				"haarcascade_frontalface_default.xml");
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(imageOpen, faceDetections);
		Vector<Rect> vector = new Vector<Rect>();
		for (Rect rect : faceDetections.toArray()) {
			vector.add(rect);
			Imgproc.rectangle(imageOpen, new Point(rect.x, rect.y), new Point(
					rect.x + rect.width, rect.y + rect.height), new Scalar(0,
					255, 0), 2);
		}
		return vector;
	}

	public Vector<Rect> getAPIFacesPosition() throws JSONException {
		Vector<Rect> vector = new Vector<Rect>();
		for (JSONObject o : caras) {
			Rect r = new Rect((int) o.getInt("x"), (int) o.getInt("y"),
					(int) o.getInt("height"), (int) o.getInt("width"));
			vector.add(r);
		}
		return vector;
	}

}
