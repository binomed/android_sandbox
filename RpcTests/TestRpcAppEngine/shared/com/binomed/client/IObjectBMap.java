package com.binomed.client;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Object that expose an HashMap
 * 
 * @author jefBinomed
 * 
 */
public interface IObjectBMap extends IObjectB, Serializable {

	HashMap<String, String> getMap();

	void setMap(HashMap<String, String> map);

}
