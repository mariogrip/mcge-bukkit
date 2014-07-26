package com.mariogrip.mcge.quarry;

public class quarryUtils extends mcgeServer {

	public String getBalance(String User){
		try {
			return this.sendGet("getBalance", User);
		} catch (Exception e) {
			return " (I got an error please report) ";
		}
	}
	public boolean checkCon(){
			try {
				if (this.sendGet("checkCon")=="true") return true;
				else return true;
			} catch (Exception e) {
				return false;
			}


		
	}
	public boolean checkPass(String User, String Pass){
		try {
			if (this.sendGet("checkPass", User, Pass) == "true"){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	public double getBalance_double(String User){
		try {
			double bal = Double.parseDouble(this.getBalance(User));
			return bal;
		} catch (Exception e){
			double bal = 0;
			return bal;
		}
	}
	public String getTop(){
		try {
			return this.sendGet("getTop");
		} catch (Exception e) {
			return " (I got an error please report) ";
		}
	}
	public boolean sendCoins(String User, String Pass, String Amount, String ToUser){
		try {
			if (Double.parseDouble(this.getBalance(User)) >= Double.parseDouble(Amount)){
			if (this.sendGet("sendCoins", User, Pass, Amount, ToUser) == "true"){
			return true;
			}else{return false;}
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	public boolean newAccount(){
		return true;
	}
	
}
