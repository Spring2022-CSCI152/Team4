package com.CSCI152.team4.server.Reports.Validator;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Reports.Classes.*;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ReportValidatorTest {

    @Mock
    private SettingsRepoInterface settingsRepoManager;

    @Mock
    private Report report;
    @Mock
    private ReportFormat reportFormat;

    @Mock
    private Profile profile;
    @Mock
    private CustomerProfileFormat profileFormat;

    ReportValidator underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ReportValidator(settingsRepoManager);
    }

    /*NOTE: This is not updated for changelog or attachments yet*/
    @Test
    void itShouldValidateReport() {
        // Given
        Integer businessId = 100;
        Timestamp date = Timestamp.valueOf(now());
        List<Profile> reportProfiles = List.of(profile);
        String time = "10:00am";
        String author = "Author";
        String type = "type";
        String box1 = "BOX1_STRING";
        String box2 = "BOX2_STRING";
        String box3 = "BOX3_STRING";
        String box4 = "BOX4_STRING";
        String box5 = "BOX5_STRING";

        /*Get Id to check format existence*/
        given(report.getBusinessId()).willReturn(businessId);
        /*run existence check*/
        given(settingsRepoManager.reportFormatExistsById(businessId)).willReturn(true);
        /* get format*/
        given(settingsRepoManager.getReportFormatIfExists(businessId)).willReturn(reportFormat);
        /*  run report Id check*/
        given(report.getReportIdString()).willReturn(null);
        /* set report id*/
        doNothing().when(report).setReportIdString(any());

        given(reportFormat.isProfiles()).willReturn(true);
        given(report.getProfiles()).willReturn(reportProfiles);
        doNothing().when(report).setProfiles(reportProfiles);

        given(reportFormat.isDate()).willReturn(true);
        given(report.getDate()).willReturn(date);
        doNothing().when(report).setDate(date);

        given(reportFormat.isTime()).willReturn(true);
        given(report.getTime()).willReturn(time);
        doNothing().when(report).setTime(time);

        given(reportFormat.isAuthor()).willReturn(true);
        given(report.getAuthor()).willReturn(author);
        doNothing().when(report).setAuthor(author);

        given(reportFormat.isType()).willReturn(true);
        given(report.getType()).willReturn(type);
        doNothing().when(report).setType(type);

        given(reportFormat.isBox1()).willReturn(true);
        given(report.getBox1()).willReturn(box1);
        doNothing().when(report).setBox1(box1);

        given(reportFormat.isBox2()).willReturn(true);
        given(report.getBox2()).willReturn(box2);
        doNothing().when(report).setBox2(box2);

        given(reportFormat.isBox3()).willReturn(true);
        given(report.getBox3()).willReturn(box3);
        doNothing().when(report).setBox3(box3);

        given(reportFormat.isBox4()).willReturn(true);
        given(report.getBox4()).willReturn(box4);
        doNothing().when(report).setBox4(box4);

        given(reportFormat.isBox5()).willReturn(true);
        given(report.getBox5()).willReturn(box5);
        doNothing().when(report).setBox5(box5);

        // When
        underTest.validateReport(report);
        // Then

        verify(report, times(2)).getBusinessId();
        verify(settingsRepoManager, times(1)).reportFormatExistsById(businessId);
        verify(settingsRepoManager, times(1)).getReportFormatIfExists(businessId);
        verify(report, times(1)).getReportIdString();
        verify(report, times(1)).setReportIdString(any());

        verify(reportFormat, times(1)).isProfiles();
        verify(report, times(1)).getProfiles();
        verify(report, times(1)).setProfiles(reportProfiles);

        verify(reportFormat, times(1)).isDate();
        verify(report, times(1)).getDate();
        verify(report, times(1)).setDate(date);

        verify(reportFormat, times(1)).isTime();
        verify(report, times(1)).getTime();
        verify(report, times(1)).setTime(time);

        verify(reportFormat, times(1)).isAuthor();
        verify(report, times(1)).getAuthor();
        verify(report, times(1)).setAuthor(author);


        verify(reportFormat, times(1)).isType();
        verify(report, times(1)).getType();
        verify(report, times(1)).setType(type);

        verify(reportFormat, times(1)).isBox1();
        verify(report, times(1)).getBox1();
        verify(report, times(1)).setBox1(box1);

        verify(reportFormat, times(1)).isBox2();
        verify(report, times(1)).getBox2();
        verify(report, times(1)).setBox2(box2);

        verify(reportFormat, times(1)).isBox3();
        verify(report, times(1)).getBox3();
        verify(report, times(1)).setBox3(box3);

        verify(reportFormat, times(1)).isBox4();
        verify(report, times(1)).getBox4();
        verify(report, times(1)).setBox4(box4);


        verify(reportFormat, times(1)).isBox5();
        verify(report, times(1)).getBox5();
        verify(report, times(1)).setBox5(box5);

        verifyNoMoreInteractions(settingsRepoManager, report, reportFormat);
    }

    @Test
    void itShouldValidateReportWithNull() {
        // Given
        Integer businessId = 100;
        String idString = "idString";

        /*Get Id to check format existence*/
        given(report.getBusinessId()).willReturn(businessId);
        /*run existence check*/
        given(settingsRepoManager.reportFormatExistsById(businessId)).willReturn(true);
        /* get format*/
        given(settingsRepoManager.getReportFormatIfExists(businessId)).willReturn(reportFormat);
        /*  run report Id check*/
        given(report.getReportIdString()).willReturn(idString);


        given(reportFormat.isProfiles()).willReturn(false);
        doNothing().when(report).setProfiles(null);

        given(reportFormat.isDate()).willReturn(false);
        doNothing().when(report).setDate(null);

        given(reportFormat.isTime()).willReturn(false);
        doNothing().when(report).setTime(null);

        given(reportFormat.isAuthor()).willReturn(false);
        doNothing().when(report).setAuthor(null);

        given(reportFormat.isType()).willReturn(false);
        doNothing().when(report).setType(null);

        given(reportFormat.isBox1()).willReturn(false);
        doNothing().when(report).setBox1(null);

        given(reportFormat.isBox2()).willReturn(false);
        doNothing().when(report).setBox2(null);

        given(reportFormat.isBox3()).willReturn(false);
        doNothing().when(report).setBox3(null);

        given(reportFormat.isBox4()).willReturn(false);
        doNothing().when(report).setBox4(null);

        given(reportFormat.isBox5()).willReturn(false);
        doNothing().when(report).setBox5(null);

        // When
        underTest.validateReport(report);
        // Then

        verify(report, times(2)).getBusinessId();
        verify(settingsRepoManager, times(1)).reportFormatExistsById(businessId);
        verify(settingsRepoManager, times(1)).getReportFormatIfExists(businessId);
        verify(report, times(1)).getReportIdString();

        verify(reportFormat, times(1)).isProfiles();
        verify(report, times(1)).setProfiles(null);

        verify(reportFormat, times(1)).isDate();
        verify(report, times(1)).setDate(null);

        verify(reportFormat, times(1)).isTime();
        verify(report, times(1)).setTime(null);

        verify(reportFormat, times(1)).isAuthor();
        verify(report, times(1)).setAuthor(null);


        verify(reportFormat, times(1)).isType();
        verify(report, times(1)).setType(null);

        verify(reportFormat, times(1)).isBox1();
        verify(report, times(1)).setBox1(null);

        verify(reportFormat, times(1)).isBox2();
        verify(report, times(1)).setBox2(null);

        verify(reportFormat, times(1)).isBox3();
        verify(report, times(1)).setBox3(null);

        verify(reportFormat, times(1)).isBox4();
        verify(report, times(1)).setBox4(null);


        verify(reportFormat, times(1)).isBox5();
        verify(report, times(1)).setBox5(null);

        verifyNoMoreInteractions(settingsRepoManager, report, reportFormat);
    }

    @Test
    void itShouldThrowOnBusinessNoFoundReportValidation(){

        //Given
        Integer businessId = 100;
        given(report.getBusinessId()).willReturn(businessId);
        given(settingsRepoManager.reportFormatExistsById(businessId)).willReturn(false);


        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.validateReport(report));

        //Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Business Not Found!");
    }

    /* Caught Wrong function call bug on 5-2-2022*/
    @Test
    void itShouldValidateProfile() {
        // Given
        Integer businessId = 100;
        String name = "name";
        String status = "status";
        Address address = new Address();
        BanDuration banDuration = new BanDuration();
        Attributes attributes = new Attributes();
        String imageName = "imageName";
        String involvement = "involvement";
        List<String> reports = List.of("Reports");

        given(profile.getBusinessId()).willReturn(businessId);
        given(settingsRepoManager.customerProfileFormatExistsById(businessId))
                .willReturn(true);

        given(profile.getProfileIdString()).willReturn(null);
        doNothing().when(profile).setProfileIdString(any());
        given(settingsRepoManager.getCustomerProfileFormatIfExists(businessId))
                .willReturn(profileFormat);

        given(profileFormat.isName()).willReturn(true);
        given(profile.getName()).willReturn(name);
        doNothing().when(profile).setName(name);

        given(profileFormat.isStatus()).willReturn(true);
        given(profile.getStatus()).willReturn(status);
        doNothing().when(profile).setStatus(status);

        given(profileFormat.isAddress()).willReturn(true);
        given(profile.getAddress()).willReturn(address);
        doNothing().when(profile).setAddress(address);

        given(profileFormat.isBanDuration()).willReturn(true);
        given(profile.getBanDuration()).willReturn(banDuration);
        doNothing().when(profile).setBanDuration(banDuration);

        given(profileFormat.isAttributes()).willReturn(true);
        given(profile.getAttributes()).willReturn(attributes);
        doNothing().when(profile).setAttributes(attributes);

        given(profileFormat.isImageName()).willReturn(true);
        given(profile.getImageName()).willReturn(imageName);
        doNothing().when(profile).setImageName(imageName);

        given(profileFormat.isInvolvement()).willReturn(true);
        given(profile.getInvolvement()).willReturn(involvement);
        doNothing().when(profile).setInvolvement(involvement);

        given(profileFormat.isReports()).willReturn(true);
        given(profile.getReports()).willReturn(reports);
        doNothing().when(profile).setReports(reports);

        // When
        underTest.validateProfile(profile);
        // Then

        verify(profile, times(2)).getBusinessId();
        verify(profile, times(1)).getProfileIdString();
        verify(profile, times(1)).setProfileIdString(any());
        verify(settingsRepoManager, times(1))
                .customerProfileFormatExistsById(businessId);
        verify(settingsRepoManager, times(1))
                .getCustomerProfileFormatIfExists(businessId);

        verify(profileFormat, times(1)).isName();
        verify(profile, times(1)).getName();
        verify(profile, times(1)).setName(name);

        verify(profileFormat, times(1)).isStatus();
        verify(profile, times(1)).getStatus();
        verify(profile, times(1)).setStatus(status);

        verify(profileFormat, times(1)).isAddress();
        verify(profile, times(1)).getAddress();
        verify(profile, times(1)).setAddress(address);

        verify(profileFormat, times(1)).isBanDuration();
        verify(profile, times(1)).getBanDuration();
        verify(profile, times(1)).setBanDuration(banDuration);

        verify(profileFormat, times(1)).isAttributes();
        verify(profile, times(1)).getAttributes();
        verify(profile, times(1)).setAttributes(attributes);

        verify(profileFormat, times(1)).isImageName();
        verify(profile, times(1)).getImageName();
        verify(profile, times(1)).setImageName(imageName);

        verify(profileFormat, times(1)).isInvolvement();
        verify(profile, times(1)).getInvolvement();
        verify(profile, times(1)).setInvolvement(involvement);

        verify(profileFormat, times(1)).isReports();
        verify(profile, times(1)).getReports();
        verify(profile, times(1)).setReports(reports);

        verifyNoMoreInteractions(profile, profileFormat, settingsRepoManager);

    }

    @Test
    void itShouldValidateProfileWNull() {
        // Given
        Integer businessId = 100;
        String idString = "idString";

        given(profile.getBusinessId()).willReturn(businessId);
        given(settingsRepoManager.customerProfileFormatExistsById(businessId))
                .willReturn(true);

        given(profile.getProfileIdString()).willReturn(idString);
        given(settingsRepoManager.getCustomerProfileFormatIfExists(businessId))
                .willReturn(profileFormat);

        given(profileFormat.isName()).willReturn(false);
        doNothing().when(profile).setName(null);

        given(profileFormat.isStatus()).willReturn(false);
        doNothing().when(profile).setStatus(null);

        given(profileFormat.isAddress()).willReturn(false);
        doNothing().when(profile).setAddress(null);

        given(profileFormat.isBanDuration()).willReturn(false);
        doNothing().when(profile).setBanDuration(null);

        given(profileFormat.isAttributes()).willReturn(false);
        doNothing().when(profile).setAttributes(null);

        given(profileFormat.isImageName()).willReturn(false);
        doNothing().when(profile).setImageName(null);

        given(profileFormat.isInvolvement()).willReturn(false);
        doNothing().when(profile).setInvolvement(null);

        given(profileFormat.isReports()).willReturn(false);
        doNothing().when(profile).setReports(null);

        // When
        underTest.validateProfile(profile);
        // Then

        verify(profile, times(2)).getBusinessId();
        verify(profile, times(1)).getProfileIdString();
        verify(settingsRepoManager, times(1))
                .customerProfileFormatExistsById(businessId);
        verify(settingsRepoManager, times(1))
                .getCustomerProfileFormatIfExists(businessId);

        verify(profileFormat, times(1)).isName();
        verify(profile, times(1)).setName(null);

        verify(profileFormat, times(1)).isStatus();
        verify(profile, times(1)).setStatus(null);

        verify(profileFormat, times(1)).isAddress();
        verify(profile, times(1)).setAddress(null);

        verify(profileFormat, times(1)).isBanDuration();
        verify(profile, times(1)).setBanDuration(null);

        verify(profileFormat, times(1)).isAttributes();
        verify(profile, times(1)).setAttributes(null);

        verify(profileFormat, times(1)).isImageName();
        verify(profile, times(1)).setImageName(null);

        verify(profileFormat, times(1)).isInvolvement();
        verify(profile, times(1)).setInvolvement(null);

        verify(profileFormat, times(1)).isReports();
        verify(profile, times(1)).setReports(null);

        verifyNoMoreInteractions(profile, profileFormat, settingsRepoManager);

    }

    @Test
    void itShouldThrowOnBusinessNoFoundProfileValidation(){

        //Given
        Integer businessId = 100;
        given(profile.getBusinessId()).willReturn(businessId);
        given(settingsRepoManager.reportFormatExistsById(businessId)).willReturn(false);


        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.validateProfile(profile));

        //Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Business Not Found!");
    }

    @Test
    void itShouldValidateProfiles() {
        // Given
        Integer businessId = 100;
        String name = "name";
        String status = "status";
        Address address = new Address();
        BanDuration banDuration = new BanDuration();
        Attributes attributes = new Attributes();
        String imageName = "imageName";
        String involvement = "involvement";
        List<String> reports = List.of("Reports");
        List<Profile> profileList = List.of(profile, profile, profile);
        Integer expectedRounds = profileList.size();

        given(profile.getBusinessId()).willReturn(businessId);
        given(settingsRepoManager.customerProfileFormatExistsById(businessId))
                .willReturn(true);

        given(profile.getProfileIdString()).willReturn(null);
        doNothing().when(profile).setProfileIdString(any());
        given(settingsRepoManager.getCustomerProfileFormatIfExists(businessId))
                .willReturn(profileFormat);

        given(profileFormat.isName()).willReturn(true);
        given(profile.getName()).willReturn(name);
        doNothing().when(profile).setName(name);

        given(profileFormat.isStatus()).willReturn(true);
        given(profile.getStatus()).willReturn(status);
        doNothing().when(profile).setStatus(status);

        given(profileFormat.isAddress()).willReturn(true);
        given(profile.getAddress()).willReturn(address);
        doNothing().when(profile).setAddress(address);

        given(profileFormat.isBanDuration()).willReturn(true);
        given(profile.getBanDuration()).willReturn(banDuration);
        doNothing().when(profile).setBanDuration(banDuration);

        given(profileFormat.isAttributes()).willReturn(true);
        given(profile.getAttributes()).willReturn(attributes);
        doNothing().when(profile).setAttributes(attributes);

        given(profileFormat.isImageName()).willReturn(true);
        given(profile.getImageName()).willReturn(imageName);
        doNothing().when(profile).setImageName(imageName);

        given(profileFormat.isInvolvement()).willReturn(true);
        given(profile.getInvolvement()).willReturn(involvement);
        doNothing().when(profile).setInvolvement(involvement);

        given(profileFormat.isReports()).willReturn(true);
        given(profile.getReports()).willReturn(reports);
        doNothing().when(profile).setReports(reports);

        // When
        underTest.validateProfiles(profileList);
        // Then

        verify(profile, times(2 * expectedRounds)).getBusinessId();
        verify(profile, times(expectedRounds)).getProfileIdString();
        verify(profile, times(expectedRounds)).setProfileIdString(any());
        verify(settingsRepoManager, times(expectedRounds))
                .customerProfileFormatExistsById(businessId);
        verify(settingsRepoManager, times(expectedRounds))
                .getCustomerProfileFormatIfExists(businessId);

        verify(profileFormat, times(expectedRounds)).isName();
        verify(profile, times(expectedRounds)).getName();
        verify(profile, times(expectedRounds)).setName(name);

        verify(profileFormat, times(expectedRounds)).isStatus();
        verify(profile, times(expectedRounds)).getStatus();
        verify(profile, times(expectedRounds)).setStatus(status);

        verify(profileFormat, times(expectedRounds)).isAddress();
        verify(profile, times(expectedRounds)).getAddress();
        verify(profile, times(expectedRounds)).setAddress(address);

        verify(profileFormat, times(expectedRounds)).isBanDuration();
        verify(profile, times(expectedRounds)).getBanDuration();
        verify(profile, times(expectedRounds)).setBanDuration(banDuration);

        verify(profileFormat, times(expectedRounds)).isAttributes();
        verify(profile, times(expectedRounds)).getAttributes();
        verify(profile, times(expectedRounds)).setAttributes(attributes);

        verify(profileFormat, times(expectedRounds)).isImageName();
        verify(profile, times(expectedRounds)).getImageName();
        verify(profile, times(expectedRounds)).setImageName(imageName);

        verify(profileFormat, times(expectedRounds)).isInvolvement();
        verify(profile, times(expectedRounds)).getInvolvement();
        verify(profile, times(expectedRounds)).setInvolvement(involvement);

        verify(profileFormat, times(expectedRounds)).isReports();
        verify(profile, times(expectedRounds)).getReports();
        verify(profile, times(expectedRounds)).setReports(reports);

        verifyNoMoreInteractions(profile, profileFormat, settingsRepoManager);
    }
}