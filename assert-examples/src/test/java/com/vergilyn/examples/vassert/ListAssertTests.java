package com.vergilyn.examples.vassert;

import java.util.List;

import com.google.common.collect.Lists;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListAssertTests {

	@Test
	public void contains(){
		// junit4
//		Assert.assertThat(userGroupMapPair.getCustomizedMap().keySet(),
//				hasItems(id(1), id(2)));

		List<Integer> expected = Lists.newArrayList(1000, 3000, 2000);
		List<Integer> actual = Lists.newArrayList(1000, 3000, 2000);
		List<Integer> expectedDiffOrder = Lists.newArrayList(1000, 2000, 3000);


		// junit5, order & equal-each-element
		Assertions.assertIterableEquals(expected, actual);

		// hamcrest
		MatcherAssert.assertThat(actual, IsIterableContainingInOrder.contains(expected.toArray()));
		MatcherAssert.assertThat(actual, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedDiffOrder.toArray()));

		// assertj
		org.assertj.core.api.Assertions.assertThat(actual)
				.containsExactlyElementsOf(expectedDiffOrder);  // the same order
		org.assertj.core.api.Assertions.assertThat(actual)
				.containsExactlyInAnyOrderElementsOf(expectedDiffOrder);
	}
}
