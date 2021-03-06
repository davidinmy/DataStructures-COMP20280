/**
 * The {@code SplayTreeMapTest} in this class implements a
 * junit test for a Splay tree.
 *
 * @author Ahmed Jouda & Dr. Aonghus Lawlor
 */
package projectCode20280;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SplayTreeMapTest {

	@Test
	void testGet() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		assertEquals("15", map.get(15));
		assertEquals("24", map.get(24));
		assertEquals(null, map.get(-1));
	}

	@Test
	void testPut() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals("[1, 2, 4, 5, 12, 15, 21, 23, 24, 26, 33, 35]", map.keySet().toString());
	}

	@Test
	void testRemoveK() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals(12, map.size());
		assertEquals("26", map.remove(26));
		assertEquals(11, map.size());
	}

	@Test
	void testFirstEntry() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals(1, map.firstEntry().getKey());
	}

	@Test
	void testLastEntry() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		// java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals(35, map.lastEntry().getKey());
	}

	@Test
	void testCeilingEntry() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		// java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals(12, map.ceilingEntry(11).getKey());

		assertEquals(2, map.ceilingEntry(2).getKey());
	}

	@Test
	void testFloorEntry() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		// java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals(5, map.floorEntry(11).getKey());
		assertEquals(5, map.floorEntry(5).getKey());
	}

	@Test
	void testLowerEntry() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		// java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals(23, map.lowerEntry(24).getKey());
		assertEquals(26, map.lowerEntry(31).getKey());
	}

	@Test
	void testHigherEntry() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		// java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		assertEquals(12, map.higherEntry(11).getKey());
	}

	@Test
	void testEntrySet() {
		AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (int i = 0; i < arr.length; i++) {
			map.put(arr[i], Integer.toString(i));
		}
		assertEquals(
				"[<1, 7>, <2, 10>, <4, 5>, <5, 11>, <12, 6>, <15, 2>, <21, 9>, <23, 8>, <24, 3>, <26, 1>, <33, 4>, <35, 0>]",
				map.entrySet().toString());
	}

	@Test
	void testToString() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		// java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (int j = 0; j < arr.length; j++) {
			map.put(arr[j], Integer.toString(j));
		}
		assertEquals(
				"[(null), (<1, 7>), (null), (<2, 10>), (null), (<4, 5>), (null), (<5, 11>), (null), (<12, 6>), (null), (<15, 2>), (null), (<21, 9>), (null), (<23, 8>), (null), (<24, 3>), (null), (<26, 1>), (null), (<33, 4>), (null), (<35, 0>), (null)]",
				map.toString());

	}

	@Test
	void testSubMap() {
		SplayTreeMap<Integer, String> map = new SplayTreeMap<>();
		// java.util.TreeMap<Integer, String> map = new java.util.TreeMap<>();
		Integer[] arr = new Integer[] { 35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5 };

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}

		// assertEquals("[12, 15, 21, 23, 24, 26, 33]", map.subMap(12,
		// 34).keySet().toString());
		assertEquals("[<12, 12>, <15, 15>, <21, 21>, <23, 23>, <24, 24>, <26, 26>, <33, 33>]",
				map.subMap(12, 34).toString());
	}

}