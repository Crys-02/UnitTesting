package org.adaschool.Weather.service;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WeatherReportServiceTest {

    @InjectMocks
    private WeatherReportService weatherReportService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherReport() {
        double latitude = 37.8267;
        double longitude = -122.4233;
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(25.0);
        main.setHumidity(60);
        WeatherApiResponse mockResponse = new WeatherApiResponse();
        mockResponse.setMain(main);
        when(restTemplate.getForObject(anyString(), eq(WeatherApiResponse.class))).thenReturn(mockResponse);
        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);
        assertEquals(25.0, report.getTemperature());
        assertEquals(60, report.getHumidity());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(WeatherApiResponse.class));
    }

    @Test
    public void testGetWeatherReport_NullResponse() {
        double latitude = 37.8267;
        double longitude = -122.4233;
        when(restTemplate.getForObject(anyString(), eq(WeatherApiResponse.class))).thenReturn(null);
        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);
        assertEquals(0.0, report.getTemperature()); 
        assertEquals(0, report.getHumidity());    

        verify(restTemplate, times(1)).getForObject(anyString(), eq(WeatherApiResponse.class));
    }
}
