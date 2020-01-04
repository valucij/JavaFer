package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

class UniqueNumbersTest {

/**
 * <code>test1</code> provjerava metodu <code>addNode</code>,
 * da li metoda dobro sprema čvor, i to prvi, ako je 
 * prvotno binarno stablo bilo prazno.
 */
	@Test
	public void test1() {
		TreeNode glava1 = null;
		glava1 = UniqueNumbers.addNode(glava1,42);
		assertEquals(glava1.value,42);
		
	}
/**
 * <code>test2</code> provjerava metodu <code>addNode</code>,
 * da li dobro sprema čvor u binarno stablo, ali kad ono
 * već ima neke elemente.	
 */
	@Test
	public void test2() {
		
		TreeNode glava2 = null;
		glava2 = UniqueNumbers.addNode(glava2,42);
		glava2 = UniqueNumbers.addNode(glava2,76);
		assertEquals(glava2.right.value,76);
	}
/**
 * <code>test3</code> provjerava metodu <code>containsValue</code>	
 * da li vraća <code>true</code> ako se čvor s određenom
 * vrijednošću nalazi u stablu.
 */
	@Test
	public void test3() {
		
		TreeNode glava3 = null;
		glava3 = UniqueNumbers.addNode(glava3,42);
				
		if(UniqueNumbers.containsValue(glava3, 42)) {
			return;
		}
		
		fail();
		
	}
/**
 * <code>test4</code> provjerava metodu <code>treeSize</code>,
 * da li za neko binarno stablo vraća dobru veličinu, tj.
 * njegov broj čvorova.	
 */
	@Test
	public void test4() {
		
		TreeNode glava4 = null;
		glava4 = UniqueNumbers.addNode(glava4,42);
		glava4 = UniqueNumbers.addNode(glava4,76);
		glava4 = UniqueNumbers.addNode(glava4,7);
		glava4 = UniqueNumbers.addNode(glava4,21);
		glava4 = UniqueNumbers.addNode(glava4,57);
		
		assertEquals(5,UniqueNumbers.treeSize(glava4));
		
	}
	
}
