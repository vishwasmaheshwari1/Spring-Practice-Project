package com.rest.service.consumingservice.proxy;

import com.rest.service.consumingservice.config.ProjectConfiguration;
import com.rest.service.consumingservice.model.Contact;
import feign.Headers;
import org.apache.catalina.LifecycleState;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "contactProxy", url="http://localhost:8090/api/contact", configuration = ProjectConfiguration.class)
public interface ContactProxy {

    @RequestMapping(method = RequestMethod.GET, value="/getMessagesByStatus")
    @Headers(value = "Content-type: application/json")
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status);

}
