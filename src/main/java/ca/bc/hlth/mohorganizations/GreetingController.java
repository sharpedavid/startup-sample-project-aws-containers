package ca.bc.hlth.mohorganizations;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

		CreateTableRequest tableRequest = dynamoDBMapper
				.generateCreateTableRequest(ProductInfo.class);
		tableRequest.setProvisionedThroughput(
				new ProvisionedThroughput(1L, 1L));
		try {
			amazonDynamoDB.createTable(tableRequest);
		} catch (ResourceInUseException e) {
			// ignore
		}

		ProductInfo productInfo = new ProductInfo("2", "1");
		repository.save(productInfo);
		List<ProductInfo> result = (List<ProductInfo>) repository.findAll();
		for (ProductInfo info : result) {
			System.out.println(info);
		}

		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}