package com.example.boo.test;

import com.example.boo.test.collections.Profiles;
import com.example.boo.test.controller.ProfileController;
import com.example.boo.test.requests.CreateProfileRequest;
import com.example.boo.test.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BooWorldApplicationTests {

	@InjectMocks
	private ProfileController profileController;

	@Mock
	private ProfileService profileService;

	@Test
	public void testAddProfile()
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(profileService.createProfile(any(CreateProfileRequest.class))).thenReturn(true);

		CreateProfileRequest createProfileRequest =
				CreateProfileRequest.builder().name("Suyog")
						.id(1)
						.description("testing")
						.build();

		Profiles profiles = Profiles.builder().id("one").profileSeq(1L).name("Suyog").build();
		ResponseEntity<?> responseEntity = profileController.addProfile(createProfileRequest);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void testFindAll()
	{
		// given
		Profiles profile1 = Profiles.builder().id("one").profileSeq(1L).name("Suyog").build();
		Profiles profile2 = Profiles.builder().id("two").profileSeq(2L).name("Rohit").build();

		List<Profiles> list = Arrays.asList(profile1, profile2);

		when(profileService.getAllProfiles()).thenReturn(list);

		ResponseEntity<?> responseEntity = profileController.getAllProfiles();
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

		List<Profiles> result = profileService.getAllProfiles();

		assertThat(result.size()).isEqualTo(2);

		assertThat(result.get(0).getName())
				.isEqualTo(profile1.getName());

		assertThat(result.get(1).getName())
				.isEqualTo(profile2.getName());
	}


}
