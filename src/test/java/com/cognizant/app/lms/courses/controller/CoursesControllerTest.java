package com.cognizant.app.lms.courses.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.app.lms.courses.dto.CourseDTO;
import com.cognizant.app.lms.courses.model.CourseRequestModel;
import com.cognizant.app.lms.courses.model.CourseResponseModel;
import com.cognizant.app.lms.courses.service.CoursesServiceInt;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@WebMvcTest(CoursesController.class)
class CoursesControllerTest {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MockMvc mockMvc;

	@MockBean
	ModelMapper modelMapper;
	
	@MockBean
	CoursesServiceInt coursesServiceInt;
	
	CourseRequestModel courseRequestModel;
	CourseDTO courseDto;
	CourseResponseModel courseResponseModel;
	
	@BeforeEach
	public void setup() {
		courseRequestModel = new CourseRequestModel("DMC-Digital Mobile Computing",
				"8",
				"Course includes advance technologies that are being used mainly for android and ios development, also includes the IOT hands-on training",
				"Android",
				"www.dmc.com");
		
		courseDto = new CourseDTO(
				"9999",
				"DMC-Digital Mobile Computing",
				"Course includes advance technologies that are being used mainly for android and ios development, also includes the IOT hands-on training",
				"8",
				"Android",
				"www.dmc.com");
		
		courseResponseModel = new CourseResponseModel(
				"9999",
				"DMC-Digital Mobile Computing",
				"Course includes advance technologies that are being used mainly for android and ios development, also includes the IOT hands-on training",
				"8",
				"Android",
				"www.dmc.com");
	}
	
	@Test
	public void testRegisterCourse() throws Exception{
		
		String registerCourseURI = "/course";
		
		String inputJson = mapToJson(courseRequestModel);
		

		when(modelMapper.map(any(),eq(CourseDTO.class))).thenReturn(courseDto);
		when(coursesServiceInt.registerCourse(any())).thenReturn(courseDto);
		when(modelMapper.map(any(),eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(registerCourseURI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputJson))
				.andReturn();
		
		assertEquals(201, mvcResult.getResponse().getStatus());
		
		CourseResponseModel responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel.class);
		
		assertNotNull(responseModel);
		assertEquals(courseResponseModel.getCourseId(), responseModel.getCourseId());
		assertEquals(courseResponseModel.getCourseName(), responseModel.getCourseName());
		assertEquals(courseResponseModel.getCourseDuration(), responseModel.getCourseDuration());
		assertEquals(courseResponseModel.getCourseDescription(), responseModel.getCourseDescription());
		assertEquals(courseResponseModel.getTechnology(), responseModel.getTechnology());
		assertEquals(courseResponseModel.getLaunchURL(), responseModel.getLaunchURL());
	}

	
	@Test
	public void testGetllCourses() throws Exception{
		
		String getAllCourseDURI = "/course";
		
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCourses()).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getAllCourseDURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		logger.info("Response:{}",mvcResult.getResponse().getContentAsString());
		
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();
		String outputJson = mvcResult.getResponse().getContentAsString();
		List<CourseResponseModel> responseModel = mapper.readValue(outputJson, typeFactory.constructCollectionType(List.class,CourseResponseModel.class ));
		
		assertNotNull(responseModel);
	}
	
	@Test
	public void testGetCourseByCourseId() throws Exception{
		
		String getCourseByCourseIDURI = "/course/9999";
				

		when(coursesServiceInt.getCourseById("9999")).thenReturn(courseDto);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseIDURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel.class);
		
		assertNotNull(responseModel);
		assertEquals(courseResponseModel.getCourseId(), responseModel.getCourseId());
		assertEquals(courseResponseModel.getCourseName(), responseModel.getCourseName());
		assertEquals(courseResponseModel.getCourseDuration(), responseModel.getCourseDuration());
		assertEquals(courseResponseModel.getCourseDescription(), responseModel.getCourseDescription());
		assertEquals(courseResponseModel.getTechnology(), responseModel.getTechnology());
		assertEquals(courseResponseModel.getLaunchURL(), responseModel.getLaunchURL());
	}

	
	@Test
	public void testUpdateCourse() throws Exception{
		
		String updateCourseURI = "/course/9999";
		
		courseRequestModel.setCourseName("DAC-Digital Advanced Computing");
		courseRequestModel.setCourseDuration("12");
		courseRequestModel.setTechnology("Java");
		courseRequestModel.setLaunchURL("www.dac.com");
		
		String inputJson = mapToJson(courseRequestModel);
		
		courseDto.setCourseName("DAC-Digital Advanced Computing");
		courseDto.setCourseDuration("12");
		courseDto.setTechnology("Java");
		courseDto.setLaunchURL("www.dac.com");
		
		courseResponseModel.setCourseName("DAC-Digital Advanced Computing");
		courseResponseModel.setCourseDuration("12");
		courseResponseModel.setTechnology("Java");
		courseResponseModel.setLaunchURL("www.dac.com");
		
		when(modelMapper.map(any(),eq(CourseDTO.class))).thenReturn(courseDto);
		when(coursesServiceInt.updateCourseById(any(),eq("9999"))).thenReturn(courseDto);
		when(modelMapper.map(any(),eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(updateCourseURI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputJson))
				.andReturn();
		
		assertEquals(202, mvcResult.getResponse().getStatus());
		
		CourseResponseModel responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel.class);
		
		assertNotNull(responseModel);
		assertEquals(courseResponseModel.getCourseId(), responseModel.getCourseId());
		assertEquals(courseResponseModel.getCourseName(), responseModel.getCourseName());
		assertEquals(courseResponseModel.getCourseDuration(), responseModel.getCourseDuration());
		assertEquals(courseResponseModel.getCourseDescription(), responseModel.getCourseDescription());
		assertEquals(courseResponseModel.getTechnology(), responseModel.getTechnology());
		assertEquals(courseResponseModel.getLaunchURL(), responseModel.getLaunchURL());
	}
	
	
	@Test
	public void testGetCourseByCourseName_success() throws Exception{
		
		String getCourseByCourseNameURI = "/course/name/dmc";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByName(eq("dmc"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseNameURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel[] responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel[].class);
		
		assertNotNull(responseModel);
	}
	
	@Test
	public void testGetCourseByCourseNameWrongURI_failure() throws Exception{
		
		String getCourseByCourseNameURI = "/course/abc/dmc";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByName(eq("dmc"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseNameURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(400, mvcResult.getResponse().getStatus());
		assertEquals("Wrong URI path - /course/abc", mvcResult.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testGetCourseByCourseNameWrongPrameter_failure() throws Exception{
		
		String getCourseByCourseNameURI = "/course/name/drd";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByName(eq("dmc"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseNameURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel[] responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(), CourseResponseModel[].class);
		assertEquals(0,responseModel.length);
	}
	
	
	@Test
	public void testGetCourseByCourseDescription_success() throws Exception{
		
		String getCourseByCourseDescriptionURI = "/course/description/technologies";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByDescription(eq("technologies"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseDescriptionURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel[] responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel[].class);
		
		assertNotNull(responseModel);
	}
	
	@Test
	public void testGetCourseByCourseDescriptionWrongURI_failure() throws Exception{
		
		String getCourseByCourseDescriptionURI = "/course/desc/technologies";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByDescription(eq("technologies"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseDescriptionURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(400, mvcResult.getResponse().getStatus());
		
		assertEquals("Wrong URI path - /course/desc", mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetCourseByCourseDescriptionWrongParameter_failure() throws Exception{
		
		String getCourseByCourseDescriptionURI = "/course/description/olop";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByDescription(eq("technologies"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseDescriptionURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel[] responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel[].class);
		
		assertEquals(0, responseModel.length);
	}
	
	@Test
	public void testGetCourseByCourseDuration_success() throws Exception{
		
		String getCourseByCourseDurationURI = "/course/duration/8";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByDuration(eq("8"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseDurationURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel[] responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel[].class);
		
		assertNotNull(responseModel);
	}
	
	
	@Test
	public void testGetCourseByCourseDurationWrongURI_failure() throws Exception{
		
		String getCourseByCourseDurationURI = "/course/dur/8";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByDuration(eq("8"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseDurationURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(400, mvcResult.getResponse().getStatus());
		assertEquals("Wrong URI path - /course/dur", mvcResult.getResponse().getContentAsString());
		
	}
	
	
	@Test
	public void testGetCourseByCourseDurationWrongParameter_failure() throws Exception{
		
		String getCourseByCourseDurationURI = "/course/duration/12";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByDuration(eq("8"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseDurationURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel[] responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel[].class);
		
		assertEquals(0, responseModel.length);
	}
	
	@Test
	public void testGetCourseByCourseTechnology_success() throws Exception{
		
		String getCourseByCourseNameURI = "/course/technology/Android";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByTechnology(eq("Android"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseNameURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel[] responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel[].class);
		
		assertNotNull(responseModel);
	}
	
	@Test
	public void testGetCourseByCourseTechnologyWrongURI_failure() throws Exception{
		
		String getCourseByCourseNameURI = "/course/tech/Android";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByTechnology(eq("Android"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseNameURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(400, mvcResult.getResponse().getStatus());
		
		assertEquals("Wrong URI path - /course/tech", mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetCourseByCourseTechnologyWrongParameter_failure() throws Exception{
		
		String getCourseByCourseNameURI = "/course/technology/java";
				
		List<CourseDTO> allDtos = new ArrayList<>();
		allDtos.add(courseDto);
		allDtos.add(courseDto);
		
		when(coursesServiceInt.getAllCoursesByTechnology(eq("Android"))).thenReturn(allDtos);
		when(modelMapper.map(any(), eq(CourseResponseModel.class))).thenReturn(courseResponseModel);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getCourseByCourseNameURI)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		CourseResponseModel[] responseModel = mapFromJson(mvcResult.getResponse().getContentAsString(),CourseResponseModel[].class);
		
		assertEquals(0,responseModel.length);
	}
	
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
