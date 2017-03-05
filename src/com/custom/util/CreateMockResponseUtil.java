package com.custom.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateMockResponseUtil {

	public static JSONObject createLiveQuoteJSON(int dataParam, int ohlcParam, int depthParam) {
		JSONObject outerJsonObj = new JSONObject();
		outerJsonObj.put("status", "success");
		outerJsonObj.put("data", createInnerDataJson(dataParam, ohlcParam, depthParam));
		return outerJsonObj;
	}

	public static JSONObject createInnerDataJson(int dataParam, int ohlcParam, int depthParam) {
		JSONObject innerDataObj = new JSONObject();
		innerDataObj.put("symbol", "HINDALCO");
		innerDataObj.put("last_price", 1083.15);
		innerDataObj.put("change_percent", 3);
		innerDataObj.put("change", 30);
		innerDataObj.put("volume", 50000);
		innerDataObj.put("sell/buy", 1249877);
		innerDataObj.put("buy_quantity", 1249877);
		innerDataObj.put("sell_quantity", 2650980);
		innerDataObj.put("open_interest", 1232440);
		innerDataObj.put("last_quantity", 500);
		innerDataObj.put("last_time", "2015-12-15 10:16:36");
		innerDataObj.put("ohlc", createOHLCJson(ohlcParam));
		innerDataObj.put("depth", createDepthJson(depthParam));
		return innerDataObj;
	}

	public static JSONObject createOHLCJson(int ohlcParam) {
		JSONObject ohlcObj = new JSONObject();
		ohlcObj.put("open", 1103.4);
		ohlcObj.put("high", 1103.4);
		ohlcObj.put("low", 1079.45);
		ohlcObj.put("close", 1083.15);
		return ohlcObj;
	}

	public static JSONObject createDepthJson(int depthParam) {
		JSONObject depthObj = new JSONObject();
		depthObj.put("buy", createDepthBuyJson(depthParam));
		depthObj.put("sell", createDepthSellJson(depthParam));
		return depthObj;
	}

	public static JSONArray createDepthBuyJson(int depthParam) {
		JSONArray depthBuyArray = new JSONArray();
		JSONObject depthBuyObj = new JSONObject();
		depthBuyObj.put("price", 1234);
		depthBuyObj.put("orders", 45);
		depthBuyObj.put("quantity", 451);
		depthBuyArray.put(depthBuyObj);
		return depthBuyArray;
	}

	public static JSONArray createDepthSellJson(int depthParam) {
		JSONArray depthSellArray = new JSONArray();
		JSONObject depthSellObj = new JSONObject();
		depthSellObj.put("price", 1234);
		depthSellObj.put("orders", 45);
		depthSellObj.put("quantity", 451);
		depthSellArray.put(depthSellObj);
		return depthSellArray;
	}
}
