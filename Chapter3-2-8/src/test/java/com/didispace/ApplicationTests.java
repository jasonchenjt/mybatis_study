package com.didispace;

import com.didispace.domain.Pet;
import com.didispace.domain.PetMapper;
import com.didispace.domain.User;
import com.didispace.domain.UserMapper;
import com.didispace.service.CacheService;
import com.didispace.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@EnableCaching
public class ApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PetMapper petMapper;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private UserService userService;

	@Autowired
	StringRedisTemplate redisTemplate;

//	mybatis 操作-----------------------
	@Test
//	@Rollback
	public void testUserMapper() throws Exception {
		// insert一条数据，并select出来验证
		userMapper.insert("AAA", 20);
		User u = userMapper.findByName("AAA");
		Assert.assertEquals(20, u.getAge().intValue());
		// update一条数据，并select出来验证
		u.setAge(30);
		userMapper.update(u);
		u = userMapper.findByName("AAA");
		Assert.assertEquals(30, u.getAge().intValue());

		// 删除这条数据，并select验证
		userMapper.delete(u.getId());
		u = userMapper.findByName("AAA");
		Assert.assertEquals(null, u);

		u = new User("DDD", 30);
		userMapper.insertByUser(u);
		Assert.assertEquals(30, userMapper.findByName("DDD").getAge().intValue());

		Map<String, Object> map = new HashMap<>();
		map.put("name", "CCC");
		map.put("age", 40);
		userMapper.insertByMap(map);
		Assert.assertEquals(40, userMapper.findByName("CCC").getAge().intValue());

		List<User> userList = userMapper.findAll();
		userList.forEach((user)->System.out.println(user));
		/*for(User user : userList) {
			Assert.assertEquals(null, user.getId());
			Assert.assertNotEquals(null, user.getName());
		}*/

	}

	@Test
	public void test2(){
		List<User> userList = userMapper.findAll();
		for (User user:userList) {
			System.out.println("user:"+user);
		}
	}

	@Test
	public void  test3(){
		User user= userMapper.findAndOne(4);
		System.out.println("User:"+user);
	}

	@Test
	public void  selectPet(){
		Pet pet = petMapper.findById(2);
		System.out.println("pet:"+pet);
	}

//	--------------- mybatis 操作结束

	//测试缓存Redis机制
	@Test
	public void test4(){
		userService.finduserbyname("test");
		redisTemplate.opsForValue().set("Test in mybatis","success");
	}


//redis 存储操作******************************************************
	/**
	 * 测试redis是否存在某个key
	 **/
	@Test
	public void testRedis_haskey(){
		redisTemplate.opsForValue().set("name","Jason");
		String name = redisTemplate.opsForValue().get("name");
		System.out.println("name :"+name);
		Boolean exist=redisTemplate.hasKey("name");
		System.out.println(exist);
	}

	//删除redis某个key
	@Test
	public void testRedis_delete(){
		redisTemplate.delete("number");
	}

	//map类型  set
	@Test
	public void testRedis_insMap(){
		//一次性添加多个值
		Map<String,String> redisMap = new HashMap<>();
		redisMap.put("name","jason");
		redisMap.put("age","24");
		redisMap.put("sex","man");
		redisTemplate.opsForHash().putAll("my",redisMap);
		//只添加一个值
		redisTemplate.opsForHash().put("my","weight","130");
	}

	//map类型  get
	@Test
	public void testRedis_getMap(){
		Map<Object,Object> redisMap = null;
		redisMap = redisTemplate.opsForHash().entries("my");
		System.out.println(redisMap);
	}


	//List 类型 set 注意需要使用listOperations
	@Test
	public void testRedis_setList(){
		ListOperations listOperations = redisTemplate.opsForList();
		List<Object> redisList = new ArrayList<>();
		redisList.add("1");
		redisList.add("2");

		List<String> redisList2 = new ArrayList<>();
		redisList2.add("3");
		redisList2.add("4");

		//删除redis中的元素
		listOperations.remove("number",0,"1");
		listOperations.remove("number",0,"5");
		listOperations.remove("number",0,"6");
		listOperations.remove("number",0,"2");
		listOperations.remove("number",0,"3");
		listOperations.remove("number",0,"4");

		//在redis左边添加元素
		listOperations.leftPushAll("number",redisList);
		listOperations.leftPushAll("number",redisList2);
		listOperations.leftPush("number","5");
		listOperations.leftPushAll("number","6");
	}

	/**
	 * List 类型 get
	 * 根据下标获取
	 */
	@Test
	public void testRedis_getList(){
		//获取第几个,下标从0开始
		String number = redisTemplate.opsForList().index("number",1);
		System.out.println("获取下标为1的number:"+number);

		//获取一定范围内的值, 0: 第一个  -1: 最后一个
		List<String> numbers = redisTemplate.opsForList().range("number",0,-1);
		numbers.forEach(System.out::println);

	}

	//Set 类型 set  不重复,不排序
	@Test
	public void testRedis_setSet(){
		Set<Integer>set  = new HashSet<>();
		set.add(1);
		set.add(2);
		set.add(3);
		SetOperations setOperations = redisTemplate.opsForSet();
		setOperations.add("world",set.toString());//注意添加一个set时,需要toString()方法
//		setOperations.add("world","add");
//		setOperations.add("world","add2");
//		setOperations.add("world","add3");

		//删除
//		setOperations.remove("world","add","add2","add3",set.toString());
	}

	//Set 类型 members 获取set中的元素
	@Test
	public void testRedis_getSet(){
		Set set =  redisTemplate.opsForSet().members("world");
		System.out.println(set);
	}

	//Zset 类型 set
	@Test
	public void testRedis_setZset(){


		redisTemplate.opsForZSet().add("zset","01",1);
		redisTemplate.opsForZSet().add("zset","03",3);
		redisTemplate.opsForZSet().add("zset","02",2);
		redisTemplate.opsForZSet().add("zset","04",4);
		redisTemplate.opsForZSet().add("zset","05",0);
		redisTemplate.opsForZSet().add("zset","06",0);
	}
	//Zset 类型 get
	@Test
	public void testRedis_getZset(){
		ZSetOperations listOperations = redisTemplate.opsForZSet();
		Object result = listOperations.range("zset",0,1);//从低到高查询
		Object result2 = listOperations.reverseRange("zset",0,1);//从高到低查询
		Object result3 = listOperations.rangeByScore("zset",0,1);//从高到低查询

		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
	}

//*****************************************************
}
