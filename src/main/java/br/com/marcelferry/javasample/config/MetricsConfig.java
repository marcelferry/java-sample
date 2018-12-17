package br.com.marcelferry.javasample.config;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpointMetricReader;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.writer.GaugeWriter;
import org.springframework.context.annotation.Bean;

public class MetricsConfig {
	
	private static Logger logger = LoggerFactory.getLogger(MetricsConfig.class);

	@Bean
	@ExportMetricWriter
	GaugeWriter influxMetricsWriter() {
	    InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.99.100:8086", "root", "root");
	    String dbName = "grafana";
	    influxDB.setDatabase(dbName);
	    influxDB.setRetentionPolicy("one_day");
	    influxDB.enableBatch(10, 1000, TimeUnit.MILLISECONDS);
	 
	    return new GaugeWriter() {
	 
	        @Override
	        public void set(Metric<?> value) {
	            Point point = Point.measurement(value.getName()).time(value.getTimestamp().getTime(), TimeUnit.MILLISECONDS)
	                    .addField("value", value.getValue()).build();
	            influxDB.write(point);
	            logger.info("write(" + value.getName() + "): " + value.getValue());
	        }
	    };
	}
	
	@Bean
	public MetricsEndpointMetricReader metricsEndpointMetricReader(final MetricsEndpoint metricsEndpoint) {
	    return new MetricsEndpointMetricReader(metricsEndpoint);
	}

}
