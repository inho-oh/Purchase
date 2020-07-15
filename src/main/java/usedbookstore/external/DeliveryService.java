
package usedbookstore.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="Delivery", url="http://Delivery:8080")
//@FeignClient(name="Delivery", url="http://localhost:8085")
public interface DeliveryService {

    @RequestMapping(method= RequestMethod.PUT, path="/deliveries/{id}")
    public void cancel(@PathVariable Long id, @RequestBody Delivery delivery);


}