package br.com.gft.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.gft.dto.user.UserMapper;
import br.com.gft.entities.Profile;
import br.com.gft.entities.User;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.ProfileRepository;
import br.com.gft.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private static final String CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION = "Check if throws BusinessRuleException";
	private static final String CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION = "Check if throws ResourceNotFoundException";
	private static final String CHECK_IF_PAGE_NOT_NULL_AND_CONTENT = "Check if page not null and content";
	private static final String PASSWORD = "Gft@1234";
	private static final String EMAIL = "usuario@gft.com";
	private static final String USUARIO = "USUARIO";
	private static final String ADMIN = "ADMIN";
	private static final long ID = 1L;
	private static final String CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES = "Check if not null, object class and attributes values";
	
	private User user;
	private Optional<User> optionalUser;
	private Profile profile;
	private Profile profileAdmin;
	private Page<User> page;
	private Pageable pageable;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ProfileRepository profileRepository;

	@Mock
	private UserMapper mapper;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() throws Exception {
		startUser();
	}
	
	@Test
	@DisplayName("Should create an new User")
	void whenCreateReturnSuccess() {
		when(userRepository.save(any())).thenReturn(user);
		when(profileRepository.findById(2L)).thenReturn(Optional.of(profile));
		
		User response = userService.create(user);
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(User.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(EMAIL, response.getEmail())
				, () -> assertEquals(USUARIO, response.getProfile().getRole()));
	}
	
	@Test
	@DisplayName("Should throws BusinessRuleException when validEmail")
	void whenValidEmailReturnBusinessRuleException() {
		when(userRepository.findByEmail(anyString())).thenThrow(BusinessRuleException.class);
		
		assertThrows(BusinessRuleException.class
				, () -> userService.validEmail(EMAIL)
				, CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
		
	}
	
	@Test
	@DisplayName("Should throws UsernameNotFoundException when findByEmail")
	void whenFindByEmailThenReturnUsernameNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenThrow(UsernameNotFoundException.class);
		
		assertThrows(UsernameNotFoundException.class
				, () -> userService.findByEmail(EMAIL));
	}

	@Test
	@DisplayName("Should return an User found by email")
	void whenFindByEmailReturnSuccess() {
		when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

		User response = userService.findByEmail(EMAIL);
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(User.class, response.getClass())
				, () -> assertEquals(EMAIL, response.getEmail()));
	}
	
	@Test
	@DisplayName("Should return an User found by id")
	void whenFindByIdReturnSuccess() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		
		User response = userService.findById(ID);
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(User.class, response.getClass())
				, () -> assertEquals(EMAIL, response.getEmail()));
	}
	
	@Test
	@DisplayName("Should throws a ResourceNotFoundException when findById")
	void whenFindByIdReturnResourceNotFoundException() {
		when(userRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> userService.findById(ID)
				, CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should return a Page with list of User")
	void whenFindAllReturnAPageWithListOfUser() {
		when(userRepository.findAll(pageable)).thenReturn(page);
		Page<User> response = userService.findAll(pageable);
		
		assertAll(CHECK_IF_PAGE_NOT_NULL_AND_CONTENT
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(user), response.getContent())
				, () -> assertEquals(10, response.getSize())
				, () -> assertEquals(10, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages()));
	}
	
	@Test
	@DisplayName("Should not throw an exception when validEmail")
	void whenValidEmailThenReturnSuccess() {
		userService.validEmail(EMAIL);
		verify(userRepository, times(1)).findByEmail(anyString());
	}
	
	@Test
	@DisplayName("Should delete a User")
	void whenDeleteThenReturnSuccess() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		doNothing().when(userRepository).delete(user);
		userService.delete(ID);
		verify(userRepository, times(1)).delete(user);
	}
	
	@Test
	@DisplayName("Should throws a ResourceNotFoundException when delete User")
	void whenDeleteThenReturnAResourceNotFoundException() {
		when(userRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> userService.delete(ID)
				,  CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should not throw an exception when validUpdate")
	void whenValidUpdateReturnSuccess() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		userService.validUpdate(ID, user);
		verify(userRepository, times(1)).findByEmail(anyString());
		verify(userRepository, times(1)).findById(anyLong());
	}
	
	
	@Test
	@DisplayName("Should throws BusinessRuleException when validUpdate")
	void whenValidUpdateThenReturnBussinessRuleException() {
		when(userRepository.findByEmail(anyString())).thenThrow(BusinessRuleException.class);
		assertThrows(BusinessRuleException.class
				, () -> userService.validUpdate(ID, user)
				,  CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when validUpdate")
	void whenValidUpdateThenReturnResourceNotFoundException() {
		when(userRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		assertThrows(ResourceNotFoundException.class
				, () -> userService.validUpdate(ID, user)
				,  CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should update an User")
	void whenUpdateThenReturnSuccess() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		when(userRepository.save(any())).thenReturn(user);
		User response = userService.update(ID, user);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(User.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(EMAIL, response.getEmail()));
		
	}
	
	@Test
	@DisplayName("Should change profile")
	void whenChangeProfileThenReturnSuccess() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profileAdmin));
		when(userRepository.save(any())).thenReturn(user);
		
		User response = userService.changeProfile(ID, ID);
				
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(User.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(ADMIN, response.getProfile().getRole()));
		
		
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when changeProfile")
	void whenChangeProfileThenThrowsResourceNotFoundException() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		when(profileRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> userService.changeProfile(ID, ID)
				,  CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
		
	}
	
	private void startUser() {
		profile = new Profile(2L, USUARIO);
		profileAdmin = new Profile(ID, ADMIN);
		user = new User(ID, EMAIL, PASSWORD, profile);
		optionalUser = Optional.of(user);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(user), pageable, 10);
	}

}
