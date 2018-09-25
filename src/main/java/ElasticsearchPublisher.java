import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class ElasticsearchPublisher {
    private int documentId = 0;
    private TransportClient client;
    private Properties properties;

    public ElasticsearchPublisher(Properties properties) throws UnknownHostException {
        this.properties = properties;
        Settings settings = Settings.builder().put("cluster.name", properties.getProperty("cluster.name"))
                .put("client.transport.sniff", true).build();

        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(
                        InetAddress.getByName(properties.getProperty("remote-machine.address")),
                        Integer.valueOf(properties.getProperty("elasticsearch.port"))));
    }

    public void indexDocument(Document document) throws JsonProcessingException {
        IndexRequest indexRequest = new IndexRequest("sensor", "Document", "docId:" + documentId);
        indexRequest.source(new ObjectMapper().writeValueAsString(document), XContentType.JSON);
        IndexResponse response = client.index(indexRequest).actionGet();
        System.out.println("Response status:" + response.status());

    }

    public void indexDocumentHighLevelApi(Document document) throws IOException {
        try (RestHighLevelClient restHighLevelClient = new RestHighLevelClient
                (RestClient.builder(new HttpHost(InetAddress.getByName(properties.getProperty("remote-machine.address")),
                        Integer.parseInt(properties.getProperty("elasticsearch.port")), "http")))) {
            IndexRequest indexRequest = new IndexRequest("my_index_2", "_doc", "docId:" + documentId);
            indexRequest.source(new ObjectMapper().writeValueAsString(document), XContentType.JSON);
            try {
                restHighLevelClient.index(indexRequest);
            } catch (ConnectException connectException) {
                System.out.println("Error: " + connectException.getMessage());
            }
        }
    }
}



