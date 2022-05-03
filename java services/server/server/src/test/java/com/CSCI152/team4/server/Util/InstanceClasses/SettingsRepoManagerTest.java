package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Repos.CustomerProfileFormatRepo;
import com.CSCI152.team4.server.Repos.ReportFormatRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SettingsRepoManagerTest {

    @Mock
    private ReportFormatRepo reportFormats;
    @Mock
    private CustomerProfileFormatRepo profileFormats;

    @Mock
    private ReportFormat reportFormat;
    @Mock
    private CustomerProfileFormat profileFormat;

    private SettingsRepoManager underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new SettingsRepoManager(reportFormats, profileFormats);
    }

    @Test
    void itShouldSaveReportFormat() {
        // Given
        given(reportFormats.save(reportFormat)).willReturn(reportFormat);
        // When
        ReportFormat actual = underTest.saveReportFormat(reportFormat);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(reportFormat);
        verify(reportFormats, times(1)).save(reportFormat);
        verifyNoMoreInteractions(reportFormats);
    }

    @Test
    void itShouldSaveCustomerProfileFormat() {
        // Given
        given(profileFormats.save(profileFormat)).willReturn(profileFormat);
        // When
        CustomerProfileFormat actual = underTest.saveCustomerProfileFormat(profileFormat);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(profileFormat);
        verify(profileFormats, times(1)).save(profileFormat);
        verifyNoMoreInteractions(profileFormats);
    }

    @Test
    void itShouldReportFormatExistsById() {
        // Given
        Integer businessId = 100;
        given(reportFormats.existsById(businessId)).willReturn(true);
        // When
        // Then
        assertTrue(underTest.reportFormatExistsById(businessId));
        given(reportFormats.existsById(businessId)).willReturn(false);
        assertFalse(underTest.reportFormatExistsById(businessId));
    }

    @Test
    void itShouldCustomerProfileFormatExistsById() {
        // Given
        Integer businessId = 100;
        given(profileFormats.existsById(businessId)).willReturn(true);
        // When
        // Then
        assertTrue(underTest.customerProfileFormatExistsById(businessId));
        given(profileFormats.existsById(businessId)).willReturn(false);
        assertFalse(underTest.customerProfileFormatExistsById(businessId));
    }

    @Test
    void itShouldGetReportFormatIfExists() {
        // Given
        Integer businessId = 100;
        given(reportFormats.findById(businessId)).willReturn(Optional.of(reportFormat));
        // When
        ReportFormat actual = underTest.getReportFormatIfExists(businessId);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(reportFormat);
        verify(reportFormats, times(1)).findById(businessId);
        verifyNoMoreInteractions(reportFormats);
    }

    @Test
    void itShouldThrowOnReportFormatNotExists(){
        // Given
        Integer businessId = 100;
        given(reportFormats.findById(businessId)).willReturn(Optional.empty());
        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.getReportFormatIfExists(businessId));
        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Report Format Not Found!");
    }

    @Test
    void itShouldGetCustomerProfileFormatIfExists() {
        // Given
        Integer businessId = 100;
        given(profileFormats.findById(businessId)).willReturn(Optional.of(profileFormat));
        // When
        CustomerProfileFormat actual = underTest.getCustomerProfileFormatIfExists(businessId);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(profileFormat);
        verify(profileFormats, times(1)).findById(businessId);
        verifyNoMoreInteractions(profileFormats);
    }

    // 5-1-22: Found wrong error message bug
    @Test
    void itShouldThrowOnProfileFormatNotExists(){
        // Given
        Integer businessId = 100;
        given(profileFormats.findById(businessId)).willReturn(Optional.empty());
        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.getCustomerProfileFormatIfExists(businessId));
        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Profile Format Not Found!");
    }
}