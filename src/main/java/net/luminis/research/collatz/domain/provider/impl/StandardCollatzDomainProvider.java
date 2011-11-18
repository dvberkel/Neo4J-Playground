package net.luminis.research.collatz.domain.provider.impl;

import net.luminis.research.collatz.domain.CollatzDomain;
import net.luminis.research.collatz.domain.impl.StandardCollatzDomain;
import net.luminis.research.collatz.domain.provider.CollatzDomainProvider;

public class StandardCollatzDomainProvider implements CollatzDomainProvider {

	@Override
	public CollatzDomain getInstance() {
		return new StandardCollatzDomain();
	}

}
