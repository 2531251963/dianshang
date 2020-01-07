package all.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class EsUtil {
    private static TransportClient client;
    public static TransportClient getClient(){
        if (client==null){
            Settings settings=Settings.builder()
                    .put("cluster.name","els")
                    .build();
             client=new PreBuiltTransportClient(settings);
            try {
                client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9300));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return client;
    }
}
