package ca.bc.hlth.mohorganizations;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	ProductInfoRepository repository;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "${GREETING:World}") String name) {

		ProductInfo productInfo = new ProductInfo("2", "1");
		repository.save(productInfo);
		List<ProductInfo> result = (List<ProductInfo>) repository.findAll();
		for (ProductInfo info : result) {
			System.out.println(info);
		}

		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}