package com.custom.service;

import static com.custom.util.CreateMockResponseUtil.createLiveQuoteJSON;

import org.json.JSONArray;

public class MockDataService {

	public static JSONArray createLiveQuotes(int dataParam, int ohlcParam, int depthParam) {

		JSONArray outerJsonArray = new JSONArray();
		for (int i = 0; i < 200; i++) {
			outerJsonArray.put(createLiveQuoteJSON(i, i, i));
		}
		return outerJsonArray;
	}
}
