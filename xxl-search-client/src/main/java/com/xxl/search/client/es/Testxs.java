package com.xxl.search.client.es;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author xushuai
 * @date 2019年1月18日
 * @note 
 */
public class Testxs {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		for(int i=0;i<29;i++){
//			System.out.println(RandomUtils.nextInt(10));
//		}
//		xu();
		
		String aa = "非法人";
		String[] gg = aa.split(",");
		for(String g:gg){
			System.out.println(g);
		}
		
	}

	/**
	 * 
	 */
	private static void xu() {
		Person p2 = new Person();
		p2.setId(0);
		p2.setName("xu");
		Person p = new Person();
		p.setId(0);
		p.setName("张三");
		p.setNum("55555");
		replace(p2,p);
		System.out.println(p.toString());
		System.out.println("*******");
		System.out.println(p2.toString());
//		Field[]  fields=p.getClass().getFields();
//		test2(p);
	}

	/**
	 * @param p
	 */
	private static void test2(Person p) {
		Field[]  fields=p.getClass().getDeclaredFields();
		Method[] methods = p.getClass().getDeclaredMethods();
		for(Field field:fields){
			
			field.setAccessible(true);
	        try {
				System.out.println(field.getName()+";字段value:"+field.get(p));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
	    }
		System.out.println("----------------------------");
		for(Method method:methods){
	        try {
	        	if(method.getName().equals("getName")){
	        		System.out.println("********:"+method.invoke(p, null));
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
	
	public static void replace(Object firstO,Object secondO){
		Field[]  fieldsFirst=firstO.getClass().getDeclaredFields();
		Field[]  fieldsSecond=secondO.getClass().getDeclaredFields();
		for(Field fieldOldF:fieldsFirst){
			for(Field fieldNewS:fieldsSecond){
				System.out.println("------------------->>>"+fieldOldF.getType().getName());
				if(fieldOldF.getName().contains("n") && fieldNewS.getName().contains("n")){
			        try {
			        	
			        	if(fieldOldF.getName().equals(fieldNewS.getName())){
			        		fieldOldF.setAccessible(true);
							fieldNewS.setAccessible(true);
							if(fieldOldF.get(firstO)==null || fieldOldF.get(firstO).equals("")){
								fieldOldF.set(firstO, fieldNewS.get(secondO));
//				        		System.out.println("^^^^"+fieldOldF.get(firstO));
				        		break;
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}else{
					continue;
				}
//				System.out.println(fieldNewS.getName()+"-->>>"+fieldOldF.getName());
			}
	    }
	}

}
