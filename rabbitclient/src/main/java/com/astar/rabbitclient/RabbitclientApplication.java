package com.astar.rabbitclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@SpringBootApplication
public class RabbitclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitclientApplication.class, args);
	}

	@RabbitListener(queues="carts")
	public void getOrders(String message)
	{
		System.out.println(message);
	}

}
