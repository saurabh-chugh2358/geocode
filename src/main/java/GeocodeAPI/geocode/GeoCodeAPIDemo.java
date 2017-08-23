package GeocodeAPI.geocode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class GeoCodeAPIDemo {
	private static final String GEO_CODE_SERVER = "http://maps.googleapis.com/maps/api/geocode/json?";
	public Location getLongLats(String args){
		String code = args;
		String response = getLocation(code);
		String[] result = parseLocation(response);
		//System.out.println("ZipCode: " + args);
		//System.out.println("Latitude: " + result[0]);
		//System.out.println("Longitude: " + result[1]);
		Location location = new Location();
		location.setZipcode(args);
		location.setLongitude(result[1]);
		location.setLatitude(result[0]);
		return location;
	}

	private static String getLocation(String code)
	{
		String address = buildUrl(code);
		String content = null;
		try{
			URL url = new URL(address);
			InputStream stream = url.openStream();
			try{
				int available = stream.available();
				byte[] bytes = new byte[available];
				stream.read(bytes);
				content = new String(bytes);
			}finally{
				stream.close();
			}
			return (String) content.toString();
		}catch (IOException e){
			throw new RuntimeException(e);
		}
	}

	private static String buildUrl(String code){
		StringBuilder builder = new StringBuilder();
		builder.append(GEO_CODE_SERVER);
		builder.append("address=");
		builder.append(code.replaceAll(" ", "+"));
		builder.append("&sensor=false");
		//builder.append("&key=AIzaSyCgiHMfwOyRY2Q5Th6PVROk5wnvRmGhwuw");
		return builder.toString();
	}

	private static String[] parseLocation(String response){
		String[] lines = response.split("\n");
		String lat = null;
		String lng = null;
		for (int i = 0; i < lines.length; i++){
			if ("\"location\" : {".equals(lines[i].trim())){
				lat = getOrdinate(lines[i+1]);
				lng = getOrdinate(lines[i+2]);
				break;
			}
		}
		return new String[] {lat, lng};
	}

	private static String getOrdinate(String s)
	{
		String[] split = s.trim().split(" ");
		if (split.length < 1){
			return null;
		}
		String ord = split[split.length - 1];
		if (ord.endsWith(",")){
			ord = ord.substring(0, ord.length() - 1);
		}
		Double.parseDouble(ord);
		return ord;
	}
}