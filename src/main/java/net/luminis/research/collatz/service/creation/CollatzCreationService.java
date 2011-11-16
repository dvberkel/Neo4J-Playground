package net.luminis.research.collatz.service.creation;

import net.luminis.research.collatz.domain.CollatzDomain;

public interface CollatzCreationService {

	CollatzDomain createDomain(Integer low, Integer high);

}
