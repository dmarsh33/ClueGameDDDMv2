//package tests;
//
//import static org.junit.Assert.*;
//
//import java.util.Set;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import clueGame.BoardCell;
//import experiment.IntBoard;
//
//public class IntBoardTests {
//	private IntBoard board;
//
//	@Before
//	public void setUpIntBoard() {
//		board = new IntBoard(4,4);		
//	}	
//	
//	@Test
//	public void topLeftCorner() {
//		BoardCell cell = board.getCell(0,0);
//		Set<BoardCell> testList = board.getAdjList(cell);
//		assertTrue(testList.contains(board.getCell(1, 0)));
//		assertTrue(testList.contains(board.getCell(0, 1)));
//		assertEquals(2, testList.size());
//	}
//	
//	@Test
//	public void bottomRightCorner() {
//		BoardCell cell = board.getCell(3,3);
//		Set<BoardCell> testList = board.getAdjList(cell);
//		assertTrue(testList.contains(board.getCell(2, 3)));
//		assertTrue(testList.contains(board.getCell(3, 2)));
//		assertEquals(2, testList.size());
//	}
//	
//	@Test
//	public void rightEdge() {
//		BoardCell cell1 = board.getCell(1,3);
//		Set<BoardCell> testList1 = board.getAdjList(cell1);
//		assertTrue(testList1.contains(board.getCell(0, 3)));
//		assertTrue(testList1.contains(board.getCell(1, 2)));
//		assertTrue(testList1.contains(board.getCell(2, 3)));
//		assertEquals(3, testList1.size());
//		
//		BoardCell cell2 = board.getCell(2,3);
//		Set<BoardCell> testList2 = board.getAdjList(cell2);
//		assertTrue(testList2.contains(board.getCell(1, 3)));
//		assertTrue(testList2.contains(board.getCell(2, 2)));
//		assertTrue(testList2.contains(board.getCell(3, 3)));
//		assertEquals(3, testList2.size());
//	}
//	
//	@Test
//	public void leftEdge() {
//		BoardCell cell1 = board.getCell(1,0);
//		Set<BoardCell> testList1 = board.getAdjList(cell1);
//		assertTrue(testList1.contains(board.getCell(0, 0)));
//		assertTrue(testList1.contains(board.getCell(1, 1)));
//		assertTrue(testList1.contains(board.getCell(2, 0)));
//		assertEquals(3, testList1.size());
//		
//		BoardCell cell2 = board.getCell(2,0);
//		Set<BoardCell> testList2 = board.getAdjList(cell2);
//		assertTrue(testList2.contains(board.getCell(1, 0)));
//		assertTrue(testList2.contains(board.getCell(2, 1)));
//		assertTrue(testList2.contains(board.getCell(3, 0)));
//		assertEquals(3, testList2.size());
//	}
//	
//	@Test
//	public void secondColumnMiddle() {
//		BoardCell cell1 = board.getCell(1,1);
//		Set<BoardCell> testList1 = board.getAdjList(cell1);
//		assertTrue(testList1.contains(board.getCell(0, 1)));
//		assertTrue(testList1.contains(board.getCell(1, 0)));
//		assertTrue(testList1.contains(board.getCell(2, 1)));
//		assertTrue(testList1.contains(board.getCell(1, 2)));
//		assertEquals(4, testList1.size());
//		
//		BoardCell cell2 = board.getCell(2,1);
//		Set<BoardCell> testList2 = board.getAdjList(cell2);
//		assertTrue(testList2.contains(board.getCell(1, 1)));
//		assertTrue(testList2.contains(board.getCell(2, 0)));
//		assertTrue(testList2.contains(board.getCell(3, 1)));
//		assertTrue(testList2.contains(board.getCell(2, 2)));
//		assertEquals(4, testList2.size());
//	}
//	
//	@Test
//	public void secondFromLastColumn() {
//		BoardCell cell1 = board.getCell(1,2);
//		Set<BoardCell> testList1 = board.getAdjList(cell1);
//		assertTrue(testList1.contains(board.getCell(1, 1)));
//		assertTrue(testList1.contains(board.getCell(0, 2)));
//		assertTrue(testList1.contains(board.getCell(2, 2)));
//		assertTrue(testList1.contains(board.getCell(1, 3)));
//		assertEquals(4, testList1.size());
//		
//		BoardCell cell2 = board.getCell(2,2);
//		Set<BoardCell> testList2 = board.getAdjList(cell2);
//		assertTrue(testList2.contains(board.getCell(1, 2)));
//		assertTrue(testList2.contains(board.getCell(2, 1)));
//		assertTrue(testList2.contains(board.getCell(3, 2)));
//		assertTrue(testList2.contains(board.getCell(2, 3)));
//		assertEquals(4, testList2.size());
//	}
//	
//	@Test
//	public void testTargets00_3()
//	{
//		BoardCell cell = board.getCell(0, 0);
//		board.calcTargets(cell, 3);
//		Set<BoardCell> targets = board.getTargets();
//		assertEquals(6, targets.size());
//		assertTrue(targets.contains(board.getCell(3, 0)));
//		assertTrue(targets.contains(board.getCell(2, 1)));
//		assertTrue(targets.contains(board.getCell(0, 1)));
//		assertTrue(targets.contains(board.getCell(1, 2)));
//		assertTrue(targets.contains(board.getCell(0, 3)));
//		assertTrue(targets.contains(board.getCell(1, 0)));
//	}
//	
//	@Test
//	public void testTargets00_2()
//	{
//		BoardCell cell = board.getCell(0, 0);
//		board.calcTargets(cell, 2);
//		Set<BoardCell> targets = board.getTargets();
//		assertEquals(4, targets.size());
//		assertTrue(targets.contains(board.getCell(0, 0)));
//		assertTrue(targets.contains(board.getCell(0, 2)));
//		assertTrue(targets.contains(board.getCell(2, 0)));
//		assertTrue(targets.contains(board.getCell(1, 1)));
//	}
//	
//	@Test
//	public void testTargets00_1()
//	{
//		BoardCell cell = board.getCell(0, 0);
//		board.calcTargets(cell, 1);
//		Set<BoardCell> targets = board.getTargets();
//		assertEquals(2, targets.size());
//		assertTrue(targets.contains(board.getCell(0, 1)));
//		assertTrue(targets.contains(board.getCell(1, 0)));
//
//	}
//	
//	@Test
//	public void testTargets11_3()
//	{
//		BoardCell cell = board.getCell(1, 1);
//		board.calcTargets(cell, 3);
//		Set<BoardCell> targets = board.getTargets();
//		assertEquals(8, targets.size());
//		assertTrue(targets.contains(board.getCell(2, 3)));
//		assertTrue(targets.contains(board.getCell(2, 1)));
//		assertTrue(targets.contains(board.getCell(0, 1)));
//		assertTrue(targets.contains(board.getCell(1, 0)));
//		assertTrue(targets.contains(board.getCell(1, 2)));
//		assertTrue(targets.contains(board.getCell(0, 3)));
//		assertTrue(targets.contains(board.getCell(3, 0)));
//		assertTrue(targets.contains(board.getCell(3, 2)));
//	}
//	
//	@Test
//	public void testTargets11_2()
//	{
//		BoardCell cell = board.getCell(1, 1);
//		board.calcTargets(cell, 2);
//		Set<BoardCell> targets = board.getTargets();
//		assertEquals(7, targets.size());
//		assertTrue(targets.contains(board.getCell(1, 3)));
//		assertTrue(targets.contains(board.getCell(3, 1)));
//		assertTrue(targets.contains(board.getCell(2, 2)));
//		assertTrue(targets.contains(board.getCell(2, 0)));
//		assertTrue(targets.contains(board.getCell(0, 2)));
//		assertTrue(targets.contains(board.getCell(1, 1)));
//		assertTrue(targets.contains(board.getCell(0, 0)));
//	}
//	
//	@Test
//	public void testTargets11_1()
//	{
//		BoardCell cell = board.getCell(1, 1);
//		board.calcTargets(cell, 1);
//		Set<BoardCell> targets = board.getTargets();
//		assertEquals(4, targets.size());
//		assertTrue(targets.contains(board.getCell(0, 1)));
//		assertTrue(targets.contains(board.getCell(2, 1)));
//		assertTrue(targets.contains(board.getCell(1, 0)));
//		assertTrue(targets.contains(board.getCell(1, 2)));
//	}
//	
//	public static void main(String args[]){
//		IntBoardTests test = new IntBoardTests();
//	}
//	
//}
