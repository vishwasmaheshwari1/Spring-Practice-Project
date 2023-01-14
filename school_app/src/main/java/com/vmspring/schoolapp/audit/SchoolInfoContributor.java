package com.vmspring.schoolapp.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SchoolInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String,String> schoolmap = new HashMap<String, String>();
        schoolmap.put("App Name","School");
        schoolmap.put("App Description","School App for learning spring");
        schoolmap.put("App Version","1.0.0");
        schoolmap.put("Contact Email","School@gmail.com");
        schoolmap.put("Contact Mobile","12345778");
        builder.withDetail("school-info",schoolmap);
    }
}
