package org.learn.storm.ch04.spouts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.storm.shade.org.apache.http.HttpResponse;
import org.apache.storm.shade.org.apache.http.StatusLine;
import org.apache.storm.shade.org.apache.http.auth.AuthScope;
import org.apache.storm.shade.org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.storm.shade.org.apache.http.client.methods.HttpGet;
import org.apache.storm.shade.org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.storm.shade.org.apache.http.impl.client.CloseableHttpClient;
import org.apache.storm.shade.org.apache.http.impl.client.HttpClientBuilder;
import org.apache.storm.shade.org.json.simple.parser.JSONParser;
import org.apache.storm.shade.org.json.simple.parser.ParseException;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiStreamingSpout extends BaseRichSpout {

	private static final long serialVersionUID = 7055096565680063261L;

	private Logger logger = LoggerFactory.getLogger(ApiStreamingSpout.class);

	private String STREAMING_API_URL = "https://stream.twitter.com/1/statuses/filter.json?track=";
	private String track;
	private String user;
	private String password;
	private SpoutOutputCollector collector;
	private UsernamePasswordCredentials credentials;
	private BasicCredentialsProvider credentialProvider;

	LinkedBlockingQueue<String> tweets = new LinkedBlockingQueue<String>();

	private JSONParser jsonParser = new JSONParser();

	@Override
	public void nextTuple() {
		/*
		 * Create the client call
		 */
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialProvider)
				.build();
		HttpGet get = new HttpGet(STREAMING_API_URL + track);
		HttpResponse response;
		try {
			// Execute
			response = client.execute(get);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() == 200) {
				InputStream inputStream = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String in;
				// Read line by line
				while ((in = reader.readLine()) != null) {
					try {
						// Parse and emit
						Object json = jsonParser.parse(in);
						collector.emit(new Values(track, json));
					} catch (ParseException e) {
						logger.error("Error parsing message from twitter", e);
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error in communication with twitter api [" + get.getURI().toString() + "]");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		int spoutsSize = context.getComponentTasks(context.getThisComponentId()).size();
		int myIdx = context.getThisTaskIndex();
		String[] tracks = ((String) conf.get("track")).split(",");
		StringBuffer tracksBuffer = new StringBuffer();
		for (int i = 0; i < tracks.length; i++) {
			if (i % spoutsSize == myIdx) {
				tracksBuffer.append(",");
				tracksBuffer.append(tracks[i]);
			}
		}

		if (tracksBuffer.length() == 0)
			throw new RuntimeException("No track found for spout" + " [spoutsSize:" + spoutsSize + ", tracks:"
					+ tracks.length + "] the amount" + " of tracks must be more then the spout paralellism");

		this.track = tracksBuffer.substring(1).toString();

		user = (String) conf.get("user");
		password = (String) conf.get("password");

		credentials = new UsernamePasswordCredentials(user, password);
		credentialProvider = new BasicCredentialsProvider();
		credentialProvider.setCredentials(AuthScope.ANY, credentials);
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("criteria", "tweet"));
	}

}
