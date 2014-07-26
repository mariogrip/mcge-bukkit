package com.mariogrip.mcge.quarry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class mcgeServer {
	
    private String Server = "http://mariogrip.com/mcge";
	private final String USER_AGENT = "Mozilla/5.0";
	
	
	public String sendGet(String cmd) throws Exception{
		return this.sendGet(cmd, "none", "none", "none", "none");
	}
	public String sendGet(String cmd, String User) throws Exception{
		return this.sendGet(cmd, User, "none", "none", "none");
	}
	public String sendGet(String cmd, String User, String Pass) throws Exception{
		return this.sendGet(cmd, User, Pass, "none", "none");
	}
	public String sendGet(String cmd, String User, String Pass, String Amount, String ToUser) throws Exception {
		String url;
		if (User == "none" && Pass == "none" && Amount == "none" && ToUser == "none"){
			url = Server + "/api/api.php?cmd=" + cmd;
		}else if (User != "none" && Pass == "none" && Amount == "none" && ToUser == "none"){
		    url = Server + "/api/api.php?cmd=" + cmd + "&user=" + User;
		}else if (User != "none" && Pass != "none" && Amount == "none" && ToUser == "none"){
			url = Server + "/api/api.php?cmd=" + cmd + "&pass=" + Pass + "&user=" + User;
		}else if (User != "none" && Pass != "none" && Amount != "none" && ToUser == "none"){
			url = Server + "/api/api.php?cmd=" + cmd + "&user=" + User + "&pass=" + Pass + "&Amount=" + Amount;
		}else if (User != "none" && Pass != "none" && Amount != "none" && ToUser != "none"){
			url = Server + "/api/api.php?cmd=" + cmd + "&user=" + User + "&pass=" + Pass + "&Amount=" + Amount + "&toUser=" + ToUser;
		}else{
			url = Server + "";
		       throw new Exception("Not Correct CMD!");
		}
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
        return response.toString();
	}
	public boolean checkCMD(String Cmd) throws Exception{
		if (this.sendGet(Cmd) == "true"){
			return true;
		}else{
			return false;
		}
	}
}
