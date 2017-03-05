package com.custom.service;

import static com.custom.constants.AllConstants.BEARISH_LTP_OPEN_STRATEGY;
import static com.custom.constants.AllConstants.BULLISH_LTP_OPEN_STRATEGY;
import static com.custom.constants.AllConstants.TICKER_TAPE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.neovisionaries.ws.client.WebSocketException;
import com.rainmatter.kiteconnect.KiteConnect;
import com.rainmatter.kitehttp.SessionExpiryHook;
import com.rainmatter.kitehttp.exceptions.KiteException;
import com.rainmatter.models.Instrument;
import com.rainmatter.models.Tick;
import com.rainmatter.models.UserModel;
import com.rainmatter.ticker.OnConnect;
import com.rainmatter.ticker.OnDisconnect;
import com.rainmatter.ticker.OnTick;

/**
 * @author Kulbir
 *
 */
public class FetchKiteConnectDataService {

	public static void main(String[] args) {
		try {
			// 1. How to get request_token , when the redirect URL is
			// http://127.0.0.1?
			// 2. Login manually to
			// https://kite.zerodha.com/connect/login?api_key=9rrtu9ln1a00gcfp
			// 3. This asks for client id and password, later security
			// questions.
			// 4. On successful login, we will be able to capture the request
			// token from the browser address bar (Manually)
			// 5. Later use this request token for any further interaction
			// (programmatically) with the platform.
			// 6. Every time the session expires, or there is a need to create a
			// new connection then we will follow steps 1 to 4 again.

			// Initialize KiteSdk with your apiKey.
			KiteConnect kiteConnect = new KiteConnect("9rrtu9ln1a00gcfp");

			// set userId
			kiteConnect.setUserId("RK4277");

			// set proxy is optional, if you want to set proxy.
			// kiteConnect.setProxy(new HttpHost("10.154.37.231"));

			// Get login url
			String url = kiteConnect.getLoginUrl();

			// Set session expiry callback.
			kiteConnect.registerHook(new SessionExpiryHook() {
				@Override
				public void sessionExpired() {
					System.out.println("session expired");
				}
			});

			// Set request token and public token which are obtained from login
			// process.
			UserModel userModel = kiteConnect.requestAccessToken("xize8j6i7z53366jw9lwkx5db8fg87vh", "npt3pe4kcisim9c5u5mqkkdqx2ltqciv");

			kiteConnect.setAccessToken(userModel.accessToken);
			kiteConnect.setPublicToken(userModel.publicToken);

			getInstrumentsForExchange(kiteConnect);

			// executeStrategy(BEARISH_LTP_OPEN_STRATEGY, NIFTY_FUTUTRES_CASH,
			// 2, kiteConnect);
			// tickerUsage(createTickerList(), true, kiteConnect);
		} catch (KiteException e) {
			System.out.println(e.message);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * @param 1 : strategyName : BullishLTPOpenStrategy, BearishLTPOpenStrategy
	 * 
	 * @param 2 : symbolList : NiftyFiftyCash , NiftyFuturesCash
	 * 
	 * @param 3 : ratio : Ratio of Buyers to Sellers or vice versa.
	 * 
	 * @param 4 : kiteConnect : KiteConnect object
	 */
	public static void executeStrategy(String strategyName, String symbolList, double ratio, KiteConnect kiteConnect) throws JSONException, KiteException {
		switch (strategyName) {
		case BULLISH_LTP_OPEN_STRATEGY:
			StrategyService.executeBullishLTPOpenStrategy(symbolList, ratio, kiteConnect);
			break;

		case BEARISH_LTP_OPEN_STRATEGY:
			StrategyService.executeBearishLTPOpenStrategy(symbolList, ratio, kiteConnect);
			break;

		case TICKER_TAPE:

		default:
			break;
		}
	}

	/** Get instruments for the desired exchange. */
	public static void getInstrumentsForExchange(KiteConnect kiteConnect) throws KiteException, IOException {
		// Get instruments for an exchange.
		List<Instrument> nseInstruments = kiteConnect.getInstruments("NFO");
		System.out.println(nseInstruments.size());
		for (Instrument instrument : nseInstruments) {
			System.out.println("Stock Name->" + instrument.name + "	SYMBOL->" + instrument.tradingsymbol + "	TOKEN->" + instrument.instrument_token);
		}
	}

	public static ArrayList createTickerList() {
		ArrayList tokenList = new ArrayList();
		// SBIN
		tokenList.add(779521);
		// ICICIBANK
		tokenList.add(1270529);
		// VEDL
		tokenList.add(784129);
		// HINDALCO
		tokenList.add(348929);
		// TATASTEEL
		tokenList.add(895745);
		// AXISBANK
		tokenList.add(1510401);
		// IDEA
		tokenList.add(3677697);
		// BHARTIARTL
		tokenList.add(2714625);
		// TCS
		tokenList.add(2953217);
		// INFY
		tokenList.add(408065);

		tokenList.add(5633);
		tokenList.add(325121);
		tokenList.add(60417);
		tokenList.add(4267265);
		tokenList.add(112129);
		tokenList.add(558337);
		tokenList.add(134657);
		tokenList.add(3580417);
		tokenList.add(177665);
		tokenList.add(5215745);
		tokenList.add(225537);
		tokenList.add(1207553);
		tokenList.add(1850625);
		tokenList.add(341249);
		tokenList.add(3789569);
		tokenList.add(345089);
		tokenList.add(356865);
		tokenList.add(424961);
		tokenList.add(2672641);
		tokenList.add(633601);

		return tokenList;

	}

	/**
	 * Demonstrates ticker connection, subcribing for instruments, unsubscribing
	 * for instruments, set mode of tick data, ticker disconnection
	 */
	public static void tickerUsage(ArrayList tokens, boolean isProxied, KiteConnect kiteconnect) throws IOException, WebSocketException {
		/**
		 * To get live price use com.rainmatter.ticker websocket connection. It
		 * is recommended to use only one websocket connection at any point of
		 * time and make sure you stop connection, once user goes out of app.
		 */
		TickerService tickerProvider = new TickerService(kiteconnect);

		tickerProvider.setOnConnectedListener(new OnConnect() {
			@Override
			public void onConnected() {
				try {
					/**
					 * Subscribe ticks for token. By default, all tokens are
					 * subscribed for modeQuote.
					 * */
					tickerProvider.subscribe(tokens, isProxied);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WebSocketException e) {
					e.printStackTrace();
				}
			}
		});

		tickerProvider.setOnDisconnectedListener(new OnDisconnect() {
			@Override
			public void onDisconnected() {
				// your code goes here
			}
		});

		tickerProvider.setOnTickerArrivalListener(new OnTick() {
			@Override
			public void onTick(ArrayList<Tick> ticks) {
				System.out.println("Tick Size->" + ticks.size());
				for (Tick tick : ticks) {
					System.out.println("Token->" + tick.getToken() + "	LTP->" + tick.getLastTradedPrice() + "	Sellers->" + tick.getTotalSellQuantity() + "	Buyers->" + tick.getTotalBuyQuantity());
				}
			}
		});

		/** connects to com.rainmatter.ticker server for getting live quotes */
		tickerProvider.connect(isProxied);

		/**
		 * You can check, if websocket connection is open or not using the
		 * following method.
		 */
		boolean isConnected = tickerProvider.isConnectionOpen();
		System.out.println(isConnected);

		/**
		 * set mode is used to set mode in which you need tick for list of
		 * tokens. Ticker allows three modes, modeFull, modeQuote, modeLTP. For
		 * getting only last traded price, use modeLTP For getting last traded
		 * price, last traded quantity, average price, volume traded today,
		 * total sell quantity and total buy quantity, open, high, low, close,
		 * change, use modeQuote For getting all data with depth, use modeFull
		 */
		tickerProvider.setMode(tokens, TickerService.modeLTP);

		// Unsubscribe for a token.
		// tickerProvider.unsubscribe(tokens);

		// After using com.rainmatter.ticker, close websocket connection.
		// tickerProvider.disconnect();
	}
}
