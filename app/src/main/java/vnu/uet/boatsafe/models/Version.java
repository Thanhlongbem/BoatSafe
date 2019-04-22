package vnu.uet.boatsafe.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Version{

	@SerializedName("versionName")
	private String versionName;

	@SerializedName("versionCode")
	private int versionCode;

	public void setVersionName(String versionName){
		this.versionName = versionName;
	}

	public String getVersionName(){
		return versionName;
	}

	public void setVersionCode(int versionCode){
		this.versionCode = versionCode;
	}

	public int getVersionCode(){
		return versionCode;
	}

	@Override
 	public String toString(){
		return 
			"Version{" + 
			"versionName = '" + versionName + '\'' + 
			",versionCode = '" + versionCode + '\'' + 
			"}";
		}
}