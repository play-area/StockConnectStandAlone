package com.custom.service;

import static com.custom.constants.AllConstants.NIFTY_FIFTY_CASH;
import static com.custom.constants.AllConstants.NIFTY_FUTUTRES_CASH;
import static com.custom.constants.AllConstants.NSE_SYMBOL;

import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONException;

import com.custom.util.StockSymbolsUtil;
import com.rainmatter.kiteconnect.KiteConnect;
import com.rainmatter.kitehttp.exceptions.KiteException;
import com.rainmatter.models.Quote;

/**
 * @author Kulbir
 *
 */
public class StrategyService {

	/*
	 * Strategy : if Buyers greater than twice the sellers and LTP is Above or
	 * Below Open
	 */

	public static void executeBullishLTPOpenStrategy(String symbolList, double ratio, KiteConnect kiteConnect) throws JSONException, KiteException {
		Iterator entries = null;
		if (symbolList.equalsIgnoreCase(NIFTY_FIFTY_CASH)) {
			entries = StockSymbolsUtil.getNiftyFiftyCashSymbolList().entrySet().iterator();
		}
		if (symbolList.equalsIgnoreCase(NIFTY_FUTUTRES_CASH)) {
			entries = StockSymbolsUtil.getNiftyFuturesCashSymbolList().entrySet().iterator();
		}
		System.out.println("Executing Bullish LTP  Open Strategy Started...");
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Quote quote = kiteConnect.getQuote(NSE_SYMBOL, (String) thisEntry.getValue());
			if (quote.buyQuantity > (ratio * quote.sellQuantity)) {
				if (quote.lastPrice > quote.ohlc.open) {
					System.out.println("STRATEGY : Bullish LTP above open" + "	STOCK->" + (String) thisEntry.getValue() + "			Change(%)->" + quote.changePercent + "	B/S->" + quote.buyQuantity
					        / quote.sellQuantity + "	Sellers->" + quote.sellQuantity + "	Buyers->" + quote.buyQuantity);
				}
				if (quote.lastPrice < quote.ohlc.open) {
					System.out.println("STRATEGY : Bullish LTP above open" + "	STOCK->" + (String) thisEntry.getValue() + "			Change(%)->" + quote.changePercent + "	B/S->" + quote.buyQuantity
					        / quote.sellQuantity + "	Sellers->" + quote.sellQuantity + "	Buyers->" + quote.buyQuantity);
				}
			}
		}
		System.out.println("Executing Bullish LTP Open Strategy Ended...");
	}

	/*
	 * Strategy : if Sellers greater than twice the Buyers and LTP is Above or
	 * Below Open
	 */

	public static void executeBearishLTPOpenStrategy(String symbolList, double ratio, KiteConnect kiteConnect) throws JSONException, KiteException {
		Iterator entries = null;
		if (symbolList.equalsIgnoreCase(NIFTY_FIFTY_CASH)) {
			entries = StockSymbolsUtil.getNiftyFiftyCashSymbolList().entrySet().iterator();
		}
		if (symbolList.equalsIgnoreCase(NIFTY_FUTUTRES_CASH)) {
			entries = StockSymbolsUtil.getNiftyFuturesCashSymbolList().entrySet().iterator();
		}
		System.out.println("Executing Bearish LTP Open Strategy Started...");
		System.out.format("%20s%15s%15s", "Strategy", "Symbol", "Change(%)", "S/B");
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Quote quote = kiteConnect.getQuote(NSE_SYMBOL, (String) thisEntry.getValue());
			if (quote.sellQuantity > (ratio * quote.buyQuantity)) {
				if (quote.lastPrice > quote.ohlc.open) {
					System.out.format("Above open" + (String) thisEntry.getValue() + quote.changePercent + quote.sellQuantity / quote.buyQuantity);
				}
				if (quote.lastPrice < quote.ohlc.open) {
					System.out.format("Below open" + (String) thisEntry.getValue() + quote.changePercent + quote.sellQuantity / quote.buyQuantity);
				}
			}

		}
		System.out.println("Executing Bearish LTP Open Strategy Ended...");
	}

}
