package com.narcis.devpath.noteservice.client;

import com.narcis.devpath.noteservice.dto.ModuleSummaryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="learning-service") // passiamo da Eureka e diamo il nome con cui è registrato sul service registry
public interface ModuleClient {

    @RequestMapping(method = RequestMethod.GET, value = "/modules/{moduleId}/feign")
    ModuleSummaryDto getModuleSummary(@PathVariable Long moduleId);

}
