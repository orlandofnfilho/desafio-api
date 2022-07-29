package br.com.gft.services;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.gft.entities.Breed;
import br.com.gft.repositories.BreedRepository;

@ExtendWith(MockitoExtension.class)
class BreedServiceTest {
	
	
	private static final long ID = 1L;
	private static final String CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION = "Check if throws BusinessRuleException";
	private static final String CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION = "Check if throws ResourceNotFoundException";
	private static final String CHECK_IF_PAGE_NOT_NULL_AND_CONTENT = "Check if page not null and content";
	private static final String CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES = "Check if not null, object class and attributes values";

	private Breed breed;
	private Optional<Breed> optionalBreed;
	private Page<Breed> page;
	private Pageable pageable;
	
	@Mock
	private BreedRepository breedRepository;
	
	@InjectMocks
	private BreedService breedService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
