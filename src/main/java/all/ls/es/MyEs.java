package all.ls.es;

import all.netty.Handler;
import all.util.EsUtil;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Demo class
 *
 * @author ls
 * @date 20-2-20
 */
public class MyEs {
    private TransportClient client= EsUtil.getClient();
    private Logger logger = Logger.getLogger(Handler.class);
    public String creatindex(String s){
        HashMap hashMap=new HashMap();
        hashMap= JSON.parseObject(s,HashMap.class);
        client.admin()
                .indices()
                .prepareCreate((String) hashMap.get("name")).get();
        client.close();
        return "";
    }
    public String creat() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject() // 相当于json的'{'
                .startObject("properties")
                .startObject("giftname")
                .field("type", "text")
                .field("store", true)
                .field("analyzer", "ik_smart") //采用ik_smart分词 "search_analyzer": "ik_smart"
                .endObject()
                .startObject("giftcost")
                .field("type", "integer")
                .field("store", true)
                .endObject()
                .startObject("giftcapacity")
                .field("type", "integer")
                .field("store", true)
                .endObject()
                .startObject("giftdesc")
                .field("type", "text")
                .field("store", true)
                .field("analyzer", "ik_smart")
                .endObject()
                .startObject("giftimgurl")
                .field("type", "text")
                .field("store", true)
                .field("analyzer", "ik_smart")
                .endObject()
                .endObject()
                .endObject();

        client.admin().indices()
                .preparePutMapping("dianshang")
                .setType("gifts") //对应数据库的表名称
                .setSource(builder)
                .get();
        client.close();
        return "";
    }
    public String selectEs(String s){
        logger.info("es搜索 传入数据 ：  "+s);
        HashMap hashMap=new HashMap();
        hashMap= JSON.parseObject(s,HashMap.class);
        QueryBuilder query=null;
        if (hashMap.get("end")!=""||hashMap.get("end")==null){
             query=new RangeQueryBuilder("giftcost").from(hashMap.get("start")).to(hashMap.get("end"));
        }else {
            query=new RangeQueryBuilder("giftcost").from(hashMap.get("start"));
        }

        SearchResponse searchResponse = client.prepareSearch("elastic")
                .setTypes("gifts")
                //从零开始
                .setQuery(query)
               // .setFrom((Integer) hashMap.get("page"))
                .setFrom((Integer) hashMap.get("page")*8)
                //每页显示5条
                .setSize(8)
                .get();
        SearchHits searchHits = searchResponse.getHits();
        System.out.println("查询结果总记录数："+searchHits.getTotalHits());
        StringBuilder ss = new StringBuilder();
        ss.append("[");
        Arrays.stream(searchHits.getHits()).forEach(doc-> ss.append(doc.getSourceAsString()+","+"\n"));
        ss.deleteCharAt(ss.length()-2);
        ss.append("]");
        System.out.println(ss.toString());
        logger.info("es 搜索返回数据 ： "+ss.toString());
        return ss.toString();
    }

    public static void main(String[] args) {
        new MyEs().selectEs("aa");
    }


}
