package com.chapter11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * list遍历使用迭代器删除
 * 调用迭代器的remove()方法
 * 
 * java集合遍历删除两种实现思路：
 * 1.遍历与移除分离，遍历结束后统一移除（不推荐）
 * 2.使用迭代器移除
 * 
 * 使用迭代器遍历删除的原因：
 * 1.Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。 Iterator 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，所以当索引指针往后移动的时候就找不到要迭代的对象，
 * 所以按照 fail-fast 原则 Iterator 会马上抛出 java.util.ConcurrentModificationException 异常。
 * 2.所以 Iterator 在工作的时候是不允许被迭代的对象被改变的。但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove() 方法会在删除当前迭代对象的同时维护索引的一致性。
 * 
 * 报错原因：
 * ArrayList采用size属性来维护自已的状态，而Iterator采用cursor来来维护自已的状态。
 * 当size出现变化时，cursor并不一定能够得到同步，除非这种变化是Iterator主动导致的。
 * 当Iterator.remove方法导致ArrayList列表发生变化时，他会更新cursor来同步这一变化。但其他方式导致的ArrayList变化，Iterator是无法感知的。
 * Iterator会经常做checkForComodification检查，以防有变。如果有变，则以异常抛出
 * 
 * @author jiaxinxiao
 * @date 2019年11月6日
 */
public class IteratorTest {
	public static void print(List<String> strs){
		for(String str:strs){
			System.out.print(str+", ");
		}
		System.out.println();
	}
	//list
	private static void testList(){
		List<String> strs = new ArrayList<String>();
		strs.add("string1");
		strs.add("string2");
		strs.add("string3");
		strs.add("string4");
		strs.add("string5");
		strs.add("string6");
		print(strs);
//		for(String str:strs){
//			if("string3".equals(str)){
//				strs.remove(str);//java.util.ConcurrentModificationException
//			}
//		}
		
//		for(Iterator<String> it = strs.iterator()/*独立线程*/;it.hasNext();){//与第一种遍历方式是等价的
//			String str = it.next();
//			if("string3".equals(str)){
//				strs.remove(str);//java.util.ConcurrentModificationException
//			}
//		}
		
//		Iterator<String> it = strs.iterator();
//		while(it.hasNext()){
//			String str = it.next();
//			if(str.equals("string3")){
//				it.remove();
//			}
//		}
		
//		for (String str : strs) {
//			if(str.equals("string3")){
//				strs.remove(str);
//			}
//		}
		
		//代码能够正常删除
		for(int i=0;i<strs.size();i++){
			String str = strs.get(i);
			if(str.equals("string3")){
//				strs.remove(i);
				strs.remove(str);
			}
		}
		print(strs);
	}
	//
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void testSet(){
		Set set = new HashSet();
        set.add(1);
        set.add(2);
        set.add(3);
        Iterator i = set.iterator();
        while(i.hasNext()){
            Object o = i.next();
//            set.remove(o);    
            i.remove();
        }
	}
	@SuppressWarnings("unchecked")
	public static void testMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("p1", "1");
		map.put("p2", "2");
		map.put("p3", "3");
		map.put("p4", "4");
//		Iterator<String> it = map.keySet().iterator();
//		while(it.hasNext()){
//			//重新排序，更新iterator状态
////			it = map.keySet().iterator();//添加一层循环，时间复杂度增加
//			String str=(String)it.next();               
//            System.out.println(map);  
////            map.remove(str);
//            it.remove();
//		}
		
//		Map<String, String> mapBack = (Map<String, String>) ((HashMap<String, String>) map).clone();
//		Iterator<String> it = mapBack.keySet().iterator();
//		while(it.hasNext()){
//			String str=(String)it.next();               
//			System.out.println(map);  
//			map.remove(str);//使用的是map副本的迭代器，所以不用维护iterator状态
//		}
		
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			it.next();               
            System.out.println(map);  
            it.remove();
		}
	}
	public static void main(String[] args) {
//		testList();
//		testSet();
		testMap();
	}
}
