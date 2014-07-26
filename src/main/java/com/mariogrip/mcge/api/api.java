package com.mariogrip.mcge.api;

import com.mariogrip.mcge.quarry.quarryUtils;

public class api{
	quarryUtils quarry = new quarryUtils();

	public String getBalance(String UUID){
		return quarry.getBalance(UUID);
	}
	public String getTop(){
		return quarry.getTop();
	}
	public boolean sendCoins(String UUID, String Password, String Amount, String toUUID){
		return quarry.sendCoins(UUID, Password, Amount, toUUID);
	}
	public boolean checkPassword(String UUID, String Password){
		return quarry.checkPass(UUID, Password);
	}

}
