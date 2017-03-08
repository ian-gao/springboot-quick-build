
package com.github.benyzhous.springboot.sharding.jdbc.masterslave.service.test;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.github.benyzhous.springboot.sharding.jdbc.masterslave.Application;
import com.github.benyzhous.springboot.sharding.jdbc.masterslave.model.Order;
import com.github.benyzhous.springboot.sharding.jdbc.masterslave.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
//@SpringApplicationConfiguration(classes = Application.class)// 1.4.0 前版本  
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderServiceTest {
	// id生成器
	@Autowired
    private CommonSelfIdGenerator commonSelfIdGenerator;
	
	@Autowired
	OrderService orderService;
	
	
	@Test
	public void save(){
		orderService.deleteAll();
		// 生成1000条数据，看看分布情况
		for(int i = 0; i < 10; i++){
			Order order = new Order();
			order.setOrderId(commonSelfIdGenerator.generateId().longValue());
			order.setUserId(i);
			order.setStatus("1");
			
			orderService.insertSelective(order);
		}
	}
	
	@Test
	public void selectAll(){
		List<Order> orderList = orderService.selectAll();
		orderList.forEach(order -> {
			System.err.println(order.toString());
		});
	}
}
 