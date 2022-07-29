package br.com.gft.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.gft.dto.user.UserRequestDTO;
import br.com.gft.dto.user.UserResponseDTO;
import br.com.gft.entities.Profile;
import br.com.gft.entities.User;
import br.com.gft.repositories.ProfileRepository;
import br.com.gft.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	
	private static final long ID = 2L;
	private static final String PASSWORD = "Gft@1234";
	private static final String EMAIL = "usuario@gft.com";
	private static final String USUARIO = "USUARIO";
	private static final String ADMIN = "ADMIN";
	private static final String CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY = "Check if not null, HttpStatus and response body";
	private static final int INDEX = 0;
	private User user;
	private User userAdmin;
	private UserRequestDTO requestDTO;
	private UserResponseDTO responseDTO;
	private Profile profile;
	private Profile profileUsuario;
	private Page<User> page;
	private Pageable pageable;
	
	@Mock
	private UserService userService;
	
	@Mock
	private ProfileRepository profileRepository;
	
	@InjectMocks
	private UserController userController;
	
	@BeforeEach
	void setUp() throws Exception {
		startUser();
	}

	@Test
	@DisplayName("Should return an UserResponseDTO")
	void whenCreateThenReturnCreated() throws URISyntaxException{
		when(userService.create(any())).thenReturn(user);		

		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request)); 
		
		 ResponseEntity<UserResponseDTO> response  = userController.create(requestDTO);
			
		    assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
		    		, () -> assertNotNull(response)
		    		, () -> assertEquals(ResponseEntity.class, response.getClass())
		    		, () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
		    		, () -> assertEquals(response.getBody(), responseDTO));	
	}
	
	@Test
	@DisplayName("Should return a page with list of UserResponseDTO")
	void whenFindAllThenReturnAPageOfUserResponseDTO() {
		when(userService.findAll(pageable)).thenReturn(page);
		ResponseEntity<Page<UserResponseDTO>> response = userController.findAll(pageable);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(UserResponseDTO.class, response.getBody().getContent().get(INDEX).getClass())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getId())
				, () -> assertEquals(EMAIL, response.getBody().getContent().get(INDEX).getEmail())
				, () -> assertEquals(USUARIO, response.getBody()
						.getContent().get(INDEX).getProfile()));
	}
	
	@Test
	@DisplayName("Should return a UserResponseDTO found by id")
	void whenFindByIdThenReturnSuccess() {
		when(userService.findById(ID)).thenReturn(user);
		ResponseEntity<UserResponseDTO> response = userController.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(EMAIL, response.getBody().getEmail())
				, () -> assertEquals(USUARIO, response.getBody().getProfile()));
	}
	
	@Test
	@DisplayName("Should delete an User then return noContent")
	void whenDeleteThenReturnNoContent() {
		doNothing().when(userService).delete(anyLong());
		
		ResponseEntity<UserResponseDTO> response = userController.delete(ID);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		verify(userService, times(1)).delete(anyLong());
	}
	
	@Test
	@DisplayName("Should update an User then return UserResponseDTO")
	void whenUpdateThenReturnSuccess() {
		when(userService.update(anyLong(), any(User.class))).thenReturn(user);
		ResponseEntity<UserResponseDTO> response = userController.update(ID, requestDTO);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(EMAIL, response.getBody().getEmail())
				, () -> assertEquals(USUARIO, response.getBody().getProfile()));
		
	}
	
	@Test
	@DisplayName("Should update an User profile then return UserResponseDTO")
	void whenChangeProfileThenReturnSuccess() {
		when(userService.changeProfile(anyLong(), anyLong())).thenReturn(userAdmin);
		ResponseEntity<UserResponseDTO> response = userController.changeProfile(ID, 1L);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(EMAIL, response.getBody().getEmail())
				, () -> assertEquals(ADMIN, response.getBody().getProfile()));
	}
	

	
	private void startUser() {
		profile = new Profile(1L, ADMIN);
		profileUsuario = new Profile(ID, USUARIO);
		user = new User(ID, EMAIL, PASSWORD, profileUsuario);
		userAdmin = new User(ID, EMAIL, PASSWORD, profile);
		requestDTO = new UserRequestDTO(EMAIL, PASSWORD);
		responseDTO = new UserResponseDTO(ID, EMAIL, profileUsuario.getRole());
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(user), pageable, 10);
	}

}
