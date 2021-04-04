package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

public class Main2 {
	
	private static final String BASE_URL = "http://api.currencylayer.com/";
	private static final String API_KEY = "8b2de8e77c0967d683bea6a478bf74e6";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "EUR,CHF,CAD";
	private static final String DATE = "2021-03-30";

	public static void main(String[] args) {
		
		try(FileWriter file = new FileWriter("ostale_transakcije.json")){
			Gson gson = new Gson();
			
			URL url = new URL(BASE_URL + "/historical?access_key=" + API_KEY + "&date=" + DATE +
								"&source=" + SOURCE + "&currencies=" + CURRENCIES);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			con.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			JsonObject rez = gson.fromJson(reader, JsonObject.class);
			
			System.out.println(rez);
			
			double kursKanadskiDolar = rez.get("quotes").getAsJsonObject().get("USDCAD").getAsDouble();
			double kursEvro = rez.get("quotes").getAsJsonObject().get("USDEUR").getAsDouble();
			double kursSvajcarskiFranak = rez.get("quotes").getAsJsonObject().get("USDCHF").getAsDouble();
			
			double konvertovaniIznosKDolar = kursKanadskiDolar*100;
			double konvertovaniIznosEvro = kursEvro*100;
			double konvertovaniIznosSFranak = kursSvajcarskiFranak*100;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String d = "30/03/2021";
			
			Date datum = sdf.parse(d);
			
			
			
			Transakcija t1 = new Transakcija();
			t1.setIzvornaValuta(SOURCE);
			t1.setKrajnjaValuta("EUR");
			t1.setDatumTransakcije(datum);
			t1.setPocetniIznos(100);
			t1.setKonvertovaniIznos(konvertovaniIznosEvro);
			
			Transakcija t2 = new Transakcija();
			t2.setIzvornaValuta(SOURCE);
			t2.setKrajnjaValuta("CHF");
			t2.setDatumTransakcije(datum);
			t2.setPocetniIznos(100);
			t2.setKonvertovaniIznos(konvertovaniIznosSFranak);
			
			Transakcija t3 = new Transakcija();
			t3.setIzvornaValuta(SOURCE);
			t3.setKrajnjaValuta("CAD");
			t3.setDatumTransakcije(datum);
			t3.setPocetniIznos(100);
			t3.setKonvertovaniIznos(konvertovaniIznosKDolar);
			
			List<Transakcija> transakcije = new LinkedList<>();
			transakcije.add(t1);
			transakcije.add(t2);
			transakcije.add(t3);
			
			
			gson = new GsonBuilder().setPrettyPrinting().create();
			
			gson.toJson(transakcije, file);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
