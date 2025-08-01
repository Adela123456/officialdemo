package com.sun.feign;


import com.sun.config.FeignConfig;
import com.sun.util.RestApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "scloud-service",url = "${scloud.service.url:http://cloudservice.st.iblidc.com:7810/cloud}",configuration = FeignConfig.class)
public interface CloudServiceClient {

    @PostMapping(value = "/FMerchant/getMerchantLogoUrls.htm")
    String getMerchantLogoUrlList(@RequestBody List<String> merchantIdList);

    @PostMapping(value = "/employee/updateMerchantName.htm")
    String updateShopName(Map<String,String> param);

    /**
     * 根据memberId查询关注商户idList
     * @param param
     * @return
     */
    @PostMapping("/FMerchant/findFMerchantListByMemberId")
    RestApiResponse<Map<String, List<String>>> findFMerchantListByMemberId(String param);



    @PostMapping("/activity/getGoodsByActivityId")
    Map<String, Object> getGoodsByActivityId(@RequestBody Map<String,Object> param);

}
