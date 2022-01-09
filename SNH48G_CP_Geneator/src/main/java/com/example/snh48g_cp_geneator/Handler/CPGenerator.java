package com.example.snh48g_cp_geneator.Handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.snh48g_cp_geneator.classes.CPName;
import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class CPGenerator {

    private static final String[] member_list = new String[]{"贝楚涵", "陈雨孜", "段艺璇", "黄恩茹", "蒋芸", "李慧", "刘倩倩",
            "芦馨怡", "刘增艳", "马玉灵", "宁轲", "彭嘉敏", "沈小爱", "邵雪聪", "田姝丽", "王秋茹", "由淼", "闫明筠", "朱涵佳",
            "赵天杨", "柏欣妤", "陈倩楠", "韩家乐", "胡晓慧", "刘洁", "刘姝贤", "袁一琦", "张诗芸", "张昕", "张月铭", "陈琳", "何阳青青",
            "卢天惠", "刘闲", "青钰雯", "苏杉杉", "颜沁", "杨宇馨", "张怀瑾", "赵佳蕊", "沈梦瑶", "孙语姗", "孙珍妮", "王奕", "许杨玉琢",
            "张睿婕", "周诗雨", "张茜", "张笑盈", "张雨鑫", "费沁源", "冯思佳", "郝婧怡", "姜杉", "蒋舒婷", "李佳恩", "林舒晴", "林佳怡",
            "刘胜男", "吕一", "潘璐瑶", "祁静", "冉蔚", "宋昕冉", "武博涵", "王菲妍", "王睿琦", "王晓佳", "王依柳", "谢天依", "熊紫轶",
            "杨冰怡", "禹佳蔚", "闫娜", "潘瑛琪", "陈泓宇", "陈珂", "符冰冰", "黄楚茵", "罗寒月", "梁娇", "林嘉佩", "罗可嘉", "李姗姗",
            "林芝", "徐楚雯", "阳青颖", "叶舒淇", "曾艾佳", "张琼予", "朱怡欣", "陈楠茜", "洪静雯", "江雨航", "刘果", "卢静", "刘力菲",
            "吕曼菲", "石竹君", "吴羽霏", "谢艾琳", "冼燊楠", "杨若惜", "郑丹妮", "曾佳", "左婧媛", "张润", "陈桂君", "邓惠恩", "方琪",
            "高蔚然", "梁乔", "梁婉琳", "龙亦瑞", "马昕玥", "农燕萍", "唐莉佳", "王芊诺", "王炯义", "王偲越", "王梓", "王秭歆", "杨可璐",
            "杨媛媛", "周培溪", "张书瑀", "张幼柠", "陈佳莹", "黄汝彤", "惠煜轩", "林恩同", "雷淑怡", "陈蓁蓁", "范璐元", "郭晓盈", "韩雪",
            "黄宣绮", "黄怡慈", "李思奥", "林滢", "曲美霖", "任蔓琳", "孙晓艳", "唐晨葳", "王若诗蓝", "王思奕", "王言蹊", "张宸祎",
            "朱虹蓉", "张梦慧", "周湘", "张智杰"};

    public static JSONObject get_cp_name(String name1, String name2, String type){

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://49.232.68.86/cp/cpname?name1=" + name1 + "&name2=" + name2+ "&tp=" + type);
        CloseableHttpResponse response = null;

        try {
            response = client.execute(get);
            // HttpEntity entity = response.getEntity();
            // Header header = entity.getContentType();
            String strResp = EntityUtils.toString(response.getEntity());
            JSONObject json = JSONObject.parseObject(strResp);
            CPName[] name_list = name_convertor(json, name1, name2);

        }catch (IOException e){
            System.out.println(e);
        }
        finally {
            try {
                response.close();
                client.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    public static void main(String[] args) {
        get_cp_name("邵雪聪", "赵佳蕊", "2");
    }

    private static CPName[] name_convertor(JSONObject jsonObject, String name1, String name2){

        JSONArray list = jsonObject.getJSONArray("data");
        CPName[] result = new CPName[list.size()];
        String name1_pinyin = PinyinHelper.toPinyin(name1, PinyinStyleEnum.NORMAL);
        String name2_pinyin = PinyinHelper.toPinyin(name2, PinyinStyleEnum.NORMAL);
        for (int i=0; i<list.size(); i++){
            JSONObject cp = (JSONObject) list.get(i);
            String cp_name = cp.getString("name");
            String cp_name_pinyin = PinyinHelper.toPinyin(cp_name, PinyinStyleEnum.NORMAL);
            String[] name1_list = name1_pinyin.split(" ");
            String[] name2_list = name2_pinyin.split(" ");
            String[] cp_name_list = cp_name_pinyin.split(" ");
            StringBuffer stringBuffer = new StringBuffer(cp_name);
            for(int j=0; j<name1_list.length; j++){
                for (int k=0; k<cp_name_list.length; k++){
                    if (name1_list[j].equals(cp_name_list[k])){
                        String same = String.valueOf(name1.charAt(j));
                        stringBuffer.replace(k, k+1, same);
                        //cp_name.replace(cp_name.charAt(), name1.charAt(j));
                    }
                }
            }
            for(int j=0; j<name2_list.length; j++){
                for (int k=0; k<cp_name_list.length; k++){
                    if (name2_list[j].equals(cp_name_list[k])){
                        String same = String.valueOf(name2.charAt(j));
                        stringBuffer.replace(k, k+1, same);
                    }
                }
            }
            JSONArray desc = cp.getJSONArray("desc");
            result[i] = new CPName(stringBuffer.toString(), ((String) desc.get(1)), ((String) desc.get(2)));
        }
        return result;
    }
}
