package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

public class Main1 {
	
	private static final String BASE_URL = "http://api.currencylayer.com/";
	private static final String API_KEY = "8b2de8e77c0967d683bea6a478bf74e6";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "CAD";
	private static final int AMOUNT = 46;
	

	public static void main(String[] args) {
		
		
		try(FileWriter file = new FileWriter("prva_transakcija.json")){
			Gson gson = new Gson();
			
//			URL url = new URL(BASE_URL + "/convert?access_key=" + API_KEY +
//									"&from=" + SOURCE + "&to=" + CURRENCIES + "&amount="+AMOUNT);
//			
//		
//			HttpURLConnection con = (HttpURLConnection)url.openConnection();
//			
//			con.setRequestMethod("GET");
//
//			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			
//			JsonObject rez = gson.fromJson(reader, JsonObject.class);
//			
//			System.out.println(rez);
//			
//			double konvertovaniIznos = rez.get("result").getAsDouble();
//			
//			
//			Transakcija t = new Transakcija();
//			t.setIzvornaValuta(SOURCE);
//			t.setKrajnjaValuta(CURRENCIES);
//			t.setDatumTransakcije(new Date());
//			t.setPocetniIznos(AMOUNT);
//			t.setKonvertovaniIznos(konvertovaniIznos);
//			
//			
//			gson = new GsonBuilder().setPrettyPrinting().create();
//			
//			gson.toJson(t, file);
			
			
			URL url = new URL(BASE_URL + "/live?access_key=" + API_KEY +
					"&source=" + SOURCE + "&currencies=" + CURRENCIES);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			con.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			JsonObject rez = gson.fromJson(reader, JsonObject.class);
			
			System.out.println(rez);
			
			double kurs = rez.get("quotes").getAsJsonObject().get("USDCAD").getAsDouble();
			
			double konvertovaniIznos = kurs*AMOUNT;
			
			Transakcija t = new Transakcija();
			t.setIzvornaValuta(SOURCE);
			t.setKrajnjaValuta(CURRENCIES);
			t.setDatumTransakcije(new Date());
			t.setPocetniIznos(AMOUNT);
			t.setKonvertovaniIznos(konvertovaniIznos);
			
			
			gson = new GsonBuilder().setPrettyPrinting().create();
			
			gson.toJson(t, file);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

}
