package net.luminis.research.collatz.service.creation;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CollatzCreationServiceTest {
	@Test
	public void shouldExist() {
		assertNotNull(CollatzCreationService.class);
	}

	@Test
	public void shouldHaveACreateRangeMethod() {
		CollatzCreationService service = new CollatzCreationService() {
			@Override
			public List<Integer> createRange(Integer low, Integer high) {
				return null;
			}
		};

		assertNull(service.createRange(1, 2));
	}
}
