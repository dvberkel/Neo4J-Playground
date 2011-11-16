package net.luminis.research.collatz.service.creation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.luminis.research.collatz.domain.CollatzDomain;

import org.junit.Test;

public class CollatzCreationServiceTest {
	@Test
	public void shouldExist() {
		assertNotNull(CollatzCreationService.class);
	}

	@Test
	public void shouldHaveACreateDomainMethod() {
		CollatzCreationService service = new CollatzCreationService() {
			@Override
			public CollatzDomain createDomain(Integer low, Integer high) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		assertNull(service.createDomain(1, 2));
	}
}
