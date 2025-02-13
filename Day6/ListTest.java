import java.util.Iterator;

/**
 * Simple tests of list implementations
 * @author Tim Pierson, Dartmouth CS10, Winter 2024
 */
public class ListTest {
	public static void main(String[] args) throws Exception {
		SimpleList<String> list = new SinglyLinked<String>();
		System.out.println(list);
		list.add("1"); System.out.println(list);
		list.add("2"); System.out.println(list);
		list.add(0, "a"); System.out.println(list);
		list.add(1, "c"); System.out.println(list);
		list.add(1, "b"); System.out.println(list);
		list.set(2, "e"); System.out.println(list.get(2));
		list.add(0, "z"); System.out.println(list);
		String data = list.remove(2); System.out.println(data);
		System.out.println(list);
		data = list.remove(0); System.out.println(data);
		System.out.println(list);
		data = list.remove(1); System.out.println(data);
		System.out.println(list);
		data = list.remove(list.size()-1); System.out.println(list);

		//test for each loop works
		// for(Iterator<String> iter = list.iterator(); iter.hasNext();){ NO STEP PORTION OF FOR BECUASE NEXT MOVES YOU ALONG
		//String item = iter.next();
		//Sout (item + "-")
		//}
		for (String item : list) {
			System.out.print(item + "->");
		}
		System.out.println("[/]");


	}
}
